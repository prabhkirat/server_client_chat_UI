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
import javax.swing.border.TitledBorder;

public class ServerChatUI extends JFrame {
	Controller controller = new Controller();

	private JTextField messagetext;
	private JTextArea chattext;

	ServerChatUI(java.net.Socket s){
		
		super("Gurkirat's Friend ServerChatUI");
		try {
			UIManager.setLookAndFeel(UIManager.getCrossPlatformLookAndFeelClassName());
		} catch (Exception e) {
			e.printStackTrace();
		}
		JPanel panel = new JPanel();
		setFrame(panel);
		runClient();
	}
	private class WindowController extends WindowAdapter {

		public void windowClosing(WindowEvent e) {
			System.exit(0);

		}

	}

	private class Controller implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
			// TODO Auto-generated method stub

		}

	}
	
	
	public void createUI(){
		JPanel top = new JPanel(new BorderLayout());
		JPanel messageP = new JPanel(new BorderLayout());

		JPanel middle = new JPanel();
		JPanel bottom = new JPanel(new BorderLayout());
		JButton send = new JButton("Send");
		JScrollPane scrollPane;

		

		middle.setBorder(BorderFactory.createTitledBorder(BorderFactory.createLineBorder(Color.BLACK, 10), "MESSAGE"));

		messagetext = new JTextField();
		messagetext.setText("Enter message");
		messagetext.setBackground(Color.WHITE);
		messagetext.setEditable(true);
		messagetext.setPreferredSize(new Dimension(440, 20));
		messagetext.setCaretPosition(0);
		
		send.setEnabled(true);
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

		add(bottom, BorderLayout.CENTER);
		
	}
	public final void setFrame(JPanel panel) {
	//setContentPane(panel);

		
	}
	private void runClient() {
		createUI();

	}
}
