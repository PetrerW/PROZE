import javax.swing.*;
import java.awt.*;

/**
 * Created by Daniel on 2017-03-27.
 */
public class ViewPanel extends JPanel {
    JButton start_pause;
    JLabel scores, scoresCount;


    private GameSpace space;
    private GameSpace previewPanel;
    private CountPointPanel pointPanel;
    private GameWindow gameFrame;
    private JPanel box, box2;
    private TextPanel level, scoreText;
    private JLabel username;

    /*
     * @param _username Username of a player that plays current game
     */
    ViewPanel(String _username) {
    	this();
    	username = new JLabel(_username);
    	box.add(username);
    }
    
    ViewPanel() {

        //_previewPanel = new GridPanel(4, 4, true, Config.interfaceColors[1],
        //      Config.boardSize);*/
        setLayout(new BorderLayout());


        pointPanel = new CountPointPanel(Config.fontLabel[1]);
        scoreText = new TextPanel(Config.packLanguage[14],
                Config.fontLabel[0]);
        //  TextPanel previewText = new TextPanel(Config.packLanguage[15] + ":",
        //        Config.fontLabel[0]);

        start_pause = new JButton(Config.packLanguage[9]);
        start_pause.setPreferredSize(new Dimension(Config.sizeButton[0], Config.sizeButton[1]));
        // _level = new TextPanel(Config.language[28] + " 1",
        //         Config.interfaceColors[2], Config.textSize[2]);

        int size = Config.boardSize * 4;
        pointPanel.setPreferredSize(new Dimension(Config.sizeLabel[0], Config.fontLabel[1]));


        box = new JPanel();
        box2 = new JPanel();
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
        all.add(box2);
        all.add(box);
        all.add(level);

        space = new GameSpace();
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

    public GameSpace getGameSpace() {
        return this.space;
    }
}