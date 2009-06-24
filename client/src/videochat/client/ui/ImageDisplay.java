/**
 * 
 */
package videochat.client.ui;

import java.awt.Canvas;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.IOException;

import javax.imageio.ImageIO;


/**
 * Displays an image. Extends canvas and can be added to any control
 *
 * @author "ppetkov" (Jun 24, 2009)
 *
 * <br><b>History:</b> <br>
 * Jun 24, 2009 "ppetkov" created <br>
 */
class ImageDisplay extends Canvas {
	private static final long serialVersionUID = -554909844771551841L;
	private Image image;
	private BufferedImage backBuffer;
	public ImageDisplay() {
		super();
		setSize(new Dimension(128, 128));
		backBuffer = new BufferedImage(128, 128, BufferedImage.TYPE_INT_BGR);
	}
	
	public void setJpegData(byte[] data){
		
		try {
			setImage(ImageIO.read(new ByteArrayInputStream(data)));
		}catch (IOException e) {
			setImage(null);
		}
	}
	
	public void setImage(Image i) {
		image = i;
		repaint();
	}
	/* (non-Javadoc)
	 * @see java.awt.Canvas#update(java.awt.Graphics)
	 */
	@Override
	public void update(Graphics gr) {
		Graphics g = backBuffer.getGraphics();
		g.setColor(Color.BLACK);
		g.fillRect(0, 0, 128, 128);
		if (image != null) {
			g.drawImage(image, 
					(128 - image.getWidth(this)) / 2, 
					(128 - image.getHeight(this)) / 2, this);
		} 
		gr.drawImage(backBuffer, 0, 0, null);
	}
	public void paint(Graphics g) {
		update(g);
	}
}