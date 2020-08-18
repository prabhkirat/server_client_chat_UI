package ass2;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

public class ConnectionWrapper {

	ObjectOutputStream outputStream;
	ObjectInputStream inputStream;
	Socket socket;

	public ConnectionWrapper(Socket socket){
		this.socket=socket;
	}
	
	Socket getSocket() {
		return this.socket;
	}
	ObjectOutputStream getOutputStream() {
		return this.outputStream;
	}
	ObjectInputStream getInputStream() {
		return this.inputStream;
	}

	ObjectInputStream createObjectIStreams() throws IOException{
		inputStream= new ObjectInputStream(socket.getInputStream());
		return inputStream;
	}
	
	ObjectOutputStream createObjectOStreams() throws IOException{
		
		outputStream = new ObjectOutputStream(socket.getOutputStream());
		return outputStream;
	}
	
	void createStreams() throws IOException{
		createObjectOStreams();
		createObjectIStreams();
	}

	public void closeConnection()throws IOException{
		if(outputStream!=null) {
			outputStream.close();
		}
		if(inputStream!=null) {
			inputStream.close();
		}
		if(socket!=null) {
			socket.close();
		}
	}
	
}
