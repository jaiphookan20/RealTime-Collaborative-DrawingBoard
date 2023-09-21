import javax.swing.*;
import java.rmi.AccessException;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

public class JoinWhiteBoard {

    private static String serverAddress;
    private static String username;
    private static int userId;
    private static int port;

    public static void main(String[] args) {

        try {
            //Connect to the rmiregistry that is running on localhost
            handleArgs(args);
            Registry registry = LocateRegistry.getRegistry(port);

            //Retrieve the stub/proxy for the remote math object from the registry
            IRemote remoteWhiteBoard = (IRemote) registry.lookup("WhiteBoard");
            
            userId = remoteWhiteBoard.requestConnect(username);
            
            if(userId == -1) {
            	JOptionPane.showMessageDialog(null, "Client was not able to connect!");
                System.exit(1);
            }
            else {

            	JOptionPane.showMessageDialog(null, username + " was able to connect and has joined!");
            }
            
            
            ClientGUI clientGUI = new ClientGUI(remoteWhiteBoard, username, userId);
            clientGUI.getClientWhiteBoardFrame().setVisible(true);
            
            while(true) {
            	clientGUI.repaint();
            	
            	if(!remoteWhiteBoard.getUserMap().containsKey(userId)) {
					JOptionPane.showMessageDialog(null, "You are kicked out.");
					System.exit(0);
				}
            	
            	try {
					Thread.sleep(500);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
            }
        }

        catch (AccessException e) {
            throw new RuntimeException(e);
        } catch (NotBoundException e) {
            throw new RuntimeException(e);
        } catch (RemoteException e) {
            throw new RuntimeException(e);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        
      
    }
    
    private static void handleArgs(String[] args) throws Exception {
        if (args.length != 3) throw new Exception("Error: Wrong number of arguments, need two arguments Port number and Username.");
        try {
            serverAddress = args[0];
            port = Integer.parseInt(args[1]);
            username = args[2];
        } catch (Exception e) {
            throw new Exception("Error: Wrong type of arguments, need Port number to be Integer and Username to be String.");
        }
        if (port < 1024 || port > 65535) throw new Exception("Error: Wrong value of Port number, need Port number to be between 1024 and 65535.");
    }
}
