
package application.ai;

import java.util.ArrayList;

import application.ai.inputfacts.ClosestCoin;
import application.ai.inputfacts.Coin;
import application.ai.inputfacts.MinDistances;
import application.ai.inputfacts.inPath;
import application.ai.outputfacts.Next;
import application.ai.outputfacts.NextCell;
import application.model.ChangeIdentityManager;
import application.model.Coordinates;
import application.model.Game;
import application.model.GemManager;
import application.model.LockPowerfulTimer;
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

public class AILockBrain {
	private String encodingPath = "src/main/java/application/lib/encodings/progdlv2.txt";
    private Handler handler;
    private Game game;
    private InputProgram program;
    private int counter = 0;
    public AILockBrain(Game game) {
    	this.game = game;
    	this.handler = new DesktopHandler(new DLV2DesktopService("src/main/java/application/lib/dlv2.exe"));
    	this.program = new ASPInputProgram();
    	try {
    		//INPUT FACTS
			ASPMapper.getInstance().registerClass(ClosestCoin.class);
			ASPMapper.getInstance().registerClass(Coin.class);
			ASPMapper.getInstance().registerClass(inPath.class);
			ASPMapper.getInstance().registerClass(MinDistances.class);
			//OUTPUT FACTS
			ASPMapper.getInstance().registerClass(Next.class);
			ASPMapper.getInstance().registerClass(NextCell.class);
			
		} catch (Exception e) {
			System.out.println("Ciao2");
			e.printStackTrace();
		
		}
    	OptionDescriptor optionDescriptor = new OptionDescriptor("-n 0 --no-facts");
    	OptionDescriptor optionDescriptor2 = new OptionDescriptor("--printonlyoptimum");
        handler.addOption(optionDescriptor);
        handler.addOption(optionDescriptor2);
        this.program.addFilesPath(encodingPath);
		
	}
    
    public void exec() {
    	this.program.clearPrograms();
    	initializeFactsFromGame();
    	OptionDescriptor optionDescriptor = new OptionDescriptor("-n 0 --no-facts");
    	OptionDescriptor optionDescriptor2 = new OptionDescriptor("--printonlyoptimum");
        handler.addOption(optionDescriptor);
      
    	 
    	Output o = handler.startSync();
    	AnswerSets answers = (AnswerSets) o;
    	System.out.println(answers.getAnswerSetsString());
    	//System.out.println(answers.getOptimalAnswerSets().get(0).toString());
    	//System.out.println(answers.getAnswersets().get(0).toString());
    	//AnswerSet a = answers.getAnswersets().get(0);
    	//System.out.println(a.toString());
    	
    	if(answers.getOptimalAnswerSets().size() != 0) {
    		
			try {
				for(AnswerSet a : answers.getAnswersets()) {
					System.out.println("NON OTTIMO:");
					System.out.println(a.toString());
				}
				for(AnswerSet a : answers.getOptimalAnswerSets()) {
					System.out.println("OTTIMO:");
					System.out.println(a.toString());
				}
				AnswerSet a = answers.getOptimalAnswerSets().get(0);
				for(Object obj :a.getAtoms()){
					System.out.println("STO ITERANDO");
					//Scartiamo tutto ciò che non è un oggetto della classe Next o NextCell
					//if(!(obj instanceof Next) || !(obj instanceof NextCell)) continue;
					
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
								game.gameFinished(1);
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
						if(game.getMappa()[nextCell.getX()][nextCell.getY()] != 'W') {
							System.out.println("Sto settando a Lock : " + nextCell.getX() + " " + nextCell.getY());
							game.getLock().setPosition(new Coordinates(nextCell.getX(),nextCell.getY()));
							game.getMappa()[nextCell.getX()][nextCell.getY()] = 'L';
							game.ricalculateMinDistancesandClosestCoin();
						}
					}
				}
				System.out.println("HEY");
			} catch (Exception e) {
				e.printStackTrace();
			} 
			}
			handler.removeAll();
			this.counter+=1;
		
    }
    
    
    
    private void initializeFactsFromGame() {
    	
    	 ArrayList<inPath> percorso = game.getPercorso();
         ArrayList<Coin> coins = game.getCoins();
         ArrayList<MinDistances> minDistances = game.getLock().getMinDistances();
         ArrayList<ClosestCoin> closestCoins = game.getLock().getClosestCoin();
         
         for(inPath it : percorso) {
				try {
					this.program.addObjectInput(it);
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			for(Coin c : coins) {
				try {
					this.program.addObjectInput(c);
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			
			//System.out.println(coins.size());
			for(MinDistances m : minDistances) {
				try {
					this.program.addObjectInput(m);
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			for(ClosestCoin cc : closestCoins) {
				try {
					this.program.addObjectInput(cc);
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			
			String myFacts = "";
	        StringBuilder g = new StringBuilder();
	        System.out.println("lock("+game.getLock().getPosition().getX()+","+game.getLock().getPosition().getY()+"). ");
	        g.append("lock("+game.getLock().getPosition().getX()+","+game.getLock().getPosition().getY()+"). ");
	        
	        for(int i=0;i<4;i++) {
	        	g.append("policeman("+game.getMappaPoliziotti().get(i).getPosition().getX()+","+game.getMappaPoliziotti().get(i).getPosition().getY()+","+game.getMappaPoliziotti().get(i).getType()+"). ");
	        }
	        g.append("previous_action("+game.getLock().getPreviousAction()+"). ");
	        GemManager gm = GemManager.getInstance();
	        if(counter>10)
	        if(gm.tryToPutGemInMap()) {
	        	g.append("gem("+game.getGem().getX()+","+game.getGem().getY()+"). ");
	        }
	       /* ChangeIdentityManager gm2 = ChangeIdentityManager.getInstance();
	        gm2.tryToPutChangeIdentityInMap()*/
	        if(game.getLock().getPowerful())
	        	g.append("powerup.");
	        myFacts = g.toString();
	        program.addProgram(myFacts);
	        System.out.println("FACT: " + program.getPrograms());
	        
	       
	        this.handler.addProgram(this.program);
    }
    
    
}
