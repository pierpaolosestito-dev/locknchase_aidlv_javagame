/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package application.model;

import application.Main;
import application.ai.inputfacts.ClosestCoin;
import application.ai.inputfacts.Coin;

import application.ai.inputfacts.MinDistances;
import application.ai.inputfacts.inPath;
import application.algorithm.BFSCalculator;
import application.view.MainFrame;
import application.view.ResponsiveManager;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.logging.Level;
import java.util.logging.Logger;


public class Game {
    private char[][] map = new char[ResponsiveManager.MATRIX_ROW][ResponsiveManager.MATRIX_COLUMN];
    private Lock lock;
    private HashMap<Integer,Poliziotto> mappaPoliziotti;
    private ArrayList<inPath> percorso;
    private ArrayList<Coin> coins;
    private Gem bonusGem;
    private Integer moneteTotali;
    private Integer moneteRaccolte;
    private static Game instance = null;
    private boolean isFinished;
    private ChangeIdentityBonus ciB;
    private HashMap<Integer,Coordinates> coordinatesIniziali;
   
   
 
    private Game() {
    	System.out.println("SOLO UNA VOLTA");
    	this.coordinatesIniziali = new HashMap<>();
        lock = new Lock(new Coordinates(-1,-1));
        Poliziotto scaredy = new Poliziotto(new Coordinates(-1,-1),0);
        Poliziotto stiffy = new Poliziotto(new Coordinates(-1,-1),1);
        Poliziotto silly = new Poliziotto(new Coordinates(-1,-1),2);
        Poliziotto smarty = new Poliziotto(new Coordinates(-1,-1),3);
        this.mappaPoliziotti = new HashMap<>();
        this.mappaPoliziotti.put(0,scaredy);
        
        this.mappaPoliziotti.put(1,stiffy);
        this.mappaPoliziotti.put(2,silly);
        this.mappaPoliziotti.put(3,smarty);
        
        percorso = new ArrayList<>();
        coins = new ArrayList<>();
        this.bonusGem=null;
        initializeMap();
        initializeMap2();
        this.moneteTotali = percorso.size()-5;
        this.moneteRaccolte=0;
        this.isFinished = false;

    }
    
    public ArrayList<MinDistances> calculateAllMinDistancesOnceTime(){
    	//Cerco di evitare la cornice però non sono sicuro sulla terminazione
    	ArrayList<MinDistances> daTornare = new ArrayList<>();
    	for(int i=1;i<ResponsiveManager.MATRIX_ROW;i++) {
    		for(int j=1;j<ResponsiveManager.MATRIX_COLUMN;j++) {
    			if(map[i][j] != 'W') {
    				System.out.println("STO ANALIZZANDO INDICE: ["+i+"]"+"["+j+"]");
    				for(int k=1;k<ResponsiveManager.MATRIX_ROW;k++) {
    					for(int w=1;w<ResponsiveManager.MATRIX_COLUMN;w++) {
    						if(k!=i && w!=j) {
    							if(map[k][w] != 'W') {
    								System.out.println("STO CALCOLANDO LA DISTANZA TRA INDICE: ["+i+"]"+"["+j+"]" + "E INDICE ["+i+"]"+"["+j+"]" );
    								MinDistances daAggiungere = new MinDistances(i,j,k,w,BFSCalculator.minDistance(map, map[k][w], new Coordinates(i,j)));
    								daTornare.add(daAggiungere);
    							}
    						}
    					}
    				}

    		
    			}
    		}
    	}
    	
		return daTornare;
    	
    }
    public void revalidate() {
    	 
         Poliziotto scaredy = new Poliziotto(new Coordinates(-1,-1),0);
         Poliziotto stiffy = new Poliziotto(new Coordinates(-1,-1),1);
         Poliziotto silly = new Poliziotto(new Coordinates(-1,-1),2);
         Poliziotto smarty = new Poliziotto(new Coordinates(-1,-1),3);
    	this.mappaPoliziotti.clear();
    	 this.mappaPoliziotti.put(0,scaredy);
         this.mappaPoliziotti.put(1,stiffy);
         this.mappaPoliziotti.put(2,silly);
         this.mappaPoliziotti.put(3,smarty);
    	this.percorso.clear();
    	this.coins.clear();
    	this.coordinatesIniziali.clear();
    	this.lock = null;
    	lock = new Lock(new Coordinates(-1,-1));
    	this.bonusGem = null;
    	this.moneteRaccolte = 0;
    	this.isFinished = false;
    	initializeMap();
    	initializeMap2();
    	this.moneteTotali = percorso.size()-5;
    }
    public void setCiBNull() {
 		this.ciB = null;
 	}
     public void setCiB(ChangeIdentityBonus ciB) {
 		this.ciB = ciB;
 	}
     public boolean existsCiB() {
     	if(ciB == null)
     		return false;
     	return true;
     }
     public ChangeIdentityBonus getCiB() {
 		return ciB;
 	}
    public Integer getMoneteTotali() {
		return moneteTotali;
	}
    public void setMoneteRaccolte(Integer moneteRaccolte) {
		this.moneteRaccolte = moneteRaccolte;
	}
    public Integer getMoneteRaccolte() {
		return moneteRaccolte;
	}
    public Gem getGem() {
		return bonusGem;
	}
    public ArrayList<Coin> getCoins() {
		return coins;
	}
    public boolean existsGem(){
        if(bonusGem == null)
            return false;
        return true;
    }
    public HashMap<Integer, Poliziotto> getMappaPoliziotti() {
        return mappaPoliziotti;
    }

    
    public Lock getLock() {
        return lock;
    }
    
