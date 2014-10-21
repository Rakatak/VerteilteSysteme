package vs1.data;

import java.util.ArrayList;

/**
 * This class represents the PhoneThread searching for Phone numbers in the PhoneBook.list
 * @author Robin
 *
 */

public class PhoneThread extends Thread {

	String number;
	ArrayList <String> list;
	ArrayList<String> resultList;
	
    public PhoneThread(final String number, final ArrayList<String> list,  ArrayList<String> resultList) {
       this.number = number;
       this.list = list;
       this.resultList = resultList;
    }

    @Override
    public void run() {
    	for (int i = 1; i < list.size(); i += 2){
    		if (number.equals(list.get(i))){
    			resultList.add(list.get(i - 1));
    			resultList.add(list.get(i));

//    			System.out.println("Name: " + list.get(i - 1)+ " PhoneNumber: " + list.get(i));
    		}
    	}
       
    }
}
