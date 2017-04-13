import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;

/**
 * @author Daniel
 * @version 2017-04-02.
 */
public class FirstWindow extends JFrame implements ActionListener,ItemListener {
    JButton OK,Anuluj;
    Choice  languageChoice;
    JLabel textt;
    public FirstWindow()
    {
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(500,300);
        setLayout(null);


       languageChoice= new Choice();
       languageChoice.addItemListener(this);
       textt=new JLabel(Config.languageList[2]);
       OK=new JButton (Config.standardButton[0]);
       Anuluj= new JButton(Config.standardButton[1]);
       OK.addActionListener(this);
       Anuluj.addActionListener(this);
        languageChoice.add(Config.languageList[0]);
        languageChoice.add(Config.languageList[1]);
        add(textt);
        textt.setBounds(150,100, 100,30);
        add(languageChoice);
        languageChoice.setBounds(275,100, Config.sizeChoice[0],Config.sizeChoice[1]);
        add(OK);
        OK.setBounds(400,200,Config.sizeButton[0],Config.sizeButton[1]);
        add(Anuluj);
        Anuluj.setBounds(300,200,Config.sizeButton[0],Config.sizeButton[1]);




    }

    public void actionPerformed(ActionEvent e) {
        Object source = e.getSource();

        if(source==OK)
        {
            Config.language("Language/"+languageChoice.getSelectedItem()+".txt");
            dispose();
            GameWindow gameWindow=new GameWindow();
            gameWindow.setVisible(true);
            gameWindow.pack();
        }
        else if(source==Anuluj) {
            dispose();
        }}
    
    public void itemStateChanged(ItemEvent ie)

    {
      String arg=ie.getItem().toString();

      if(arg==Config.languageList[0])
      {
          textt.setText(Config.languageList[2]);

      }
      else if (arg==Config.languageList[1])
      {
          textt.setText(Config.languageList[3]);


      }

    }

}
