package sudoku;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;


/*affiche le bouton genere, charger et quiter*/
class FenetreMenu extends JFrame {
	protected JTextField[][] champ;
	private JButton b_genere, b_quitte, b_retablir; 

	/*genere une fenetre avec 2 boutons "generer" et "quitter" */
	public FenetreMenu(){
		setTitle("Menu Sudoku");
		Container cf = this.getContentPane(); 
		
		JPanel p3= new JPanel();
		p3.setLayout(new FlowLayout(FlowLayout.RIGHT) );
		
		b_genere = new JButton("Generer");
		b_quitte = new JButton("Quitter");
		b_retablir = new JButton("Charger");
		p3.add(b_genere);
		p3.add(b_quitte);
		p3.add(b_retablir);
		cf.add("South", p3);
		Bretablir b0 = new Bretablir();
		b_retablir.addActionListener((ActionListener) b0);
		Bgenere b1 = new Bgenere();
		b_genere.addActionListener(b1);
		Bquite b2 = new Bquite();
		b_quitte.addActionListener(b2);

	}
}
/**/

class FenetreSudo extends JFrame implements Serializable{	
	private JButton b_corrige,b_ok, sauvegarde;
	public SudokuARemplir s1;
	private JPanel pC1,pC2,pC3,pC4,pC5,pC6,pC7,pC8,pC9;
	
	/*renvoit la case correspondant au numero c passer en parametre*/
	private	JPanel ChoixCase(int c){
		if (c==1) 	return pC1;
		
		if (c==2) 	return pC2;
		
		if (c==3) 	return pC3;
		
		if (c==4) 	return pC4;
		
		if (c==5) 	return pC5;
		
		if (c==6) 	return pC6;
		
		if (c==7) 	return pC7;
		
		if (c==8) 	return pC8;
		return pC9;
	}
	
	public FenetreSudo(){
		s1=new SudokuARemplir();
		initFenetre();
	}

	public FenetreSudo(SudokuARemplir s){
		s1=s;
		initFenetre();
	}
	
	public void initFenetre(){
		setTitle("Sudoku");
		Container cf= this.getContentPane();

		//Creation d'un "tableau" de 3*3 cases pour mettre les 9 cases dedans
		GridLayout grille1=new GridLayout(3,3);
		JPanel pgrille =new JPanel();
		pgrille.setLayout(grille1);
		//creation des 9 cases du Sudoku puis des bordures 

		pC1=petitCarre(); pC2=petitCarre(); pC3=petitCarre(); pC4=petitCarre(); pC5=petitCarre();
		pC6=petitCarre(); pC7=petitCarre(); pC8=petitCarre(); pC9=petitCarre();
		
		setBorder(pC1);setBorder(pC2);setBorder(pC3);setBorder(pC4);setBorder(pC5);
		setBorder(pC6);setBorder(pC7);setBorder(pC8);setBorder(pC9);
		//
		
		JPanel p2;
		int c;
		for (int i=0; i<9; i++) {
			for (int j=0; j<9; j++) {
				c=s1.qCase(i, j);
				p2=ChoixCase(c);
				if(s1.getVisible(i,j)){//quand getVisible()retourne vrai placement dans la case d'une zone de texte non modifiable
					c=s1.qCase(i, j);
					 
					p2.add(new JLabel(s1.getValeur(i, j)));
					p2.setOpaque(true);
					
					if (c==1||c==5||c==9||c==7||c==3) 
						p2.setBackground(Color.GRAY);
				}else{ 
					JTextField zoneDeTexte= new Casse(i,j,s1).retour();
					if (s1.getResul(i, j)) {
						zoneDeTexte.setText(s1.getValeur(i, j));
					}
					p2.add(zoneDeTexte);
				}
			}
		}
		pgrille.add(pC1);
		pgrille.add(pC2);
		pgrille.add(pC3);
		pgrille.add(pC4);
		pgrille.add(pC5);
		pgrille.add(pC6);
		pgrille.add(pC7);
		pgrille.add(pC8);
		pgrille.add(pC9);

		cf.add("Center", pgrille);
		
		JPanel p3=new JPanel();//ajout des bouton 
		b_corrige=new JButton("corriger");
		Bcorrige corige=new Bcorrige(s1);
		b_corrige.addActionListener(corige);
		
		sauvegarde= new JButton("sauvegarde");
		Bsauvegarder sss=new Bsauvegarder(s1);
		sauvegarde.addActionListener(sss);
		
		b_ok=new JButton("OK");//Creation du bouton
		Ok o=new Ok();//Creation de l'evenement
		b_ok.addActionListener(o);//association du bouton a l'evenement
		
		p3.add(sauvegarde);
		p3.add(b_corrige);
		p3.add(b_ok);//ajout du bouton a la suite du paneau
		cf.add("South",p3);
	}
	public void setBorder(JPanel g){
		g.setBorder(BorderFactory.createCompoundBorder(
				BorderFactory.createEmptyBorder(0, 0, 0, 0),
				BorderFactory.createLineBorder(Color.BLACK, 1)	)
			);
	}
	public JPanel petitCarre(){
		JPanel pC=new JPanel();
		GridLayout c1=new GridLayout(3,3);
		pC.setLayout(c1);
		return pC;
	}
}

