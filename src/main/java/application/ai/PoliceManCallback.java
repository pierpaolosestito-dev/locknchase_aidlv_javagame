package application.ai;

import application.ai.outputfacts.NextCellS;
import application.ai.outputfacts.NextS;
import application.model.Coordinates;
import application.model.Game;
import it.unical.mat.embasp.base.Callback;
import it.unical.mat.embasp.base.Output;
import it.unical.mat.embasp.languages.asp.AnswerSet;
import it.unical.mat.embasp.languages.asp.AnswerSets;

public class PoliceManCallback implements Callback{

	private Game game;
	public PoliceManCallback() {
		this.game = Game.getInstance();
	}
	@Override
	public void callback(Output o) {
		 AnswerSets answers = (AnswerSets) o;
		    System.out.println(answers.getAnswerSetsString());
		   
		    int n=0;
		    for(AnswerSet a : answers.getAnswersets()) {
		    	System.out.println("Answerset N: "+ ++n);
		    	if(a.toString().length()==2) {
		    		System.out.println("VUOTO");
		    		continue;
		    	}
		    	System.out.println(a.toString());
		    }
		    System.out.println("OPTIMUM: " +answers.getOptimalAnswerSets().get(0).toString());
		    if(answers.getOptimalAnswerSets().size() != 0) {
				try {
					
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
	}

}
