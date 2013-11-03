package model;
/**Cette class ne sert qu'a corriger le sudoku 
 * J'aurais très bien pu mettre cela dans la class SudokuARemplir */

public class Correction {

	private int [][] grille;
//================CONSTRUCTEUR========================
	public Correction(int [][] g){	
		grille=g;	
	}
	
	public String toString(){String sudo="Sudoku: \n";
		for (int i=0; i<9; i++) {
			for (int j=0; j<9; j++) {
				sudo+=grille[i][j];
			}sudo+="\n";
		}
		return sudo;
	}
	/**prend en parametre une ligne de 0 a 9*/
	public boolean est1LigneValide(int numLigne){
		boolean [] verif= new boolean[10];
		for( int i=0;i<9;i++){
			if (grille[numLigne][i]!=0) {// pour ne pas prendre en compte les case vides
				if (!verif[grille[numLigne][i]]) verif[grille[numLigne][i]]=true;
				else return false;
			}
		}return true;
	}
	public boolean est1ColoneValide(int numColone){
		boolean [] verif= new boolean[10];
		for( int i=0;i<9;i++){
			if (grille[i][numColone]!=0) {// pour ne pas prendre en compte les case vides
				if (!verif[grille[i][numColone]]) verif[grille[i][numColone]]=true;
				else return false;
			}
		}return true;
	}
	public boolean est1PetitCarreValide(int numPC){
		boolean [] verif= new boolean[10];
		for (int i=numLigneDebPC(numPC); i<numLigneDebPC(numPC)+3 ; i++) {
			for (int j=numColoneDebPC(numPC); j<numColoneDebPC(numPC)+3 ; j++) {
				if (grille[i][j]!=0){
					if (!verif[grille[i][j]]) verif[grille[i][j]]=true;
					else return false;
				}
			}
		}return true;
		}	
	public boolean estValide(){
	 for (int i=0; i<9; i++) {
		 if (!est1PetitCarreValide(i) || !est1LigneValide(i) || !est1ColoneValide(i))		
			 return false;
	 }return true;
	 
	}

	/**renvoit num carre de 0 a 8*/
	public int numPC(int numLigne,int numColone){ 
		return ((numLigne/3)*3+(numColone / 3))+1;}			//++++
	/**prend en parametre un carre de 1 a 9 et renvois le numero d'index de la ligne du coin superieur gauche soit 0 3 6*/
	public int numLigneDebPC(int num){ return (num / 3)*3;}		//++++
	/**prend en parametre un carre de 1 a 9 et renvois le numero d'index de la colone du coin superieur gauche soit 0 3 6*/
	public int numColoneDebPC(int num){	return (num % 3)*3;}	//++++
	
}