/*
 * 
 */
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Scanner;

import javax.swing.JFrame;
// TODO: Auto-generated Javadoc

/**
 * The Class ServerWindow.
 *
 * @author PetrerW
 * @version 16.06.2017
 */
public class ServerWindow extends JFrame implements ActionListener{
	
	/** The server. */
	private Server server;
	
	/**
	 * Gets the server.
	 *
	 * @return the server
	 */
	public Server getServer(){
		return this.server;
	}
	
	/**
	 * Instantiates a new server window.
	 */
	ServerWindow(){
		super("Server");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(500,300);
        setLayout(null);
        //Show FirstWindow in the middle of the screen
        this.setLocationRelativeTo(null);
	}
	
	/**
	 * The main method.
	 *
	 * @param args the arguments
	 */
	public static void main(String args[]){
		
		//Show Server Window
		ServerWindow SW = new ServerWindow();
		
		//Create a new Server instance
		SW.server = new Server();
		
		//Show Server window
		SW.setVisible(true);
		
		//TODO: start shall return 0 if the server has to terminate
		
			//run server
			SW.server.start();

	}
	
	/* (non-Javadoc)
	 * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
	 */
	@Override
	public void actionPerformed(ActionEvent arg0) {
		// TODO Auto-generated method stub
		
	}
}
