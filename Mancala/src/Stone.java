import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.geom.Ellipse2D;

import javax.swing.JPanel;


public class Stone extends JPanel {
	
	public void paintComponent(Graphics g){
		setColorOFStones(g);
		g.fillOval(0, 0, g.getClipBounds().width, g.getClipBounds().height);
	}

	private void setColorOFStones(Graphics g) {
		g.setColor (Color.red);
		
	}

	public Color format(ColorFormatter formatter){
		return formatter.formatPitColorGreen();
	}

}
