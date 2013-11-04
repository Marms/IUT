package vue;

import javax.swing.*;

import vue.Casse;
import vue.FenetreSudo;

import model.GrilleGamer;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;


/**/

class FenetreSudo extends JFrame implements Serializable{	
	private JButton b_corrige,b_ok, sauvegarde;
	public GrilleGamer s1;
	private List<JPanel> pC;//sert à rien pour l'instant

	public FenetreSudo(){
		s1=new GrilleGamer();
		initAttr();
		initFenetre();
	}

	public FenetreSudo(GrilleGamer s){
		s1=s;
		initAttr();
		initFenetre();
	}

	public void initAttr(){
		this.pC = new ArrayList<JPanel>();
		this.b_corrige=new JButton("corriger");
		this.b_ok=new JButton("OK");
		this.sauvegarde= new JButton("sauvegarde");
	}

	public void initFenetre(){
		setTitle("Sudoku");
		Container container=this.getContentPane();

		//Creation d'un "tableau" de 3*3 cases pour mettre les 9 cases dedans
		GridLayout layoutGrille=new GridLayout(3,3);
		JPanel grillePanel=new JPanel();
		grillePanel.setLayout(layoutGrille);

		//generation des grandes cases une par une
		for (int cmp=0; cmp<9; cmp++){
			JPanel carre =carrePanel(cmp);
			pC.add(carre);
			grillePanel.add(pC.get(cmp));
		}

		//association des boutons aux evenements
		sauvegarde.addActionListener(new sauvegarderListener());
		b_corrige.addActionListener(new corrigeListener());
		b_ok.addActionListener(new OkListener());

		JPanel boutonPanel=new JPanel();
		boutonPanel.add(sauvegarde);
		boutonPanel.add(b_corrige);
		boutonPanel.add(b_ok);

		container.add("Center", grillePanel);
		container.add("South",boutonPanel);
	}

	public void setBorderCarre(JPanel g){
		g.setBorder(BorderFactory.createCompoundBorder(
				BorderFactory.createEmptyBorder(0, 0, 0, 0),
				BorderFactory.createLineBorder(Color.BLACK, 1)	)
		);
	}

	/** Renvois un JPanel avec un GridLayout 3*3
	 **/

	public JPanel carrePanel(int cmp){
		JPanel carre=new JPanel();
		carre.setLayout(new GridLayout(3,3));
		//generation des cases contenu dans le panelCarre
		for (int i=0; i<3; i++) {
			for (int j=0; j<3; j++){
				String val = s1.getValeur(i+(3*(cmp/3)),j+(3*(cmp%3)));

				if(val.compareTo("0") > 0){	//si valeur > 0 la case est découverte
					carre.add(new JLabel(val));
					carre.setOpaque(true);

					if (cmp %2 != 0)
						carre.setBackground(Color.GRAY);
				}else{
					JTextField zoneDeTexte= new Casse(i,j,s1).retour();//simplifier en ne donnant pas s1????
					carre.add(zoneDeTexte);
				}
			}
		}
		setBorderCarre(carre);
		return carre;
	}

//------------------------------------------------------------
//------------------------- Listener -------------------------
//------------------------------------------------------------
	class OkListener implements ActionListener{
		@Override
		public void actionPerformed(ActionEvent e){
			System.out.println("t'as appuyer sur OK ");
		}
	}

	/*ne fait rien pour l'instant*/
	class corrigeListener implements ActionListener{
		@Override
		public void actionPerformed(ActionEvent e){
			if(s1.verifGrille())
				System.out.println("grille true ");
			else
				System.out.println("grille false ");
		}
	}

	//serialise l'objet sudokuAremplir pour pouvoir le charger dans fenetreMenu
	class sauvegarderListener implements ActionListener{
		@Override
		public void actionPerformed(ActionEvent e){
			try {
				java.io.FileOutputStream  f = new FileOutputStream("Sudoku.sdku");
				java.io.ObjectOutputStream s = new ObjectOutputStream(f);
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
}

//
// casse et btext vont ensemble
// possibilité de suprimmer case pour le remplacer par une simple methode
class Casse{
	private GrilleGamer s;
	private int i,j;
	private JTextField text;

	public Casse(int ii,int jj,GrilleGamer ss){
		i=ii;j=jj;s=ss;
		text=new JTextField(1);
		TextListener b = new TextListener(text,ss,i,j);
		text.addActionListener(b);

	}
	/*retourne une zone de texte qui est lier a une case i,j
	 de l'objet ss de type sudokuARemplir  */
	public JTextField retour(){
		return text;
	}
}
class TextListener implements ActionListener {
	private JTextField t;
	private GrilleGamer s;
	private int i,j;
	public TextListener(JTextField tt,GrilleGamer ss,int ii,int jj){i=ii;j=jj;t=tt;s=ss;}

	public void actionPerformed(ActionEvent e){
		//faire en sorte que l'on ne peux pas entrer autre que 1 2 3 4 5  6 7 8 9 
		int res= Integer.parseInt(t.getText());
		s.setGrille(i,j,res);
		System.out.println(""+s);
		//	return t;
	}
}
