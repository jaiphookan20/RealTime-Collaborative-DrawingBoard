import java.awt.EventQueue;
import java.awt.Graphics;
import java.awt.Graphics2D;
import javax.swing.JFrame;
import java.awt.BorderLayout;
import java.awt.Canvas;
import java.awt.Color;
import javax.swing.JTextArea;
import javax.swing.JTextPane;
import javax.swing.JComboBox;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JMenu;
import java.awt.Label;
import javax.swing.JLabel;
import java.awt.TextField;
import javax.swing.JTextField;
import javax.swing.JButton;
import javax.swing.SwingConstants;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.event.MouseMotionAdapter;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Line2D;
import java.awt.geom.Rectangle2D;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.List;
import java.awt.event.MouseEvent;
import java.awt.event.MouseAdapter;
import java.awt.Window.Type;

public class ClientGUI {

	private JFrame clientWhiteBoardFrame;
	private JTextField chatInputBox;
	private JTextField inputTextField;
	private JTextArea chatArea;
	
	private static String chosenShape;
	private static String chosenSize;
	private Color chosenColor;
	private int userId = 1;
	private String username;
	private JTextArea onlineUsersDisplay;
	private String textDisplay;
	
	Canvas canvas;
	IRemote remote;
	
	public ClientGUI(IRemote remote, String username, int userId) {
		this.remote = remote;
		this.username = username;
		this.userId = userId;
		initialize();
	}
	
	public JFrame getClientWhiteBoardFrame() {
		return clientWhiteBoardFrame;
	}

	public void setClientWhiteBoardFrame(JFrame clientWhiteBoardFrame) {
		this.clientWhiteBoardFrame = clientWhiteBoardFrame;
	}

	public JTextField getChatInputBox() {
		return chatInputBox;
	}

	public void setChatInputBox(JTextField chatInputBox) {
		this.chatInputBox = chatInputBox;
	}

	public JTextField getInputTextField() {
		return inputTextField;
	}

	public void setInputTextField(JTextField inputTextField) {
		this.inputTextField = inputTextField;
	}

