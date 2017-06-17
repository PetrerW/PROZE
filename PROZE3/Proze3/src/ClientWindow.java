import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;

/**
 * 
 * @author PetrerW
 * @version 16.06.2017
 * 
 * GUI for Client app
 */
public class ClientWindow extends JFrame implements ActionListener {
	private Client client;
	private String serverIP;
	JButton LOGIN, GetLevel, BestRank, SendScore, LOGOUT, EXIT;
	
	public void setServerIP(String serverIP){
		this.serverIP = serverIP;
	}
	
	public String getServerIP(){
		return serverIP;
	}
	
	//default constructor
	ClientWindow(){
		client = new Client();
		
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        int width = 500;
        int height = 400;
        setSize(width,height);
        setLayout(null);

        //Show FirstWindow in the middle of the screen
        this.setLocationRelativeTo(null);
        //TODO: Instead of "Connecting to the server" should go languageChoice[n] from Config class
        this.setTitle("Client");
        
        //Create Buttons
        this.LOGIN = new JButton("Log in");
        this.LOGOUT = new JButton("Log out");
        this.BestRank = new JButton("Get Server's Best Rank");
        this.GetLevel = new JButton("Load Map");
        this.SendScore = new JButton("Save your score");
        this.EXIT = new JButton("Exit");
        
        //Place buttons on the window and add them listeners
        LOGIN.addActionListener(this);
        LOGIN.setBounds(width/4-Config.sizeButton[0]/2, (int)(height*0.2), Config.sizeButton[0],Config.sizeButton[1]);
        add(LOGIN);
        
        LOGOUT.addActionListener(this);
        LOGOUT.setBounds(width/2+Config.sizeButton[0]/2, (int)(height*0.2), (int)(1.2*Config.sizeButton[0]) ,Config.sizeButton[1]);
        add(LOGOUT);
        
        BestRank.addActionListener(this);
        BestRank.setBounds(width/2-Config.sizeButton[0]/5, (int)(height*0.4), (int)(2.5*Config.sizeButton[0]) ,Config.sizeButton[1]);
        add(BestRank);
        
        GetLevel.addActionListener(this);
        GetLevel.setBounds(width/4-Config.sizeButton[0]/2, (int)(height*0.4), (int)(1.2*Config.sizeButton[0]) ,Config.sizeButton[1]);
        add(GetLevel);
        
        SendScore.addActionListener(this);
        SendScore.setBounds(width/2+Config.sizeButton[0]/5, (int)(height*0.6), (int)(2*Config.sizeButton[0]) ,Config.sizeButton[1]);
        add(SendScore);
        
        EXIT.addActionListener(this);
        EXIT.setBounds(width/4-Config.sizeButton[0]/2, (int)(height*0.6), Config.sizeButton[0] ,Config.sizeButton[1]);
        add(EXIT);
	}

	@Override
	public void actionPerformed(ActionEvent arg0) {
		// TODO Auto-generated method stub
		Object source = arg0.getSource();
		
		//Command sent to server
		String command;

		//response of the server
		String response;
		
		if(source == LOGIN){
			//Generate client's command 
			command = "login @ " + client.getServerIP();
			
			System.out.println("ClientWindow: Sending command:\"" + command + "\"");
			
			//Get server's response
			response = client.sendMessage(command);
			
			//handle that response
			client.handleLogin(response);
			
		}
		else if(source == LOGOUT){
			//send LOGOUT command
			command = "logout @ " + client.getIndex();
			
			System.out.println("ClientWindow: Sending command:\"" + command + "\"");
			
			//Get server's response
			response = client.sendMessage(command);
			
			//handle that response
			client.handleLogout(response);
		}
		else if(source == BestRank){
			//send get_best_rank command
		}
		else if(source == GetLevel){
			//send get_Level command
		}
		else if(source == SendScore){
			//send SendScore command
		}
		else if(source == EXIT){
			//send LOGOUT command and exit
			this.dispose();
		}
		
	}
}
