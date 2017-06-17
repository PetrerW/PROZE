/*
 * 
 */
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

// TODO: Auto-generated Javadoc
/**
 * The Class Arrow.
 */
public class Arrow {
	
	/** The img. */
	public BufferedImage img;
	
	/** The length. */
	private int length;
	
	/** The width. */
	private int width;
	
	/** The beginning position X. */
	public int beginningPositionX;
 	
	 /** The beginning position Y. */
	 public int beginningPositionY;

	/**
	 * Instantiates a new arrow.
	 */
	Arrow(){
		img = new BufferedImage(1,1,1);
		try {
			img = ImageIO.read(new File("Graphics/Arrow.png"));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	/**
	 * Sets the size.
	 *
	 * @param l the l
	 * @param w the w
	 */
	public void setSize(int l, int w){
		length = l;
		width = w;
	}
	
	/**
	 * Gets the length.
	 *
	 * @return the length
	 */
	public int getLength() {return length;}
	
	/**
	 * Gets the width.
	 *
	 * @return the width
	 */
	public int getWidth() {return width; }
}
