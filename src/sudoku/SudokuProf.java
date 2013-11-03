package sudoku;

public class SudokuProf{
	private int [][] grille;
//================CONSTRUCTEUR========================
	public SudokuProf(int [][] g){	
		grille=g;	
	}
	public SudokuProf(){	
		grille=new int[9][9];	
	}
	public SudokuProf(String ligne){ 
		grille=new int[9][9];
		int index;
		for (int i=0; i<9; i++) {
			for (int j=0; j<9; j++) {
				index=(i*9)+j;
				if (index >= ligne.length())
					grille[i][j]=0;
				else				
					placement(ligne.charAt(index), i, j);
			}	
		}	
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
		}
		return true;
	}	
	public boolean estValide(){
	 for (int i=0; i<9; i++) {
		 if (!est1PetitCarreValide(i))		
			 return false;
	 }return true;
	 
	}
	//
	public boolean placement(char digit, int numLigne, int numColone){
		if (!estDansPC(digit,numPC(numLigne,numColone)) && !estDansLaLigne(digit, numLigne) && !estDansColone(digit, numColone) ) {
			grille[numLigne][numColone]=convertiCharToInt(digit);
			return true;
		}else{
			return false;
		}
	}
	public boolean estDansLaLigne(char digit, int numLigne){ if(digit=='0')return false;
		int b=convertiCharToInt(digit),	a;
		for (int i=0; i<9; i++) {
			a=grille[numLigne][i];
			if (a==b) return true;
		}return false;
	}		//
	public boolean estDansColone(char digit, int numColone){ if(digit=='0')return false;
		int b=convertiCharToInt(digit),a;
		for (int i=0; i<9; i++) {
			a=grille[i][numColone];
			if (a==b) return true;
		}return false;
		
	}		//
	public boolean estDansPC(char digit, int numPC){	if(digit=='0')return false;
		int b=convertiCharToInt(digit),a;
		for (int i= numLigneDebPC(numPC); i<numLigneDebPC(numPC)+3; i++) {
			for (int j = numColoneDebPC(numPC); j<numColoneDebPC(numPC )+3;j++) {
				a=grille[i][j];
				if(a == b)return true;
			}
		}
		return false;
	}				//	
	/**convertie un char en int*/
	public int convertiCharToInt(char digit){
		try {
			String a="";
			a+=digit;
			return Integer.parseInt(a);
		}
		catch (java.lang.NumberFormatException e) {
			System.out.println("***** ERREUR DE DONNEE ****");
		}
		return 0;
	}
	/**renvoit num carre de 0 a 8*/
	public int numPC(int numLigne,int numColone){ 
		return ((numLigne/3)*3+(numColone / 3))+1;}			//++++
	/**prend en parametre un carre de 1 a 9 et renvois le numero d'index de la ligne du coin superieur gauche soit 0 3 6*/
	public int numLigneDebPC(int num){ return (num / 3)*3;}		//++++
	/**prend en parametre un carre de 1 a 9 et renvois le numero d'index de la colone du coin superieur gauche soit 0 3 6*/
	public int numColoneDebPC(int num){	return (num % 3)*3;}	//++++
	
	public static void main(String [] args) {
		SudokuProf s1=new SudokuProf();
		System.out.println("s1 "+s1);
		String ligne="00001111000011101010101010101001010101";
		SudokuProf s2=new SudokuProf(ligne);
		System.out.println("ssss "+s2);
	}
}