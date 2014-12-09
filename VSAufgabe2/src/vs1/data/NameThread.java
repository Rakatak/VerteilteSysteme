import java.util.ArrayList;

/**
 * This class represents the NameThread searching for Names in the PhoneBook.list
 * @author Robin
 *
 */

public class NameThread extends Thread {

	String number;
	ArrayList <String> list;
	ArrayList<String> resultList;
	
    public NameThread(final String number, final ArrayList<String> list,  ArrayList<String> resultList) {
       this.number = number;
       this.list = list;
       this.resultList = resultList;
    }

    //thread ist started searching for names
    @Override
    public void run() {
    	for (int i = 0; i < list.size(); i += 2){
    		if (number.equals(list.get(i))){
    			resultList.add(list.get(i));
    			resultList.add(list.get(i + 1));

//    			System.out.println("Name: " + list.get(i)+ " Phone Number: " + list.get(i + 1));
    		}
    	}
       
    }
}
