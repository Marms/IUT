package vue;

import javax.swing.*;

import vue.corrigeListener;
import vue.sauvegarderListener;
import vue.Btext;
import vue.Casse;
import vue.FenetreSudo;
import vue.OkListener;

import model.GrilleGamer;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.Serializable;


/**/

class FenetreSudo extends JFrame implements Serializable{	
	private JButton b_corrige,b_ok, sauvegarde;
	public GrilleGamer s1;
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
		s1=new GrilleGamer();
		initFenetre();
	}

	public FenetreSudo(GrilleGamer s){
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
				c=s1.quelleCase(i, j);
				p2=ChoixCase(c);
				if(s1.getVisible(i,j)){//quand getVisible()retourne vrai placement dans la case d'une zone de texte non modifiable
					c=s1.quelleCase(i, j);
					 
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
		corrigeListener corige=new corrigeListener(s1);
		b_corrige.addActionListener(corige);
		
		sauvegarde= new JButton("sauvegarde");
		sauvegarderListener sss=new sauvegarderListener(s1);
		sauvegarde.addActionListener(sss);
		
		b_ok=new JButton("OK");//Creation du bouton
		OkListener o=new OkListener();//Creation de l'evenement
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
//------------------- BOUTON FENETRE SUDOKU ------------------
//------------------------------------------------------------

//serialise l'objet sudokuAremplir pour pouvoir le charger dans fenetreMenu
class sauvegarderListener implements ActionListener{
	private GrilleGamer s1;
	private java.io.ObjectOutputStream s;
	private java.io.FileOutputStream f;
	
	public sauvegarderListener(GrilleGamer sAr){ s1=sAr;}
	public void actionPerformed(ActionEvent e){
		try {
			f = new FileOutputStream("Sudoku.sdku");
			s = new ObjectOutputStream(f);
			s.writeObject(s1);
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
class corrigeListener implements ActionListener{
	private GrilleGamer s1;
	public corrigeListener(GrilleGamer s){
		s1=s;}
	@Override
	public void actionPerformed(ActionEvent e){
		if(s1.verifGrille())
			System.out.println("grille true ");
		else 
			System.out.println("grille false ");
	}
}
//
/*ne fait rien pour l'instant*/
class OkListener implements ActionListener{
	@Override
	public void actionPerformed(ActionEvent e){
		System.out.println("t'as appuyer sur OK ");}
}
// casse et btext vont ensemble
// possibilité de suprimmer case pour le remplacer par une simple methode
class Casse{
	private GrilleGamer s;
	private int i,j;
	private JTextField text;
	public Casse(int ii,int jj,GrilleGamer ss){
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
	private GrilleGamer s;
	private int i,j;
	public Btext(JTextField tt,GrilleGamer ss,int ii,int jj){i=ii;j=jj;t=tt;s=ss;}
	
	public void actionPerformed(ActionEvent e){
		//faire en sorte que l'on ne peux pas entrer autre que 1 2 3 4 5  6 7 8 9 
		int res= Integer.parseInt(t.getText());
		s.setGrille(i,j,res);
		System.out.println(""+s);
		//	return t;
	}
}

