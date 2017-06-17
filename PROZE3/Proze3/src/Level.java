/*
 * 
 */
import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.net.URI;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Random;

// TODO: Auto-generated Javadoc
/**
 * The Class Level.
 */
public class Level {
	
	/** The color chooser. */
	//String[] colors = {"Yellow", "Blue", "Green", "Purple", "Red", "Multicolor", "Orange"};
	public static Random colorChooser;
	
	/** The color data. */
	//File[] colorFiles = {new File("Graphics/Yellow.png"), new File("Graphics/Blue.png"), new File("Graphics/Green.png"), new File("Graphics/Purple.png"), new File("Graphics/Red.png"), new File("Graphics/Multicolor.png"), new File("Graphics/Orange.png")};
	public static ColorData colorData;
	
	/** The explosion data. */
	//A class with images of exploding bubbles
	public static ExplosionImageData explosionData;
	
	/** The max color. */
	//A variable that says how much colors will be in the app
	int maxColor;
	
	/** The how hard. */
	private String howHard;

	static{
		Level.colorData = new ColorData();
		Level.explosionData = new ExplosionImageData();
	}

	/**
	 * Instantiates a new level.
	 */
	Level(){
		//colorData = new ColorData();
		colorChooser = new Random();
		//Array of colors will reach only maxColor values
		maxColor = 5;
		determineHowHard(maxColor);
	}
	
	/**
	 * Instantiates a new level.
	 *
	 * @param maxColor the max color
	 */
	Level(int maxColor){
		//colorData = new ColorData();
		colorChooser = new Random();
		determineHowHard(maxColor);
		this.maxColor = maxColor;
	}
	
	/**
	 * Instantiates a new level.
	 *
	 * @param howHigh the how high
	 */
	Level(String howHigh){
		//colorData = new ColorData();
		colorChooser = new Random();
		determineMaxColor(howHigh);
		this.howHard = howHigh;
	}
	
	/**
	 * Determine max color.
	 *
	 * @param howHard the how hard
	 */
	//Function that determines how much balls will be shown in the game
	public void determineMaxColor(String howHard){
		if(howHard.contains("Hard"))
			//6 different colors
			maxColor = 6;
		else if(howHard.contains("Medium"))
			//5 different colors (from 0 to 4)
			maxColor = 5;
		else if(howHard.contains("Easy"))
			maxColor = 5;
		else
			maxColor = 4;
	}
	
	/**
	 * Determine how hard.
	 *
	 * @param maxColor the max color
	 */
	//unused
	public void determineHowHard(int maxColor){
		if(maxColor ==6)
			//6 different colors
			howHard = "Hard";
		else if(maxColor == 5)
			howHard = "Meduim";
		else if(maxColor ==5)
			howHard = "Easy";
		else
			howHard = "Easy";
	}

	/**
	 * Write to file.
	 */
	public void writeToFile(){
		try {
			PrintWriter writer = new PrintWriter("LevelConfig.txt", "UTF-8");
			writer.println(howHard);
			writer.close();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	/**
	 * Read from file.
	 *
	 * @param f the f
	 */
	public void readFromFile(File f){
		String fileName = f.getName();
		//System.out.println(fileName);
		try {
			URI uri = this.getClass().getResource(fileName).toURI();
			ArrayList<String> lines = (ArrayList<String>)Files.readAllLines(Paths.get(uri), Charset.defaultCharset());
			this.determineMaxColor(lines.get(0));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

/**
 * Sets the how hard.
 *
 * @param level the new how hard
 */
public void setHowHard(String level)
	{
		this.howHard=level;
	}
	
	/**
	 * Gets the how hard.
	 *
	 * @return the how hard
	 */
	public String getHowHard()
	{
		return howHard;
	}
}