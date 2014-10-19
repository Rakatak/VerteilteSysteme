package vs1.data;

import java.util.ArrayList;

public class Main {
	
	public static void main(String[] args){
		
		ArrayList<String> list = new ArrayList<String>();
		fillArray(list);

		
	}
	
	public static void fillArray(ArrayList<String> list){		
		list.add("Hopkins");
		list.add("4711");
		list.add("Nathan");
		list.add("0815");
		list.add("Hanzo");
		list.add("4711");
		list.add("Hopkins");
		list.add("0816");		
	}

}
