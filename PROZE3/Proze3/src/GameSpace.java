//import sun.applet.Main;
//import sun.audio.AudioStream;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.swing.*;
import java.io.*;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.Properties;


// TODO: Auto-generated Javadoc
/**
 * The Class GameSpace.
 */
public class GameSpace extends JPanel implements Runnable, MouseListener, MouseMotionListener {



	/** The Bubble list. */
	private ArrayList<Bubble> BubbleList = new ArrayList<Bubble>();
	
	/** The Missle. */
	private Bubble Missle;
	
	/** The Next missle. */
	private Bubble NextMissle;
	
	/** The Shoot arrow. */
	private Arrow ShootArrow;
	
	/** The Game. */
	private GameInstance Game;
	
	/** The count shoot at level. */
	private int maxColor=5,counter=0,demagedBubbles=0,point=0,countShootAtLevel=0;
	
	/** The sound on. */
	private boolean isRunning = false, changePosition = false, blockedClicked = false, pause=false,multiBubble=false,soundOn=true;
	
	/** The game over. */
	private boolean moveOnLeft=false, moveOnRight=false, firstRowBubbleOnLeft=true,gameOver=true;
	
	/** The animation kicker. */
	private Thread animationKicker;
	
	/** The y. */
	private double locationX, locationY,movedMouseLocationX,movedMouseLocationY, speedx = 0, speedy = 0, x = 0, y = 0;
	
	/** The capacity. */
	private int sidex, sidey, capacity = 15;
	
	/** The deltay. */
	private int diameterx, diametery, deltax = 0, deltay = 0;
	
	/** The Add bubble hard. */
	int PointsBoardMedium, PointsBoardHard, ShootBoardMedium,ShootBoardHard,AddBubbleEasy,AddBubbleMedium,AddBubbleHard;
	
	/** The real deltay. */
	private double realDeltax = 0, realDeltay = 0;
	
	/** The count point panel. */
	private CountPointPanel countPointPanel;
	
	/** The level. */
	private TextPanel level;
	
	/** The last width. */
	private double lastHeight, lastWidth;
	
	/** The scale value Y. */
	private double scaleValueX=1,scaleValueY=1;
	
	/** The panel height. */
	private double panelWidth,panelHeight;





	/**
	 * Instantiates a new game space.
	 *
	 * @param pointPanel the point panel
	 * @param level the level
	 */
	GameSpace(CountPointPanel pointPanel,TextPanel level) {
		setMinimumSize(new Dimension(600, 600));
		setDiameter();
		addMouseListener(this);
		addMouseMotionListener(this);
		levelBoard();
		Game = new GameInstance();
		//setGameInstance();

		countPointPanel=pointPanel;
		this.level=level;



System.out.println(PointsBoardMedium);


		ShootArrow = new Arrow();
		//Missle = new Bubble(Game.getLevel().colorData.colorFiles.get(Game.getLevel().colorChooser.nextInt(maxColor)));
		//NextMissle = new Bubble(Game.getLevel().colorData.colorFiles.get(Game.getLevel().colorChooser.nextInt(maxColor)));


	}

	/**
	 * Inctiation game.
	 *
	 * @param gameInstance the game instance
	 */
	public void inctiationGame(GameInstance gameInstance) {


		Game=gameInstance;
		maxColor = Game.getLevel().maxColor;
		Game.getLevel().writeToFile();
		createBubbleList();
		initiationMissle();
		setPositionBubble();
		//repaint();
		gameOver=false;
		point=0;
		if(Game.getLevel().getHowHard()=="Easy")
		{
			countPointPanel.setScore(point);
		}
		else if(Game.getLevel().getHowHard()=="Medium") {
			point=PointsBoardMedium;
			countPointPanel.setScore(point);
		}else
		{
			point=PointsBoardHard;
			countPointPanel.setScore(point);
		}
		level.setText(Config.packLanguage[16]+" \n"+Game.getLevel().getHowHard());




	}


	/* (non-Javadoc)
	 * @see javax.swing.JComponent#getPreferredSize()
	 */
	public Dimension getPreferredSize() {

		return new Dimension(600, 600);
	}




