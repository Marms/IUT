package model;

import java.io.*;

import model.GeneratorSudoku;

public class GeneratorSudoku implements Serializable {
	static protected int NBCARREGRILLE = 0;
	static protected int NBCARRE = 0;
	protected int[][] sudoku;

	public GeneratorSudoku(int nbCarre) {
		NBCARRE = nbCarre;
		NBCARREGRILLE = nbCarre*nbCarre;
		sudoku = new int[nbCarre*nbCarre][nbCarre*nbCarre];
		initGrille();
		genere();

	}
	/*place des zero dans chaque case de la grille*/
	private void initGrille() {
		for (int i = 0; i < NBCARREGRILLE; i++) {
			for (int j = 0; j < NBCARREGRILLE; j++) {
				sudoku[i][j] = 0;
			}
		}
	}

	public String toString() {
		String local = "grille: \n";
		for (int i = 0; i < NBCARREGRILLE; i++) {
			if (i%NBCARRE == 0)
				local += "\n";
			for (int j = 0; j < NBCARREGRILLE; j++) {
				if (j%NBCARRE == 0) {
					local += "\t";
				}
				local += sudoku[i][j]+" ";
			}

			local += "\n";
		}
		return local;
	}

	/** verifie si la ligne comporte une valeur en double */
	protected boolean verifLigne(int valeur, int ligne) {
			for (int i = 0; i<NBCARREGRILLE; i++) {
				if (sudoku[ligne][i] == valeur)
					return false;
			}
		return true;
	}
	/** verifie si la colone comporte une valeur en double */
	protected boolean verifColone(int valeur, int col) {
		for (int i = 0; i<NBCARREGRILLE; i++) {
			if (sudoku[i][col] == valeur)
				return false;
		}
		return true;
	}

	/** verifie si le carre comporte une valeur en double */
	protected boolean verifCase(int valeur, int carre){
		for (int i = numDebutI(carre), cmpI = 0; cmpI < NBCARRE; cmpI++, i++ )
			for (int j = numDebutJ(carre),cmpJ = 0; cmpJ < NBCARRE; cmpJ++, j++ )
				if (sudoku[i][j] == valeur)
					return false;
		return true;
	}

	/**renvoit le numero du petit carre correspondant i,j */
	public int quelleCase(int numLigne,int numColone) {
		return ((numLigne/NBCARRE)*NBCARRE+(numColone / NBCARRE))+1;
	}

	public static int numDebutI(int num){
		return ((num-1) / NBCARRE)*NBCARRE;
	}
	public static int numDebutJ(int num){
		return ((num-1) % NBCARRE)*NBCARRE;
	}
	/*methode qui genere un chiffre :
		cette methode calcul les chiffres possible que l'on peut mettre a la case grille[i][j]
		les stock dans un tableau puis renvois un numero au
		hazard parmi le tableau*/
	private int genere1Chiffre(int i,int j) {
		int [] valeursPossibles = new int [NBCARREGRILLE];
		int cmp = 0;
		for (int k = 0; k<NBCARREGRILLE; k++) {
			if (verifCase(k+1, quelleCase(i, j)) && verifLigne(k+1, i) && verifColone(k+1, j)) {
				valeursPossibles[cmp] = k+1;
				cmp++;
			}
		}
		if (cmp == 0) //aucun chiffre possible
			return 0;
		return valeursPossibles[ (int)( ( Math.random() * cmp ) )];
	}

	/*methode qui genere la case entiere qui porte le numero passer en parametre
	 */
	private void genere1Case(int cmpCase) {
		int num;
		int impossible = 0; //s'incrémente chaque fois qu'une case est effacer
		int impos = 0; //s'incrémente chaque fois qu'une case est effacer revient à zéro lorsque l'on efface la case prècédente

		for (int i = numDebutI(cmpCase),cmpI = 0 ; cmpI < NBCARRE ; cmpI++,i++) {
			for (int j = numDebutJ(cmpCase),cmpJ = 0 ; cmpJ < NBCARRE ; cmpJ++,j++) {
				num = genere1Chiffre(i,j);
				while (num == 0) {
					/* si le num == 0 c'est qu'il n'y aucune valeur possible pour la petite case i j
					 * et que la case est impossible à générer
					 * il faut donc effacer la case et la regénerer
					 */
					videCase(cmpCase);
					i = numDebutI(cmpCase);
					j = numDebutJ(cmpCase);
					cmpI = 0; cmpJ = 0;
					impos++;

					/* cas ou la case actuelle est impossible à générer il faut effacer la case précédente,
					 * la valeur 15 est arbitraire
					 */
					if (impos > 15) {
						System.out.println("case impossible\n");
						videCase(cmpCase-1);
						genere1Case(cmpCase-1);
						//impos = 0;
						impossible += 1;
					}
					/* cas ou la case précédente a déja été effacée trop de fois recommencer le sudoku
					 * souvent quand il ne reste que 3 2 case à générer,
					 * la valeur 20 est arbitraire
					 */
					if (impossible>30) {
						System.out.println("sudoku impossible");
						impossible = 0;
						impossible(cmpCase);
					}
					num = genere1Chiffre(i,j);
				}

				sudoku[i][j] = num;
			}
		}
	}
	/*supprime toute les case et regenere les case jusqu'a la cmpCase */
	private void impossible(int cmpCase) {
		cmpCase = NBCARREGRILLE;
		for (int y = 1; y<=cmpCase; y++)
			videCase(y);
		for (int y = 1; y<NBCARREGRILLE; y++)
			genere1Case(y);
	}
	/*vide la case passer en parrametre*/
	private void videCase(int carre) {
		for (int i = numDebutI(carre),cmp = 0 ; cmp < NBCARRE ; cmp++,i++) {
			for (int j = numDebutJ(carre),cmpJ = 0 ; cmpJ < NBCARRE ; cmpJ++,j++) {
				sudoku[i][j] = 0;
			}
		}
	}
	/*methode qui genere une grille entiere 9*9 numero */
	private void genere() {
		for (int cmpCase = 1; cmpCase <= NBCARREGRILLE; cmpCase += 1) {	genere1Case(cmpCase);
		}
	}
	public int getChiffre(int ligne,int colone) {
		return sudoku[ligne][colone];
	}

}

class TestSudoku {
	public static void main(String [] args) {
		GeneratorSudoku sud = new GeneratorSudoku(4);
		System.out.println(sud);
 	}
}