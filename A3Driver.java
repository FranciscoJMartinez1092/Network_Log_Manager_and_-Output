import java.io.*;
import java.util.*;
import java.text.*;

class LogEntry{
	//member variables of LogEntry class
	private String id;
	private String timestamp;
	private String source;
	private String destination;
	private String protocol;
	private String length;
	private String description;
	 
	//non default constructor 
	//also throwing exceptions for each variable that is not in the right format
	public LogEntry(String id, String timestamp, String source, String destination, String protocol, String length, String description)throws IllegalArgumentException{
	this.id = id;
	try {
		Integer.parseInt(this.id);
	}
	catch (IllegalArgumentException e) {
		throw e;
		
	}
	this.timestamp = timestamp;
	if(this.timestamp.equals(null) || this.timestamp.equals(" "))
		throw new IllegalArgumentException("Must have a value");
	this.source = source;
	if(this.source.equals(null) || this.source.equals (""))
		throw new IllegalArgumentException("Must have a value");
	this.destination = destination;
	if(this.destination.equals(null) || this.destination.equals(""))
		throw new IllegalArgumentException("Must have a value");
	this.protocol = protocol;
	if(this.protocol.equals(null) || this.protocol.equals("" ))
		throw new IllegalArgumentException("Must have a value");
	if( this.protocol.equals("-"))
		throw new IllegalArgumentException("Must have a value");
	this.length = length;
	if(this.length.equals(null) || this.length.equals("") || this.length.equals("0"))
		throw new IllegalArgumentException("Must have a value");
	this.description = description;
	if(this.description.equals(null) || this.description.equals( ""))
		throw new IllegalArgumentException("Must have a value");
	}
	
	//to string method that returns the member variables of the LogEntry object parsed with commas
	public String toString(){
		String variables = id + timestamp + source + destination + protocol + length + description;
		return String.join(",", variables);
	}
	
	//member variable accesors or getters
	public String getId(){
	return id;
	}
	public String getTimestamp(){
	return timestamp;
		
	}
	public String getSource(){
	return source;
		
	}
	public String getDestination(){
	return destination;
	}
	public String getProtocol(){
	return protocol;
	}
	public String getLength(){
		return length;
	}
	public String getDescription(){
		return description;
	}
}

class NetworkLogManager{
	//member variable
	private ArrayList<LogEntry> listLogEntries;
	//Enum I use for the switch var in the searchBy
	enum Variables{ID, SOURCE, DESTINATION, PROTOCOL, LENGTH, DESCRIPTION;}
	//variable enum I use for the the search by methods
	Variables variable;
	//default constructor for the class
	public NetworkLogManager() {
		this.listLogEntries =new ArrayList<>();
		
	}
	//method for load the file. It handles the exceptions thrown from the logENtry
	//non default constructor
	public boolean loadFile(String Log) {
		File file = new File(Log);
		Scanner s;
		try {
			s = new Scanner(file);
			
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return false;
		}
		String line;
		
		LogEntry logentry;
		//reads the lines and then handles the exceptions form the LogEntry class
		//if no exceptions are handled it adds the LogEntry to the arraylist ListLogEntries
		while (s.hasNextLine()) {
			
			 line = s.nextLine();
			 String log[] = line.split(",");
			 
			 try {
			 logentry = new LogEntry(log[0], log[1], log[2], log[3], log[4], log[5], log[6]);
			 listLogEntries.add(logentry);
			 
			 
			 }
			 catch(IllegalArgumentException e){
				 System.out.print("Skipping line: ");
				 System.out.print(line + "\n");
				 
			 }
			 
			
			 
			 
		}
		s.close();
		return true;
			
	}		
		     
		
	//returns the amount of LogEntries in the arraylist
	public String toString() {
		
		int amount = listLogEntries.size();
		return ("NetworkLogManager: there are " + amount + " records");
	}
	
	//search through the arraylist for the first LogEntry that has the same id as the argument
	public LogEntry searchById(String id){
		variable = Variables.ID;
		
		return searchBy(id, variable);
	}
	
