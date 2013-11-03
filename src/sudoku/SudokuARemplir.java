package sudoku;


import java.io.*;

import travail.Saisie;
public class SudokuARemplir implements Serializable{
	private Sudoku s1;
	private int[][] grilleJouer;
	private boolean resul[][];
	private boolean visible[][];

	public SudokuARemplir(){
		grilleJouer=new int [9][9];
		resul = new  boolean[9][9];
		visible = new boolean[9][9];
		s1	= new Sudoku(3);
		initGrilleJouer(4);
		initVisible();
	}
	//------------------------------------------------ METHODE PRIVEE -----------------
	private void initGrilleJouer(int a){
		int ligne, colone,i=0,min;
		for (int z=0; z<9; z++) {
			do{
				min=s1.numDebutI(z+1);
				ligne = (int)(Math.random()*3)+min;
				min=s1.numDebutJ(z+1);
				colone =  (int)(Math.random()*3)+min;
			if (!resul[ligne][colone]) {
					i++;
				grilleJouer[ligne][colone]=s1.getChiffre(ligne,colone);
				resul[ligne][colone]=true;
			}}
			while (i<a);
			i=0;
		}
	}
	private void initVisible(){
		for (int i=0; i<9; i++) 
			for (int j=0; j<9; j++) 
				if (resul[i][j]) 
					visible[i][j]=true;
	}
	private boolean verifLigne(int a, int ligne){
		for (int i=0; i<9; i++) {
			if (grilleJouer[ligne][i]==a) 
				return false;
		}
		return true;
	}
	private boolean verifColone(int a,int col){
		for (int i=0; i<9; i++) {
			if (grilleJouer[i][col]== a) 
				return false;
		}
		return true;
	}	
	private boolean verifCase(int a, int carre){
		for (int i=s1.numDebutI(carre),cmpI=0	; cmpI < 3; cmpI++,i++ ) {
			for (int j=s1.numDebutJ(carre),cmpJ=0; cmpJ < 3; cmpJ++,j++ ) {
				if (grilleJouer[i][j] == a) 
					return false;
			}
		}
		return true;
	}
	public boolean verifGrille(){
		for( int i=0;i<9;i++)
			for (int j=0; j<9; j++) {
				if (grilleJouer[i][j]!=s1.getChiffre(i,j) || !resul[i][j]) {
					return false;
				}
			}
		return true;
	
	}
	//
	
	
	public String toString(){
		String local= "grille\n\t  0  1  2  3  4  5  6  7  8\n";
		for (int i=0; i<9; i++) { if (i%3==0) local +=" \t ---------------------------\n";
			local+= i+"\t";
			for (int j=0; j<9; j++) {
				if (j%3==0){
					local +=" | ";
					if (j==8) 
						local+="\t"+j;
				}
					
				if ( resul[i][j])
					local += grilleJouer[i][j]+" ";
				else {
					local +="  ";
				}

			}
			local+="|\n";
		}
		return local+"\t  --------------------------\n";
	}
	//------------------------------------------------------------- ACESSEURS -----------------
	public boolean getVisible(int i,int j){return visible[i][j];}
	public boolean getResul(int i,int j){return resul[i][j];}
	public String getValeur(int i,int j){ 
		String local=""+grilleJouer[i][j];
		return local; }
	public int qCase(int i,int j){
		return s1.quelleCase(i, j);
	}
	public void setGrille(int i, int j, int valeur){
		if (i<=9 && j<=9 && i>=0 && j>=0) {
			grilleJouer[i][j]=valeur;
			resul[i][j]=true;
		}
		
	}
}



class Main{
	public static void main(String [] args) {
		int i,j,val;
		SudokuARemplir sar1 = new SudokuARemplir();
		while (true) {
				System.out.println(""+sar1);
			i=Saisie.lireInt("entrer ligne: ");
			j=Saisie.lireInt("entrer colone: ");
			val=Saisie.lireInt("enter valeur: ");
			sar1.setGrille(i,j,val);
			if (sar1.verifGrille()) {
				break;
			}
		}
 	}
}