	/**
	 * Creates the random bubble list.
	 *
	*
     * Adding Bubbles to the game field at random.
     * Useful with creating a new game
     * Already saves a beginning bubble color configuration to the file
     */
	public void createRandomBubbleList() {
		BubbleList = new ArrayList<Bubble>();
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

	/**
	 * Creates the bubble list.
	 */
	public void createBubbleList() {
      	/*
  		 * Adding Bubbles to game field
  		 */
if(Game.getLevel().getHowHard()=="Hard") {
	BubbleList = Game.readFromFile(Config.configurationMapHard);

}else if(Game.getLevel().getHowHard()=="Medium")
{
	BubbleList = Game.readFromFile(Config.configurationMapMedium);
}
else {
	BubbleList = Game.readFromFile(Config.configurationMapEasy);
}

	}

	/* (non-Javadoc)
	 * @see javax.swing.JComponent#paint(java.awt.Graphics)
	 */
	public void paint(Graphics g) {


		Graphics2D g2d=(Graphics2D) g;

		panelHeight=getHeight();
		panelWidth=getWidth();

		scalingLocationParameters();

		float[] dash={2f,0f,2f};
		BasicStroke basicStroke=new BasicStroke(1,BasicStroke.CAP_BUTT,BasicStroke.JOIN_ROUND,1.0f,dash,2f);
		g2d.setStroke(basicStroke);

		g.setColor(Color.black);
		//Rectangle scales with screen
		g.drawRect(0, 0, (int) (panelWidth * 1), (int) (panelHeight));

		g.setColor(Color.gray);
		g.fillRect(0, 0, (int) (panelWidth * 1), (int) (panelHeight * 0.9));

		g.setColor(Color.darkGray);
		g.fillRect(0, (int) (panelHeight * 0.9), (int) (panelWidth * 1), (int) (panelHeight * 0.1));


		//sidex = (int)(Math.sqrt(size2.height*0.8*size2.width*0.8));
		sidex = (int) (panelWidth * 1);
		sidey = (int) (panelHeight * 0.8);


		diameterx = (int) (sidex / (capacity + 0.5));
		diametery = (int) (sidey / (capacity + 0.5)) + 1;

		setPositionBubble();




      			/*
      			 * Displaying every row moved by 1/2 diameter to right or left
      			 */

		for (int i = 0; i < BubbleList.size(); i++) {
			//even are moved to left

			if (BubbleList.get(i) != null) {
				g.drawImage(BubbleList.get(i).img, BubbleList.get(i).getXPosition(), BubbleList.get(i).getYPosition(), diameterx, diametery, null);

			}
		}
		if(!gameOver) {
			//Setting a missle under the game field
			g.drawImage(Missle.img, (Missle.getXPosition()), (Missle.getYPosition()), diameterx, diametery, null);


			//Drawing next bubble to shoot (NextMissle)
			g.drawImage(NextMissle.img, (int) (getWidth() / 20), (int) (panelHeight * 0.92), diameterx, diametery, null);

			g2d.drawLine((int) panelWidth / 2, (int) (panelHeight * 0.92 + diametery / 2), (int) movedMouseLocationX, (int) (movedMouseLocationY));
		}
		}

	/**
	 * Sets the game instance.
	 *
	 * @param Game the new game instance
	 */
	/*
     * Set game instance
     */
	public void setGameInstance(GameInstance Game) {
		this.Game = Game;
	}

	/* (non-Javadoc)
	 * @see java.awt.event.MouseListener#mouseClicked(java.awt.event.MouseEvent)
	 */
	@Override
	public void mouseClicked(MouseEvent e) {
		if (!blockedClicked) {
			locationX = e.getX();
			locationY = e.getY();

			if(locationX>getHeight()/2)
			{
				moveOnRight=true;
				moveOnLeft=false;
			}
			else
			{
				moveOnLeft=true;
				moveOnRight=false;
			}

			changePosition = true;
			lastWidth=getWidth();
			lastHeight=getHeight();
			deltaSpeed();
			blockedClicked = true;
		}

	}

	/* (non-Javadoc)
	 * @see java.awt.event.MouseListener#mousePressed(java.awt.event.MouseEvent)
	 */
	@Override
	public void mousePressed(MouseEvent e) {

	}

	/* (non-Javadoc)
	 * @see java.awt.event.MouseListener#mouseReleased(java.awt.event.MouseEvent)
	 */
	@Override
	public void mouseReleased(MouseEvent e) {

	}

	/* (non-Javadoc)
	 * @see java.awt.event.MouseListener#mouseEntered(java.awt.event.MouseEvent)
	 */
	@Override
	public void mouseEntered(MouseEvent event) {

	}

	/* (non-Javadoc)
	 * @see java.awt.event.MouseListener#mouseExited(java.awt.event.MouseEvent)
	 */
	@Override
	public void mouseExited(MouseEvent e) {

	}

	/* (non-Javadoc)
	 * @see java.awt.event.MouseMotionListener#mouseDragged(java.awt.event.MouseEvent)
	 */
	public void mouseDragged(MouseEvent event) {

	}


	/* (non-Javadoc)
	 * @see java.awt.event.MouseMotionListener#mouseMoved(java.awt.event.MouseEvent)
	 */
	public void mouseMoved(MouseEvent event) {
		if (event.getX() < getWidth() && event.getY() > 0) {
			movedMouseLocationX = event.getX();
			movedMouseLocationY = event.getY();
		} else {

			movedMouseLocationX = getWidth()/2;
			movedMouseLocationY = getHeight()*0.92;
		}
	}
	
	/**
	 * Start animation thread.
	 */
	void startAnimationThread() {
		(animationKicker = new Thread(this)).start();
	}

	/**
	 * Stop animation thread.
	 */
	void stopAnimationThread() {
		animationKicker = null;
	}

	/* (non-Javadoc)
	 * @see java.lang.Runnable#run()
	 */
	@Override
	public void run() {

		try {
			while (animationKicker == Thread.currentThread()) {

//System.out.println("poczatek");

				drawingAnimation();


//System.out.println("koniec");

		}
	}catch(InterruptedException e)
		{
e.printStackTrace();
		}



	}

	/**
	 * Start.
	 */
	public synchronized void start() {

		startAnimationThread();



	}

	/**
	 * Drawing animation.
	 *
	 * @throws InterruptedException the interrupted exception
	 */
	public synchronized void drawingAnimation() throws InterruptedException {
		while(pause) {
			wait();
		}
		try {
			Thread.sleep(30);
		} catch (InterruptedException ex) {
			Thread.currentThread().interrupt();
		}

		repaint();
		deltaSpeed();
		//scalingMisslePosition();
		moveBubble();
		checkColision();
		checkGameOver();


		notify(); // notifyAll() for multiple gameSpace/vievPanel threads
	}

	/**
	 * Stop animation.
	 *
	 * @throws InterruptedException the interrupted exception
	 */
	public synchronized void stopAnimation() throws InterruptedException {
	//System.out.println("ooo");
			while(!pause) {
			wait();
		}
		//System.out.println("ooo1");

		notify(); // notifyAll() for multiple producer/consumer threads
	}


	/**
	 * Sets the diameter.
	 */
	private void setDiameter() {
		sidex = (int) getWidth();
		sidey = (int) (getHeight() * 0.8);
		diameterx = (int) (sidex / (capacity + 0.5));
		diametery = (int) (sidey / (capacity + 0.5)) + 1;

	}

	/**
	 * Scaling location parameters.
	 */
	public void scalingLocationParameters()
	{

		scaleValueY=getHeight()/lastHeight;
		scaleValueX=getWidth()/lastWidth;

		lastHeight=getHeight();
		lastWidth=getWidth();

		locationX=locationX*scaleValueX;
		locationY=locationY*scaleValueY;
		movedMouseLocationX=movedMouseLocationX*scaleValueX;
		movedMouseLocationY=movedMouseLocationY*scaleValueY;


	}

/**
 * Scaling missle position.
 */
public void scalingMisslePosition()
{

	speedx*=scaleValueX;
	speedy*=scaleValueY;
}


	/**
	 * Move bubble.
	 */
	public void moveBubble() {
		double tmp;
		speedx*=scaleValueX;
		speedy*=scaleValueY;
		if ((getHeight() * 0.92 + realDeltay) <= 0) {
			realDeltay = 0.92 * getHeight();
			changePosition = false;
			tmp = Math.round((Missle.getDoubleXPosition() ) / (getHeight() / (capacity + 0.5)));
			Missle.setPosition((int) (tmp * diameterx), 0);
			setInitiation();
			setNextMissle();
		}
		if (changePosition) {
			if ( Missle.getXPosition() <= 0 ) {
				moveOnRight=true;
				moveOnLeft=false;
			} else if( (Missle.getDoubleXPosition()+diameterx) >= getWidth())
			{
				moveOnRight=false;
				moveOnLeft=true;
			}
			if ((getHeight() * 0.92 - locationY) > 0) {
				if(moveOnRight){
					Missle.setPosition(Missle.getDoubleXPosition()+speedx,Missle.getYPosition()-speedy);

				} else {
					Missle.setPosition(Missle.getDoubleXPosition()-speedx,Missle.getYPosition()-speedy);
					/*realDeltax -= speedx;
					realDeltay -= speedy;*/

				}

			}
		}

	}



	/**
	 * Delta speed.
	 */
	public void deltaSpeed() {
		double tmpy, tmpx, tmpDelta;
		int speed = 4;


		if (getHeight() * 0.92 - locationY > 50) {
			tmpx = locationX - (getHeight() / 2  );
			tmpy = (getHeight()*0.92+diametery/2) - locationY;

			tmpDelta = Math.abs(tmpx) / Math.abs(tmpy);
			if (tmpDelta < 1) {
				speedy = speed;
				speedx=tmpDelta*speedy;

			} else {
				speedx = speed;
				speedy=speedx/tmpDelta;
			}
		}
		else
		{
			blockedClicked=false;
		}

	}

	/**
	 * Sets the initiation.
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

	/**
	 * Initiation missle.
	 */
	public void initiationMissle() {
		Missle.setPosition(getWidth() / 2 - diameterx / 2, (int) (getHeight() * 0.92));

		NextMissle.setPosition(diametery * 5, diameterx);

	}

	/**
	 * Sets the next missle.
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
	BubbleList.add(Missle);
}
else {
	BubbleList.set(positionInList,Missle);

}


extinguishBubble(getNeighborsIndexes(Missle));
		levelControl();
System.out.println("tu jestem");
counter++;
myFunction();
countShootAtLevel++;
if(multiBubble)
{
	Missle=new Bubble(Game.getLevel().colorData.colorFiles.get(6));
	Missle.setPosition(getWidth() / 2 - diameterx / 2, (int) (this.getHeight() * 0.92));

}else {
	Missle = NextMissle;
	Missle.setPosition(getWidth() / 2 - diameterx / 2, (int) (this.getHeight() * 0.92));
	NextMissle = new Bubble(Game.getLevel().colorData.colorFiles.get(Game.getLevel().colorChooser.nextInt(maxColor)));
	NextMissle.setPosition((int) (getWidth() / 20), (int) (getHeight() * 0.92));
}
	}

	/**
	 * Sets the position bubble.
	 */
	public void setPositionBubble() {
		double x;
if(firstRowBubbleOnLeft) {
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
} else{
	for (int i = 0; i < BubbleList.size(); i++) {
		if ((i / capacity) % 2 == 0)
			x = (i % capacity+0.5) * diameterx;
			//uneven moved to the right
		else
			x = (i % capacity ) * diameterx;

		if (BubbleList.get(i) != null) {
			BubbleList.get(i).setPosition((int) x, (int) (i / capacity) * diametery);
		}
	}
}
		if(Missle!=null)
		Missle.setPosition(Missle.getDoubleXPosition()*scaleValueX,Missle.getDoubleYPosition()*scaleValueY);
		else
		{
			Missle = new Bubble(Game.getLevel().colorData.colorFiles.get(Game.getLevel().colorChooser.nextInt(maxColor)));
			NextMissle = new Bubble(Game.getLevel().colorData.colorFiles.get(Game.getLevel().colorChooser.nextInt(maxColor)));
			initiationMissle();
		}
	}

	/**
	 * My function.
	 */
	public void myFunction()
	{
System.out.println(counter);
int counterForLevel;
if(Game.getLevel().getHowHard()=="Easy")
{
	counterForLevel=AddBubbleEasy;
}else if(Game.getLevel().getHowHard()=="Medium")
{
	counterForLevel=AddBubbleMedium;
}
else
{
	counterForLevel=AddBubbleHard;
}
		if(counter==counterForLevel)
		{
			for(int i=0; i<capacity;i++) {
				BubbleList.add(0, new Bubble(Game.getImageList().get(Game.getLevel().colorChooser.nextInt(maxColor))));
			}
			if(firstRowBubbleOnLeft)
			{
				firstRowBubbleOnLeft=false;
			}
			else{
				firstRowBubbleOnLeft=true;
			}
			counter=0;
		}
	// = new Bubble(Game.getLevel().colorData.colorFiles.get(Game.getLevel().colorChooser.nextInt(maxColor)));
	}

	/**
	 * Check colision.
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

	/**
	 * Gets the neighbors indexes.
	 *
	 * @param B the b
	 * @return the neighbors indexes
	 */
	/*
	 * get indexes of Bubbles that are neighbors of a particular Bubble
	 * @param Bubble object, whose neighbours we're looking for
	 * @return int table filled with indexes of neighbours in BubbleList
	 */
	public ArrayList<Integer> getNeighborsIndexes(Bubble B){
		int B_index = BubbleList.indexOf(B);
	int indexes[];

	//Return list of bubbles' indexes
		ArrayList<Integer> IndexList = new ArrayList<Integer>();

if(firstRowBubbleOnLeft) {
	if (B_index == 0) //left-top
	{
		indexes = new int[]{1, capacity, B_index};

	} else if (B_index == (capacity - 1)) //left-top
	{
		indexes = new int[]{B_index - 1, B_index, B_index + capacity, B_index + capacity - 1};

	} else if (B_index - capacity + 1 <= 0)  //top
	{
		indexes = new int[]{B_index - 1, B_index, B_index + 1, B_index + capacity - 1, B_index + capacity};
		//return indexes;
	} else if ((B_index) % (capacity) == 0) //left margin
		if (((B_index - B_index % capacity) / capacity) % 2 == 0) { // left row
			indexes = new int[]{B_index - capacity, B_index, B_index + 1,
					B_index + capacity};

		} else { //right row
			indexes = new int[]{B_index - capacity, B_index - capacity + 1, B_index, B_index + 1, B_index + capacity,
					B_index + capacity + 1};

		}

	else if ((B_index) % (capacity) == capacity) //right margin
		if (((B_index - B_index % capacity) / capacity) % 2 == 0) { // left row
			indexes = new int[]{B_index - capacity - 1, B_index - capacity, B_index - 1, B_index, B_index + capacity - 1,
					B_index + capacity};

		} else { //right row
			indexes = new int[]{B_index - capacity, B_index - 1, B_index + capacity, B_index,
					B_index + capacity + 1};

		}

	else if ((B_index / capacity) % 2 == 0) { // left row
		indexes = new int[]{B_index - capacity - 1, B_index - capacity, B_index - 1, B_index, B_index + 1, B_index + capacity - 1,
				B_index + capacity};

	} else {  //right row
		indexes = new int[]{B_index - capacity, B_index - capacity + 1, B_index - 1, B_index, B_index + 1, B_index + capacity,
				B_index + capacity + 1};

	}
}else {
	if (B_index == 0) //left-top
	{
		indexes = new int[]{1, capacity, B_index};
	} else if (B_index == (capacity - 1)) //left-top
	{
		indexes = new int[]{B_index - 1, B_index, B_index + capacity, B_index + capacity - 1};

	} else if (B_index - capacity + 1 <= 0)  //top
	{
		indexes = new int[]{B_index - 1, B_index, B_index + 1, B_index + capacity - 1, B_index + capacity};
		//return indexes;
	} else if ((B_index) % (capacity) == 0) //left margin
		if (((B_index - B_index % capacity) / capacity) % 2 == 0) { // left row
			indexes = new int[]{B_index - capacity, B_index - capacity + 1, B_index, B_index + 1, B_index + capacity,
					B_index + capacity + 1};
			//return indexes;
		} else { //right row
			indexes = new int[]{B_index - capacity, B_index, B_index + 1,
					B_index + capacity};
			//return indexes;
		}

	else if ((B_index) % (capacity) == capacity) //right margin
		if (((B_index - B_index % capacity) / capacity) % 2 == 0) { // left row
			indexes = new int[]{B_index - capacity, B_index - 1, B_index + capacity, B_index,
					B_index + capacity + 1};
			//return indexes;
		} else { //right row

			indexes = new int[]{B_index - capacity - 1, B_index - capacity, B_index - 1, B_index, B_index + capacity - 1,
					B_index + capacity};
			//return indexes;
		}

	else if ((B_index / capacity) % 2 == 0) { // left row
		indexes = new int[]{B_index - capacity, B_index - capacity + 1, B_index - 1, B_index, B_index + 1, B_index + capacity,
				B_index + capacity + 1};
		//return indexes;
	} else { //if((B_index - B_index%capacity)/capacity%2 == 1) //right row

		indexes = new int[]{B_index - capacity - 1, B_index - capacity, B_index - 1, B_index, B_index + 1, B_index + capacity - 1,
				B_index + capacity};
		//return indexes;
	}
}

		for(int i=0; i<indexes.length; i++) {
			if(indexes[i]<BubbleList.size())
				if(BubbleList.get(indexes[i])!=null)
				IndexList.add(indexes[i]);

			}
		return IndexList;

	}
	
	/**
	 * Extinguish bubble.
	 *
	 * @param indexes the indexes
	 */
	/*
	 * @param int table with indexes of Bubbles to extinguish
	 */
	public void extinguishBubble(ArrayList<Integer> indexes){
		boolean run=true;
		int size;
				ArrayList<Integer> tmp=new ArrayList<Integer>();
		if(!multiBubble) {
			for (int k = (indexes.size() - 1); k >= 0; k--) {
				if (Missle.getColorInt() != BubbleList.get(indexes.get(k)).getColorInt()) {
					indexes.remove(k);
				}
			}
		}
				while(run) {
					size=indexes.size();
					for (int k = (indexes.size() - 1); k >= 0; k--) {
						tmp = getNeighborsIndexes(BubbleList.get(indexes.get(k)));
						for(int i=(tmp.size()-1);i>=0;i--) {
							if (BubbleList.get(indexes.get(k)).getColorInt() != BubbleList.get(tmp.get(i)).getColorInt()) {
								tmp.remove(i);
							}else if(indexes.get(k).byteValue()==tmp.get(i).byteValue())
							{
								tmp.remove(i);
							}
						}

						for (int j = (indexes.size() - 1); j >= 0; j--)
						{
						for(int i=(tmp.size()-1);i>=0;i--) {
							if(indexes.get(j).byteValue()==tmp.get(i).byteValue() && i==0) {
								tmp.clear();
							}
							else if (indexes.get(j).byteValue()==tmp.get(i).byteValue())
							{
								tmp.remove(i);

							}
						}}
						for(int i=0; i<tmp.size();i++)
						{
							System.out.println(tmp.get(i));
						}

						if(!tmp.isEmpty())
						{
							indexes.addAll(tmp);

						}
						tmp.clear();

					}
					if(indexes.size()==size)
					{
						run=false;
					}
		System.out.println("linia");
				}



				if(indexes.size()<3) {
				indexes.clear();
				}
				if(indexes.size()>6)
				{
					multiBubble=true;
				}
				else
				{
					multiBubble=false;
				}

				for(int i: indexes)
				{
		playSound("");
				//sounds.playSound();

					try{
						if(BubbleList.size()>i) {
							BubbleList.get(i).img = Game.getImageExplosionList().get(BubbleList.get(i).getColorInt()-1); //append explosion image
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
					Thread.sleep(30);
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
					if(BubbleList.size()==i+1)
					{

						while(BubbleList.get(i)==null)
						{
							BubbleList.remove(i);
							i--;
						}
					}
					//System.out.println(i);
				}
				countAndSetPoint();


			}

			/**
			 * Count and set point.
			 */
			public void countAndSetPoint()
			{
				if(demagedBubbles>2)
				if(demagedBubbles==3)
				{
					point+=10;
				}
				else if(demagedBubbles==4)
				{
					point+=16;
				}
				else
				{
					point+=Math.pow(2,demagedBubbles);
				}
				countPointPanel.setScore(point);
				demagedBubbles=0;
			}

	/**
	 * Gets the game.
	 *
	 * @return the game
	 */
	/*
	 * get GameInstance
	 */
	public GameInstance getGame()
	{
		return Game;
	}

	/**
	 * Sets the pause.
	 *
	 * @param value the new pause
	 */
	/*
	* set value variable pause
	* @param value is true or false parameter
	 */
	public void setPause(boolean value)
	{
		pause=value;
	}

	/**
	 * Check game over.
	 */
	/*
	*Check if the game is over
	 */
	public void checkGameOver()
	{
		if((int)(BubbleList.size()/capacity)+1>=18)
		{
			gameOver=true;
			repaint();
			stopAnimationThread();
			pause=true;
			JOptionPane.showMessageDialog(null,"Game over\n Liczba uzyskanych punktow: "+point);
			updateBestRanking();


			//BubbleList.clear();
			Missle=null;
			NextMissle=null;
			setInitiation();
			scaleValueY=1;
			scaleValueX=1;

		}
	}
	
	/**
	 * Update best ranking.
	 */
	/*
	*Updates the best player results
	 */
	public void updateBestRanking()
	{
		String[] scores = Config.bestRanking();
		ArrayList<String> nick=new ArrayList<String>();//List containing nicks player with best records list
		ArrayList<String> points=new ArrayList<String>();//List containing points player with best records list
		if (scores == null) {
              /*  JOptionPane.showMessageDialog(null, Config.language[23]
                                + "!", Config.language[9],
                        JOptionPane.ERROR_MESSAGE);*/
		} else {
			String[] message = new String[10];

			for(int i =0; i<10; i++) {
				//if (scores[2*i]!= null) {
				nick.add(scores[2 * i]);
				points.add(scores[2 * i + 1]);
				//}
			}
			int i=0;
			boolean breakWhile=false;
			while(i<10 &&  !breakWhile) {
				StringBuilder sb = new StringBuilder();
				sb.append("");
				sb.append(point);
				String pointString = sb.toString();

				if (points.get(i) != null) {
					if (point > Integer.parseInt(points.get(i))) {

						points.add(i, pointString);
						nick.add(i, Game.getUsername());
						breakWhile = true;

					} }else {
					points.set(i,pointString);
					nick.set(i,Game.getUsername());
					breakWhile=true;
				}
				i++;

			}
			Game.writeBestRanking(nick,points);


		}
	}
	
	/**
	 * Level control.
	 */
	public void levelControl()
	{
		if(Game.getLevel().getHowHard()=="Easy" && point>PointsBoardMedium)
		{
			JOptionPane.showMessageDialog(null,Config.packLanguage[18]);
			Game.getLevel().determineMaxColor("Medium");
			Game.getLevel().setHowHard("Medium");
			countShootAtLevel=0;
			maxColor = Game.getLevel().maxColor;
			Game.getLevel().writeToFile();
			BubbleList.clear();
			createBubbleList();
			level.setText(Config.packLanguage[16]+" \n"+Game.getLevel().getHowHard());


		}else if (Game.getLevel().getHowHard()=="Easy" && ShootBoardMedium<countShootAtLevel)
		{
			JOptionPane.showMessageDialog(null,Config.packLanguage[18]);
			Game.getLevel().determineMaxColor("Medium");
			Game.getLevel().setHowHard("Medium");
			countShootAtLevel=0;
			maxColor = Game.getLevel().maxColor;
			Game.getLevel().writeToFile();
			BubbleList.clear();
			createBubbleList();
			level.setText(Config.packLanguage[16]+" \n"+Game.getLevel().getHowHard());

		}else if (Game.getLevel().getHowHard()=="Medium" && point>PointsBoardHard)
		{
			JOptionPane.showMessageDialog(null,Config.packLanguage[18]);
			Game.getLevel().determineMaxColor("Hard");
			Game.getLevel().setHowHard("Hard");
			countShootAtLevel=0;
			maxColor = Game.getLevel().maxColor;
			Game.getLevel().writeToFile();
			BubbleList.clear();
			createBubbleList();
			level.setText(Config.packLanguage[16]+" \n"+Game.getLevel().getHowHard());

		}else if (Game.getLevel().getHowHard()=="Medium" && ShootBoardHard<countShootAtLevel)
		{
			JOptionPane.showMessageDialog(null,Config.packLanguage[18]);
			Game.getLevel().determineMaxColor("Hard");
			Game.getLevel().setHowHard("Hard");
			countShootAtLevel=0;
			maxColor = Game.getLevel().maxColor;
			Game.getLevel().writeToFile();
			BubbleList.clear();
			createBubbleList();
			level.setText(Config.packLanguage[16]+" \n"+Game.getLevel().getHowHard());
		}
	}

/**
 * Level board.
 */
/*
*Function read parameters with configuration file
 */
	public void levelBoard()
	{

		Properties levelBoard = new Properties();

		try {
//load  contents file with path configuration
			levelBoard.load(new FileInputStream(Config.configurationPath));

//get points board for medium level
				PointsBoardMedium=	Integer.parseInt(levelBoard.getProperty("Points medium"));
			//get points board for hard level
					PointsBoardHard=Integer.parseInt(levelBoard.getProperty("Points hard"));
			//get number of shoot for go easy level
					ShootBoardMedium=Integer.parseInt(levelBoard.getProperty("Shoot medium"));
			//get number of shoot for go medium level
				ShootBoardHard=Integer.parseInt(levelBoard.getProperty("Shoot hard"));
			//get Number of shots on easy after which bubbles are added
			AddBubbleEasy=Integer.parseInt(levelBoard.getProperty("Add easy"));
			//get Number of shots on easy after which bubbles are added
			AddBubbleMedium=Integer.parseInt(levelBoard.getProperty("Add medium"));
			//get Number of shots on easy after which bubbles are added
			AddBubbleHard=Integer.parseInt(levelBoard.getProperty("Add hard"));


			}



		 catch (IOException e) {
			System.out.println(e);
		}


	}

	/**
	 * Checks if is game over.
	 *
	 * @return true, if is game over
	 */
	public boolean isGameOver()
	{
		return gameOver;
	}
	
	/**
	 * Sets the sound on.
	 *
	 * @param value the new sound on
	 */
	public void setSoundOn(boolean value)
	{
	this.soundOn=value;
	}

	/**
	 * Play sound.
	 *
	 * @param url the url
	 */
	public  synchronized void playSound(final String url) {
		if(soundOn)
		{
		new Thread(new Runnable() {
			// The wrapper thread is unnecessary, unless it blocks on the
			// Clip finishing; see comments.
			public void run() {
				try {
					//InputStream test = new FileInputStream("C:\\Music1.wmv");
				AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(Config.fileSoundExplosion.getAbsoluteFile());
					Clip clip = AudioSystem.getClip();
					clip.open(audioInputStream);
					clip.start();
				} catch (Exception e) {
					System.err.println(e.getMessage());
				}
			}
		}).start();}
	}

}