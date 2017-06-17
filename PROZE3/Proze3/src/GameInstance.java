/*
 * 
 */
import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.*;
import java.net.URI;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Properties;

// TODO: Auto-generated Javadoc
/**
 * The Class GameInstance.
 */
public class GameInstance {
	
	/** The username. */
	private String username;
	
	/** The score. */
	private int score;
	
	/** The level. */
	private Level level;
	
	/** The image list. */
	private ArrayList<BufferedImage> imageList;
	
	/** The image explosion list. */
	private ArrayList<BufferedImage> imageExplosionList;

	/**
	 * Instantiates a new game instance.
	 */
	GameInstance() {
		readImagefromFile();
		readImageExplosionfromFile();
		setLevel(new Level());
	}

	/**
	 * Instantiates a new game instance.
	 *
	 * @param maxColor the max color
	 */
	GameInstance(int maxColor) {
		setLevel(new Level(maxColor));
	}


	/**
	 * Read imagefrom file.
	 */
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

	/**
	 * Read image explosionfrom file.
	 */
	/*
	 * A functions that read Image Explosion pictures from files
	 */
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

	/**
	 * Write to file.
	 *
	 * @param BubbleList the bubble list
	 */
	//TODO Add mkdir of Config and Graphics!!!!!
	public void writeToFile(ArrayList<Bubble> BubbleList) {
		if (BubbleList != null) {
			try {
				PrintWriter writer = new PrintWriter("MapConfig.txt", "UTF-8");
				for (int i = 0; i < BubbleList.size(); i++) {
					writer.println(BubbleList.get(i).getColorInt());
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

	/**
	 * Write to file string.
	 *
	 * @param ColorList the color list
	 */
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


	/**
	 * Read from file.
	 *
	 * @param f the f
	 * @return the array list
	 */
	//TODO Add reading from directory. Actually works if config files are in the project directory
	public ArrayList<Bubble> readFromFile(String f) {
		ArrayList<Bubble> BubbleList = new ArrayList<Bubble>();
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
				if (kindBubble > 0) {
					BubbleList.add(new Bubble(imageList.get(kindBubble - 1)));
					BubbleList.get(BubbleList.size() - 1).setColorString(strLine); //set color of added Bubble
					BubbleList.get(BubbleList.size() - 1).setColorInt(kindBubble);
				} else {
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

	/**
	 * Gets the level.
	 *
	 * @return the level
	 */
	public Level getLevel() {
		return level;
	}

	/**
	 * Sets the level.
	 *
	 * @param level the new level
	 */
	public void setLevel(Level level) {
		this.level = level;
	}

	/**
	 * Sets the username.
	 *
	 * @param username the new username
	 */
	/*
	 * Change username of the GameInstance
	 */
	public void setUsername(String username) {
		this.username = username;
	}

	/**
	 * Gets the username.
	 *
	 * @return the username
	 */
	public String getUsername() {
		return this.username;
	}

	/**
	 * Gets the image list.
	 *
	 * @return the image list
	 */
	public ArrayList<BufferedImage> getImageList() {
		return this.imageList;
	}

	/**
	 * Gets the image explosion list.
	 *
	 * @return the image explosion list
	 */
	/*
         *Passing a  ArrayList with explosion image
         * @return instantion ArrayList<BufferedImage>
         */
	public ArrayList<BufferedImage> getImageExplosionList() {
		return this.imageExplosionList;
	}


	/**
	 * Write best ranking.
	 *
	 * @param nick the nick
	 * @param points the points
	 */
	public void writeBestRanking(ArrayList<String> nick, ArrayList<String> points) {



		Properties ranking = new Properties();

		try {


			FileOutputStream out = new FileOutputStream(Config.bestRankingPath, true);

			ranking.load(new FileInputStream(Config.bestRankingPath));

			PrintWriter writer = new PrintWriter(Config.bestRankingPath);
			writer.print("");
			writer.close();

			for (int i = 1; i < 11; i++) {
				if (nick.get(i - 1) != null) {
					ranking.setProperty("Nick " + i, nick.get(i - 1));
					ranking.setProperty("Point " + i, points.get(i - 1));
				}
			}
			ranking.store(out,"Ustawienia");


		} catch (IOException e) {
			System.out.println(e);
		}


	}
	
	/**
	 * Best ranking.
	 *
	 * @return the string[]
	 */
	public String[] bestRanking() {


		Properties bestlist=new Properties();

		try {

			bestlist.load(new FileInputStream(Config.bestRankingPath));


		} catch (IOException e) {
			//JOptionPane.showMessageDialog(null,"Error");

		}


		String[] strLine = new String[20];
		for(int i=1; i<11; i++) {

			strLine[2*i-2] = bestlist.getProperty("Nick "+(i));
			System.out.println(strLine[2*i-2]);
			strLine[2*i-1]=bestlist.getProperty("Point "+i);
		}


		return strLine;

	}
}






