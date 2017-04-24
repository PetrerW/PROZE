/**
 * @author Daniel, PetrerW
 * @version 2017-04-15.
 */


import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Arrays;


public class GameWindow extends JFrame implements ActionListener {

    private JFrame languageWindow;
    private JMenuBar menubar;
    private JMenu file, help, view;
    private JMenuItem newGame, ranking,  end, save, language, setDefaultView;
    //final private JToolBar toolbar;
    private JButton pause_start, endCurrentGame;
    private GameSpace space;
    ViewPanel view_panel;


    public GameWindow()
    {
        super("Bubble-Hit");
        setSize(600,600);
        setMinimumSize(new Dimension(600,600));
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());
        
        
         view_panel=new ViewPanel();
         space=getGameSpace();
        menubar=new JMenuBar();
        file=new JMenu(Config.packLanguage[0]);
        help= new JMenu(Config.packLanguage[8]);
        view = new JMenu("Widok");

        //TODO: add its text to be read from Config data
        setDefaultView = new JMenuItem("Ustaw pocz¹tkowy widok");
        newGame=new JMenuItem(Config.packLanguage[1]);

        save= new JMenuItem(Config.packLanguage[3]);
        ranking=new JMenuItem(Config.packLanguage[4]);

        language=new JMenuItem( Config.packLanguage[6]);
        end=new JMenuItem(Config.packLanguage[7]);

        newGame.addActionListener(this);
        ranking.addActionListener(this);
        language.addActionListener(this);
        end.addActionListener(this);

        file.add(newGame);
        file.add(save);
        file.add(ranking);

        //file.add(language);
        file.addSeparator();
        file.add(end);

        view.add(setDefaultView);

        menubar.add(file);
        menubar.add(help);
        menubar.add(view);

        setJMenuBar(menubar);

        add(BorderLayout.CENTER,view_panel);

    }

    public  String fill(String value, int length, String with) {

        StringBuilder sb = new StringBuilder();
        int rest = length - value.length();
        for(int i = 1; i < rest; i++)
        {
            sb.append(" ");
        }
        sb.append(with);
        return sb.toString();

    }

    public void WriteFile()
    {
        try(FileWriter fw = new FileWriter(Config.bestRankingPath, true);
            BufferedWriter bw = new BufferedWriter(fw);
            PrintWriter out = new PrintWriter(bw))
        {
            out.println(" ");
            out.println("cos");

        }
        catch (IOException e) { }
    }


    public static void main(String[] args) {

        FirstWindow raz=new FirstWindow();
        raz.setVisible(true);
        //raz.pack();

    }

    public void actionPerformed(ActionEvent e) {
        Object source = e.getSource();

        if(source==newGame) {
            NewGameWindow nowaGra = new NewGameWindow(this);
            nowaGra.setVisible(true);
        }
        else  if (source == ranking) {

            String[] scores = Config.bestRanking();
            if (scores == null) {
              /*  JOptionPane.showMessageDialog(null, Config.language[23]
                                + "!", Config.language[9],
                        JOptionPane.ERROR_MESSAGE);*/
            } else {
                String[] message = new String[10];

                for(int i =0; i<10; i++) {
                    if (scores[2*i]!= null)
                        message[i] = (i + 1) + "."+fill(Integer.toString(i+1),3," " ) + scores[2 * i] + fill("", 25, " ") + "   "
                                + scores[2 * i + 1];

                }

                JOptionPane.showMessageDialog(null, message,
                        Config.packLanguage[11], JOptionPane.PLAIN_MESSAGE);
            }


        }
        else  if(source==language)
        {

            languageWindow=new JFrame(Config.packLanguage[12]);
            JLabel text;
            Choice languageChoice;


            languageWindow.setLayout(null);
            //languageWindow.setBounds(100,100,600,300);

            /*text=new JLabel(Config.packLanguage[13]);
            languageWindow.add(text);*/
            languageChoice= new Choice();
            languageChoice.add(Config.languageList[0]);
            languageChoice.add(Config.languageList[1]);
            languageWindow.add(languageChoice);
            languageWindow.pack();
            languageWindow.setVisible(true);
            // languageChoice.setBounds(200,100, 100,50);


        }
        else if(source==end)
        {
            dispose();
        }
        else if(source == setDefaultView){
            //TODO: doesn't work at all
            //Set default size
            setSize(getPreferredSize());
        }
    }

    /*
     * Return this.space
     */
    public GameSpace getGameSpace(){
        return this.view_panel.getGameSpace();
    }
}