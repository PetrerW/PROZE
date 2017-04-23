import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.font.FontRenderContext;
import java.awt.font.GlyphVector;
import java.awt.geom.AffineTransform;
import java.awt.image.*;
import java.awt.image.renderable.RenderableImage;
import java.io.File;
import java.io.IOException;
import java.text.AttributedCharacterIterator;
import java.util.ArrayList;
import java.util.Map;

public class GameSpace extends JPanel implements Runnable, MouseListener, MouseMotionListener {

	JButton pause_start;
	private JPanel game_field;
	private ArrayList<Bubble> BubbleList = new ArrayList<>();
	;
	private Bubble Missle;
	private Bubble NextMissle;
	private Arrow ShootArrow;
	private GameInstance Game;
	private Timer timer;
	private int delay = 100;
	private int maxColor;
	private boolean isRunning = false, changePosition = false, blockedClicked = false;
	private Thread thread;
	private double locationX, locationY, speedx = 0, speedy = 0, x = 0, y = 0;
	private int sidex, sidey, capacity = 15;
	private int diameterx, diametery, deltax = 0, deltay = 0;
	private double realDeltax = 0, realDeltay = 0;


	GameSpace() {
		setMinimumSize(new Dimension(600, 600));
		setDiameter();
		addMouseListener(this);
		addMouseMotionListener(this);
		Game = new GameInstance();

		File levelFile = new File("LevelConfig.txt");
		Game.getLevel().writeToFile();
		//Game.getLevel().readFromFile(levelFile);

		maxColor = Game.getLevel().maxColor;
		ShootArrow = new Arrow();
		Missle = new Bubble(Game.getLevel().colorData.colorFiles.get(Game.getLevel().colorChooser.nextInt(maxColor)));
		NextMissle = new Bubble(Game.getLevel().colorData.colorFiles.get(Game.getLevel().colorChooser.nextInt(maxColor)));


	}

	public void inctiationGame(GameInstance gameInstance) {


		//Game=gameInstance;
		createBubbleList();
		initiationMissle();
		setPositionBubble();
		//repaint();


	}


	public Dimension getPreferredSize() {

		return new Dimension(600, 600);
	}

	/*
     * Adding Bubbles to the game field at random.
     * Useful with creating a new game
     * Already saves a beginning bubble color configuration to the file
     */
	public void createRandomBubbleList() {
		BubbleList = new ArrayList<Bubble>();
		int maxColor = Game.getLevel().maxColor;
		StringBuffer sb = new StringBuffer();
		//Game.getLevel().colorData.colorFiles;
		ArrayList<String> Colors = new ArrayList<String>();
		for (int i = 0; i < 150; i++) {
			try {
				//Random position on the color list (from 0 to 5);
				int index = Level.colorChooser.nextInt(maxColor);
				System.out.println(index);
				//Adding just a color name to the list
				String line = Game.getLevel().colorData.colorArray.get(index);
				System.out.println(line);
				Colors.add(line);
				Game.writeToFileString(Colors);
				//BubbleList = Game.readFromFile(Config.configurationMap);
			} catch (Exception e) {
				System.err.println("GameSpace.createRandomBubbleList() - unable to add Bubble to the list!");
			}
		}
	}

	public void createBubbleList() {
      	/*
  		 * Adding Bubbles to game field
  		 */

		BubbleList = Game.readFromFile(Config.configurationMap);
		int maxColor = Game.getLevel().maxColor;

	}

