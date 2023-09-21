import java.net.Socket;
import java.rmi.AlreadyBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.concurrent.ConcurrentHashMap;

public class CreateWhiteBoard {
    private static int port;
    private static String username;
    private static String serverAddress;
    private static int userId = 0;
    private Socket socket;

    public static void main(String[] args) {
        try {
            handleArgs(args);
        } catch (Exception e) {
            System.out.println(e.getMessage());
            System.exit(0);
        }

        try {
            IRemote remoteCall = new Remote();
            Registry registry = LocateRegistry.createRegistry(port);
            registry.bind("WhiteBoard", remoteCall);
            System.out.println("Joined Server successfully!");
            
            ServerGUI serverGUI = new ServerGUI(remoteCall, username); //calls initialise
            remoteCall.getUserMap().put(0, username);
            
            serverGUI.getFrame().setVisible(true);
            
            while(true) {
            	serverGUI.repaint();
            	
            	try {
					Thread.sleep(500);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
            }
             
        } catch (RemoteException e){
            System.out.println("Error: Something wrong with the rmi, please try it later!");
            System.exit(0);
        } catch (AlreadyBoundException e2){
            System.out.println("Error: RMI Port number has been used, need to change another one!");
            System.exit(0);
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