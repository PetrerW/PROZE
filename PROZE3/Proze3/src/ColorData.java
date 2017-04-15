import java.io.File;
import java.util.ArrayList;
/*
 * @author PetrerW
 * @version 12.04.2017
 */
public class ColorData {
	public static ArrayList<File> colorFiles;
	public static ArrayList<String> colorArray;
	public static String[]fileName={
			"Graphics/Yellow.png","Graphics/Blue.png","Graphics/Green.png","Graphics/Red.png", "Graphics/Purple.png",
	 		"Graphics/Orange.png"};
	ColorData(){
		colorFiles = new ArrayList<>();
		colorFiles.add(new File("Graphics/Yellow.png"));
		colorFiles.add(new File("Graphics/Blue.png"));
		colorFiles.add(new File("Graphics/Green.png"));
		colorFiles.add(new File("Graphics/Red.png"));
		colorFiles.add(new File("Graphics/Purple.png"));
		colorFiles.add(new File("Graphics/Orange.png"));
		colorFiles.add(new File("Graphics/Multicolor.png"));
		
		colorArray = new ArrayList<String>();
		colorArray.add(new String("Yellow"));
		colorArray.add(new String("Blue"));
		colorArray.add(new String("Green"));
		colorArray.add(new String("Red"));
		colorArray.add(new String("Purple"));
		colorArray.add(new String("Orange"));
		colorArray.add(new String("Multicolor"));
	}
}
