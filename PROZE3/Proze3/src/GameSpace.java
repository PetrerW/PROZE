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
	private int maxColor,counter=0,demagedBubbles=0,point;
	private boolean isRunning = false, changePosition = false, blockedClicked = false, firstPaint = true;
	private Thread thread;
	private double locationX, locationY,movedLocationX,movedLocationY, speedx = 0, speedy = 0, x = 0, y = 0;
	private int sidex, sidey, capacity = 15;
	private int diameterx, diametery, deltax = 0, deltay = 0;
	private double realDeltax = 0, realDeltay = 0;
	private CountPointPanel countPointPanel;



	GameSpace(CountPointPanel pointPanel) {
		setMinimumSize(new Dimension(600, 600));
		setDiameter();
		addMouseListener(this);
		addMouseMotionListener(this);
		Game = new GameInstance();
		countPointPanel=pointPanel;

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

		Graphics2D g2d=(Graphics2D) g;


		setLayout(new GridLayout());

		Dimension size2;
		size2 = this.getSize();

		float[] dash={2f,0f,2f};
		BasicStroke basicStroke=new BasicStroke(1,BasicStroke.CAP_BUTT,BasicStroke.JOIN_ROUND,1.0f,dash,2f);
		g2d.setStroke(basicStroke);

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
		//initiationMissle();
		setPositionBubble();
		 //initiationMissle();



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


		//Drawing next bubble to shoot (NextMissle)
		g.drawImage(NextMissle.img, (int) (sidex / 20), (int) (size2.height * 0.92), diameterx, diametery, null);

		//Not to let the arrow be drawn before initiation of the game
		if(!firstPaint)
			g2d.drawLine(size2.width/2,(int)(size2.height * 0.9),(int)movedLocationX, (int)movedLocationY);
		firstPaint = false;
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

	/*
	 * to track mouse moves by an arrow
	 * (non-Javadoc)
	 * @see java.awt.event.MouseMotionListener#mouseMoved(java.awt.event.MouseEvent)
	 */
	public void mouseMoved(MouseEvent event) {
		movedLocationX=event.getX();
		movedLocationY=event.getY();
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

	/*
	 * start a thread
	 */
	public synchronized void start() {
		if (isRunning) return;
		isRunning = true;
		thread = new Thread(this);
		thread.start();

	}
	
	/*
	 * stop a thread
	 */
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

	/*
	 * Setting start position of a Bubbble
	 */
	public void setStartPositionActivityBubble() {
		x = sidex / 2 - diameterx / 2;
		y = getHeight() * 0.92;
	}

	/*
	 * Counting diameter of Bubbles n the basis of capacity (how much Bubbles in one line)
	 */
	private void setDiameter() {
		sidex = (int) getWidth();
		sidey = (int) (getHeight() * 0.8);
		diameterx = (int) (sidex / (capacity + 0.5));
		diametery = (int) (sidey / (capacity + 0.5)) + 1;

	}

	/*
	 * calculate next position of Bubble in the animation
	 */
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

	/*
	 * TODO: add commment
	 */
	public void deltaSpeed() {
		double tmpy, tmpx, tmpDelta;
		int speed = 4;
		if (getHeight() * 0.92 - locationY > 50) {
			tmpx = locationX - (getHeight() / 2 - diameterx / 2);
			tmpy = (getHeight() * 0.92) - locationY;

			tmpDelta = Math.abs(tmpx) / Math.abs(tmpy);
			if (tmpDelta < 1) {
				speedy = speed;
				speedx=tmpDelta*speedy;

			} else {
				speedx = speed;
				speedy=speedx/tmpDelta;
			}
		}
	}

	/*
	 * set beginning values
	 */
	public void setInitiation() {
		speedx = 0;
		speedy = 0;
		deltax = 0;
		deltay = 0;
		realDeltax = 0;
		realDeltay = 0;
		blockedClicked = false;

	}

	/*
	 * set beginning position of the Missle
	 */
	public void initiationMissle() {
		Missle.setPosition(sidex / 2 - diameterx / 2, (int) (getHeight() * 0.92));

		NextMissle.setPosition(diametery * 5, diameterx);

	}

	/*
	 * Things done after Missle is with the other Bubbles
	 */
	public void setNextMissle() {
		int positionInList;


		int positionInRow,positionInColumn;
		positionInColumn=Missle.getXPosition()/diameterx;
		positionInRow=Missle.getYPosition()/diametery;
		positionInList=positionInRow*capacity+positionInColumn;
		if(BubbleList.size()<(positionInList+1)) {
			while (BubbleList.size() < (positionInList )) {
				BubbleList.add(null);
			}
			BubbleList.add(Missle); //Adding Bubble to the BubbleList
		}
		else {
			BubbleList.set(positionInList,Missle); 
		
		}

		
		extinguishBubble(getNeighborsIndexes(Missle));
		counter++;
		myFunction();
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

	public void myFunction()
	{
System.out.println(counter);
		if(counter==4)
		{
			for(int i=0; i<capacity;i++) {
				BubbleList.add(0, new Bubble(Game.getImageList().get(Game.getLevel().colorChooser.nextInt(maxColor))));
			}
			counter=0;
		}
	// = new Bubble(Game.getLevel().colorData.colorFiles.get(Game.getLevel().colorChooser.nextInt(maxColor)));
	}

	/*
	 * Check, where to stop the Missle. 
	 * When the Missle meets with Bubble?
	 */
	public void checkColision() {
		for (int i = 0; i < BubbleList.size(); i++) {
			if(BubbleList.get(i)!=null)
			if ((Missle.getYPosition() + deltay) <= (BubbleList.get(i).getYPosition() + diametery)
					&&(Missle.getYPosition() + deltay) > (BubbleList.get(i).getYPosition())) {
				if ((Missle.getXPosition() + deltax) > (BubbleList.get(i).getXPosition() - diameterx *3/4)
						&& (Missle.getXPosition() + deltax) < (BubbleList.get(i).getXPosition() + diameterx * 3/4)) {
					if ((Missle.getXPosition() + deltax) > (BubbleList.get(i).getXPosition())) {
						//if( (Missle.getYPosition() + deltay) > (BubbleList.get(i).getYPosition()+diametery/2)) {
							Missle.setPosition(BubbleList.get(i).getXPosition() + diameterx / 2, BubbleList.get(i).getYPosition() + diametery);
							changePosition = false;
							setInitiation();
							setNextMissle();
						//}
						//else
						{
						/*	Missle.setPosition(BubbleList.get(i).getXPosition() + diameterx / 2, BubbleList.get(i).getYPosition() + diametery);
							changePosition = false;
							setInitiation();
							setNextMissle();*/
						}

					} else {
						Missle.setPosition(BubbleList.get(i).getXPosition() - diameterx / 2, BubbleList.get(i).getYPosition()+diametery);
						changePosition = false;
						setInitiation();
						setNextMissle();

					}

				}
			} else if ((Missle.getYPosition() + deltay) > (BubbleList.get(i).getYPosition() - diametery / 2)
					&& (Missle.getYPosition() + deltay) < (BubbleList.get(i).getYPosition() + diametery / 2)) {
				if ((Missle.getXPosition() + deltax+diameterx) > (BubbleList.get(i).getXPosition()+diameterx/5)
						&& (Missle.getXPosition() + deltax) < (BubbleList.get(i).getXPosition() + diameterx*4/5)) {
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

	/*
	 * get indexes of Bubbles that are neighbors of a particular Bubble
	 * @param Bubble object, whose neighbours we're looking for
	 * @return int table filled with indexes of neighbours in BubbleList
	 */
	public int[] getNeighborsIndexes(Bubble B){
		int B_index = BubbleList.indexOf(B);

		if(B_index == 0) //left-top
		{
			int indexes[] = {1};
			return indexes;
		}
		if(B_index - capacity + 1 <= 0)  //top
		{
			int indexes[] = {B_index-1, B_index+1, B_index + capacity - 1, B_index + capacity};
			return indexes;
		}

		if((B_index - 1)%(capacity-1) ==0 ) //left margin
			if((B_index - B_index%capacity)/capacity%2 == 0){ // left row
				int indexes[] = {B_index - capacity + 1, B_index - capacity, B_index + 1, B_index + capacity -1,
						B_index+capacity};
				return indexes;
			}
			else{ //right row
				int indexes[] = {B_index - capacity, B_index - capacity - 1, B_index + 1, B_index + capacity,
						B_index+capacity+1};
				return indexes;
			}

		if((B_index - 1)%(capacity-1) == 0 ) //right margin
			if((B_index - B_index%capacity)/capacity%2 == 0){ // left row
				int indexes[] = {B_index - capacity + 1, B_index - capacity, B_index - 1, B_index + capacity -1,
						B_index+capacity};
				return indexes;
			}
			else{ //right row
				int indexes[] = {B_index - capacity, B_index - capacity - 1, B_index - 1, B_index + 1, B_index + capacity,
						B_index+capacity+1};
				return indexes;
			}

		if((B_index/ capacity)%2 == 0){ // left row
			int indexes[] = {B_index - capacity - 1, B_index - capacity, B_index - 1,B_index, B_index + 1, B_index + capacity +1,
					B_index+capacity};
			return indexes;
		}

		else { //if((B_index - B_index%capacity)/capacity%2 == 1) //right row
			int indexes[] = {B_index - capacity, B_index - capacity + 1, B_index - 1,B_index, B_index + 1, B_index + capacity,
					B_index+capacity-1};
			return indexes;
		}
	}
	/*
	 * @param int table with indexes of Bubbles to extinguish
	 */
	public void extinguishBubble(int indexes[]){
		for(int i: indexes)
		{
			//TODO: Check if Bubbles have correctly assigned colors
			int colorIndex = 5;//ColorData.colorArray.indexOf(BubbleList.get(i).color); //find what index has Bubble color
			try{
				if(BubbleList.size()>i) {
					BubbleList.get(i).img = Game.getImageExplosionList().get(colorIndex); //append explosion image
					demagedBubbles++;
				}
			}
			catch(Exception e)
			{
				System.err.println("extinguishBubble");
			}
		}
		repaint();
		try {
			thread.sleep(20);
		} catch (InterruptedException ex) {
			Thread.currentThread().interrupt();
		}
		for(int i:indexes)
		{
			System.out.println(BubbleList.size());
			System.out.println(i);
			if(BubbleList.size()>(i)) {
				BubbleList.set((i ), null);
			}
			//System.out.println(i);
		}
		countAndSetPoint();

	}

	public void countAndSetPoint()
	{
		if(demagedBubbles==3)
		{
			point+=10;
		}
		else if(demagedBubbles==4)
		{
			point+=20;
		}
		else
		{
			point+=3;
		}
		countPointPanel.setScore(point);
		demagedBubbles=0;
	}

	/*
	 * get GameInstance
	 */
	public GameInstance getGame()
	{
		return Game;
	}
}