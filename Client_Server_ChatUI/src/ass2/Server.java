package ass2;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.Toolkit;
import java.net.Socket;

import javax.swing.JFrame;

public class Server {

	
	public static void main(String[] args) {

		launchClient(null,null);
		
		
	}
	
	
	public static void launchClient(Socket s, String str) {
		EventQueue.invokeLater(new Runnable() {

			
			public void run() {

				
				
				/** Creating a jframe with title Calculator */
				ServerChatUI serverChatUI = new ServerChatUI(s);
			
				
				serverChatUI.setMinimumSize(new Dimension(588, 500));
				serverChatUI.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
				Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
				serverChatUI.setLocation(dim.width/2-serverChatUI.getSize().width/2, dim.height/2-serverChatUI.getSize().height/2);
  				serverChatUI.pack();
				serverChatUI.setVisible(true);
				serverChatUI. setResizable(false);
				


			}
		});	
	}
	
}



