package vue;

import java.awt.Container;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTextField;

import model.GrilleGamer;

	/*affiche le bouton genere, charger et quiter*/
	class FenetreMenu extends JFrame {
		protected JTextField[][] champ;
		private JButton b_genere, b_quitte, b_retablir; 
	
		/*genere une fenetre avec 2 boutons "generer" et "quitter" */
		public FenetreMenu(){
			this.b_genere = new JButton("Nouveau");
			this.b_quitte = new JButton("Quitter");
			this.b_retablir = new JButton("Charger");

			Container container = this.getContentPane();
			JPanel panel = new JPanel();
			panel.setLayout(new FlowLayout());
			
			b_genere.addActionListener(new genereListener());
			b_quitte.addActionListener(new quiteListener());
			b_retablir.addActionListener(new retablirListener());

			panel.add(b_genere);
			panel.add(b_quitte);
			panel.add(b_retablir);
			container.add(panel);
		}

		
	/*quitte le programme reprend la main dans le terminal*/
	class quiteListener implements ActionListener{
		@Override
		public void actionPerformed(ActionEvent e){
			System.out.println("fin par bouton quitte");
			System.exit(0);
		}
	}
	/*instancie une fentreSudo et la rend visible*/
	class genereListener implements ActionListener{//pour fenetreMenu 
		@Override
		public void actionPerformed(ActionEvent e){
			FenetreSudo f2;
			f2 = new FenetreSudo();
			f2.pack();
			f2.setVisible(true);
		}
	}

	class retablirListener implements ActionListener{
		@Override
		public void actionPerformed(ActionEvent e){
			GrilleGamer sudo;
			try{
				FileInputStream f = new FileInputStream("Sudoku.sdku");
				ObjectInputStream s = new ObjectInputStream(f);
				sudo= (GrilleGamer) s.readObject();
				FenetreSudo f1 = new FenetreSudo(sudo);
				f1.pack();
				f1.setVisible(true);
				
			}catch (IOException a) {System.out.println("nouveau Fichier");}
			catch (ClassNotFoundException b) {System.out.println("probleme");}
		}
	}

}
	//------------------------------------------------------------
	//------------------------- Class test  ----------------------
	//------------------------------------------------------------
	class FenetreSudoku{
		 public static void main(String [] args) {
			 FenetreMenu f1 = new FenetreMenu();
			 f1.pack();
			 f1.setVisible(true);
			 	}
	}