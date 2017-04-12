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

    public void createBubbleList(){
      	/*
  		 * Adding Bubbles to game field at random
  		 */
  		BubbleList = new ArrayList<>();
  		BubbleList=Game.readFromFile(Config.configurationMap);
  		//BubbleList.ensureCapacity(250);
  		int maxColor = Game.getLevel().maxColor;



  	/*	for ()
  		{
  			//System.out.println(i + " " + index + " " + colors[index]);
  			try{
      			//BubbleList.add(new Bubble(Game.getLevel().colors[Game.getLevel().colorChooser.nextInt(6)]));
  				BubbleList.add(new Bubble(Game.getLevel().colorData.colorFiles.get(Game.getLevel().colorChooser.nextInt(maxColor))));
				//BubbleList.add(new Bubble());
  			}
  			catch(Exception e){
  				System.out.println("Failed to add a Bubble to the BubbleList!");
  			}
  		}*/
  		System.out.println(Game.getLevel().colorData.colorFiles.isEmpty());
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
          
          int side;
          side = (int)(Math.sqrt(size2.height*0.8*size2.width*0.8));
          /*if(size2.width<size2.height)
      			side = (int)(size2.width*0.8);
          else
        	  side = (int)(size2.height*0.8);*/
          
      			int capacity = 15;
      			int diameter = (int)(side/(capacity+0.5))+1;
      			double x;
      			/*
      			 * Displaying every row moved by 1/2 diameter to right or left
      			 */
      			for(int i = 0; i<BubbleList.size();i++)
      			{
      				//even are moved to left
      					if((i/capacity)%2 == 0 )
      						x = (i%capacity)*diameter;
      				//uneven moved to the right
      					else
      						x = (i%capacity+0.5)*diameter;
      				g.drawImage(BubbleList.get(i).img, (int)x, (int)(i/capacity)*diameter, diameter, diameter, null);
      			}
      			//Setting a missle under the game field
      			g.drawImage(Missle.img, (int)(side/2 - diameter/2), (int)(size2.height*0.92), diameter, diameter, null);
      			//Drawing an arrow
      			ShootArrow.setSize(diameter*5, diameter);
      			g.drawImage(ShootArrow.img, (int)(side/2 - diameter/2), (int)(size2.height*0.92 - ShootArrow.getLength()*1.01), ShootArrow.getWidth(), ShootArrow.getLength(), null);
      			//Drawing next bubble to shoot (NextMissle)
      			g.drawImage(NextMissle.img, (int)(side/20), (int)(size2.height*0.92), diameter, diameter, null);
      }
}
