import java.awt.EventQueue;
import java.awt.Graphics;
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
import javax.swing.JOptionPane;
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
import java.awt.event.MouseEvent;
import java.awt.event.MouseAdapter;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Line2D;
import java.awt.geom.Rectangle2D;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.rmi.RemoteException;
import java.awt.Graphics2D;
import java.awt.geom.*;
import java.util.*;
import javax.swing.JFileChooser;
import java.io.ObjectInputStream;
import java.awt.Window.Type;

public class ServerGUI {

	private JFrame managerFrame;
	private JTextField chatInputBox;
	private static String chosenShape;
	private static String chosenSize;
	private Color chosenColor;
	private String username;
	private JTextArea chatArea;
	private JTextArea onlineUsersDisplay;
	
	Canvas canvas;	
	IRemote remote;
	private JTextField textField;
	
	//Getters
	public JFrame getFrame() {
		return managerFrame;
	}

	public void setFrame(JFrame frame) {
		this.managerFrame = frame;
	}

	public JTextField getChatInputBox() {
		return chatInputBox;
	}

	public void setChatInputBox(JTextField chatInputBox) {
		this.chatInputBox = chatInputBox;
	}

	public ServerGUI(IRemote remote, String username) {
		this.remote = remote;
		this.username = username;
		initialize();
	}