	//returns a list with all the logentries with the timestamp between the two timestamp arguments
	public ArrayList<LogEntry> searchByRange(String rangeInt, String rangeLast){
		ArrayList<LogEntry> list = new ArrayList<LogEntry>();
		try {
		 Date date1 = new SimpleDateFormat("MMM dd yyy HH:mm:ss").parse(rangeInt);
		 Date date2 = new SimpleDateFormat("MMM dd yyy HH:mm:ss").parse(rangeLast);
		 
		 for(LogEntry entry: listLogEntries) {
			 Date date = new SimpleDateFormat("MMM dd yyy HH:mm:ss").parse(entry.getTimestamp());
			 if(date.after(date1) && date.before(date2)) {
				 list.add(entry);
			 }
		 }
		}
		 catch(Exception e) {
			 
		 }
		
		 
		 return list;
		
	}
	//search through the arraylist for the first LogEntry that has the same source as the argument
	public LogEntry searchBySource(String source) {
		variable = Variables.SOURCE;
		return searchBy(source, variable);
	}
	//search through the arraylist for the first LogEntry that has the same destination as the argument
	public LogEntry searchByDestination(String destination) {
		variable = Variables.DESTINATION;
		return searchBy(destination, variable);
	}
	//search through the arraylist for the first LogEntry that has the same protocol as the argument
	public LogEntry searchByProtocol(String protocol) {
		variable = Variables.PROTOCOL;
		return searchBy(protocol, variable);
	}
	//search through the arraylist for the first LogEntry that has the same length as the argument
	public LogEntry searchByLength(String length) {
		variable = Variables.LENGTH;
		return searchBy(length, variable);
	}
	//search through the arraylist for the first LogEntry that has the same description as the argument
	public LogEntry searchByDescription(String description) {
		variable = Variables.DESCRIPTION;
		return searchBy(description, variable);
	}
	//method used for all the other searchby methods. It uses a switch method to choose the right output
	private LogEntry searchBy(String var, Variables variable) {
		
		switch(variable){
			case ID:
				for(LogEntry entry: listLogEntries) {
					if( entry.getId().equals(var)) {
						
						return entry;
					}
					
				}
				
				return null;
				

				
		
			case SOURCE:
				for(LogEntry entry: listLogEntries) {
					if( entry.getSource().equals(var))
						return entry;
				}
				return null;
				

			case DESTINATION:
				for(LogEntry entry: listLogEntries) {
					if( entry.getDestination().equals(var))
						return entry;
				}
				return null;
			case PROTOCOL:
				for(LogEntry entry: listLogEntries) {
					if( entry.getProtocol().equals(var))
						return entry;
				}
				return null;
			case LENGTH:
				for(LogEntry entry: listLogEntries) {
					if( entry.getLength().equals(var))
						return entry;
				}
				return null;
			case DESCRIPTION: 
				for(LogEntry entry: listLogEntries) {
					if( entry.getDescription().equals(var))
						return entry;
				}
				return null;
			default:
				return null;
			
				
			
		}
		
		
		
		
				
	}
}
//given class
public class A3Driver {
	private static final StringBuilder separator = new StringBuilder();

    static {
        for (int i = 0; i < 110; i++)
            separator.append('-');
    }

    public static void main(String[] args) throws FileNotFoundException, ParseException {

        NetworkLogManager networkLogManager = new NetworkLogManager();

        System.out.println(networkLogManager);

        if (networkLogManager.loadFile("Network.log")) {

            System.out.println(separator.toString());

            System.out.println(networkLogManager);

            System.out.println(separator.toString());

            System.out.print("Record with id 1: ");
            System.out.println(networkLogManager.searchById("1"));

            System.out.print("Record with id 9: ");
            System.out.println(networkLogManager.searchById("9"));

            System.out.println(separator.toString());

            ArrayList<LogEntry> list = networkLogManager.searchByRange("Jan 1 2018 00:00:00", "Dec 31 2018 23:59:59");

            System.out.printf(String.format("There are %,d entries from 2018", list.size()));
        }
        else
            System.err.println("Failed to load file");
    }
}
