import java.rmi.Remote;
import java.rmi.RemoteException;
import java.rmi.NotBoundException;
import java.net.MalformedURLException;
import java.lang.InterruptedException;
import java.util.ArrayList;



public interface ServerInterface extends Remote
    {
        public String start() throws RemoteException;
        
        public void stop() throws RemoteException, NotBoundException, MalformedURLException, InterruptedException;

        public ArrayList<String> search(String name, String number) throws RemoteException;

    }