	private void initialize() {
		
		managerFrame = new JFrame();
		managerFrame.setType(Type.UTILITY);
		managerFrame.setTitle("Manager Window");
		
		managerFrame.setBounds(100, 100, 635, 667);
		managerFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		/* CANVAS */
		canvas = new DrawFrame(remote); 
		
		canvas.setBounds(0, 68, 390, 422);
		canvas.addMouseListener(new MouseAdapter() {}
		);
		
		managerFrame.getContentPane().setLayout(null);
		canvas.setBackground(new Color(250, 255, 235));
		managerFrame.getContentPane().add(canvas);
		
		
		/* AVAILABLE SHAPES BOX */
		JComboBox availableShapesBox = new JComboBox();
		availableShapesBox.setBounds(130, 28, 83, 27);
		availableShapesBox.setModel(new DefaultComboBoxModel(new String[] {"Line", "Rectangle", "Circle", "Oval", "Text"}));
		managerFrame.getContentPane().add(availableShapesBox);
		
		
		JLabel shapeLabel = new JLabel("Shape");
		shapeLabel.setBounds(120, 0, 114, 16);
		shapeLabel.setHorizontalAlignment(SwingConstants.CENTER);
		managerFrame.getContentPane().add(shapeLabel);
		
		chatInputBox = new JTextField();
		chatInputBox.setBounds(406, 504, 223, 101);
		chatInputBox.setText("Write Message:");
		managerFrame.getContentPane().add(chatInputBox);
		chatInputBox.setColumns(10);
		
		chatArea = new JTextArea();
		chatArea.setBounds(406, 65, 223, 425);
		chatArea.setEditable(false);
		chatArea.setText("Chat Area:");
		managerFrame.getContentPane().add(chatArea);
		
		JButton sendBtn = new JButton("Send");
		sendBtn.setBounds(445, 610, 117, 29);
		sendBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				String messageTobeSent = username  + ": " +  chatInputBox.getText() + "\n";
			
				try {
					remote.addMessage(messageTobeSent);
					
				} catch (RemoteException e1) {
					System.out.println("Chat message could not be sent");
					e1.printStackTrace();
				}
				
			}
		});
		managerFrame.getContentPane().add(sendBtn);
		
		onlineUsersDisplay = new JTextArea();
		onlineUsersDisplay.setBounds(10, 532, 390, 101);
		onlineUsersDisplay.setText("Users Currently Online:");
		managerFrame.getContentPane().add(onlineUsersDisplay);
		
		JLabel colourLabel = new JLabel("Colour");
		colourLabel.setBounds(377, 3, 102, 16);
		managerFrame.getContentPane().add(colourLabel);
        
		JComboBox colourSelectionBox = new JComboBox();
		colourSelectionBox.setBounds(355, 28, 124, 27);
		colourSelectionBox.setModel(new DefaultComboBoxModel(new String[] {"Light Red", "Red", "Dark Red", "Orange", "Pink", "Green", "Dark Green", "Magenta", "White", "Light Gray", "Dark Gray", "Gray", "Blue", "Cyan", "Brown", "Black"}));
		//String colorName = ((String) colourSelectionBox.getSelectedItem()).toLowerCase();
		
		managerFrame.getContentPane().add(colourSelectionBox);
		
		JComboBox dimensionComboBox = new JComboBox();
		dimensionComboBox.setModel(new DefaultComboBoxModel(new String[] {"Small", "Medium", "Large"}));
		dimensionComboBox.setBounds(240, 28, 102, 27);
		managerFrame.getContentPane().add(dimensionComboBox);
		
		//Adding ShapeAdapter
		canvas.addMouseListener(new ShapeAdapter(remote, availableShapesBox, dimensionComboBox, colourSelectionBox, null, canvas));
				
		JLabel dimensionLabel = new JLabel("Length");
		dimensionLabel.setText("Dimensions");
		dimensionComboBox.setModel(new DefaultComboBoxModel(new String[] {"Small", "Medium", "Large"}));
		
		dimensionLabel.setHorizontalAlignment(SwingConstants.CENTER);
		dimensionLabel.setBounds(228, 3, 114, 16);
		managerFrame.getContentPane().add(dimensionLabel);
		
		textField = new JTextField();
		textField.setBounds(10, 504, 390, 26);
		managerFrame.getContentPane().add(textField);
		textField.setColumns(10);
		
		JLabel inputTextLabel = new JLabel("Input Text:");
		inputTextLabel.setBounds(151, 491, 83, 16);
		managerFrame.getContentPane().add(inputTextLabel);
		
		
		JMenuBar menuBar = new JMenuBar();
		menuBar.setToolTipText("");
		menuBar.setBounds(0, 0, 102, 22);
		managerFrame.getContentPane().add(menuBar);
		
		JMenu optionsMenu = new JMenu("Options");
		menuBar.add(optionsMenu);
		optionsMenu.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			}
		});
		
		JMenuItem newBtn = new JMenuItem("New");
		newBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					remote.getCombinedShapeList().clear();
				} catch (RemoteException e1) {
					
					e1.printStackTrace();
				}
			}
		});
		optionsMenu.add(newBtn);
		
		JMenuItem saveBtn = new JMenuItem("Save");
		saveBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
								
	            try {
	            	FileOutputStream fileOutput = new FileOutputStream("shapehistory.txt");
	            	ObjectOutputStream objOutput = new ObjectOutputStream(fileOutput);
	            	objOutput.writeObject(remote.getCombinedShapeList());
	            	objOutput.close();
		            fileOutput.close();
				} catch (RemoteException e1) {
					e1.printStackTrace();
				} catch (IOException e1) {
					e1.printStackTrace();
				}
	            
			}
		});
		optionsMenu.add(saveBtn);
		
		JMenuItem saveAsBtn = new JMenuItem("Save As");
		saveAsBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				JFileChooser jfilechooser = new JFileChooser();
				int option = jfilechooser.showSaveDialog(null);
				
				if(option == 0) {
					System.out.println("Shape History saved successfully to file!");
					
					FileOutputStream fileOutput;
					try {
						fileOutput = new FileOutputStream(jfilechooser.getSelectedFile());
						ObjectOutputStream objOutput = new ObjectOutputStream(fileOutput);
		            	objOutput.writeObject(remote.getCombinedShapeList());
		            	objOutput.close();
			            fileOutput.close();
					} catch (FileNotFoundException e1) {
						e1.printStackTrace();
					} catch (RemoteException e1) {
						e1.printStackTrace();
					} catch (IOException e1) {
						e1.printStackTrace();
					}

					
				}
				else {
					System.out.println("Shape History Save Failed!");
				}
			}
		});
		optionsMenu.add(saveAsBtn);
		
		JMenuItem openBtn = new JMenuItem("Open");
		openBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				JFileChooser jfilechooser = new JFileChooser();
				int option = jfilechooser.showOpenDialog(null);
				
				if(option == 0) {
					System.out.println("Opening File!");
					
					FileInputStream fileInputStream;
					try {
						fileInputStream = new FileInputStream(jfilechooser.getSelectedFile());
						ObjectInputStream objInput = new ObjectInputStream(fileInputStream);
						List<Shape> shapeList = (List<Shape>) objInput.readObject(); 
						remote.setCombinedShapeList(shapeList);
						objInput.close();
						fileInputStream.close();
					} catch (FileNotFoundException e1) {
						e1.printStackTrace();
					} catch (RemoteException e1) {
						e1.printStackTrace();
					} catch (IOException e1) {
						e1.printStackTrace();
					} catch (ClassNotFoundException e1) {
						e1.printStackTrace();
					}

					
				}
				else {
					System.out.println("Shape History Save Failed!");
				}

				
				
				
			}
		});
		optionsMenu.add(openBtn);
		
		JButton removeBtn = new JButton("Remove User");
		removeBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
	        		String[] listOfOnlineUsers = remote.getOnlineUsersList();
	        		
	        		String userToRemove = (String) JOptionPane.showInputDialog(null, "Please select the user to kick out.", null, JOptionPane.QUESTION_MESSAGE, null, listOfOnlineUsers, null);
	        		
	        		if(userToRemove != null) {
	        			int userID = Integer.parseInt(userToRemove.split("(: )|(, )")[1]);
	        			remote.removeUser(userID);		
	        		}
	        		
	        		} catch(Exception e1) {
	        			JOptionPane.showMessageDialog(new JFrame(), e1.getMessage(), "Dialog", JOptionPane.ERROR_MESSAGE);
	        		}
			}
		});
		removeBtn.setBounds(491, 27, 117, 29);
		managerFrame.getContentPane().add(removeBtn);
	}
	
	
	
	public void repaint() {
		canvas.repaint();
		try {
			chatArea.setText(remote.getMessageHistory());
			String usersOnline = remote.getUserMap().entrySet().toString();
	        onlineUsersDisplay.setText("Users Currently Online: " + usersOnline);
		} catch (RemoteException e) {
			System.out.println("Error inside repaint method of ServerGUI");
			e.printStackTrace();
		}
	}
}