    public static Game getInstance(){
        if(instance==null)
            instance = new Game();
        return instance;
    }
    private void initializeMap(){
        System.out.println("initializeMapDebug");
         try {    
           BufferedReader br = new BufferedReader(new FileReader("src/main/java/application/map_rivisitata.txt"));
              int row = 0;
              int column = 0;
             while (br.ready()) {
                String line = br.readLine();
                
                 for(int i=0;i<line.length();i++){
                     
                     if(line.charAt(i) != ' '){
                        System.out.print(line.charAt(i));
                         if(line.charAt(i)=='L'){
                             //OK
                        	 Coordinates lockP = new Coordinates(row,column);
                             lock.setPosition(lockP);
                             //La posizione di Lock è anche inclusa nel percorso
                             inPath lockPath = new inPath(row,column);
                            percorso.add(lockPath);
                            coordinatesIniziali.put(5, lockP);
                             
                         }
                         if(line.charAt(i)=='C'){
                             //OK
                             inPath toAdd = new inPath(row,column);
                            
                             percorso.add(toAdd);
                             //In COIN
                             Coin coinToAdd = new Coin(row,column);
                   
                             coins.add(coinToAdd);
                              
                         }
                         if(line.charAt(i)=='T') {
                        	 inPath toAdd = new inPath(row,column);
                        	 percorso.add(toAdd);
                         }
                         if(line.charAt(i) == '0' || line.charAt(i) =='1' || line.charAt(i) == '2' || line.charAt(i) == '3'){//SCAREDY
                        	 Coordinates position = new Coordinates(row,column);
                             mappaPoliziotti.get(Character.getNumericValue(line.charAt(i))).setPosition(new Coordinates(row,column));
                             coordinatesIniziali.put(Character.getNumericValue(line.charAt(i)),position);
                             //SUBPARTE:OK
                             inPath policeManPosition = new inPath(row,column);
                   
                             percorso.add(policeManPosition);

                         }
                         
                     map[row][column]=line.charAt(i);
                     column+=1;
                     }
                 }
                 System.out.println("");
                row+=1;
                column=0;
            }
            br.close();
        } catch (FileNotFoundException ex) {
            System.out.println("Non esiste");
        } catch (IOException ex) {
            Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
        }
         
    }
    
