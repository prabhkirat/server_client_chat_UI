package ass2;
	import java.awt.*;
	import javax.swing.*;
	
public class Client {



	

	
		public static void main(String[] args) {

			
			EventQueue.invokeLater(new Runnable() {

				
				public void run() {

					
			
					/** Creating a jframe with title Calculator */
					ClientChatUI clientChatUI = new ClientChatUI("Gurkirat's ClientChatUI");
					clientChatUI.setMinimumSize(new Dimension(588, 500));
					clientChatUI.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
					//frame.setContentPane(controller);
					clientChatUI.setLocationByPlatform(true);
					clientChatUI.pack();
					clientChatUI.setVisible(true);
					clientChatUI. setResizable(false);


				}
			});
		}
	}



