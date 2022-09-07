package application.ai;

import java.util.ArrayList;

import application.ai.inputfacts.ClosestCoin;
import application.ai.inputfacts.Coin;
import application.ai.inputfacts.MinDistances;
import application.ai.inputfacts.inPath;
import application.ai.outputfacts.Next;
import application.ai.outputfacts.NextCell;
import application.ai.outputfacts.NextCellS;
import application.ai.outputfacts.NextS;
import application.model.ChangeIdentityManager;
import application.model.Coordinates;
import application.model.Game;
import application.model.GemManager;
import application.model.LockPowerfulTimer;
import application.view.CustomWindow;
import it.unical.mat.embasp.base.Handler;
import it.unical.mat.embasp.base.InputProgram;
import it.unical.mat.embasp.base.OptionDescriptor;
import it.unical.mat.embasp.base.Output;
import it.unical.mat.embasp.languages.asp.ASPInputProgram;
import it.unical.mat.embasp.languages.asp.ASPMapper;
import it.unical.mat.embasp.languages.asp.AnswerSet;
import it.unical.mat.embasp.languages.asp.AnswerSets;
import it.unical.mat.embasp.platforms.desktop.DesktopHandler;
import it.unical.mat.embasp.specializations.dlv2.desktop.DLV2DesktopService;

public class UniqueBrain {
	private static int MAX_LOOP = 7;
	
	private String encodingPath = "src/main/java/application/lib/encodings/progdlv_total.txt";
    private Handler handler;
    private Game game;
    private InputProgram program;
    private int counter,counterLoop;
    private boolean finished;

    private int n;
    
    public UniqueBrain(Game game) {
    	this.game = game;
    	this.handler = new DesktopHandler(new DLV2DesktopService("src/main/java/application/lib/dlv2.exe"));
    	this.program = new ASPInputProgram();
    	this.counter = 0;
    	this.finished = false;
    	
    	this.counterLoop = 0;
    
    	try {
    		//INPUT FACTS
			ASPMapper.getInstance().registerClass(ClosestCoin.class);
			ASPMapper.getInstance().registerClass(Coin.class);
			ASPMapper.getInstance().registerClass(inPath.class);
			ASPMapper.getInstance().registerClass(MinDistances.class);
			//OUTPUT FACTS
			ASPMapper.getInstance().registerClass(Next.class);
			ASPMapper.getInstance().registerClass(NextCell.class);
			ASPMapper.getInstance().registerClass(NextS.class);
			ASPMapper.getInstance().registerClass(NextCellS.class);
			
		} catch (Exception e) {
			System.out.println("Ciao2");
			e.printStackTrace();
		
		}
    	OptionDescriptor optionDescriptor = new OptionDescriptor("-n 10 --no-facts");
    	//OptionDescriptor optionDescriptor2 = new OptionDescriptor("--printonlyoptimum");
        handler.addOption(optionDescriptor);
        //handler.addOption(optionDescriptor2);
        this.program.addFilesPath(encodingPath);
        this.n=0;
	}

