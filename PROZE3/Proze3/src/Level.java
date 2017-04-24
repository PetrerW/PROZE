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

public class Level {
	//String[] colors = {"Yellow", "Blue", "Green", "Purple", "Red", "Multicolor", "Orange"};
	public static Random colorChooser;
	//File[] colorFiles = {new File("Graphics/Yellow.png"), new File("Graphics/Blue.png"), new File("Graphics/Green.png"), new File("Graphics/Purple.png"), new File("Graphics/Red.png"), new File("Graphics/Multicolor.png"), new File("Graphics/Orange.png")};
	public static ColorData colorData;
	//A class with images of exploding bubbles
	public static ExplosionImageData explosionData;
	//A variable that says how much colors will be in the app
	int maxColor;
	private String howHard;

	static{
		Level.colorData = new ColorData();
		Level.explosionData = new ExplosionImageData();
	}

	Level(){
		//colorData = new ColorData();
		colorChooser = new Random();
		//Array of colors will reach only maxColor values
		maxColor = 5;
		determineHowHard(maxColor);
	}
	Level(int maxColor){
		//colorData = new ColorData();
		colorChooser = new Random();
		determineHowHard(maxColor);
		this.maxColor = maxColor;
	}
	Level(String howHigh){
		//colorData = new ColorData();
		colorChooser = new Random();
		determineMaxColor(howHigh);
		this.howHard = howHigh;
	}
	//Function that determines how much balls will be shown in the game
	public void determineMaxColor(String howHard){
		if(howHard.contains("Hard"))
			//6 different colors
			maxColor = 5;
		else if(howHard.contains("Medium"))
			//5 different colors (from 0 to 4)
			maxColor = 4;
		else if(howHard.contains("Easy"))
			maxColor = 3;
		else
			maxColor = 3;
	}
	//unused
	public void determineHowHard(int maxColor){
		if(maxColor ==5)
			//6 different colors
			howHard = "Hard";
		else if(maxColor == 4)
			howHard = "Meduim";
		else if(maxColor ==3)
			howHard = "Easy";
		else
			howHard = "Easy";
	}

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

}