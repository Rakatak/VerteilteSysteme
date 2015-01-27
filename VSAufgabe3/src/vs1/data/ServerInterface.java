import java.rmi.Remote;
import java.rmi.RemoteException;
import java.rmi.NotBoundException;
import java.net.MalformedURLException;
import java.lang.InterruptedException;
import java.util.ArrayList;


//server interface for RMI
public interface ServerInterface extends Remote
    {
        //test method at start register
        public String start() throws RemoteException;
        
        //method for unregister disconnet and shutdown server
        public void stop() throws RemoteException, NotBoundException, MalformedURLException, InterruptedException;
        
        //method for searching for rmi input
        public ArrayList<String> search(String name, String number) throws RemoteException;

    }

