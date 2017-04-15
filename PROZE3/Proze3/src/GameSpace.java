import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.font.FontRenderContext;
import java.awt.font.GlyphVector;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import java.awt.image.BufferedImageOp;
import java.awt.image.ImageObserver;
import java.awt.image.RenderedImage;
import java.awt.image.renderable.RenderableImage;
import java.io.File;
import java.io.IOException;
import java.text.AttributedCharacterIterator;
import java.util.ArrayList;
import java.util.Map;

public class GameSpace extends JComponent {

JButton pause_start;
private JPanel game_field;
private ArrayList<Bubble> BubbleList;
private Bubble Missle;
private Bubble NextMissle;
private Arrow ShootArrow;
private GameInstance Game;



        GameSpace() {
            Game = new GameInstance();
            
            //File mapFile = new File("MapConfig.txt");
            //Game.writeToFile(BubbleList);
            File levelFile = new File("LevelConfig.txt");
            Game.getLevel().writeToFile();
            //Game.getLevel().readFromFile(levelFile);
            
           	int maxColor = Game.getLevel().maxColor;
            Missle = new Bubble(Game.getLevel().colorData.colorFiles.get(Game.getLevel().colorChooser.nextInt(maxColor)));
            NextMissle = new Bubble(Game.getLevel().colorData.colorFiles.get(Game.getLevel().colorChooser.nextInt(maxColor)));
            ShootArrow = new Arrow();
            createBubbleList();
            
        }



    public Dimension getPreferredSize() {
        return new Dimension(600, 600);
    }

    /*
     * Adding Bubbles to the game field at random.
     * Useful with creating a new game
     * Already saves a beginning bubble color configuration to the file
     */
    public void createRandomBubbleList (){
    	BubbleList = new ArrayList<Bubble>();
    	int maxColor = Game.getLevel().maxColor;
    	StringBuffer sb = new StringBuffer();
    	//Game.getLevel().colorData.colorFiles;
    	ArrayList<String> Colors = new ArrayList<String>();
    	for(int i = 0; i<150; i++){
    		try{
    			//Random position on the color list (from 0 to 5);
    			int index = Level.colorChooser.nextInt(maxColor);
    			System.out.println(index);
    			//Adding just a color name to the list
    			String line = Game.getLevel().colorData.colorArray.get(index);
    			System.out.println(line);
    			Colors.add(line);
    			Game.writeToFileString(Colors);
    			BubbleList = Game.readFromFile(Config.configurationMap);
    		}
    		catch (Exception e){
    			System.err.println("GameSpace.createRandomBubbleList() - unable to add Bubble to the list!");
    		}
    	}
    }
    
    public void createBubbleList(){
      	/*
  		 * Adding Bubbles to game field
  		 */
  		BubbleList = new ArrayList<>();
  		BubbleList=Game.readFromFile(Config.configurationMap);
  		int maxColor = Game.getLevel().maxColor;
    }
    
      public void paint (Graphics g)
      {
  		setLayout(new GridLayout());
  		
  		Dimension size2;
  		size2 = this.getSize();
  		
          g.setColor(Color.black);
          //Rectangle scales with screen
          g.drawRect(0,0,(int)(size2.width*0.8),(int)(size2.height));

          g.setColor(Color.gray);
          g.fillRect(0,0,(int)(size2.width*0.8),(int)(size2.height*0.9));
          
          int sidex;
          int sidey;
          //sidex = (int)(Math.sqrt(size2.height*0.8*size2.width*0.8));
          sidex = (int)(size2.width*0.8);
          sidey = (int)(size2.height*0.8);
          
      			int capacity = 15;
      			int diameterx = (int)(sidex/(capacity+0.5))+1;
      			int diametery = (int)(sidey/(capacity+0.5))+1;
      			double x;
      			/*
      			 * Displaying every row moved by 1/2 diameter to right or left
      			 */
      			for(int i = 0; i<BubbleList.size();i++)
      			{
      				//even are moved to left
      					if((i/capacity)%2 == 0 )
      						x = (i%capacity)*diameterx;
      				//uneven moved to the right
      					else
      						x = (i%capacity+0.5)*diameterx;
      				g.drawImage(BubbleList.get(i).img, (int)x, (int)(i/capacity)*diametery, diameterx, diametery, null);
      			}
      			//Setting a missle under the game field
      			g.drawImage(Missle.img, (int)(sidex/2 - diameterx/2), (int)(size2.height*0.92), diameterx, diametery, null);
      			//Drawing an arrow
      			ShootArrow.setSize(diametery*5, diameterx);
      			g.drawImage(ShootArrow.img, (int)(sidex/2 - diameterx/2), (int)(size2.height*0.92 - ShootArrow.getLength()*1.01), ShootArrow.getWidth(), ShootArrow.getLength(), null);
      			//Drawing next bubble to shoot (NextMissle)
      			g.drawImage(NextMissle.img, (int)(sidex/20), (int)(size2.height*0.92), diameterx, diametery, null);
      }
      
      /*
       * Set game instance
       */
      public void setGameInstance(GameInstance Game){
    	  this.Game = Game;
      }
}
