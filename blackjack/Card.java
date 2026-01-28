package blackjack;

import javax.swing.ImageIcon;
import javax.swing.JOptionPane;
import java.io.File;
import java.net.URL;

public class Card {
	
		private String name;
		private int pts;
		private ImageIcon icon;
		
		public Card(String name) {
			this.name = name;
			createValue();
			spawnImage();
			
		}
		
	private void createValue() {
		String[] nameParts = this.name.split("_");
		int rank = Integer.parseInt(nameParts[0]);
		if (rank == 1) {
			this.pts = 1; // Ace, can be 1 or 11 (handled in Hand)
		} else if (rank >= 11) {
			this.pts = 10; // Face cards
		} else {
			this.pts = rank;
		}
	}
	private void spawnImage() {
		URL resource = Card.class.getResource("/imagesCard/" + this.name);
		if (resource != null) {
			this.icon = new ImageIcon(resource);
			return;
		}
		File local = new File("imagesCard", this.name);
		if (local.exists()) {
			this.icon = new ImageIcon(local.getPath());
			return;
		}
		JOptionPane.showMessageDialog(null, "Images not found");
		System.exit(0);
	}
		public String getName() {
			return this.name;
		}
		
		public int getPts() {
			return this.pts;
		}
		public ImageIcon getIcon() {
			return this.icon;
		}
		@Override
		public String toString() {
		    return this.name + " = " + this.pts;
		}

	}
