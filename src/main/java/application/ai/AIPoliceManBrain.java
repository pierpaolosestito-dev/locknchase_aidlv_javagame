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
import application.model.Coordinates;
import application.model.Game;
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

public class AIPoliceManBrain {
	private String encodingPath = "src/main/java/application/lib/encodings/progdlv2_pm.txt";
    private Handler handler;
    private Game game;
    private InputProgram program;
    private int counter = 0;
    
    public AIPoliceManBrain(Game game) {
    	this.game = game;
    	this.handler = new DesktopHandler(new DLV2DesktopService("src/main/java/application/lib/dlv2.exe"));
    	
    	this.program = new ASPInputProgram();
    	this.program.addFilesPath(encodingPath);
    	try {
			ASPMapper.getInstance().registerClass(Coin.class);
			ASPMapper.getInstance().registerClass(inPath.class);
			ASPMapper.getInstance().registerClass(MinDistances.class);
			
			ASPMapper.getInstance().registerClass(NextS.class);
			ASPMapper.getInstance().registerClass(NextCellS.class);
			
		} catch (Exception e) {
			System.out.println("Ciao2");
			e.printStackTrace();
		
		}
    	OptionDescriptor optionDescriptor = new OptionDescriptor("-n 10 --no-facts");
        handler.addOption(optionDescriptor);
       
	}
    
    public void exec() {
    	this.program.clearPrograms();
    	inizializzaFattiDaGame();
    	OptionDescriptor optionDescriptor = new OptionDescriptor("-n 10 --no-facts");
        handler.addOption(optionDescriptor);
       
    	
    	
	    Output o = handler.startSync();
	    
	    AnswerSets answers = (AnswerSets) o;
	    System.out.println(answers.getAnswerSetsString());
	   
	    int n=0;
	    //System.out.println("OPTIMUM: " +answers.getOptimalAnswerSets().get(0).toString());
	    if(answers.getOptimalAnswerSets().size() != 0) {
			try {

				
					for(AnswerSet a : answers.getAnswersets()) {
						System.out.println("NON OTTIMO:" + ++n);
						System.out.println(a.toString());
					}
					for(AnswerSet a : answers.getOptimalAnswerSets()) {
						System.out.println("OTTIMO:");
						System.out.println(a.toString());
					}
				
				AnswerSet a = answers.getOptimalAnswerSets().get(0);
				for(Object obj:a.getAtoms()){
					
					//Scartiamo tutto ciò che non è un oggetto della classe Next o NextCell
					//if(!(obj instanceof NextS) || !(obj instanceof NextCellS))continue;
					
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
							game.gameFinished(0);
							return;
						}
						if(game.getMappa()[nextCell.getX()][nextCell.getY()] == '0' ||game.getMappa()[nextCell.getX()][nextCell.getY()] == '1'||game.getMappa()[nextCell.getX()][nextCell.getY()] == '2' || game.getMappa()[nextCell.getX()][nextCell.getY()] == '3') {
							continue;
						}
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
				
			} catch (Exception e) {
				e.printStackTrace();
			} 
			}
	    System.out.println("HO FINITO");
	    handler.removeAll();
    }
    
    private void inizializzaFattiDaGame() {
    	
    	ArrayList<Coin> coins = game.getCoins();
    	ArrayList<inPath> percorso = game.getPercorso();
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
    	for(Coin it: coins) {
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
    	
    	
    	StringBuilder stringa = new StringBuilder();
    	stringa.append("lock("+ game.getLock().getPosition().getX()+","+ game.getLock().getPosition().getY()+"). ");
    	for(int i = 0; i<4; i++) {
    		stringa.append("policeman("+ game.getMappaPoliziotti().get(i).getPosition().getX()+","+ game.getMappaPoliziotti().get(i).getPosition().getY()+","+i+"). ");
    		stringa.append("previousAction("+ game.getMappaPoliziotti().get(i).getType()+","+ game.getMappaPoliziotti().get(i).getPreviousAction()+"). ");
    		
    	}
    	
    	String myFacts = stringa.toString();
    	this.program.addProgram(myFacts);
    	System.out.println("FACTS " + program.getPrograms());
    	System.out.println("Lunghezza fatti: " + program.getPrograms().length());
    	handler.addProgram(this.program);
    	
    	this.counter+=1;
    	
    }
}
