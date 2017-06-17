/*
 * 
 */
import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

// TODO: Auto-generated Javadoc
/**
 * Created by Daniel on 2017-03-27.
 */
public class ViewPanel extends JPanel implements ActionListener, Runnable {
   
   /** The sounds. */
   private JButton start_pause,sounds;
    
    /** The stop animation kicker. */
    private Thread stopAnimationKicker;
    
    /** The space. */
    private GameSpace space;
    
    /** The point panel. */
    private CountPointPanel pointPanel;
    
    /** The game frame. */
    private GameWindow gameFrame;
    
    /** The score text. */
    private TextPanel level, scoreText;
    
    /** The start. */
    private boolean start=false;
    
    /** The sound on. */
    private boolean isRunning=false,soundOn=true;

/** The effect on. */
Image effectOn;

/** The effect off. */
Image effectOff;

/** The icon off. */
ImageIcon iconOn,iconOff;

    /**
     * Instantiates a new view panel.
     */
    ViewPanel() {



        setLayout(new BorderLayout());

        try {
             effectOn = ImageIO.read(new File("Graphics/musicIcon.png"));
            effectOff = ImageIO.read(new File("Graphics/musicOffIcon.png"));
            iconOn=new ImageIcon(effectOn);
            iconOff=new ImageIcon(effectOff);
        } catch (Exception ex) {
            System.out.println(ex);
        }
        pointPanel = new CountPointPanel(Config.fontLabel[1]);
        scoreText = new TextPanel(Config.packLanguage[14],
                Config.fontLabel[0]);
        start_pause = new JButton(Config.packLanguage[10]);
        start_pause.setPreferredSize(new Dimension(Config.sizeButton[0], Config.sizeButton[1]));
        start_pause.addActionListener(this);
        sounds=new JButton(iconOn);
        sounds.addActionListener(this);


        int size = Config.boardSize * 4;
        pointPanel.setPreferredSize(new Dimension(Config.sizeLabel[0], Config.fontLabel[1]));


        JPanel box = new JPanel();
        JPanel box2 = new JPanel();
        JPanel box3 = new JPanel();
        level = new TextPanel(Config.packLanguage[16], Config.fontLabel[0]);
        scoreText.setPreferredSize(new Dimension(Config.sizeLabel[0],
                Config.fontLabel[0] * 3 / 2));

        box.setLayout(new BoxLayout(box, BoxLayout.PAGE_AXIS));
        JPanel all = new JPanel();
        all.setLayout(new GridLayout(5, 1));
      /*  box.setLayout(new BoxLayout(box, BoxLayout.Y_AXIS));
        box2.setLayout(new BoxLayout(box2, BoxLayout.Y_AXIS));*/
        // box3.setLayout(new GridLayout(2, 1));

        box.add(scoreText);
        box.add(pointPanel);
        //box2.add(previewText);
        // box2.add(_previewPanel);
        //_box3.add(_level);

        box2.add(start_pause);
        box3.add(sounds);
        all.add(box2);
        all.add(box);
        all.add(level);
        all.add(box3);

        space = new GameSpace(pointPanel,level);
        add(space, BorderLayout.CENTER);
        add(all, BorderLayout.EAST);
        // add(space, BorderLayout.CENTER);


    }
   /* public void paint (Graphics g)
    {
        g.setColor(Color.WHITE);
        g.drawRect(10,350,100,100);
        g.fillRect(10,350,100,100);
    }*/

    /**
    * Gets the game space.
    *
    * @return the game space
    */
   public GameSpace getGameSpace() {
        return this.space;
    }

    /* (non-Javadoc)
     * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
     */
    @Override
    public void actionPerformed(ActionEvent e) {

        Object source=e.getSource();

        if(source==start_pause) {

                if (isRunning) {
                    if (start == false) {

                        start_pause.setText(Config.packLanguage[9]);
                        space.setPause(true);
                        space.stopAnimationThread();
                        start = true;

                    } else {

                        start_pause.setText(Config.packLanguage[10]);
                        space.startAnimationThread();
                        space.setPause(false);
                        start = false;
                    }
                }

            }else if (source==sounds)
        {
            if(soundOn)
            {
                soundOn=false;
                space.setSoundOn(soundOn);
                sounds.setIcon(iconOff);
            }else
            {
                soundOn=true;
                space.setSoundOn(soundOn);
                sounds.setIcon(iconOn);
            }
        }
        }

    /**
     * Start animation thread.
     */
    void startAnimationThread() {
        (stopAnimationKicker = new Thread(this)).start();
    }

    /**
     * Stop animation thread.
     */
    void stopAnimationThread() {
        stopAnimationKicker = null;
    }

    /* (non-Javadoc)
     * @see java.lang.Runnable#run()
     */
    @Override
    public void run() {
while(stopAnimationKicker==Thread.currentThread())
{

    try {
        space.stopAnimation();
    } catch (InterruptedException e) {
        e.printStackTrace();
    }
    if(space.isGameOver()) {
        isRunning = false;
    }
    else
    {
        isRunning=true;
    }
}

    }
    
    /**
     * Start.
     */
    public synchronized void start() {

        isRunning = true;
        startAnimationThread();



    }
}