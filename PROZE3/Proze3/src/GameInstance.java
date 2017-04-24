import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.*;
import java.net.URI;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;

public class GameInstance {
	private String username;
	private int score;
	private Level level;
	private ArrayList<BufferedImage> imageList;
	private ArrayList<BufferedImage> imageExplosionList;

	GameInstance() {
		readImagefromFile();
		readImageExplosionfromFile();
		setLevel(new Level());
	}

	GameInstance(int maxColor) {
		setLevel(new Level(maxColor));
	}


	public void readImagefromFile() {
		String color;
		int position;
		imageList = new ArrayList<BufferedImage>();
		BufferedImage img;

		StringBuffer sb = new StringBuffer();
		for (String f : Level.colorData.fileName) {
			sb.append(f);
			if (sb.toString().contains("Graphics/")) {
				sb.delete(sb.indexOf("Graphics/"), sb.indexOf("Graphics/") + 9/*Length of "Graphics/" */);
			}
			if (sb.toString().contains(".png")) {
				sb.delete(sb.indexOf(".png"), sb.indexOf(".png") + 4/*Length of ".png" */);
			}
			color = sb.toString();
			position = 0;
			try {
				img = ImageIO.read(new File(f));
				imageList.add(img);
			} catch (IOException e) {
				System.out.println("Failed to read file!");
				e.printStackTrace();
			}

		}

	}
	public void readImageExplosionfromFile() {
		String color;
		int position;
		imageExplosionList = new ArrayList<BufferedImage>();
		BufferedImage img;

		StringBuffer sb = new StringBuffer();
		for (String f : Level.explosionData.fileName) {
			sb.append(f);
			if (sb.toString().contains("Graphics/")) {
				sb.delete(sb.indexOf("Graphics/"), sb.indexOf("Graphics/") + 9/*Length of "Graphics/" */);
			}
			if (sb.toString().contains(".png")) {
				sb.delete(sb.indexOf(".png"), sb.indexOf(".png") + 4/*Length of ".png" */);
			}
			color = sb.toString();
			position = 0;
			try {
				img = ImageIO.read(new File(f));
				imageExplosionList.add(img);
			} catch (IOException e) {
				System.out.println("Failed to read file!");
				e.printStackTrace();
			}

		}

	}

	//TODO Add mkdir of Config and Graphics!!!!!
	public void writeToFile(ArrayList<Bubble> BubbleList) {
		if (BubbleList != null) {
			try {
				PrintWriter writer = new PrintWriter("MapConfig.txt", "UTF-8");
				for (int i = 0; i < BubbleList.size(); i++) {
					writer.println(BubbleList.get(i).color);
				}
				writer.close();
			} catch (FileNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (UnsupportedEncodingException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

	public void writeToFileString(ArrayList<String> ColorList) {
		//If the directory doesn't exist...
		if (!new File("/Graphics").exists())
			//...create one
			new File("/Graphics").mkdir();

		if (ColorList != null) {
			try {
				PrintWriter writer = new PrintWriter("MapConfig.txt", "UTF-8");
				for (String line : ColorList) {
					writer.println(line);
				}
				writer.close();
			} catch (FileNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (UnsupportedEncodingException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}


		}
	}


	//TODO Add reading from directory. Actually works if config files are in the project directory
	public ArrayList<Bubble> readFromFile(String f) {
		ArrayList<Bubble> BubbleList = new ArrayList<>();
		//clear BubbleList to append new data
		if (BubbleList != null)
			BubbleList.removeAll(BubbleList);
		else
			BubbleList = new ArrayList<Bubble>();

		try {

			FileInputStream fstream = new FileInputStream(f);
			BufferedReader br = new BufferedReader(new InputStreamReader(fstream));

			String strLine;
			int kindBubble;
			//Read File Line By Line
			while ((strLine = br.readLine()) != null) {
				//Determining position on the colorList
				//kindBubble = Bubble.determineColorInt(Integer.parseInt(strLine));
				kindBubble = Integer.parseInt(strLine);
				//Adding new Bubble to the list with appropriate Color
				if(kindBubble>0) {
					BubbleList.add(new Bubble(imageList.get(kindBubble - 1)));
				}
				else
				{
					BubbleList.add(null);
				}
			}
			br.close();

		} catch (Exception e) {//Catch exception if any
			System.err.println("Error: " + e.getMessage());
			System.err.println("GameInstance.readFromFile(String f)");
		}
		return BubbleList;
	}

	public Level getLevel() {
		return level;
	}

	public void setLevel(Level level) {
		this.level = level;
	}

	/*
	 * Change username of the GameInstance
	 */
	public void appendUsername(String username) {
		this.username = username;
	}
	
	public String getUsername()
	{
		return this.username;
	}

	public ArrayList<BufferedImage> getImageList() {
		return this.imageList;
	}

	public ArrayList<BufferedImage>getImageExplosionList()
	{
		return this.imageExplosionList;
	}
}