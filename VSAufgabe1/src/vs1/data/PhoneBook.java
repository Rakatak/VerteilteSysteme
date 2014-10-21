import java.util.ArrayList;


/**
* This Class represents the PhoneBook used for searching
* The class contains a static final ArrayList<String> as the book
* @author  Robin Steller
*/

public final class PhoneBook {
	
	public static final ArrayList<String> list = new ArrayList<String>() {{
		add("Hopkins");
		add("4711");
		add("Nathan");
		add("0815");
		add("Hanzo");
		add("4711");
		add("Hopkins");
		add("0816");
		add("Von Knotenknut");
		add("8008");
		add("N\u00f6ga");
		add("6565");
		}};
}