	public void paint(Graphics g) {


		setLayout(new GridLayout());

		Dimension size2;
		size2 = this.getSize();

		g.setColor(Color.black);
		//Rectangle scales with screen
		g.drawRect(0, 0, (int) (size2.width * 1), (int) (size2.height));

		g.setColor(Color.gray);
		g.fillRect(0, 0, (int) (size2.width * 1), (int) (size2.height * 0.9));

		g.setColor(Color.darkGray);
		g.fillRect(0, (int) (size2.height * 0.9), (int) (size2.width * 1), (int) (size2.height * 0.1));


		//sidex = (int)(Math.sqrt(size2.height*0.8*size2.width*0.8));
		sidex = (int) (size2.width * 1);
		sidey = (int) (size2.height * 0.8);


		diameterx = (int) (sidex / (capacity + 0.5));
		diametery = (int) (sidey / (capacity + 0.5)) + 1;
		// setPositionBubble();



      			/*
      			 * Displaying every row moved by 1/2 diameter to right or left
      			 */
		for (int i = 0; i < BubbleList.size(); i++) {
		/*	//even are moved to left
			if((i/capacity)%2 == 0 )
				x = (i%capacity)*diameterx;
				//uneven moved to the right
			else
				x = (i%capacity+0.5)*diameterx;
				*/
			if (BubbleList.get(i) != null) {
				g.drawImage(BubbleList.get(i).img, BubbleList.get(i).getXPosition(), BubbleList.get(i).getYPosition(), diameterx, diametery, null);

			}
		}
		//Setting a missle under the game field
		g.drawImage(Missle.img, (int) (Missle.getXPosition() + deltax), (int) (Missle.getYPosition() + deltay), diameterx, diametery, null);
		//Missle.setPosition(sidex/2 - diameterx/2+deltax,(int)(size2.height*0.92+deltay));

		//Drawing an arrow
		ShootArrow.setSize(diametery * 5, diameterx);
		g.drawImage(ShootArrow.img, (int) (sidex / 2 - diameterx / 2), (int) (size2.height * 0.92 - ShootArrow.getLength() * 1.01), ShootArrow.getWidth(), ShootArrow.getLength(), null);
		//Drawing next bubble to shoot (NextMissle)
		g.drawImage(NextMissle.img, (int) (sidex / 20), (int) (size2.height * 0.92), diameterx, diametery, null);
	}

	/*
     * Set game instance
     */
	public void setGameInstance(GameInstance Game) {
		this.Game = Game;
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		if (!blockedClicked) {
			locationX = e.getX();
			locationY = e.getY();
			changePosition = true;
			deltaSpeed();
			blockedClicked = true;
		}

	}

	@Override
	public void mousePressed(MouseEvent e) {

	}

	@Override
	public void mouseReleased(MouseEvent e) {

	}

	@Override
	public void mouseEntered(MouseEvent e) {

	}

	@Override
	public void mouseExited(MouseEvent e) {

	}

	public void mouseDragged(MouseEvent event) {

	}

	//metoda wywoływana, gdy poruszamy kursor w obszarze nasłuchującym zdarzenia
	public void mouseMoved(MouseEvent event) {

	}

	@Override
	public void run() {
		int fps = 0;
		double timer = System.currentTimeMillis();
		long lastTime = System.nanoTime();
		double targetTick = 60.0;
		double delta = 0;
		double ns = 1000000000 / targetTick;

		while (isRunning) {
			long now = System.nanoTime();
			delta += (now - lastTime) / ns;
			lastTime = now;
			while (delta >= 1) {
				//checkColision();
				//deltaSpeed();
				//moveBubble();
				try {
					Thread.sleep(20);
				} catch (InterruptedException ex) {
					Thread.currentThread().interrupt();
				}

				repaint();
				moveBubble();
				checkColision();
				fps++;
				delta++;
			}

			if (System.currentTimeMillis() - timer >= 1000) {

				fps = 0;
				timer += 1000;
			}
		}
		stop();
	}

	public synchronized void start() {
		if (isRunning) return;
		isRunning = true;
		thread = new Thread(this);
		thread.start();

	}

	public synchronized void stop() {
		if (!isRunning) return;
		isRunning = false;
		try {
			thread.join();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}

	}


	public void positionActivityBubble() {

	}

	public void setStartPositionActivityBubble() {
		x = sidex / 2 - diameterx / 2;
		y = getHeight() * 0.92;
	}

	private void setDiameter() {
		sidex = (int) getWidth();
		sidey = (int) (getHeight() * 0.8);
		diameterx = (int) (sidex / (capacity + 0.5));
		diametery = (int) (sidey / (capacity + 0.5)) + 1;

	}

	public void moveBubble() {
		double tmp;
		if ((getHeight() * 0.92 + realDeltay) <= 0) {
			realDeltay = 0.92 * getHeight();
			changePosition = false;
			tmp = Math.round((Missle.getXPosition() + deltax) / (getHeight() / (capacity + 0.5)));
			Missle.setPosition((int) (tmp * diameterx), 0);
			setInitiation();
			setNextMissle();
		}
		if (changePosition) {
			if ((sidex / 2 - diameterx / 2 + deltax) <= 0 || (sidex / 2 + diameterx / 2 + deltax) >= getWidth()) {
				speedx = speedx * (-1);
			}
			if ((getHeight() * 0.92 - locationY) > 0) {
				if (locationX - (sidex / 2 - diameterx / 2) > 0) {
					realDeltax += speedx;
					realDeltay -= speedy;
				} else {
					realDeltax -= speedx;
					realDeltay -= speedy;

				}

				deltax = (int) realDeltax;
				deltay = (int) realDeltay;

			}
		}

	}


