package sudoku;

import java.io.*;
public class Sudoku implements Serializable {
	private int tailleGrille;
	protected int[][] sudoku;
	private int tailleCarre;

	public Sudoku(int a) {
		//		grilleDuJoueur = new int[9][9];
		tailleCarre = a;
		tailleGrille = a*a;
		sudoku = new int[a*a][a*a];
		initGrille();
		genere();

	}
	/*place des zero dans chaque case de la grille*/
	private void initGrille() {
		for (int i = 0; i<tailleGrille; i++) {
			for (int j = 0; j<tailleGrille; j++) {
				sudoku[i][j] = 0;
			}
		}
	}

	public String toString() {
		String local = "grille: \n";
		for (int i = 0; i < 9; i++) {
			if (i%3 == 0)
				local += "\n";
			for (int j = 0; j < 9; j++) {
				if (j%3 == 0) {
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
			for (int i = 0; i<tailleGrille; i++) {
				if (sudoku[ligne][i] == valeur)
					return false;
			}
		return true;
	}
	/** verifie si la colone comporte une valeur en double */
	protected boolean verifColone(int valeur, int col) {
		for (int i = 0; i<tailleGrille; i++) {
			if (sudoku[i][col] == valeur)
				return false;
		}
		return true;
	}

	/** verifie si le carre comporte une valeur en double */
	protected boolean verifCase(int valeur, int carre){
		for (int i = numDebutI(carre), cmpI = 0; cmpI < 3; cmpI++, i++ )
			for (int j = numDebutJ(carre),cmpJ = 0; cmpJ < 3; cmpJ++, j++ )
				if (sudoku[i][j] == valeur)
					return false;
		return true;
	}

	/**renvoit le numero du petit carre correspondant i,j */
	public int quelleCase(int numLigne,int numColone) {
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
	private int genere1Chiffre(int i,int j) {
		int [] valeursPossibles = new int [9];
		int cmp = 0;
		for (int k = 0; k<9; k++) {
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

		for (int i = numDebutI(cmpCase),cmpI = 0 ; cmpI < 3 ; cmpI++,i++) {
			for (int j = numDebutJ(cmpCase),cmpJ = 0 ; cmpJ < 3 ; cmpJ++,j++) {
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
						System.out.println("case impossible");
						videCase(cmpCase-1);
						genere1Case(cmpCase-1);
						impos = 0;
						impossible += 1;
					}
					/* cas ou la case précédente a déja été effacée trop de fois recommencer le sudoku
					 * souvent quand il ne reste que 3 2 case à générer,
					 * la valeur 20 est arbitraire
					 */
					if (impossible>20) {
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
		for (int y = 1; y<cmpCase; y++)
			videCase(y);
		for (int y = 1; y<9; y++)
			genere1Case(y);
	}
	/*vide la case passer en parrametre*/
	private void videCase(int carre) {
		for (int i = numDebutI(carre),cmp = 0 ; cmp < 3 ; cmp++,i++) {
			for (int j = numDebutJ(carre),cmpJ = 0 ; cmpJ < 3 ; cmpJ++,j++) {
				sudoku[i][j] = 0;
			}
		}
	}
	/*methode qui genere une grille entiere 9*9 numero */
	private void genere() {
		for (int cmpCase = 1; cmpCase < 10; cmpCase += 1) {	genere1Case(cmpCase);
		}
	}
	public int getChiffre(int ligne,int colone) {
		return sudoku[ligne][colone];
	}

}

class TestSudoku {
	public static void main(String [] args) {
		Sudoku sud = new	 Sudoku(3);
		System.out.println(sud);
 	}
}