    public void exec() {
    	this.program.clearPrograms();
    	initializeFactsFromGame();
    	OptionDescriptor optionDescriptor = new OptionDescriptor("-n 10 --no-facts");
    	//OptionDescriptor optionDescriptor2 = new OptionDescriptor("--printonlyoptimum");
        handler.addOption(optionDescriptor);
        //handler.addOption(optionDescriptor2);
    	
    	
	    Output o = handler.startSync();
	    
	    AnswerSets answers = (AnswerSets) o;
	    System.out.println(answers.getAnswerSetsString());
	   
	   
	    //System.out.println("OPTIMUM: " +answers.getOptimalAnswerSets().get(0).toString());
	   if(answers.getOptimalAnswerSets().size()!=0) {
			try {

				System.out.println("ITERAZIONE NUMERO : "  + ++n);
				
				//QUESTO SFORA, MA NON CAPIAMO PERCHE'
				if(answers.getOptimalAnswerSets().get(0)==null)
					System.out.println("CIAOCIAO");
				AnswerSet a = answers.getOptimalAnswerSets().get(0);
				if(a==null)
					System.out.println("ERROREEEE");
				
				
				
				for(Object obj:a.getAtoms()){
					//LOCK
					if((obj instanceof Next)) {
						Next next = (Next) obj;
						System.out.println("DIREZIONE: " + next.getDirection());
						//Stiamo settando la previousAction a Lock 
						game.getLock().setPreviousAction(next.getDirection());
					}
					
					if((obj instanceof NextCell)) {
						NextCell nextCell = (NextCell) obj;
						System.out.println("PROSSIMA CELLA: " + nextCell.getX() + " " + nextCell.getY());
						
						//Ci prendiamo oldPosition perché dobbiamo settare T, valore per noi che indica il vuoto.
						Coordinates oldPosition = game.getLock().getPosition();
						game.getMappa()[oldPosition.getX()][oldPosition.getY()] = 'T';
						
						//Dopo aver fatto l'operazione di inserimento 'T', dobbiamo settare la nuova posizione a Lock
							//Se nella nuova posizione, si trova una C, incrementiamo il nostro score di 2.
							//Se becchiamo la G, quindi siamo su una Gemma, incrementiamo il nostro score della gem.getValue()
								//Se becchiamo la K : ChangeIdentity, dobbiamo fare altre operazioni:
									//Far partire un Timer, che allo scadere, toglie i poteri a Lock
						//Infine, sulla matrice dobbiamo metterlo nella posizione giusta e inserire L.
						
						if(game.getMappa()[nextCell.getX()][nextCell.getY()] =='C'){
							game.setMoneteRaccolte(game.getMoneteRaccolte()+1);
							if(game.getMoneteRaccolte() == game.getMoneteTotali())
								game.gameFinished(4);
							for(int i=0;i<game.getCoins().size();i++) {
								if(game.getCoins().get(i).getXP() == nextCell.getX() && game.getCoins().get(i).getYP() == nextCell.getY()) {
									game.getCoins().remove(i);
								}
							}
						
							System.out.println(game.getCoins().size());
							game.getLock().setActualScore(game.getLock().getActualScore() + 2);
							
						}else if(game.getMappa()[nextCell.getX()][nextCell.getY()] == 'G'){
							game.getLock().setActualScore(game.getLock().getActualScore() + game.getGem().getValue());
						}else if(game.getMappa()[nextCell.getX()][nextCell.getY()] == 'K'){
							
							game.getLock().setBlueHat();
							//Setta powerful
							LockPowerfulTimer timer = new LockPowerfulTimer(game);
							Thread t = new Thread(timer);
							t.start();
							
						
							//LockPowerfulTimer t setta powerful a False
						}
						if(game.getMappa()[nextCell.getX()][nextCell.getY()] == 'T') {
							System.out.println("Sto incrementando counterLoop");
							this.counterLoop+=1;

						}
						if(game.getMappa()[nextCell.getX()][nextCell.getY()] != 'W' && game.getMappa()[nextCell.getX()][nextCell.getY()] != '0' && game.getMappa()[nextCell.getX()][nextCell.getY()] != '1' && game.getMappa()[nextCell.getX()][nextCell.getY()] != '2' && game.getMappa()[nextCell.getX()][nextCell.getY()] != '3' ) {
							
							System.out.println("Sto settando a Lock : " + nextCell.getX() + " " + nextCell.getY());
							game.getLock().setPosition(new Coordinates(nextCell.getX(),nextCell.getY()));
							game.getMappa()[nextCell.getX()][nextCell.getY()] = 'L';
							game.ricalculateMinDistancesandClosestCoin();
							
						}
						
						//Controlli su quando andiamo sulla cella dei poliziotti.
							//Se siamo powerful, lo sorpassa
						
					}
					
					//Scartiamo tutto ciò che non è un oggetto della classe Next o NextCell
					//if(!(obj instanceof NextS) || !(obj instanceof NextCellS))continue;
					
					//Policeman
					//SE LOCK NON E' POWERFUL CI MUOVIAMO
					if(!game.getLock().getPowerful()) {
						 
						 
					
					if(obj instanceof NextS){
						NextS next = (NextS) obj;
						
						game.getMappaPoliziotti().get(next.getPolicemanName()).setPreviousAction(next.getDirection());
					}
					
					if(obj instanceof NextCellS){
						NextCellS nextCell = (NextCellS) obj;
						
						//Quando un poliziotto si muove, lascia il vecchio valore della cella, non come Lock che lascia sicuro T.
							//Dentro poliziotto abbiamo oldValue che, all'istante 0 è settato 'T'. 
							//Perché quando lascia la prima cella, lascia al suo interno T, nelle prossime invece, lascerà quello che trova
							//Perché un poliziotto NON COGLIE MONETE.
						
						char oldValue = game.getMappaPoliziotti().get(nextCell.getNome()).getOldValue();
						game.getMappa()[game.getMappaPoliziotti().get(nextCell.getNome()).getPosition().getX()][game.getMappaPoliziotti().get(nextCell.getNome()).getPosition().getY()] = oldValue;
						//System.out.println("OLD VALUE: " + oldValue);
						
						if(game.getMappa()[nextCell.getX()][nextCell.getY()] == 'L')
						{
							
							game.getLock().decrementaTentativi();
							if(game.getLock().getTentativiRimasti() == 0) {
								game.gameFinished(3);
								return;
							}
							CustomWindow w = new CustomWindow("RESTART",500,500);
							w.showtoast();
							game.getMappa()[game.getLock().getPosition().getX()][game.getLock().getPosition().getY()] = 'T';
							
							//Devo settare la posizione iniziale di Lock, quella della prima iterazione, dove lo posizioniamo noi sulla mappa.
							game.getLock().setPosition(game.getCoordinatesIniziali().get(5));
							game.getMappa()[game.getCoordinatesIniziali().get(5).getX()][game.getCoordinatesIniziali().get(5).getY()] = 'L';
							
							//Passiamo ai poliziotti
							for(int i=0;i<4;i++) {
								game.getMappaPoliziotti().get(i).setOldValue('T');
								game.getMappa()[game.getMappaPoliziotti().get(i).getPosition().getX()][game.getMappaPoliziotti().get(i).getPosition().getY()] = 'T';
								game.getMappaPoliziotti().get(i).setPosition(game.getCoordinatesIniziali().get(i));
								game.getMappaPoliziotti().get(i).setPreviousAction(3);
								game.getMappa()[game.getCoordinatesIniziali().get(i).getX()][game.getCoordinatesIniziali().get(i).getY()] = Character.forDigit(i, 10);
							}
							handler.removeAll();
							this.counter+=1;
							//game.ricalculateMinDistancesandClosestCoin();
							game.ricalculateMinDistancesandClosestCoin();
							return;
						}
						if(game.getMappa()[nextCell.getX()][nextCell.getY()] != '0' && game.getMappa()[nextCell.getX()][nextCell.getY()] !='1'&& game.getMappa()[nextCell.getX()][nextCell.getY()] != '2' && game.getMappa()[nextCell.getX()][nextCell.getY()] != '3') {
							
						
						//Prima di settarlo alla nuova cella, dobbiamo settarci l'oldValue prima che ci sovrascriviamo sopra.
						
							game.getMappaPoliziotti().get(nextCell.getNome()).setOldValue(game.getMappa()[nextCell.getX()][nextCell.getY()]);
						char oldValue2 = game.getMappaPoliziotti().get(nextCell.getNome()).getOldValue();
						//System.out.println("NUOVO OLD VALUE: " + oldValue2);
						//Ora ci possiamo sovrascrivere di sopra
						//System.out.println("STO SETTANDO SU " + nextCell.getX() + " " + nextCell.getY() + " " + nextCell.getNome());
						game.getMappaPoliziotti().get(nextCell.getNome()).setPosition(new Coordinates(nextCell.getX(),nextCell.getY()));
						game.getMappa()[nextCell.getX()][nextCell.getY()] = Character.forDigit(nextCell.getNome(), 10);
						game.ricalculateMinDistancesFromPmPOV(nextCell.getNome());
						}
						
					}
					
				}
					
				}
				
			} catch (Exception e) {
				System.out.println("ERRORE");
				handler.removeAll();
				e.printStackTrace();
			} 
	   }
	    System.out.println("HO FINITO");
	    handler.removeAll();
	    this.counter+=1;
    }
    
