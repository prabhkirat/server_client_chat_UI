package ass2;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import javax.swing.JFrame;
import javax.swing.JTextArea;

public class ChatRunnable <T extends JFrame & Accessible> implements Runnable {

	
	final T ui;
	final Socket socket;
	final ObjectInputStream inputStream;
	final ObjectOutputStream outputStream;
	final JTextArea display;
	
	public ChatRunnable(T ui, ConnectionWrapper connection) {
		this.ui=ui;
		this.socket=connection.getSocket();
		this.inputStream=connection.getInputStream();
		this.outputStream=connection.getOutputStream();
		this.display=ui.getDisplay();
	}

	
	@Override
	public void run() {
		String strin=null;
		
		while(true) {
			if(!socket.isClosed()) {
				try {
					strin = inputStream.readObject().toString();
					strin.trim();
					if(strin.equals(ChatProtocolConstants.CHAT_TERMINATOR)) {
						final String terminate;
						LocalDateTime time = LocalDateTime.now();
						DateTimeFormatter formatter =DateTimeFormatter.ofPattern("L H:m a");
						terminate = ChatProtocolConstants.DISPLACMENT+time+ChatProtocolConstants.LINE_TERMINATOR+strin;
						display.append(terminate);
					}else {
						final String append;
						LocalDateTime time = LocalDateTime.now();
						DateTimeFormatter format =DateTimeFormatter.ofPattern("L H:m a");
						append = ChatProtocolConstants.DISPLACMENT+time+ChatProtocolConstants.LINE_TERMINATOR+strin;
						display.append(append);
					}
				} catch (ClassNotFoundException | IOException e) {
					e.printStackTrace();
				}
			}else {
				break;
			}
		}
		
		if(!socket.isClosed()) {
			try {
				outputStream.writeObject(ChatProtocolConstants.DISPLACMENT+
						ChatProtocolConstants.CHAT_TERMINATOR+
						ChatProtocolConstants.LINE_TERMINATOR);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		ui.closeChat();
	}

}
