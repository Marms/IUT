package vue;

import javax.swing.*;

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

class FenetreSudo extends JFrame implements Serializable{	
	private JButton b_corrige,b_ok, sauvegarde;
	public GrilleGamer game;
	private List<JPanel> listCarre; //sert à rien pour l'instant
	private static int TAILLE_SUDOKU, TAILLE_CARRE; //TAILLE_SUDOKU = TAILLE_CARRE * TAILLE_CARRE (max < 5)

	public FenetreSudo(){
		game=new GrilleGamer(1);
		initAttr();
		initFenetre();
	}

	public FenetreSudo(GrilleGamer s){
		game=s;
		initAttr();
		initFenetre();
	}

	public void initAttr(){
		this.listCarre = new ArrayList<JPanel>();
		this.b_corrige=new JButton("corriger");
		this.b_ok=new JButton("OK");
		this.sauvegarde= new JButton("sauvegarde");
		this.TAILLE_SUDOKU = game.getTailleTotal();
		this.TAILLE_CARRE = game.getTaille();
	}

	public void initFenetre(){
		setTitle("Sudoku");
		Container container=this.getContentPane();

		//Creation d'un "tableau" de 3*3 cases pour mettre les cases dedans
		GridLayout layoutGrille=new GridLayout(TAILLE_CARRE, TAILLE_CARRE);
		JPanel grillePanel=new JPanel();
		grillePanel.setLayout(layoutGrille);

		//generation des grandes cases une par une
		for (int cmp=0; cmp<TAILLE_SUDOKU; cmp++){
			JPanel carre =carrePanel(cmp);
			listCarre.add(carre);
			grillePanel.add(listCarre.get(cmp));
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

	/***/
	public JPanel carrePanel(int cmp){
		JPanel carre=new JPanel();
		carre.setLayout(new GridLayout(TAILLE_CARRE, TAILLE_CARRE));
		//generation des cases contenu dans le panelCarre
		for (int i=0; i<TAILLE_CARRE; i++) {
			for (int j=0; j<TAILLE_CARRE; j++){
				int x = i+(TAILLE_CARRE*(cmp/TAILLE_CARRE));
				int y = j+(TAILLE_CARRE*(cmp%TAILLE_CARRE));

				String val = game.getValeur(x, y);
				if(val.compareTo("0") > 0){	//si valeur > 0 la case est découverte
					carre.add(new JLabel(val));
					carre.setOpaque(true);

					if (cmp %2 != 0)
						carre.setBackground(Color.GRAY);
				}else{
					JTextField zoneDeTexte= new JTextField(2);//sert a rien ???
					zoneDeTexte.addActionListener(new TextListener(zoneDeTexte, x, y));
					carre.add(zoneDeTexte);
				}
			}
		}
		carre.setBorder(BorderFactory.createCompoundBorder(
				BorderFactory.createEmptyBorder(0, 0, 0, 0),
				BorderFactory.createLineBorder(Color.BLACK, 1))
		);
		return carre;
	}
	//------------------------- Listeners -------------------------

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
			if(game.verifGrille())
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
				s.writeObject(game);
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

	class TextListener implements ActionListener {
		private JTextField text;
		private int i,j;

		public TextListener(JTextField text, int coordX,int coordY){
			this.i=coordX;
			this.j=coordY;
			this.text=text;
		}

		@Override
		public void actionPerformed(ActionEvent e){
			//faire en sorte que l'on ne peux pas entrer autre que 1 2 3 4 5  6 7 8 9 
			int res= Integer.parseInt(text.getText());
			game.setGrille(i,j,res);
			System.out.println(""+game);
		}
	}

}