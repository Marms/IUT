package model;

import java.io.*;

import model.Corrector;
import travail.Saisie;
import model.GeneratorSudoku;

public class GrilleGamer extends GeneratorSudoku implements Serializable {
	private int[][] grilleJouer;
	private boolean resul[][];//sert pour savoir si une case est vide
	private boolean visible[][];//sert pour afficher les cases visible

	public GrilleGamer() {
		super(3);
		grilleJouer = new int [9][9];
		resul = new  boolean[9][9];
		visible = new boolean[9][9];
		initGrilleJouer(4);
		initVisible();
	}

//------------------------------------------------ METHODE PRIVEE -----------------
	private void initGrilleJouer(int a) {
		int ligne, colone, i = 0,min;
		for (int z = 0; z < 9; z++) {
			do {
				min = numDebutI(z + 1);
				ligne = (int)(Math.random()*3) + min;
				min = numDebutJ(z+1);
				colone = (int)(Math.random()*3) + min;
			if (!resul[ligne][colone]) {
					i++;
				grilleJouer[ligne][colone] = getChiffre(ligne, colone);
				resul[ligne][colone] = true;
			}}while (i<a);
			i = 0;
		}
	}

	private void initVisible() {
		for (int i = 0; i < 9; i++) 
			for (int j = 0; j < 9; j++) 
				if (resul[i][j]) 
					visible[i][j] = true;
	}

	public String toString() {
		String local = "grille\n\t  0  1  2  3  4  5  6  7  8\n";
		for (int i = 0; i<9; i++) {if (i%3 == 0) local += " \t ---------------------------\n";
			local += i+"\t";
			for (int j = 0; j < 9; j++) {
				if (j%3 == 0) {
					local += " | ";
					if (j == 8) 
						local += "\t"+j;
				}
					
				if (resul[i][j])
					local += grilleJouer[i][j]+" ";
				else {
					local += "  ";
				}

			}
			local += "|\n";
		}
		return local+"\t  --------------------------\n";
	}

//------------------------------------------------------------- ACESSEURS -----------------
	public boolean getVisible(int i, int j) {
		return visible[i][j];
	}
	public boolean getResul(int i, int j) {
		return resul[i][j];
	}
	public String getValeur(int i, int j) {
		String local = ""+grilleJouer[i][j];
		return local;
	}

	/***/
	public void setGrille(int i, int j, int valeur) {
		if (i <= 9 && j <= 9 && i >= 0 && j >= 0) {
			grilleJouer[i][j] = valeur;
			resul[i][j] = true;
		}
	}
	public boolean verifGrille(){
		Corrector corr = new Corrector(grilleJouer);
		System.out.println(this);
		return corr.estValide();
	}
}

class Main {
	public static void main(String [] args) {
		int i,j,val;
		GrilleGamer sar1 = new GrilleGamer();
		System.out.println(""+sar1);
		while (true) {
			i = Saisie.lireInt("entrer ligne: ");
			j = Saisie.lireInt("entrer colone: ");
			val = Saisie.lireInt("enter valeur: ");
			sar1.setGrille(i,j,val);
			if (sar1.verifGrille()) {
				break;
			}
		}
 	}
}