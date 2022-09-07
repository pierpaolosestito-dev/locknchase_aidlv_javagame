package application.dacancellare;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Set;


import application.ai.inputfacts.ClosestCoin;
import application.ai.inputfacts.Coin;

import application.ai.inputfacts.MinDistances;
import application.ai.inputfacts.inPath;
import application.ai.outputfacts.Next;
import application.ai.outputfacts.NextCell;
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
import it.unical.mat.embasp.specializations.dlv.desktop.DLVDesktopService;
import it.unical.mat.embasp.specializations.dlv2.desktop.DLV2DesktopService;

public class MainDLVTest2 {
    //private static String encodingPath = "src/main/java/application/lib/encodings/programma.txt";
	private static String encodingPath = "src/main/java/application/lib/encodings/progdlv2.txt";
    private static Handler handler;
    private static Game game;
    public static void main(String[] args) {
    	game = Game.getInstance();
    	 //Qui: Dobbiamo simulare che, passiamo il programma a EmbASP e ci ritorna l'output
    	//handler = new DesktopHandler(new DLVDesktopService("src/main/java/application/lib/dlv.mingw.exe"));
        handler = new DesktopHandler(new DLV2DesktopService("src/main/java/application/lib/dlv2.exe"));
        InputProgram program = new ASPInputProgram();
        
        ArrayList<inPath> percorso = game.getPercorso();
        ArrayList<Coin> coins = game.getCoins();
        ArrayList<MinDistances> minDistances = game.getLock().getMinDistances();
        ArrayList<ClosestCoin> closestCoins = game.getLock().getClosestCoin();
        try {
			ASPMapper.getInstance().registerClass(ClosestCoin.class);
			ASPMapper.getInstance().registerClass(Coin.class);
			
			ASPMapper.getInstance().registerClass(inPath.class);
			ASPMapper.getInstance().registerClass(MinDistances.class);
			
			for(inPath it : percorso) {
				program.addObjectInput(it);
			}
			for(Coin c : coins) {
				program.addObjectInput(c);
			}
			for(MinDistances m : minDistances) {
				program.addObjectInput(m);
			}
			for(ClosestCoin cc : closestCoins) {
				program.addObjectInput(cc);
			}
			
		} catch (Exception e) {
			System.out.println("Ciao2");
			e.printStackTrace();
		
		}
        String myFacts = "";
        StringBuilder g = new StringBuilder();
        g.append("lock("+game.getLock().getPosition().getX()+","+game.getLock().getPosition().getY()+"). ");
        
        for(int i=0;i<4;i++) {
        	g.append("policeman("+game.getMappaPoliziotti().get(i).getPosition().getX()+","+game.getMappaPoliziotti().get(i).getPosition().getY()+","+game.getMappaPoliziotti().get(i).getType()+"). ");
        }
        g.append("previous_action("+game.getLock().getPreviousAction()+"). ");
       
        OptionDescriptor optionDescriptor = new OptionDescriptor("--no-facts");
        handler.addOption(optionDescriptor);
        
        myFacts = g.toString();
        System.out.println("MY FACTS: " + myFacts);
        program.addProgram(myFacts);
       
        System.out.println(program.getPrograms());
        program.addFilesPath(encodingPath);
        handler.addProgram(program);
        
        Output o = handler.startSync();
        AnswerSets answers = (AnswerSets) o;
        
      System.out.println(answers.getAnswerSetsString());
      
      
        for(AnswerSet a:answers.getAnswersets()){
			try {
			
				for(Object obj:a.getAtoms()){
					System.out.println("STO ITERANDO");
					//Scartiamo tutto ciò che non è un oggetto della classe Next o NextCell
					if(!(obj instanceof Next) || !(obj instanceof NextCell)) continue;
					
					if((obj instanceof Next)) {
						Next next = (Next) obj;
						System.out.println("DIREZIONE: " + next.getDirection());
					}
					if((obj instanceof NextCell)) {
						NextCell nextCell = (NextCell) obj;
						System.out.println("PROSSIMA CELLA: " + nextCell.getX() + " " + nextCell.getY());
					}
				}
				System.out.println("HEY");
			} catch (Exception e) {
				e.printStackTrace();
			} 
			
		}
        System.out.println("A");
    }
}
