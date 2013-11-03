package sudoku;

import java.io.*;
public class Sudoku implements Serializable {	
	private int tailleGrille;
	private int[][] grille;
	private boolean[][] resultat;
	private int tailleCarre;
	private int [][]grilleDuJoueur;
	
	public Sudoku(int a){
		//		grilleDuJoueur=new int[9][9];
		tailleCarre=a;
		tailleGrille=a*a;
		grille = new int[a*a][a*a];
		resultat = new  boolean[a*a][a*a];
		initGrille();
		genere();
	
	}
	/*place des zero dans chaque case de la grille*/
	private void initGrille(){
		for (int i=0; i<tailleGrille; i++) {
			for (int j=0; j<tailleGrille; j++) {
				grille[i][j]=0;
			}
		}
	}
	
	public String toString(){
		String local="grille: \n";
		for (int i=0; i < 9; i++){
			if (i%3==0)
				local+="\n";
			for (int j=0; j < 9; j++) {
				if (j%3==0) {
					local+="\t";
				}
				local+=grille[i][j]+" ";
			}

			local+="\n";
		}
		return local;
	}
	/*pour qu'un sudoku soit valide il faut que la ligne la colone et la case ne comporte pas de 
	 chiffre en double
	 */
	private boolean verifLigne(int a, int ligne) {
			for (int i=0; i<tailleGrille; i++) {
					if (grille[ligne][i] == a) 
						return false;
			}
		return true;
	}
	private boolean verifColone(int a, int col) {
		for ( int i=0; i<tailleGrille; i++ ) {
				if ( grille[i][col] == a ) 
					return false;
		}
		return true;
	}
	private boolean verifCase( int a, int carre ) {
		for (int i = numDebutI(carre), cmpI = 0	; cmpI < 3; cmpI++, i++ ) 
			for (int j = numDebutJ(carre), cmpJ = 0; cmpJ < 3; cmpJ++, j++ ) 
				if (grille[i][j] == a) 
					return false;
		return true;
	}
	/**renvoit le numero du petit carre correspondant i,j */
	public int quelleCase(int numLigne,int numColone){ 
		return ((numLigne/3)*3+(numColone / 3))+1;
	}	
	
	public static int numDebutI(int num){ 
		return ((num-1) / 3)*3;
	}
	public static int numDebutJ(int num){	
		return ((num-1) % 3)*3;
	}
	/*methode qui genere un chiffre :
		cette methode calcul les chiffres possible que l'on peut mettre a la case grille[i][j] 
		les stock dans un tableau puis renvois un numero au 
									hazard parmi le tableau*/
	private int genere1Chiffre(int i,int j){
		int [] valeurPossible = new int [9], ne;
		int cmp = 0, num;
		//place dans un tableau les valeurs possible et incrément le compteur de 1 a chaque fois.
		//la vérification ce fais en regardans successivement la case la ligne et les colone. (peut être peut t'on amméliorrer ça.
		for (int k=0; k<9; k++) {
			if (verifCase(k+1, quelleCase(i, j)) && verifLigne(k+1, i) && verifColone(k+1, j)) {
				valeurPossible[cmp]=k+1;
				cmp++;
			}
		}
		if (cmp == 0) {//aucun chiffre possible 
			return cmp;
		}/*
		//placement dans un tableau de la bonne taille les valeurs possible puis en tire un au hazard.
		ne= new int [cmp];
		for (int k=0; k<cmp; k++) 
			ne[k]=valeurPossible[k];
		num =(int)((Math.random()*(cmp)));

	return ne[num];*/
		return valeurPossible[(int)((Math.random()*(cmp)))];
	}
	
	/*methode qui genere la case entiere qui porte le numero passer en parametre
	 */
	private void genere1Case(int cmpCase){
		int num,cmplong=0,impossible=0;
		int impos=0;//si boucle infini cela veut dire que la case est impossible a generer dans la grille actuelle donc va effacer la case precedente
		for (int i = numDebutI(cmpCase),cmp=0 ; cmp < 3 ; cmp++,i++) {
			for (int j = numDebutJ(cmpCase),cmpJ=0 ; cmpJ < 3 ; cmpJ++,j++) {
				num=genere1Chiffre(i,j);
				while ((verifCase(num,cmpCase) == false || verifLigne(num,i)==false || verifColone(num,j)==false)){
					num=genere1Chiffre(i,j);
					if (cmplong==20 || num==0) {//il y a une erreur quelque part efface la case actuelle
						cmplong=0;
						videCase(cmpCase);
						i=numDebutI(cmpCase);j = numDebutJ(cmpCase);
						cmp=0;cmpJ=0;
					}
					impos++;
					if (impos > 50) {//cas ou la case actuelle est impossible a faire efface la case précédente
						videCase(cmpCase-1);
						genere1Case(cmpCase-1);
						impos=0;
						impossible+=1;
					}//
					if (impossible>1000) {//cas ou la case précédente a déja été effacé trop de fois recommencer le sudoku
						impossible=0;
						impossible(cmpCase);
						
					}
					cmplong++;		//voir si cmplong sert vraiment a quelque chose ????			
				}				
				grille[i][j]=num;
				resultat[i][j]=true;
			}
		}		
	}
	/*supprime toute les case et regenere les case jusqu'a la cmpCase */
	private void impossible(int cmpCase){
		for (int y=1; y<cmpCase; y++) 	videCase(y);		
		for (int y=1; y<9; y++) 	genere1Case(y);
	}
	/*vide la case passer en parrametre*/
	private void videCase(int carre){
		for (int i = numDebutI(carre),cmp=0 ; cmp < 3 ; cmp++,i++) {
			for (int j = numDebutJ(carre),cmpJ=0 ; cmpJ < 3 ; cmpJ++,j++){ 
				grille[i][j]=0;
				resultat[i][j]=false;
			}
		}
	}
	/*methode qui genere une grille entiere 9*9 numero */
	private void genere(){
		for (int cmpCase = 1; cmpCase < 10; cmpCase+=1) {	genere1Case(cmpCase);
		}
	}
	public int getChiffre(int ligne,int colone){
		return grille[ligne][colone];
	}

}
class TestSudoku{
	public static void main(String [] args) {
		Sudoku sud=new	 Sudoku(3);
		System.out.println(sud);
 	}
}
