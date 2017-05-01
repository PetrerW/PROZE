/**
 * Created by Daniel on 2017-04-24.
 */
import java.io.File;
import java.util.ArrayList;

public class ExplosionImageData {
    public static ArrayList<File> explosionFiles;
    public static ArrayList<String> explosionArray;
    public static String[]fileName={
            "Graphics/YellowExplosion.png","Graphics/BlueExplosion.png","Graphics/GreenExplosion.png","Graphics/RedExplosion.png", "Graphics/PurpleExplosion.png",
            "Graphics/OrangeExplosion.png"};
    ExplosionImageData(){
        explosionFiles = new ArrayList<>();
        explosionFiles.add(new File("Graphics/YellowExplosion.png"));
        explosionFiles.add(new File("Graphics/BlueExplosion.png"));
        explosionFiles.add(new File("Graphics/GreenExplosion.png"));
        explosionFiles.add(new File("Graphics/RedExplosion.png"));
        explosionFiles.add(new File("Graphics/PurpleExplosion.png"));
        explosionFiles.add(new File("Graphics/OrangeExplosion.png"));
        explosionFiles.add(new File("Graphics/MulticolorExplosion.png"));

        explosionArray = new ArrayList<String>();
        explosionArray.add(new String("YellowExplosion"));
        explosionArray.add(new String("BlueExplosion"));
        explosionArray.add(new String("GreenExplosion"));
        explosionArray.add(new String("RedExplosion"));
        explosionArray.add(new String("PurpleExplosion"));
        explosionArray.add(new String("OrangeExplosion"));
        explosionArray.add(new String("MulticolorExplosion"));
    }
}