	public void deltaSpeed() {
		double tmpy, tmpx, tmpDelta;
		int speed = 5;
		if (getHeight() * 0.92 - locationY > 50) {
			tmpx = locationX - (sidex / 2 - diameterx / 2);
			tmpy = (getHeight() * 0.92) - locationY;

			tmpDelta = Math.abs(tmpx) / Math.abs(tmpy);
			if (tmpDelta < 1) {
				speedx = speed * tmpDelta;
				speedy = (1 - tmpDelta) * speed;
			} else {
				speedx = tmpDelta / (tmpDelta + 1) * speed;
				speedy = 1 / (tmpDelta + 1) * speed;
			}
		}
	}

	public void setInitiation() {
		speedx = 0;
		speedy = 0;
		deltax = 0;
		deltay = 0;
		realDeltax = 0;
		realDeltay = 0;
		blockedClicked = false;
	}

	public void initiationMissle() {
		Missle.setPosition(sidex / 2 - diameterx / 2, (int) (getHeight() * 0.92));

		NextMissle.setPosition(diametery * 5, diameterx);

	}

	public void setNextMissle() {
		BubbleList.add(Missle);
		Missle = NextMissle;
		Missle.setPosition(sidex / 2 - diameterx / 2, (int) (this.getHeight() * 0.92));
		NextMissle = new Bubble(Game.getLevel().colorData.colorFiles.get(Game.getLevel().colorChooser.nextInt(maxColor)));
		NextMissle.setPosition((int) (sidex / 20), (int) (getHeight() * 0.92));
	}

	public void setPositionBubble() {
		double x;

		for (int i = 0; i < BubbleList.size(); i++) {
			if ((i / capacity) % 2 == 0)
				x = (i % capacity) * diameterx;
				//uneven moved to the right
			else
				x = (i % capacity + 0.5) * diameterx;

			if (BubbleList.get(i) != null) {
				BubbleList.get(i).setPosition((int) x, (int) (i / capacity) * diametery);
			}
		}
	}

	public void checkColision() {
		for (int i = 0; i < BubbleList.size(); i++) {
			if(BubbleList.get(i)!=null)
			if ((Missle.getYPosition() + deltay) <= (BubbleList.get(i).getYPosition() + diametery)
					&&(Missle.getYPosition() + deltay) > (BubbleList.get(i).getYPosition())) {
				if ((Missle.getXPosition() + deltax) > (BubbleList.get(i).getXPosition() - diameterx / 2)
						&& (Missle.getXPosition() + deltax) < (BubbleList.get(i).getXPosition() + diameterx / 2)) {
					if ((Missle.getXPosition() + deltax) > (BubbleList.get(i).getXPosition())) {
						Missle.setPosition(BubbleList.get(i).getXPosition() + diameterx / 2, BubbleList.get(i).getYPosition() + diametery);
						changePosition = false;
						setInitiation();
						setNextMissle();

					} else {
						Missle.setPosition(BubbleList.get(i).getXPosition() - diameterx / 2, BubbleList.get(i).getYPosition()+diametery);
						changePosition = false;
						setInitiation();
						setNextMissle();

					}

				}
			} else if ((Missle.getYPosition() + deltay) > (BubbleList.get(i).getYPosition() - diametery / 2)
					&& (Missle.getYPosition() + deltay) < (BubbleList.get(i).getYPosition() + diametery / 2)) {
				if ((Missle.getXPosition() + deltax) > (BubbleList.get(i).getXPosition())
						&& (Missle.getXPosition() + deltax) < (BubbleList.get(i).getXPosition() + diameterx)) {
					if((Missle.getXPosition()+deltax)>(BubbleList.get(i).getXPosition() + diameterx/2)) {
						Missle.setPosition(BubbleList.get(i).getXPosition() + diameterx, BubbleList.get(i).getYPosition());
						changePosition = false;
						setInitiation();
						setNextMissle();
					}
					else
					{
						Missle.setPosition(BubbleList.get(i).getXPosition() - diameterx, BubbleList.get(i).getYPosition());
						changePosition = false;
						setInitiation();
						setNextMissle();
					}
				}
			}

		}
	}
}