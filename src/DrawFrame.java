import java.awt.Canvas;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Line2D;
import java.awt.geom.Rectangle2D;
import java.rmi.RemoteException;
import java.util.List;

class DrawFrame extends Canvas {
	
    	protected IRemote remote;
    	
		public DrawFrame(IRemote remote) {
			super();
			this.remote = remote;
		}

		public DrawFrame() {
			super();
		}
		
		public void update(Graphics g) {
	        
			super.update(g); //calls paint method
	        Graphics2D graphics2D = (Graphics2D) g;
	        
	        try {
	        	for (Shape shape : remote.getCombinedShapeList()) 
		        {
		        	String shapeName =  shape.getShapeName().toLowerCase();
		        	
		            graphics2D.setColor(shape.getColor());
		            
		        	switch(shapeName) {
		        		case("circle"):
		        			graphics2D.draw(new Ellipse2D.Double(shape.getX1(), shape.getY1(), shape.getX2(), shape.getY2()));
		        			break;
		        		case("rectangle"):
		        			graphics2D.draw(new Rectangle2D.Double(shape.getX1(), shape.getY1(), shape.getX2(), shape.getY2()));
		        			break;
		        		case("line"):
		        			graphics2D.draw(new Line2D.Double(shape.getX1(), shape.getY1(), shape.getX2(), shape.getY2()));
	        				break;
		        		case("oval"):
		                    graphics2D.draw(new Ellipse2D.Double(shape.getX1(), shape.getY1(), shape.getX2(), shape.getY2()));
		                    break;
		        		case("text"):
	            			graphics2D.drawString(shape.getTextToDisplay(), shape.getX1(), shape.getY1());
	            			break;
	        		}
		        }
	        }
	        
	        catch(RemoteException err) {
	        	System.out.println(err.getMessage());
	        }
	        
	        
	    }
		
	}