    private void initializeFactsFromGame() {
    	ArrayList<inPath> percorso = game.getPercorso();
    	ArrayList<Coin> coins = game.getCoins();
    	ArrayList<MinDistances> minDistancesFromPOVLock = game.getLock().getMinDistances();
    	ArrayList<ClosestCoin> cc = game.getLock().getClosestCoin();
    	ArrayList<MinDistances> minClosestCoin = game.getLock().getMinDistancesClosestCoin();
    	
    	for(int i=0;i<4;i++) {
    		ArrayList<MinDistances> temp = game.getMappaPoliziotti().get(i).getMinDistances();
    		for(MinDistances it: temp) {
        		try {
    				this.program.addObjectInput(it);
    			} catch (Exception e) {
    				// TODO Auto-generated catch block
    				e.printStackTrace();
    			}
        	}
    	}
    	for(MinDistances it: minDistancesFromPOVLock) {
    		try {
				this.program.addObjectInput(it);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
    	}
    	for(MinDistances it:minClosestCoin) {
    		try {
				this.program.addObjectInput(it);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
    	}
    	
    	for(Coin it: coins) {
    		try {
				this.program.addObjectInput(it);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
    	}
    	for(ClosestCoin it: cc) {
    		try {
				this.program.addObjectInput(it);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
    	}
    	for(inPath it: percorso) {
    		try {
				this.program.addObjectInput(it);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
    	}
    	
    	String myFacts = "";
    	StringBuilder stringa = new StringBuilder();
    	stringa.append("lock("+ game.getLock().getPosition().getX()+","+ game.getLock().getPosition().getY()+"). ");
    	
    	for(int i = 0; i<4; i++) {
    		if(game.getMappaPoliziotti().get(i).getPosition().getX() == game.getLock().getPosition().getX() && game.getMappaPoliziotti().get(i).getPosition().getY() == game.getLock().getPosition().getY())
    			game.gameFinished(3);
    		stringa.append("policeman("+ game.getMappaPoliziotti().get(i).getPosition().getX()+","+ game.getMappaPoliziotti().get(i).getPosition().getY()+","+i+"). ");
    		stringa.append("previousAction("+ game.getMappaPoliziotti().get(i).getType()+","+ game.getMappaPoliziotti().get(i).getPreviousAction()+"). ");
    		
    	}
    	
    	stringa.append("previous_action("+game.getLock().getPreviousAction()+"). ");
    	 
	        if(counter>5) {
	        	GemManager gm = GemManager.getInstance();
	        	gm.tryToPutGemInMap();
	        	
	        }
	        if(game.getGem()!=null) {
	        	counter = 0;
	        	stringa.append("gem("+game.getGem().getX()+","+game.getGem().getY()+"). ");
	        	
	        }
	        boolean chiamatodaTrue = false;
	        if(counter>8) {
	        	
	        	ChangeIdentityManager gm = ChangeIdentityManager.getInstance();
	        	if(this.counterLoop >= MAX_LOOP) {
					
					gm.tryToPutChangeIdentityInMap(true);
					chiamatodaTrue = true;
					
					System.out.println("STO PROVANDO A METTERE IL CAPPELLO");
					
				}else {
	        chiamatodaTrue = false;
	        gm.tryToPutChangeIdentityInMap(false);
	        	
				}
	        }
	        if(game.getCiB() != null) {
	        	counter =0;
	        	if(chiamatodaTrue)
	        		this.counterLoop =0;
	        	stringa.append("changeidentity("+game.getCiB().getX()+","+game.getCiB().getY()+"). ");
	        }
	        if(game.getLock().getPowerful()) {
	        	System.out.println("STo passando powerup");
	        	stringa.append("powerup.");
	        }
	        myFacts = stringa.toString();
	        program.addProgram(myFacts);
	        System.out.println("FACT: " + program.getPrograms());
	        System.out.println("Lunghezza fatti:" + program.getPrograms().length());
	       
	        this.handler.addProgram(this.program);
    }
}
