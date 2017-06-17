/*
 * 
 */
import java.awt.Choice;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemListener;

import javax.swing.JButton;
import javax.swing.JFrame;

// TODO: Auto-generated Javadoc
/**
 * The Class ClientWindow.
 *
 * @author PetrerW
 * @version 16.06.2017
 * 
 * GUI for Client app
 */
public class ClientWindow extends JFrame implements ActionListener {
	
	/** The client. */
	private Client client;
	
	/** The server IP. */
	private String serverIP;
	
	/** The exit. */
	JButton LOGIN, GetLevel, BestRank, SendScore, LOGOUT, EXIT;
	
	/** The Level choice. */
	Choice LevelChoice;
	
	/**
	 * Sets the server IP.
	 *
	 * @param serverIP the new server IP
	 */
	public void setServerIP(String serverIP){
		this.serverIP = serverIP;
	}
	
	/**
	 * Gets the server IP.
	 *
	 * @return the server IP
	 */
	public String getServerIP(){
		return serverIP;
	}
	
	/**
	 * Gets the client.
	 *
	 * @return the client
	 */
	public Client getClient(){
		return client;
	}
	
	/**
	 * Sets the client.
	 *
	 * @param client the new client
	 */
	public void setClient(Client client){
		this.client = client;
	}
	
	/**
	 * Instantiates a new client window.
	 */
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
        this.LevelChoice = new Choice();
        
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
        
        //create a choice and add available levels to it
        LevelChoice.setBounds(width/8+Config.defaultSizeChoice[0]/3, (int)(height*0.5), (int)(0.3*Config.defaultSizeChoice[0]) ,Config.defaultSizeChoice[1]);
        LevelChoice.add("1");
        LevelChoice.add("2");
        LevelChoice.add("3");
        add(LevelChoice);
        
        SendScore.addActionListener(this);
        SendScore.setBounds(width/2+Config.sizeButton[0]/5, (int)(height*0.6), (int)(2*Config.sizeButton[0]) ,Config.sizeButton[1]);
        add(SendScore);
        
        EXIT.addActionListener(this);
        EXIT.setBounds(width/4-Config.sizeButton[0]/2, (int)(height*0.6), Config.sizeButton[0] ,Config.sizeButton[1]);
        add(EXIT);
	}

	/**
	 * Instantiates a new client window.
	 *
	 * @param IP the ip
	 */
	//Constructor that sets IP address of the client
	ClientWindow(String IP){
		this();
		this.serverIP = IP;
		client.setServerIP(IP);
	}
	
	/* (non-Javadoc)
	 * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
	 */
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
		else if(source == GetLevel){
			//get Level from Choice
			String Level = LevelChoice.getSelectedItem();
			//generate a command
			command = "get_Level " + Level + " @ " + client.getIndex();
			
			System.out.println("ClientWindow: Sending command:\"" + command + "\"");
			
			//Get server's response
			response = client.sendMessage(command);
			
			//handle that response
			client.handleGetLevel(response);
		}
		else if(source == BestRank){
			//send get_best_rank command
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
