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
 * 	Class to handle Bubbles on game screen and their position in a list
 */
public class Bubble{
	//A representing picture
	BufferedImage img;
	//Its position in the list
	 private double positionX,positionY;
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
		positionX = 0;
		positionY=0;
	}

	/*
	 * Assigns a number to every color.
	 * Makes organization of Color list easier.
	 */
	Bubble(int color){
		this.colorInt = color;
		//determine color as String
		this.color = Bubble.determineColor(this.colorInt);
		positionX = 0;
		positionY=0;
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

		positionX = 0;
		positionY=0;
		String path = "Graphics/" + this.color + ".png";
		try {
			img = ImageIO.read(new File(path));
		} catch (IOException e) {
			System.out.println("Failed to read file! Bubble(String color)");
			System.out.println(path);
			e.printStackTrace();
		}
	}

	/*
	 * Bubble constructor that gets an already existing file as argument
	 */
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
		positionX = 0;
		positionY=0;
		try{
			img = ImageIO.read(f);
		}
		catch (IOException e){
			System.out.println("Failed to read file!");
			e.printStackTrace();
		}
	}

	Bubble(File f, StringBuffer sb){
		//StringBuffer sb = new StringBuffer();
		sb.append(f.getName());
		if (sb.toString().contains("Graphics/")) {
			sb.delete(sb.indexOf("Graphics/"), sb.indexOf("Graphics/")+ 9/*Length of "Graphics/" */);
		}
		if (sb.toString().contains(".png")) {
			sb.delete(sb.indexOf(".png"), sb.indexOf(".png")+ 4/*Length of ".png" */);
		}
		this.color = sb.toString();
		positionX = 0;
		positionY=0;
		try{
			img = ImageIO.read(f);
		}
		catch (IOException e){
			System.out.println("Failed to read file!");
			e.printStackTrace();
		}
	}

	/*
	 * Bubble constructor that gets an already existing image as argument
	 */
	Bubble (BufferedImage img){
		this.img = img;
	}

	/*
	 * determine color as integer when having a String
	 * example. "Red" -> 4
	 */
	public static int determineColorInt(String color){
		int colorInt;
		switch(color)
		{
			case "Yellow": colorInt = 1;
				break;
			case "Blue": colorInt = 2;
				break;
			case "Green": colorInt = 3;
				break;
			case "Red" : colorInt = 4;
				break;
			case "Purple": colorInt = 5;
				break;
			case "Orange": colorInt = 6;
				break;
			default:
				colorInt = 1;
				break;
		}
		return colorInt;
	}

	/*
	 * determine Color as String, having already an int
	 * example: 4 -> "Red"
	 */
	public static String determineColor(int colorIntt){
		String color;
		switch(colorIntt)
		{
			case 1: color="Yellow";
				break;
			case 2: color="Blue";
				break;
			case 3: color="Green";
				break;
			case 4 : color="Red";
				break;
			case 5: color="Purple";
				break;
			case 6: color="Orange";
				break;
			default:
				color = "Yellow";
				break;
		}
		return color;
	}
	public int getXPosition()
	{
		return (int)positionX;
	}
	public int getYPosition()
	{
		return (int)positionY;
	}
	public double getDoubleXPosition()
	{
		return positionX;
	}
	public double getDoubleYPosition()
	{
		return positionY;
	}
	public void setPosition(int x, int y)
	{
		this.positionX=(double)x;
		this.positionY=(double)y;
	}

	public void setPosition(double x, double y)
	{
		this.positionX=x;
		this.positionY=y;
	}
}