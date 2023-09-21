import java.awt.Canvas;
import java.awt.Color;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.rmi.RemoteException;

import javax.swing.JComboBox;
import javax.swing.JTextField;

/* ShapeAdapter */
	class ShapeAdapter extends MouseAdapter {
		
		protected IRemote remote;
		protected JComboBox availableShapesBox;
		protected JComboBox dimensionComboBox;
		protected JComboBox colourSelectionBox;
		protected JTextField inputTextField;
	    protected Canvas canvas;
	    
		public ShapeAdapter(IRemote remote, JComboBox availableShapesBox, JComboBox dimensionComboBox, JComboBox colourSelectionBox, JTextField inputTextField, Canvas canvas) {
			super();
			this.remote = remote;
			this.availableShapesBox = availableShapesBox;
			this.dimensionComboBox = dimensionComboBox;
			this.colourSelectionBox = colourSelectionBox;
			this.inputTextField = inputTextField;
			this.canvas = canvas;
		}
		
		private int x1,y1, x2, y2;
		@Override
		public void mousePressed(MouseEvent e) {
			x1 = e.getX();
			y1 = e.getY();
			
			String chosenColorString = colourSelectionBox.getSelectedItem().toString();
			Color chosenColor = getColorByName(chosenColorString.toLowerCase());
			String chosenShape = availableShapesBox.getSelectedItem().toString().toLowerCase();
			String chosenSize = dimensionComboBox.getSelectedItem().toString().toLowerCase();
			String inputTextString = null;
			
			if(inputTextField.getText() != null) {
				inputTextString = inputTextField.getText();
			}
			
//			System.out.println("Color String Mouse Pressed: " + chosenColorString);
//			System.out.println("Color Obj Mouse Pressed: " + chosenColor.toString());
			
			if (chosenShape.equals("text")) {
        		try {
					remote.addToCombinedShapeList(new Shape(x1, y1, "text", chosenColor, inputTextString));
				} catch (RemoteException e1) {
					e1.printStackTrace();
				}
        	}
				
			canvas.repaint();
		}
		
		@Override
	    public void mouseReleased(MouseEvent e) {
	        x2 = e.getX();
	        y2 = e.getY();
	        
	        String chosenColorString = colourSelectionBox.getSelectedItem().toString();
			Color chosenColor = getColorByName(chosenColorString.toLowerCase());
			String chosenShape = availableShapesBox.getSelectedItem().toString().toLowerCase();
			String chosenSize = dimensionComboBox.getSelectedItem().toString().toLowerCase();
			
			System.out.println("Color String Mouse Released: " + chosenColorString);
			System.out.println("Color Obj Mouse Released: " + chosenColor.toString());

	        // If the chosen shape is a line, create a Shape object of type "line"
	        try {
	        	if (chosenShape.equals("line")) {
		            remote.addToCombinedShapeList(new Shape(x1, y1, x2, y2, "line", chosenColor));
		        }
	        	
	        	else if (chosenShape.equals("rectangle")) {
					remote.addToCombinedShapeList(new Shape(x1, y1, x2 - x1, y2 - y1, "rectangle", chosenColor));
	        	}
	        	
	        	else if (chosenShape.equals("oval")) {
					//remote.addToCombinedShapeList(new Shape(x1 - x2/2, y1 - y2/2, x2, y2, "oval", chosenColor));
					remote.addToCombinedShapeList(new Shape(x1, y1, x2 - x1, y2 - y1, "oval", chosenColor));
	        	}
	        	
	        	else if (chosenShape.equals("circle")) {
	        		remote.addToCombinedShapeList(new Shape(x1, y1, Math.min(x2-x1, y2 - y1), Math.min(x2-x1, y2 - y1), "circle", chosenColor));
	        	}
	        	
	        }
	        catch(RemoteException err) {
	        	System.out.println(err.getMessage());
	        }
	        
	        canvas.repaint();
	    }
		
		private Color getColorByName(String colorName) {
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
}
