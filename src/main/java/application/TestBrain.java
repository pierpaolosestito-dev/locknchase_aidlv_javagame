package application;

import java.util.ArrayList;

import application.ai.AILockBrain;
import application.ai.AIPoliceManBrain;
import application.ai.inputfacts.MinDistances;
import application.ai.inputfacts.inPath;
import application.model.Coordinates;
import application.model.Game;

public class TestBrain {
public static void main(String[] args) {
	ArrayList<Integer> integers = new ArrayList<>();
	integers.add(1);
	integers.add(2);
	integers.add(3);
	integers.add(4);
	
	int counter = 0;
	while(counter<integers.size()) {
		if(integers.get(counter)==2) {
			System.out.println("TROVATO");
			break;
		}
		counter+=1;
		
	}
	
	System.out.println("CIAO");
	
}
}
