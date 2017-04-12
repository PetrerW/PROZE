import java.io.File;
import java.util.ArrayList;
/*
 * @author PetrerW
 * @version 12.04.2017
 */
public class ColorData {
	public static ArrayList<File> colorFiles;
	ColorData(){
		colorFiles = new ArrayList<>();
		colorFiles.add(new File("Graphics/Yellow.png"));
		colorFiles.add(new File("Graphics/Blue.png"));
		colorFiles.add(new File("Graphics/Green.png"));
		colorFiles.add(new File("Graphics/Red.png"));
		colorFiles.add(new File("Graphics/Purple.png"));
		colorFiles.add(new File("Graphics/Orange.png"));
		colorFiles.add(new File("Graphics/Multicolor.png"));
	}
}