	private void initialize() {
		
		clientWhiteBoardFrame = new JFrame();
		clientWhiteBoardFrame.setType(Type.UTILITY);
		clientWhiteBoardFrame.setTitle("Client WhiteBoard");
		clientWhiteBoardFrame.setBounds(100, 100, 635, 667);
		clientWhiteBoardFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		/* CANVAS */
		canvas = new DrawFrame(remote); //
		
		canvas.setBounds(10, 68, 390, 422);
		clientWhiteBoardFrame.getContentPane().setLayout(null);
		canvas.setBackground(new Color(250, 255, 235));
		clientWhiteBoardFrame.getContentPane().add(canvas);
		
		JComboBox availableShapesBox = new JComboBox();
		availableShapesBox.setBounds(20, 35, 126, 27);
		
		availableShapesBox.setModel(new DefaultComboBoxModel(new String[] {"Line", "Rectangle", "Circle", "Oval", "Text"}));
		clientWhiteBoardFrame.getContentPane().add(availableShapesBox);
		
		JLabel shapeLabel = new JLabel("Shape");
		shapeLabel.setBounds(20, 18, 114, 16);
		shapeLabel.setHorizontalAlignment(SwingConstants.CENTER);
		clientWhiteBoardFrame.getContentPane().add(shapeLabel);
		
		chatInputBox = new JTextField();
		chatInputBox.setBounds(406, 504, 223, 101);
		chatInputBox.setText("Write Message:");
		clientWhiteBoardFrame.getContentPane().add(chatInputBox);
		chatInputBox.setColumns(10);
		
		chatArea = new JTextArea();
		chatArea.setBounds(406, 65, 223, 425);
		chatArea.setEditable(false);
		chatArea.setText("Chat Area:");
		clientWhiteBoardFrame.getContentPane().add(chatArea);
		
		JButton sendBtn = new JButton("Send");
		sendBtn.setBounds(445, 610, 117, 29);
		sendBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String messageTobeSent = username  + ": " +  chatInputBox.getText() + "\n";
				
				try {
					remote.addMessage(messageTobeSent);
					chatArea.setText(remote.getMessageHistory());
					
				} catch (RemoteException e1) {
					System.out.println("Chat message could not be sent");
					e1.printStackTrace();
				}
			}
		});
		clientWhiteBoardFrame.getContentPane().add(sendBtn);
		
		onlineUsersDisplay = new JTextArea();
		onlineUsersDisplay.setBounds(10, 555, 390, 84);
		onlineUsersDisplay.setText("Users Currently Online:");
		clientWhiteBoardFrame.getContentPane().add(onlineUsersDisplay);
		
		JLabel colourLabel = new JLabel("Colour");
		colourLabel.setBounds(527, 7, 102, 16);
		clientWhiteBoardFrame.getContentPane().add(colourLabel);
		
		JComboBox colourSelectionBox = new JComboBox();
		colourSelectionBox.setBounds(493, 35, 124, 27);
		colourSelectionBox.setModel(new DefaultComboBoxModel(new String[] {"Light Red", "Red", "Dark Red", "Orange", "Pink", "Green", "Dark Green", "Magenta", "White", "Light Gray", "Dark Gray", "Gray", "Blue", "Cyan", "Brown", "Black"}));
		colourSelectionBox.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String colorName = ((String) colourSelectionBox.getSelectedItem()).toLowerCase();
				chosenColor = getColorByName(colorName);
			}
		});
		
		clientWhiteBoardFrame.getContentPane().add(colourSelectionBox);
		
		JComboBox dimensionComboBox = new JComboBox();
		dimensionComboBox.setModel(new DefaultComboBoxModel(new String[] {"Small", "Medium", "Large"}));
		dimensionComboBox.setBounds(158, 35, 102, 27);
		clientWhiteBoardFrame.getContentPane().add(dimensionComboBox);
		
		dimensionComboBox.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				chosenSize = ((String) dimensionComboBox.getSelectedItem()).toLowerCase(); //default
			}
		});
		
		JLabel dimensionLabel = new JLabel("Dimension");
		dimensionLabel.setHorizontalAlignment(SwingConstants.CENTER);
		dimensionLabel.setBounds(177, 0, 188, 16);
		clientWhiteBoardFrame.getContentPane().add(dimensionLabel);
						
		inputTextField = new JTextField();
		inputTextField.setBounds(10, 517, 390, 26);
		clientWhiteBoardFrame.getContentPane().add(inputTextField);
		inputTextField.setColumns(10);
		
		JLabel inputTextLabel = new JLabel("Input Text:");
		inputTextLabel.setBounds(146, 496, 82, 16);
		clientWhiteBoardFrame.getContentPane().add(inputTextLabel);
		
		//SETTING SHAPEADAPTER:
		canvas.addMouseListener(new ShapeAdapter(remote, availableShapesBox, dimensionComboBox, colourSelectionBox, inputTextField, canvas));
		
		// This will be called when the user clicks the "X" button.
		clientWhiteBoardFrame.addWindowListener(new java.awt.event.WindowAdapter() {
		    @Override
		    public void windowClosing(java.awt.event.WindowEvent windowEvent) {
		    	try {
					remote.removeUser(userId);
					System.out.println("Closed Window!");
				} catch (RemoteException e) {
					e.printStackTrace();
				}
		    }
		});
}
	
	public void closeWindow(java.awt.event.WindowEvent windowEvent) {
    	try {
			remote.removeUser(userId);
			System.out.println("Closed Window!");
		} catch (RemoteException e) {
			e.printStackTrace();
		}
    }
	
	Color getColorByName(String colorName) {
		switch(colorName) {
		case "light red":
	        return new Color(255, 102, 102);
	    case "red":
	        return Color.RED;
	    case "dark red":
	        return new Color(153, 0, 0);
	    case "orange":
	    	return Color.ORANGE;
	    case "pink":
	        return Color.PINK;
	    case "green":
	        return Color.GREEN;
	    case "dark green":
	    	return new Color(1, 50, 32);
	    case "magenta":
	        return Color.MAGENTA;
	    case "white":
	        return Color.WHITE;
	    case "light gray":
	        return Color.LIGHT_GRAY;
	    case "dark gray":
	        return Color.DARK_GRAY;
	    case "gray":
	        return Color.GRAY;
	    case "blue":
	        return Color.BLUE;
	    case "cyan":
	        return Color.CYAN;
	    case "brown":
	        return new Color(150,75, 0);
	    case "black":
	        return Color.BLACK;
	    default:
	        return Color.BLACK;
		}
	}
	
	public void repaint() {
		try {
			chatArea.setText(remote.getMessageHistory());
			
			String usersOnline = remote.getUserMap().entrySet().toString();
	        onlineUsersDisplay.setText("Users Currently Online: " + usersOnline);
			
		} catch (RemoteException e) {
			System.out.println("Error inside repaint method of ClientGUI");
			e.printStackTrace();
		}
		canvas.repaint();
	}
	
	
	
}