//------------------------------------------------------------
//------------------- BOUTON FENETRE MENU --------------------
//------------------------------------------------------------

/*ne sert a rien */
class paneau extends JPanel{
	public void paintcomponent(Graphics g){
		g.drawRect(10, 10, 50, 50);
		g.fillRect(65, 65, 30, 40);
	}
}

/*quitte le programme reprend la main dans le terminal*/
class Bquite implements ActionListener{
	public void actionPerformed(ActionEvent e){
		System.out.println("fin par bouton quitte");
		System.exit(0);
	}
}

/*instancie une fentreSudo et la rend visible*/
class Bgenere implements ActionListener{//pour fenetreMenu 
	public FenetreSudo f2;//amodifier 
	public void actionPerformed(ActionEvent e){
		
		f2 = new FenetreSudo();
		f2.pack();
		f2.setVisible(true);
		//while (true) 
		//	System.out.println(""+f2.s1);
		
		
	}
}
//
class Bretablir implements ActionListener{
	private SudokuARemplir sudo;
	
	public void actionPerformed(ActionEvent e){
		try{
			FileInputStream f = new FileInputStream("Sudoku.sdku");
			ObjectInputStream s = new ObjectInputStream(f);
			sudo= (SudokuARemplir) s.readObject();
			FenetreSudo f1 = new FenetreSudo(sudo);
			f1.pack();
			f1.setVisible(true);
			
		}catch (IOException a) {System.out.println("nouveau Fichier");}
		catch (ClassNotFoundException b) {System.out.println("probleme");}
	}
}
//------------------------------------------------------------
//------------------- BOUTON FENETRE SUDOKU ------------------
//------------------------------------------------------------

//serialise l'objet sudokuAremplir pour pouvoir le charger dans fenetreMenu
class Bsauvegarder implements ActionListener{
	private SudokuARemplir sudo;
	private java.io.ObjectOutputStream s;
	private java.io.FileOutputStream f;
	
	public Bsauvegarder(SudokuARemplir sAr){ sudo=sAr;}
	public void actionPerformed(ActionEvent e){
		try {
			f = new FileOutputStream("Sudoku.sdku");
			s = new ObjectOutputStream(f);
			s.writeObject(sudo);
			s.flush();
			s.close();
			f.close();
			System.out.println("Grille Sauvegarder");
		}
		catch (IOException ioe) {
			System.out.println("ERREUR IO : "+ioe);
		}
	}
}
/*ne fait rien pour l'instant*/
class Bcorrige implements ActionListener{
	private SudokuARemplir ss;
	public Bcorrige(SudokuARemplir s){
		ss=s;}
	public void actionPerformed(ActionEvent e){
		if(ss.verifGrille()) 
			System.out.println("grille true ");
		else 
			System.out.println("grille false ");
	}
}
//
/*ne fait rien pour l'instant*/
class Ok implements ActionListener{
	public void actionPerformed(ActionEvent e){
		System.out.println("t'as appuyer sur OK ");}
}
// casse et btext vont ensemble
// possibilité de suprimmer case pour le remplacer par une simple methode
class Casse{
	private SudokuARemplir s;
	private int i,j;
	private JTextField text;
	public Casse(int ii,int jj,SudokuARemplir ss){
		i=ii;j=jj;s=ss;
		text=new JTextField(1);
		Btext b = new Btext(text,ss,i,j);
		text.addActionListener(b);
		
	}
	/*retourne une zone de texte qui est lier a une case i,j
	 de l'objet ss de type sudokuARemplir  */
	public JTextField retour(){
		return text;
	}
}
class Btext implements ActionListener {
	private JTextField t;
	private SudokuARemplir s;
	private int i,j;
	public Btext(JTextField tt,SudokuARemplir ss,int ii,int jj){i=ii;j=jj;t=tt;s=ss;}
	
	public void actionPerformed(ActionEvent e){
		//faire en sorte que l'on ne peux pas entrer autre que 1 2 3 4 5  6 7 8 9 
		int res= Integer.parseInt(t.getText());
		s.setGrille(i,j,res);
		System.out.println(""+s);
		//	return t;
	}
}
//
//------------------------------------------------------------
//------------------------- Class test  ----------------------
//------------------------------------------------------------

public class FenetreSudoku{
	 public static void main(String [] args) {
		 FenetreMenu f1 = new FenetreMenu();
		 f1.pack();
		 f1.setVisible(true);
		 	}
}
