import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.List;
import java.util.concurrent.*;


public interface IRemote extends Remote {

    public int requestConnect(String name) throws RemoteException;
    
    public void setCombinedShapeList(List<Shape> combinedShapeList) throws RemoteException;
    
    public List<Shape> getCombinedShapeList() throws RemoteException;;
    
    public void addToCombinedShapeList(Shape shape) throws RemoteException;
    
    public void removeUser(int userID) throws RemoteException;
    
    public void addMessage(String message) throws RemoteException;
    
    public ConcurrentHashMap<Integer, String> getUserMap() throws RemoteException;
    
    public void setUserMap(ConcurrentHashMap<Integer, String> userMap) throws RemoteException;
    
    public String getMessageHistory() throws RemoteException;
    
    public String[] getOnlineUsersList() throws RemoteException;
    
    
}