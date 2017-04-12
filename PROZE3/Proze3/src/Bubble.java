import java.awt.Graphics;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.awt.image.ImageObserver;
import java.awt.image.ImageProducer;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
/*
 * @author PetrerW
 * @description:
 * 	Class to handle Bubbles on game screen and their position in a list
 */
public class Bubble{
	//A representing picture
	BufferedImage img;
	//Its position in the list
	int position;
	//Color
	/*
	 * y - yellow
	 * b - blue
	 * g - green
	 * p - purple
	 * r - red
	 * m - multicolor
	 * o - orange
	 */
	String color;
	int colorInt;
	//Localization on game field
	int x,y;
	
	Bubble(){
		img = new BufferedImage(1,1,1);
		try {
			img = ImageIO.read(new File("Graphics/Blue.png"));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	color = "Blue";
	position = 0;
	}
	
	Bubble(int color){
		this.colorInt = color;
		switch(colorInt)
		{
			case 1: this.color="Yellow";
			break;
			case 2: this.color="Blue";
			break;
			case 3: this.color="Green";
			break;
			case 4 : this.color="Red";
			break;
			case 5: this.color="Purple";
			break;
			case 6: this.color="Orange";
			break;
			default:
				break;
		}
		position = 0;
		String path = "Graphics/" + this.color + ".png";
		try {
			img = ImageIO.read(new File(path));
		} catch (IOException e) {
			System.out.println("Failed to read file! Bubble(String color)");
			System.out.println(path);
			e.printStackTrace();
		}
	}

	Bubble(String color){
		this.color = color;

		position = 0;
		String path = "Graphics/" + this.color + ".png";
		try {
			img = ImageIO.read(new File(path));
		} catch (IOException e) {
			System.out.println("Failed to read file! Bubble(String color)");
			System.out.println(path);
			e.printStackTrace();
		}
	}
	
	Bubble(File f){
		StringBuffer sb = new StringBuffer();
		sb.append(f.getName());
		if (sb.toString().contains("Graphics/")) {
		    sb.delete(sb.indexOf("Graphics/"), sb.indexOf("Graphics/")+ 9/*Length of "Graphics/" */);
		}
		if (sb.toString().contains(".png")) {
		    sb.delete(sb.indexOf(".png"), sb.indexOf(".png")+ 4/*Length of ".png" */);
		}
		this.color = sb.toString();
		position = 0;
		try{
			img = ImageIO.read(f);
		}
		catch (IOException e){
			System.out.println("Failed to read file!");
			e.printStackTrace();
		}
	}
	
	Bubble (BufferedImage img){
		this.img = img;
	}
}
