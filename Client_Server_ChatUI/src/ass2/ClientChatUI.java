package ass2;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.Socket;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.UIManager;
import javax.swing.border.EmptyBorder;
import javax.swing.border.TitledBorder;

public class ClientChatUI extends JFrame implements Accessible {
	private JLabel host;
	private JLabel port;
	private JComboBox<String> combo;

	private JTextField hosttext;
	private JTextField messagetext;
	private JTextArea chattext;
	Controller controller = new Controller();
	ObjectOutputStream outputStream;
	Socket socket;
	ConnectionWrapper connection;
	JButton connect;
	JButton send;
	ClientChatUI(String title) {

		super(title);

		/*
		 * This is done so that the user can see the same GUI view in mac OS as in
		 * windows
		 */
		try {
			UIManager.setLookAndFeel(UIManager.getCrossPlatformLookAndFeelClassName());
		} catch (Exception e) {
			e.printStackTrace();
		}
		runClient();
	}
	
	public void closeChat(){
		if(!socket.isClosed()) {
			enableConnectButton();
		}
	}

	private void enableConnectButton(){
		connect.setEnabled(true);
		connect.setBackground(Color.RED);
		send.setEnabled(false);
		hosttext.requestFocus();
	}

	private class WindowController extends WindowAdapter {

		public void windowClosing(WindowEvent e) {
		
				try {
					outputStream.writeObject(ChatProtocolConstants.CHAT_TERMINATOR+"");
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					//System.out.println(e1.getMessage());
					System.exit(0);
				}		
			System.exit(0);
		}

	}
	
	public ClientChatUI thisReturn(){
		return this;
	}

	private class Controller implements ActionListener,Accessible {

		@Override
		public void actionPerformed(ActionEvent e) {
			
			boolean connected = false;
			String host = hosttext.getText();
			
			int port;
			port = (int) combo.getSelectedItem();

			connected = connect();
			if(connected) {
				connection = new ConnectionWrapper(socket);
				Thread thread = new Thread(new ChatRunnable<ClientChatUI>(thisReturn(), connection));
				thread.start();
			}else {
				return;
			}
		}

		@Override
		public JTextArea getDisplay() {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public void closeChat() {
			// TODO Auto-generated method stub
			
		}

	}

	boolean connect() {
		return rootPaneCheckingEnabled;
	}
	
	public void createClientUI() {

		JPanel top = new JPanel(new BorderLayout());
		JPanel ctop = new JPanel(new BorderLayout());
		JPanel messageP = new JPanel(new BorderLayout());

		JPanel middle = new JPanel();
		JPanel bottom = new JPanel(new BorderLayout());
		String ports[] = new String[] { "", "8089", "65000", "65535" };
		connect = new JButton("Connect");
		send = new JButton("Send");
		JScrollPane scrollPane;

		ctop.setBorder(BorderFactory.createTitledBorder(BorderFactory.createLineBorder(Color.RED, 10), "CONNECTION"));

		JPanel hostP = new JPanel(new FlowLayout(FlowLayout.LEFT));
		host = new JLabel("Host:");

		host.setPreferredSize(new Dimension(35, 30));
		host.setDisplayedMnemonic('H');

		hostP.add(host, BorderLayout.WEST);

		hosttext = new JTextField("localhost");
		hosttext.setAlignmentX(Component.LEFT_ALIGNMENT);
		hosttext.setCaretPosition(0);

		hosttext.setPreferredSize(new Dimension(510, 20));

		hosttext.setBackground(Color.WHITE);
		hosttext.setEditable(true);
		hosttext.setMargin(new Insets(0, 5, 0, 0));

		host.setLabelFor(hosttext);

		hostP.add(hosttext, BorderLayout.CENTER);

		ctop.add(hostP, BorderLayout.NORTH);

		JPanel portP = new JPanel(new FlowLayout(FlowLayout.LEFT));
		port = new JLabel("Port: ");
		port.setDisplayedMnemonic('P');
		port.setPreferredSize(new Dimension(35, 30));
		portP.add(port);

		combo = new JComboBox<String>(ports);

		combo.setMaximumRowCount(4);
		combo.setPreferredSize(new Dimension(100, 20));
		combo.setAlignmentX(Component.LEFT_ALIGNMENT);
		combo.setBackground(Color.WHITE);
		combo.setEditable(true);
		port.setLabelFor(combo);
		combo.addActionListener(controller);

		portP.add(combo);

		connect.setBackground(Color.RED);
		connect.setPreferredSize(new Dimension(100, 20));
		connect.setMnemonic(KeyEvent.VK_C);
		connect.addActionListener(controller);
		portP.add(connect);
		ctop.add(portP, BorderLayout.SOUTH);

		top.add(ctop, BorderLayout.NORTH);

		middle.setBorder(BorderFactory.createTitledBorder(BorderFactory.createLineBorder(Color.BLACK, 10), "MESSAGE"));

		messagetext = new JTextField("Type message");
		messagetext.setBackground(Color.WHITE);
		messagetext.setEditable(true);
		messagetext.setPreferredSize(new Dimension(440, 20));

		send.setEnabled(false);
		send.setPreferredSize(new Dimension(100, 20));
		send.setMnemonic(KeyEvent.VK_S);
		send.addActionListener(controller);

		messageP.setPreferredSize(new Dimension(548, 20));

		messageP.add(messagetext, BorderLayout.WEST);
		messageP.add(send, BorderLayout.EAST);

		middle.add(messageP);

		top.add(middle, BorderLayout.CENTER);

		add(top, BorderLayout.NORTH);

		bottom.setBorder(BorderFactory.createTitledBorder(BorderFactory.createLineBorder(Color.BLUE, 10),
				"CHAT DISPLAY", TitledBorder.CENTER, TitledBorder.CENTER));
		chattext = new JTextArea(30, 45);

		bottom.setPreferredSize(new Dimension(450, 310));

		chattext.setEditable(false);

		scrollPane = new JScrollPane(chattext, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS,
				JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);

		// bottom.add(chattext, BorderLayout.CENTER);
		bottom.add(scrollPane, BorderLayout.CENTER);

		add(bottom, BorderLayout.SOUTH);

	}

	public void runClient() {
		createClientUI();
		WindowController windowController = new WindowController();
		addWindowListener(windowController);

	}

	@Override
	public JTextArea getDisplay() {
		// TODO Auto-generated method stub
		return null;
	}
}
