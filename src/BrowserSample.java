import java.sql.Timestamp;
import java.time.Instant;
import java.util.*;

import org.json.JSONArray;

public class BrowserSample {
	class Browser {
		private String browserName;
		private String version;
		private JSONArray browserHistory = new JSONArray();
		
		public Browser(String browserName, String version) {   //constructor to create a new Browser
			this.browserName = browserName;
			this.version = version;
		}
		
		public void addNewHistory(String url, String ip) {		//add new History
			JSONArray history = new JSONArray();
			history.put(url);
			history.put(ip);
			Timestamp accessTime = Timestamp.from(Instant.now());
			history.put(accessTime);
			browserHistory.put(history);
		}
		public void displayHistory() {
			if(browserHistory.length() == 0) {
				System.out.println("No History To Display");
				return;
			}
			System.out.println(this.browserName + " Browser History");
			System.out.println("-------------------");
			String history = browserHistory.toString();
			int n = 0;
			for(int i=1;i<history.length(); ++i) {
				char ch = history.charAt(i);
				if(ch == '[') {
					n++;
					System.out.print("\n" + n + "  ");
				}
				else if (ch == ']'){
					System.out.println("");
				}
				else if(ch == ',') {
					System.out.print("\t");
				}
				else {
					System.out.print(ch);
				}
			}
		}
		
		public void getBrowserDetails() {
			System.out.print(this.browserName + "\t" + this.version + "\n");
		}
		
		public void removeHistory(int n) {
			browserHistory.remove(n-1);
			System.out.println("Record removed Successfully...");
		}
		public void resetHistory() {
			while(this.browserHistory.length()> 0) {
				browserHistory.remove(0);
			}
			System.out.println(this.browserName + " reset Successfully... ");
		}
		public int getNumberOfRecords() {
			return this.browserHistory.length();
		}
		
	}
	public static Scanner scanner = new Scanner(System.in);
	private static Map<String, Browser> browserMap = new HashMap<>();
	public static void main(String[] args) {
		
		int choice = 0;
		while(choice != 5) {
			System.out.println("\nMAIN MENU");
			System.out.println("========================");
			System.out.println("1. Show all browsers");
			System.out.println("2. Add new Browser");
			System.out.println("3. Remove Browser");
			System.out.println("4. Browser History");
			System.out.println("5. Exit");
			System.out.print("Enter your choice: ");
			choice = scanner.nextInt();
			
			switch(choice) {
				case 1: showBrowser();
						break;
				case 2: addBrowser();
						break;
				case 3: removeBrowser();
						break;
				case 4: browserData();
						break;
				case 5: System.out.println("Thank you... ");
						break;
				default:System.out.println("Invalid Choice!!!");
			}	
		}
		System.out.println("Adios...");
		scanner.close();
	}
	
	private static void showBrowser() {		//display all the browsers
		if(browserMap.size() == 0) {
			System.out.println("\nNo Browser added yet!!!");
		}
		else {
			System.out.println("\nBrowser List");
			System.out.println("-------------");
			for(Map.Entry<String, Browser> m: browserMap.entrySet()) {

				browserMap.get(m.getKey()).getBrowserDetails();
			}
		}

	}
	
	private static void addBrowser() {		//add a new browser
		System.out.print("Enter browser: ");
		//Scanner scanner = new Scanner(System.in);
		String newBrowser = scanner.next();
		if(browserMap.containsKey(newBrowser)) {
			System.out.println(newBrowser + "already exists");
		}
		else {
			System.out.print("Enter version: ");
			String version = scanner.next();
			browserMap.put(newBrowser, new BrowserSample().new Browser(newBrowser, version));
			System.out.println("Browser Added...");
		}
	}
	
	private static void removeBrowser() {	//remove browser
		System.out.println("Select the browser to be removed: ");
		for(Map.Entry<String, Browser> m: browserMap.entrySet()) {
			browserMap.get(m.getKey()).getBrowserDetails();
		}
		System.out.print("Enter: ");
		String deleteBrowser = scanner.next();
		browserMap.remove(deleteBrowser);
		System.out.println(deleteBrowser + " removed Successfully... ");
	}
	
	private static void browserData() {		//browser history
		System.out.print("Enter browser: ");
		String browserChoice = scanner.next();
		if(!browserMap.containsKey(browserChoice)) {
			System.out.println("Browser Not Found... TRY AGAIN...");
			return;
		}
		System.out.println("\n1. Display Browser History");
		System.out.println("2. Add New History Entry");
		System.out.println("3. Remove History Entry");
		System.out.println("4. Reset History");
		System.out.print("Enter your Choice: ");
		int option = scanner.nextInt();
		
		switch(option) {
			case 1: browserMap.get(browserChoice).displayHistory();
					break;
			case 2: System.out.print("Enter url: ");
					String url = scanner.next();
					System.out.print("Enter ip: ");
					String ip = scanner.next();
					browserMap.get(browserChoice).addNewHistory(url, ip);
					break;
			case 3: if(browserMap.get(browserChoice).getNumberOfRecords() == 0) {
						System.out.println("\nNo records to delete...");
					}
					else {
						browserMap.get(browserChoice).displayHistory();
						System.out.print("Enter the record to be removed: ");
						int line = scanner.nextInt();
						browserMap.get(browserChoice).removeHistory(line);
					}
					break;
			case 4: browserMap.get(browserChoice).resetHistory();
					break;
			default:System.out.println("Invalid Choice... ");
		}
	}
	

}