    public HashMap<Integer, Coordinates> getCoordinatesIniziali() {
		return coordinatesIniziali;
	}
    //MinDistances di sopra, e closestCoin(X,Y)
    private void initializeMap2(){
        
        for(int i=0;i<ResponsiveManager.MATRIX_ROW;i++){
            for(int j=0;j<ResponsiveManager.MATRIX_COLUMN;j++){
                if(map[i][j]=='0'||map[i][j]=='1'||map[i][j]=='2'||map[i][j]=='3'){
                    //Coordinates poliziottoTrovato = new Coordinates(i,j);
                    //this.mappaPoliziotti.get(Character.getNumericValue(map[i][j])).setPosition(poliziottoTrovato);
                    		//MINDISTANCES:LOCK
                			int x,y=0;
                			
                			 Coordinates lockPosition = lock.getPosition();
                			 x = lockPosition.getX();
                			 y = lockPosition.getY();
                             //lockPosition.setY(lockPosition.getY()+1);
                             MinDistances right = new MinDistances(x,y+1,i,j,BFSCalculator.minDistance(map, map[i][j], new Coordinates(x,y+1)));
                             x = lockPosition.getX();
                			 y = lockPosition.getY();
                             
                           
                             MinDistances left = new MinDistances(x,y-1,i,j,BFSCalculator.minDistance(map, map[i][j], new Coordinates(x,y-1)));
                             x = lockPosition.getX();
                			 y = lockPosition.getY();
                             MinDistances up = new MinDistances(x-1,y,i,j,BFSCalculator.minDistance(map, map[i][j], new Coordinates(x-1,y)));
                             
                             x = lockPosition.getX();
                			 y = lockPosition.getY();
                             MinDistances down = new MinDistances(x+1,y,i,j,BFSCalculator.minDistance(map, map[i][j], new Coordinates(x+1,y)));
                             
                    
                             lock.addMinDistances(up);
                             lock.addMinDistances(right);
                             lock.addMinDistances(left);
                             lock.addMinDistances(down);
                             //MINDISTANCES:POLICEMAN
                             //i,j sono le coordinate del poliziotto
                             int indexI = i;
                             int indexJ = j;
                             lockPosition = lock.getPosition();
                             MinDistances pmRight = new MinDistances(indexI,indexJ+1,lockPosition.getX(),lockPosition.getY(),BFSCalculator.minDistance(map, 'L', new Coordinates(indexI,indexJ+1)));
                             indexI = i;
                             indexJ = j;
                             MinDistances pmLeft = new MinDistances(indexI,indexJ-1,lockPosition.getX(),lockPosition.getY(),BFSCalculator.minDistance(map, 'L', new Coordinates(indexI,indexJ-1)));
                             indexI = i;
                             indexJ = j;
                             MinDistances pmUp = new MinDistances(indexI+1,indexJ,lockPosition.getX(),lockPosition.getY(),BFSCalculator.minDistance(map, 'L', new Coordinates(indexI+1,indexJ)));
                             indexI = i;
                             indexJ = j;
                             
                             MinDistances pmDown = new MinDistances(indexI-1,indexJ,lockPosition.getX(),lockPosition.getY(),BFSCalculator.minDistance(map, 'L', new Coordinates(indexI-1,indexJ)));
                             this.mappaPoliziotti.get(Character.getNumericValue(map[i][j])).addMinDistances(pmRight);
                             this.mappaPoliziotti.get(Character.getNumericValue(map[i][j])).addMinDistances(pmLeft);
                             this.mappaPoliziotti.get(Character.getNumericValue(map[i][j])).addMinDistances(pmUp);
                             this.mappaPoliziotti.get(Character.getNumericValue(map[i][j])).addMinDistances(pmDown);
                }
            }
        }
        
        	calculateClosestCoin();
       
    }
    public void ricalculateMinDistancesFromPmPOV(int index) {
    	
    		 mappaPoliziotti.get(index).clearMinDistances();
    		 Coordinates lockPosition = lock.getPosition();
    		
    		 int x,y=0;
    		 x = mappaPoliziotti.get(index).getPosition().getX();
    		 y = mappaPoliziotti.get(index).getPosition().getY();
             MinDistances pmRight = new MinDistances(x,y+1,lockPosition.getX(),lockPosition.getY(),BFSCalculator.minDistance(map, 'L', new Coordinates(x,y+1)));
             x = mappaPoliziotti.get(index).getPosition().getX();
    		 y = mappaPoliziotti.get(index).getPosition().getY();
             MinDistances pmLeft = new MinDistances(x,y-1,lockPosition.getX(),lockPosition.getY(),BFSCalculator.minDistance(map, 'L', new Coordinates(x,y-1)));
             x = mappaPoliziotti.get(index).getPosition().getX();
    		 y = mappaPoliziotti.get(index).getPosition().getY();
             MinDistances pmUp = new MinDistances(x+1,y,lockPosition.getX(),lockPosition.getY(),BFSCalculator.minDistance(map, 'L', new Coordinates(x+1,y)));
             x = mappaPoliziotti.get(index).getPosition().getX();
    		 y = mappaPoliziotti.get(index).getPosition().getY();
             MinDistances pmDown = new MinDistances(x-1,y,lockPosition.getX(),lockPosition.getY(),BFSCalculator.minDistance(map, 'L', new Coordinates(x-1,y)));
             this.mappaPoliziotti.get(index).addMinDistances(pmRight);
             this.mappaPoliziotti.get(index).addMinDistances(pmLeft);
             this.mappaPoliziotti.get(index).addMinDistances(pmUp);
             this.mappaPoliziotti.get(index).addMinDistances(pmDown);
    	
    }
    public void ricalculateMinDistancesandClosestCoin() {
    	lock.clearArrays();
    	//GEMMA
    	if(this.bonusGem != null) {
    		System.out.println("STO CALCOLANDO LE MINDISTANCES PER I GEM");
    		Coordinates lockPosition = lock.getPosition();
    		int x,y=0;
    		x = lockPosition.getX();
    		y = lockPosition.getY();
    		MinDistances right = new MinDistances(x,y+1,bonusGem.getX(),bonusGem.getY(),BFSCalculator.minDistance(map, 'G', new Coordinates(x,y+1)));
    		lock.addMinDistances(right);
    		x = lockPosition.getX();
    		y = lockPosition.getY();
    		MinDistances left = new MinDistances(x,y-1,bonusGem.getX(),bonusGem.getY(),BFSCalculator.minDistance(map, 'G', new Coordinates(x,y-1)));
    		lock.addMinDistances(left);
    		
    		x = lockPosition.getX();
    		y = lockPosition.getY();
    		MinDistances up = new MinDistances(x+1,y,bonusGem.getX(),bonusGem.getY(),BFSCalculator.minDistance(map, 'G', new Coordinates(x+1,y)));
    		lock.addMinDistances(up);
    		
    		x = lockPosition.getX();
    		y = lockPosition.getY();
    		MinDistances down = new MinDistances(x-1,y,bonusGem.getX(),bonusGem.getY(),BFSCalculator.minDistance(map, 'G', new Coordinates(x-1,y)));
    		lock.addMinDistances(down);
    		
    	}
    	//CAMBIO IDENTITA' BONUS
    	if(this.ciB != null) {
    		Coordinates lockPosition = lock.getPosition();
    		int x,y=0;
    		x = lockPosition.getX();
    		y = lockPosition.getY();
    		MinDistances right = new MinDistances(x,y+1,ciB.getX(),ciB.getY(),BFSCalculator.minDistance(map, 'K', new Coordinates(x,y+1)));
    		lock.addMinDistances(right);
    		x = lockPosition.getX();
    		y = lockPosition.getY();
    		MinDistances left = new MinDistances(x,y-1,ciB.getX(),ciB.getY(),BFSCalculator.minDistance(map, 'K', new Coordinates(x,y-1)));
    		lock.addMinDistances(left);
    		
    		x = lockPosition.getX();
    		y = lockPosition.getY();
    		MinDistances up = new MinDistances(x+1,y,ciB.getX(),ciB.getY(),BFSCalculator.minDistance(map, 'K', new Coordinates(x+1,y)));
    		lock.addMinDistances(up);
    		
    		x = lockPosition.getX();
    		y = lockPosition.getY();
    		MinDistances down = new MinDistances(x-1,y,ciB.getX(),ciB.getY(),BFSCalculator.minDistance(map, 'K', new Coordinates(x-1,y)));
    		lock.addMinDistances(down);
    	}
    	for(int i=0;i<4;i++) {
    		Coordinates lockPosition = lock.getPosition();
    		int x,y=0;
    		x = lockPosition.getX();
    		y = lockPosition.getY();
    		MinDistances right = new MinDistances(x,y+1,mappaPoliziotti.get(i).getPosition().getX(),mappaPoliziotti.get(i).getPosition().getY(),BFSCalculator.minDistance(map, Character.forDigit(mappaPoliziotti.get(i).getType(), 10), new Coordinates(x,y+1)));
    		lock.addMinDistances(right);
    		
    		//LEFT
    		x = lockPosition.getX();
    		y = lockPosition.getY();
    		MinDistances left = new MinDistances(x,y-1,mappaPoliziotti.get(i).getPosition().getX(),mappaPoliziotti.get(i).getPosition().getY(),BFSCalculator.minDistance(map, Character.forDigit(mappaPoliziotti.get(i).getType(), 10), new Coordinates(x,y-1)));
    		lock.addMinDistances(left);
    		
    		//UP
    		x = lockPosition.getX();
    		y = lockPosition.getY();
    		MinDistances up = new MinDistances(x+1,y,mappaPoliziotti.get(i).getPosition().getX(),mappaPoliziotti.get(i).getPosition().getY(),BFSCalculator.minDistance(map, Character.forDigit(mappaPoliziotti.get(i).getType(), 10), new Coordinates(x+1,y)));
    		lock.addMinDistances(up);
    		
    		//DOWN
    		x = lockPosition.getX();
    		y = lockPosition.getY();
    		MinDistances down = new MinDistances(x-1,y,mappaPoliziotti.get(i).getPosition().getX(),mappaPoliziotti.get(i).getPosition().getY(),BFSCalculator.minDistance(map, Character.forDigit(mappaPoliziotti.get(i).getType(), 10), new Coordinates(x-1,y)));
    		lock.addMinDistances(down);
    	}
    	
    	calculateClosestCoin();
    }
    
