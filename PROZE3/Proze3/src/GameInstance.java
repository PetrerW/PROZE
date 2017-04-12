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
	 GameInstance(){
	 	readImagefromFile();
		setLevel(new Level());
	}
	 GameInstance(int maxColor){
		 setLevel(new Level(maxColor));
	 }


	 public void readImagefromFile()
	 {
	 	String color;
	 	int position;
		  imageList= new ArrayList<BufferedImage>();
		  BufferedImage img;
		 String[]fileName={"Graphics/Yellow.png","Graphics/Blue.png","Graphics/Green.png","Graphics/Red.png", "Graphics/Purple.png",
				 "Graphics/Orange.png"};

		 StringBuffer sb = new StringBuffer();
		 for(String f :fileName ) {
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
	 
	 //TODO Add mkdir of Config and Graphics!!!!!
	 public void writeToFile(ArrayList<Bubble> BubbleList){
		 	if(BubbleList!=null){
		    	try {
					PrintWriter writer = new PrintWriter("MapConfig.txt", "UTF-8");
					for(int i=0; i<BubbleList.size();i++){
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



	 //TODO Add reading from directory. Actually works if config files are in the project directory
	 public ArrayList<Bubble> readFromFile(String f)
     {
     	ArrayList<Bubble> BubbleList=new ArrayList<>();
   	  //clear BubbleList to append new data
   	  if(BubbleList!=null)
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
				kindBubble = Bubble.determineColorInt(strLine);
				
			 	//Adding new Bubble to the list with appropriate Color
				BubbleList.add(new Bubble(imageList.get(kindBubble-1)));
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
}
