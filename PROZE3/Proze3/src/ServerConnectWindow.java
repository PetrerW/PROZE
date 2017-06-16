import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.regex.PatternSyntaxException;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;

/*
 * @author PetrerW
 * @version 13.06.2017 
 */
public class ServerConnectWindow extends JFrame implements ActionListener {

	JTextField TextField; //Type in there an IP address of the server
	JLabel TypeInAddressLabel; //"Please type IP address of the server: "
	JButton OK, Cancel; //Obvious
	GameWindow gamewindow; //Hold an object of the GameWindow
	
	ServerConnectWindow(GameWindow gamewindow_){
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        int width = 500;
        int height = 300;
        setSize(width,height);
        setLayout(null);

        //Show FirstWindow in the middle of the screen
        this.setLocationRelativeTo(null);
        //TODO: Instead of "Connecting to the server" should go languageChoice[n] from Config class
        this.setTitle("Connecting to the server");
        
        //TODO: add inscriptions from Config class
        this.TypeInAddressLabel = new JLabel("Please type in IP address of the server: ");
        
        this.TextField = new JTextField("0.0.0.0");
        
        this.OK = new JButton("OK");
        this.Cancel = new JButton("Cancel");
        
        
        //Adding elements to the window and setting their size
        
        OK.addActionListener(this);
        OK.setBounds(width/4-Config.sizeButton[0]/2, (int)(height*0.7), Config.sizeButton[0],Config.sizeButton[1]);
        add(OK);

        Cancel.addActionListener(this);
        Cancel.setBounds(width/4+width/2-Config.sizeButton[0]/2, (int)(height*0.7), Config.sizeButton[0],Config.sizeButton[1]);
        add(Cancel);

        TypeInAddressLabel.setBounds(width/2-50 - 150,height/6, 255,30);
        add(TypeInAddressLabel);
        
        //Place textField in the middle and up
        TextField.setBounds(width/2+50, height/6, 100, 30);
        add(TextField);
        
        gamewindow = gamewindow_;
	}
	
	@Override
	public void actionPerformed(ActionEvent arg0) {
		// TODO Auto-generated method stub
		Object source = arg0.getSource();
		
		//IPv4 address entered by the user
		String ip = TextField.getText();
		
		//If the OK button is the source
		if( source == OK){
			try{
				//The result of matching
				returnInfo Result = IpMatcher.validateIP(ip);
				
				//error matching the IP address
				if(Result.isMatched() == false){
					throw new Exception(Result.getMessage());
				}
				else if(Result.isMatched() == true){
					this.dispose();
				}
				
			}catch(Exception e){
				String IpInfo =  new String("A correct IP address has the form: (xxx.xxx.xxx.xxx), where xxx is a number from 000 to 255");
				JOptionPane.showMessageDialog(this, e.getMessage() + IpInfo);
			}
			
		}
		else if (source == Cancel){
			this.dispose();
		}
	}
}