    public void deleteCoin(Coin c) {
    	System.out.println(this.coins.remove(c));
    	
    }
    
    private void calculateClosestCoin() {
    	 int r,c;
         r=lock.getPosition().getX();
         c=lock.getPosition().getY();
         int counterIterazioni = 0;
         int tempR=r;
         while(tempR>=0){
             counterIterazioni+=1;
             if(map[tempR][c] == 'W')
            	 break;
             if(map[tempR][c] == 'C'){
            	 System.out.println("TROVATO UN CLOSESTCOIN ALLA CELLA ["+tempR+"]["+c+"] A DISTANZA" + (counterIterazioni-1) + " SOPRA");
                ClosestCoin toAdd = new ClosestCoin(tempR,c);
                lock.addClosestCoin(toAdd);
                
                //RIGHT
                if(map[r][c+1]!='W') {
                	MinDistances right = new MinDistances(r,c+1,tempR,c,counterIterazioni-1);
                	lock.addMinDistancesClosestCoin(right);
                }
                //LEFT
                
                if(map[r][c-1]!='W') {
                	MinDistances left = new MinDistances(r,c-1,tempR,c,counterIterazioni-1);
                	lock.addMinDistancesClosestCoin(left);
                }
                //UP
                
                if(map[r-1][c] != 'W') {
                	MinDistances down = new MinDistances(r-1,c,tempR,c,counterIterazioni-1);
                	lock.addMinDistancesClosestCoin(down);
                }
                //DOWN
                if(map[r+1][c]!='W') {
                	MinDistances down = new MinDistances(r+1,c,tempR,c,counterIterazioni-1);
                	lock.addMinDistancesClosestCoin(down);
                }
                 break; 
                 //DOBBIAMO USCIRE DAL WHILE SENZA FARE RETURN
             }
             tempR-=1;
         }
         counterIterazioni=0;
         //Da LockPosition verso GIU
         tempR = r;
         while(tempR<ResponsiveManager.MATRIX_ROW){
             counterIterazioni+=1;
             if(map[tempR][c] == 'W')
            	 break;
             if(map[tempR][c] == 'C'){
            	 System.out.println("TROVATO UN CLOSESTCOIN ALLA CELLA ["+tempR+"]["+c+"] A DISTANZA" + counterIterazioni + "SOTTO");
             	ClosestCoin toAdd = new ClosestCoin(tempR,c);
                lock.addClosestCoin(toAdd);
                //RIGHT
                if(map[r][c+1]!='W') {
                	MinDistances right = new MinDistances(r,c+1,tempR,c,counterIterazioni-1);
                	lock.addMinDistancesClosestCoin(right);
                }
                //LEFT
                
                if(map[r][c-1]!='W') {
                	MinDistances left = new MinDistances(r,c-1,tempR,c,counterIterazioni-1);
                	lock.addMinDistancesClosestCoin(left);
                }
                //UP
                
                if(map[r-1][c] != 'W') {
                	MinDistances down = new MinDistances(r-1,c,tempR,c,counterIterazioni-1);
                	lock.addMinDistancesClosestCoin(down);
                }
                //DOWN
                if(map[r+1][c]!='W') {
                	MinDistances down = new MinDistances(r+1,c,tempR,c,counterIterazioni-1);
                	lock.addMinDistancesClosestCoin(down);
                }
                 break;
                 //DOBBIAMO USCIRE DAL WHILE SENZA FARE RETURN
             }
             tempR+=1;
         }
         counterIterazioni=0;
         int tempC = c;
         while(tempC>=0){
        	 
             counterIterazioni+=1;
             if(map[r][tempC] == 'W')
            	 break;
            if(map[r][tempC] == 'C'){
            	System.out.println("TROVATO UN CLOSESTCOIN ALLA CELLA ["+r+"]["+tempC+"] A DISTANZA" + counterIterazioni + "SULLA SINISTRA");
         	   ClosestCoin toAdd = new ClosestCoin(r,tempC);
                lock.addClosestCoin(toAdd);
                if(map[r][c+1]!='W') {
                	MinDistances right = new MinDistances(r,c+1,r,tempC,counterIterazioni-1);
                	lock.addMinDistancesClosestCoin(right);
                }
                //LEFT
                
                if(map[r][c-1]!='W') {
                	MinDistances left = new MinDistances(r,c-1,r,tempC,counterIterazioni-1);
                	lock.addMinDistancesClosestCoin(left);
                }
                //UP
                
                if(map[r-1][c] != 'W') {
                	MinDistances down = new MinDistances(r-1,c,r,tempC,counterIterazioni-1);
                	lock.addMinDistancesClosestCoin(down);
                }
                //DOWN
                if(map[r+1][c]!='W') {
                	MinDistances down = new MinDistances(r+1,c,r,tempC,counterIterazioni-1);
                	lock.addMinDistancesClosestCoin(down);
                }
                break;
                 //DOBBIAMO USCIRE DAL WHILE SENZA FARE RETURN
             }
            tempC-=1;
         }
         tempC = c;
         counterIterazioni=0;
          while(tempC<ResponsiveManager.MATRIX_COLUMN){
              counterIterazioni+=1;
              if(map[r][tempC]=='W')
             	 break;
            if(map[r][tempC] == 'C'){
            	System.out.println("TROVATO UN CLOSESTCOIN ALLA CELLA ["+r+"]["+tempC+"] A DISTANZA" + (counterIterazioni-1) + "SULLA DESTRA");
         	   ClosestCoin toAdd = new ClosestCoin(r,tempC);
               lock.addClosestCoin(toAdd);
               if(map[r][c+1]!='W') {
               	MinDistances right = new MinDistances(r,c+1,r,tempC,counterIterazioni-1);
               	lock.addMinDistancesClosestCoin(right);
               }
               //LEFT
               
               if(map[r][c-1]!='W') {
               	MinDistances left = new MinDistances(r,c-1,r,tempC,counterIterazioni-1);
               	lock.addMinDistancesClosestCoin(left);
               }
               //UP
               
               if(map[r-1][c] != 'W') {
               	MinDistances down = new MinDistances(r-1,c,r,tempC,counterIterazioni-1);
               	lock.addMinDistancesClosestCoin(down);
               }
               //DOWN
               if(map[r+1][c]!='W') {
               	MinDistances down = new MinDistances(r+1,c,r,tempC,counterIterazioni-1);
               	lock.addMinDistancesClosestCoin(down);
               }
                break;
                 //DOBBIAMO USCIRE DAL WHILE SENZA FARE RETURN
             }
            tempC+=1;
         }
    }
    public char[][] getMappa(){
        return map;
    }
    public ArrayList<inPath> getPercorso(){
        return percorso;
    }
    
    public ArrayList<inPath> getPercorsoVicinoLock(){
    	ArrayList<inPath> percorsoVicino = new ArrayList<>();
    	int x,y=0;
    	x=lock.getPosition().getX();
    	y=lock.getPosition().getY();
    	for(inPath it : percorso) {
    		//La nostra posizione la evitiamo
    		if(it.getXP() != x && it.getYP()!=y) {
    			if(Math.abs(it.getXP()-x)<=3 || Math.abs(it.getYP()-x)<=3) {
    				percorsoVicino.add(it);
    			}
    		}
    	}
    	return percorsoVicino;
    	
    }
	public void setGemNull() {
				this.bonusGem=null;
	}
	public void setGem(Gem g) {
		this.bonusGem = g;
	}
	public boolean getFinished() {
		return this.isFinished;
	}
	public void gameFinished(int type) { //Se 0, capiamo che lo stiamo chiamando da Perdita, 1 se abbiamo vinto
		this.isFinished = true;
		MainFrame f = MainFrame.getInstance();
		f.switchContent(type);
		
		
	}
}
