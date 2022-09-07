package application.dacancellare;

import application.ai.outputfacts.Next;
import application.ai.outputfacts.NextCell;
import it.unical.mat.embasp.base.Handler;
import it.unical.mat.embasp.base.InputProgram;
import it.unical.mat.embasp.base.OptionDescriptor;
import it.unical.mat.embasp.base.Output;
import it.unical.mat.embasp.languages.asp.ASPInputProgram;
import it.unical.mat.embasp.languages.asp.AnswerSet;
import it.unical.mat.embasp.languages.asp.AnswerSets;
import it.unical.mat.embasp.platforms.desktop.DesktopHandler;
import it.unical.mat.embasp.specializations.dlv.desktop.DLVDesktopService;

public class MainDLV2Test {
	 private static String encodingPath = "src/main/java/application/lib/encodings/progdlv2_test.txt";
	    private static Handler handler;
	    public static void main(String[] args) {
	        //Qui: Dobbiamo simulare che, passiamo il programma a EmbASP e ci ritorna l'output
	        handler = new DesktopHandler(new DLVDesktopService("src/main/java/application/lib/dlv2.exe"));
	        InputProgram program = new ASPInputProgram();
	        program.addFilesPath(encodingPath);
	        
	        /*try {
				ASPMapper.getInstance().registerClass(Col.class);
				program.addObjectInput(new Col(1,new SymbolicConstant("red")));
			} catch (Exception e) {
				e.printStackTrace();
			}*/
	        System.out.println(program.getPrograms());
	        OptionDescriptor optionDescriptor = new OptionDescriptor("--no-facts");
	        handler.addOption(optionDescriptor);
	        handler.addProgram(program);
	        Output o = handler.startSync();
	        AnswerSets answers = (AnswerSets) o;
	        System.out.println(answers.getAnswerSetsString());
	        
	        int n=0;
	        System.out.println("B");
	        for(AnswerSet a:answers.getAnswersets()){
	        try {
	     	   
	      	  
	     	   
	            for(Object obj: a.getAtoms()) {
	         	   System.out.println("STO ITERANDO");
	         	   //QUI NON RIESCO AD ARRIVARCI, ENTRO NEL CATCH PER VIA DI A.GETATOMS()
	         	   if(!(obj instanceof NextCell) ||!(obj instanceof Next)) {
	         		   continue;
	         	   }
	         	   if(obj instanceof Next) {
	         		   Next dir = (Next) obj;
	         		   System.out.println(dir.getDirection());
	         	   }
	         	   if(obj instanceof NextCell){
	         		   NextCell next = (NextCell) obj;
	         		   System.out.println(next.getX() + " " + next.getY());
	         		   
	         	   }
	            }
	        } catch (Exception e) {
	     	   System.out.println("Errore");
	            
	        }
	        System.out.println("A");
	        
	           
	        
	    }
	    }
}
