import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Scanner;

import javax.swing.JFrame;
/**
 * @author PetrerW
 * @version 16.06.2017
 */
public class ServerWindow extends JFrame implements ActionListener{
	private Server server;
	
	public Server getServer(){
		return this.server;
	}
	
	ServerWindow(){
		server = new Server();
	}
	
	public static void main(String args[]){
		
		//Create a new Server instance
		Server server = new Server();
		
		//run server
		server.start();
	}
	
	@Override
	public void actionPerformed(ActionEvent arg0) {
		// TODO Auto-generated method stub
		
	}
}
