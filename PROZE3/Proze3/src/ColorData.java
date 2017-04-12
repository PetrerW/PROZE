import java.io.File;
import java.util.ArrayList;

public class ColorData {
	ArrayList<File> colorFiles;
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
