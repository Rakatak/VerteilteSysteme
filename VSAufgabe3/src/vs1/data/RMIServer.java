
import java.util.Date;
import java.rmi.*;
import java.rmi.registry.*;
import java.rmi.server.*;
import java.lang.InterruptedException;
import java.net.*;
import java.util.ArrayList;


public class RMIServer extends UnicastRemoteObject
implements ServerInterface {
    
    
    public RMIServer() throws RemoteException {}
    
    public String start() throws RemoteException {
        System.out.println("----Receiving first RMI----");
        return "######    RMI-Server(1099) Found    ######";
    }
    
    public ArrayList<String> search(String name, String number) throws RemoteException {
        ArrayList<String> numResult = new ArrayList<String>();
        ArrayList<String> namResult = new ArrayList<String>();
        PhoneThread numThread = null;
        NameThread namThread = null;
        
        //Start of configure request
        System.out.println("Request wird bearbeitet");
        System.out.println("***********************");
        
        
        //starting number thread
        if (number.matches(".*\\w.*")) {
            System.out.println("+++PhoneThread started+++");
            numThread = new PhoneThread(number, PhoneBook.list, numResult);
            numThread.start();
        }
        
        //starting name thread
        if (name.matches(".*\\w.*"))  {
            System.out.println("+++NameThread started+++");
            namThread = new NameThread(name, PhoneBook.list, namResult);
            namThread.start();
        }
        
        //join both threads
        if (numThread != null)  {
            try {
                System.out.println("+++PhoneThread joined+++");
                numThread.join();
            } catch (InterruptedException e1) {
                // TODO Auto-generated catch block
                e1.printStackTrace();
            }
        }
        
        if (namThread != null) {
            try {
                System.out.println("+++NameThread joined+++");
                namThread.join();
            } catch (InterruptedException e1) {
                // TODO Auto-generated catch block
                e1.printStackTrace();
            }
        }
        
        // after threads are joined, both array are joined and printed on console
        numResult.addAll(namResult);
        if (numResult.size() == 0){
            System.out.println();
            System.out.println("Suche nach " + name +  "  " + number + "   war erfolglos");
            System.out.println();
            
        } else {
            System.out.println();
            System.out.println("-----------------Results-----------------");
            for (int i = 0; i < numResult.size() - 1 ; i += 2){
                System.out.println("Name: " + numResult.get(i) + "   Number: " + numResult.get(i + 1));
                
            }
            System.out.println();
            
        }
        return numResult;
    }
    
    public void stop() throws RemoteException, NotBoundException, MalformedURLException, InterruptedException {
        System.out.println("\n\n############################################");
        System.out.println("############# Closing RMI-Server ###########");
        System.out.println("############################################");
        Naming.unbind("hello-server");
        UnicastRemoteObject.unexportObject(this, true);
        System.out.println("\n\n############################################");
        System.out.println("############# RMI-Server Closed ############");
        System.out.println("############################################");    }
    
    
    public static void main(String[] args) throws Exception {
        LocateRegistry.createRegistry(1099);     // Port 1099
        RMIServer rmiServer = new RMIServer();
        
        // Anmeldung des Dienstes mit
        // rmi://Serverhostname/Eindeutige Bezeichnung des Dienstes
        // ---------------------------------------------------------
        Naming.rebind("hello-server", rmiServer);
        System.out.println("############################################");
        System.out.println("############# RMI-Server started ###########");
        System.out.println("############################################\n\n");    }
}