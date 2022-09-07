
package application.dacancellare;


import it.unical.mat.embasp.base.Handler;
import it.unical.mat.embasp.platforms.desktop.DesktopHandler;
import it.unical.mat.embasp.specializations.dlv.desktop.DLVDesktopService;
import it.unical.mat.embasp.languages.asp.ASPInputProgram;
import it.unical.mat.embasp.base.InputProgram;
import it.unical.mat.embasp.base.Output;
import it.unical.mat.embasp.languages.IllegalAnnotationException;
import it.unical.mat.embasp.languages.ObjectNotValidException;
import it.unical.mat.embasp.languages.asp.ASPMapper;
import it.unical.mat.embasp.languages.asp.AnswerSet;
import it.unical.mat.embasp.languages.asp.AnswerSets;
import java.util.logging.Level;
import java.util.logging.Logger;
public class MainDLVTest {
    private static String encodingPath = "src/main/java/application/lib/encodings/programma_test.txt";
    private static Handler handler;
    public static void main(String[] args) {
        //Qui: Dobbiamo simulare che, passiamo il programma a EmbASP e ci ritorna l'output
        handler = new DesktopHandler(new DLVDesktopService("src/main/java/application/lib/dlv.mingw.exe"));
        InputProgram program = new ASPInputProgram();
        program.addFilesPath(encodingPath);
        
        /*try {
			ASPMapper.getInstance().registerClass(Col.class);
			program.addObjectInput(new Col(1,new SymbolicConstant("red")));
		} catch (Exception e) {
			e.printStackTrace();
		}*/
        
        handler.addProgram(program);
        Output o = handler.startSync();
        AnswerSets answers = (AnswerSets) o;
        System.out.println(answers.getAnswerSetsString());
       
        int n=0;
        System.out.println("B");
        for(AnswerSet a:answers.getAnswersets()){
            System.out.println("AS N:" + ++n);
            try {

				for(Object obj:a.getAtoms()){
                                        System.out.println("DEBUG:");
					System.out.println(obj.toString());
				}
				System.out.println();
			} catch (Exception e) {
				System.out.println("CIAo");
				e.printStackTrace();
				
			} 
            
        }
        System.out.println("A");
        
           
        
    }
}
