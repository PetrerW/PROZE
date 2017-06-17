/*
 * 
 */
import java.awt.Graphics;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.awt.image.ImageObserver;
import java.awt.image.ImageProducer;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
// TODO: Auto-generated Javadoc

/**
 * The Class Bubble.
 */
/*
 * @author PetrerW
 * 	Class to handle Bubbles on game screen and their position in a list
 */
public class Bubble{
	
	/** The img. */
	//A representing picture
	BufferedImage img;
	
	/** The position Y. */
	//Its position in the list
	 private double positionX,positionY;
	//Color
	/** The color. */
	/*
	 * y - yellow
	 * b - blue
	 * g - green
	 * p - purple
	 * r - red
	 * m - multicolor
	 * o - orange
	 */
private	String color;

/** The color int. */
private	int colorInt;
	
	/** The y. */
	//Localization on game field
	int x,y;

	/**
	 * Instantiates a new bubble.
	 */
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

	/**
	 * Instantiates a new bubble.
	 *
	 * @param color the color
	 */
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

	/**
	 * Instantiates a new bubble.
	 *
	 * @param color the color
	 */
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

	/**
	 * Instantiates a new bubble.
	 *
	 * @param f the f
	 */
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
		colorInt=determineColorInt(sb.toString());
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

	/**
	 * Instantiates a new bubble.
	 *
	 * @param f the f
	 * @param sb the sb
	 */
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

	/**
	 * Instantiates a new bubble.
	 *
	 * @param img the img
	 */
	/*
	 * Bubble constructor that gets an already existing image as argument
	 */
	Bubble (BufferedImage img){
		this.img = img;
	}

	/**
	 * Determine color int.
	 *
	 * @param color the color
	 * @return the int
	 */
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

	/**
	 * Determine color.
	 *
	 * @param colorIntt the color intt
	 * @return the string
	 */
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
	
	/**
	 * Gets the x position.
	 *
	 * @return the x position
	 */
	public int getXPosition()
	{
		return (int)positionX;
	}
	
	/**
	 * Gets the y position.
	 *
	 * @return the y position
	 */
	public int getYPosition()
	{
		return (int)positionY;
	}
	
	/**
	 * Gets the double X position.
	 *
	 * @return the double X position
	 */
	public double getDoubleXPosition()
	{
		return positionX;
	}
	
	/**
	 * Gets the double Y position.
	 *
	 * @return the double Y position
	 */
	public double getDoubleYPosition()
	{
		return positionY;
	}
	
	/**
	 * Sets the position.
	 *
	 * @param x the x
	 * @param y the y
	 */
	public void setPosition(int x, int y)
	{
		this.positionX=(double)x;
		this.positionY=(double)y;
	}

	/**
	 * Sets the position.
	 *
	 * @param x the x
	 * @param y the y
	 */
	public void setPosition(double x, double y)
	{
		this.positionX=x;
		this.positionY=y;
	}
	
	/**
	 * Gets the color int.
	 *
	 * @return the color int
	 */
	public int getColorInt()
	{
		return colorInt;
	}
	
	/**
	 * Gets the color string.
	 *
	 * @return the color string
	 */
	public String getColorString()
	{
		return color;
	}
	
	/**
	 * Sets the color int.
	 *
	 * @param color the new color int
	 */
	public void setColorInt(int color)
	{
		colorInt=color;
	}
	
	/**
	 * Sets the color string.
	 *
	 * @param color the new color string
	 */
	public void setColorString(String color)
	{
		this.color=color;
	}
}