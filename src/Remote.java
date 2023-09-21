

import javax.swing.*;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.concurrent.*;
import java.util.*;

public class Remote extends UnicastRemoteObject implements IRemote{
    
	private int userId = 1;
	private List<Shape> combinedShapeList = new ArrayList<>();
    private ConcurrentHashMap<Integer, String> userMap = new ConcurrentHashMap<Integer, String>();
    
	public ConcurrentHashMap<Integer, String> getUserMap() throws RemoteException {
		return userMap;
	}

	public void setUserMap(ConcurrentHashMap<Integer, String> userMap) {
		this.userMap = userMap;
	}

	public String messageHistory = "";

	public String getMessageHistory() throws RemoteException { 
		return messageHistory;
	}

	public void setMessageHistory(String messageHistory) {
		this.messageHistory = messageHistory;
	}

	protected Remote() throws RemoteException {}
	
	public int requestConnect(String name) throws RemoteException {
        int option = JOptionPane.showConfirmDialog(null,  "Do you approve User: " + name + " to join?", "Request to join",JOptionPane.YES_NO_OPTION);
        
        if (option == 0){
            userMap.put(userId, name); 
            userId++;
        	return userId - 1;
        }
        return -1;
    }
	
	public void setCombinedShapeList(List<Shape> combinedShapeList) throws RemoteException {
		this.combinedShapeList = combinedShapeList;
	}
	
	public List<Shape> getCombinedShapeList() throws RemoteException {
		return combinedShapeList;
	}

	
    public void addToCombinedShapeList(Shape shape) throws RemoteException {
    	combinedShapeList.add(shape);
    }
    
    public void removeUser(int userID) throws RemoteException {
        userMap.remove(userID);
        System.out.println("Printing UserMap: " + userMap.entrySet().toString());
    }
    
    public synchronized void addMessage(String message) throws RemoteException {
    	messageHistory += message;
    }
    
    
    public String[] getOnlineUsersList() throws RemoteException {
		ArrayList<String> onlineUsers = new ArrayList<String>();
				
		for(Map.Entry<Integer, String> user: userMap.entrySet()) {
			onlineUsers.add("userID: " + user.getKey() + ", " + "username: " + user.getValue());
		}
		
		return onlineUsers.toArray(new String[1]);
	}
    
    
    
}