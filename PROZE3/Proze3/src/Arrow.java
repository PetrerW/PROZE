import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

public class Arrow {
	public BufferedImage img;
	private int length;
	private int width;
	public int beginningPositionX;
	public int beginningPositionY;
	
	Arrow(){
		img = new BufferedImage(1,1,1);
		try {
			img = ImageIO.read(new File("Graphics/Arrow.png"));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	public void setSize(int l, int w){
		length = l;
		width = w;
	}

	
	public int getLength() {return length;}
	public int getWidth() {return width; }
}
