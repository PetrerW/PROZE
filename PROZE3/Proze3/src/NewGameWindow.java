import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
/*
 * @author PetrerW
 * @version 15.04.2017
 */
public class NewGameWindow extends JFrame implements ActionListener{
    JTextField textField;
    JLabel pickUsernameLabel, chooseLevel;
    JButton OK;
    JButton Cancel;
    Choice levelChoice;
    GameWindow gameWindow;

    public NewGameWindow(GameWindow gameWindow_){
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        int width = 500;
        int height = 300;
        setSize(width,height);
        setLayout(null);

        //Show FirstWindow in the middle of the screen
        this.setLocationRelativeTo(null);
        //TODO: Instead of "Nowa Gra" should go languageChoice[n] from Config class
        this.setTitle(Config.packLanguage[1]);

        textField = new JTextField();
        //TODO: Instead of "Enter your username" should go languageChoice[n] from Config class
        pickUsernameLabel = new JLabel("Enter your username: ");
        chooseLevel = new JLabel("Choose level:");
        OK = new JButton("OK");
        Cancel = new JButton("Cancel");
        levelChoice = new Choice();

        //Place textField in the middle and up
        textField.setBounds(width/2-50, height/6, 100, 30);
        add(textField);

        OK.addActionListener(this);
        OK.setBounds(width/4-Config.sizeButton[0]/2, (int)(height*0.7), Config.sizeButton[0],Config.sizeButton[1]);
        add(OK);

        Cancel.addActionListener(this);
        Cancel.setBounds(width/4+width/2-Config.sizeButton[0]/2, (int)(height*0.7), Config.sizeButton[0],Config.sizeButton[1]);
        add(Cancel);

        pickUsernameLabel.setBounds(width/2-50 - 150,height/6, 150,30);
        add(pickUsernameLabel);

        //Level choice options
        levelChoice.add("Easy");
        levelChoice.add("Medium");
        levelChoice.add("Hard");
        
        //Size of the choice component
        levelChoice.setBounds(width/2-50, height/3, 100, 30);
        
        //Add to the window
        add(levelChoice);

        //Size of the label "Choose the level"
        chooseLevel.setBounds(width/2-50 - 100,height/3, 150,30);
        
        //Add the label to the window
        add(chooseLevel);
        
        //gameWindow=new GameWindow();
        gameWindow=gameWindow_;
    }

    @Override
    public void actionPerformed(ActionEvent e) {

        Object source = e.getSource();

        if(source == OK){
            try{
                //Username must not be empty
                if(textField.getText().isEmpty()){
                    throw new Exception("Empty Username");
                }
                else if(textField.getText().contains("#") | textField.getText().contains("/")){
                    throw new Exception("Input String contains wrong characters");
                }
                else{
                    //Create new GameInstance
                    GameInstance Game = new GameInstance();
                    
                    //Get username from text field
                    Game.getUsername(textField.getText());
                    
                    //Get level from choice bar
                    Game.getLevel().determineMaxColor(levelChoice.getSelectedItem());

                    //Create new game, initiation new game
                    gameWindow.getGameSpace().inctiationGame(Game);
                    gameWindow.getGameSpace().start();

                    dispose();
                    this.dispose();
                }

            }
            catch(Exception exc){
                if(exc.getMessage() == "Empty Username")
                    JOptionPane.showMessageDialog(this, "Please enter a valid Username!");
                else if(exc.getMessage() == "Input String contains wrong characters")
                    JOptionPane.showMessageDialog(this, "Please enter a valid Username! It may not contain characters: \"#\" or /");
                System.err.println("NewGameWindow.itemStateChanged, source = textField");
            }
        }
      else  if(source == Cancel)
            this.dispose();
    }
}