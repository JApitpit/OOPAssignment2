package implementations;

import exceptions.EmptyQueueException;

public class Driver {

	public static void main(String[] args) throws NullPointerException, EmptyQueueException {
		
		
		XMLParser Parser = new XMLParser("C:\\Users\\deepa\\OneDrive\\Desktop\\Max's Programs\\OOP3\\xmlparserproject\\res\\sample1.xml");
		
		Parser.ParseFile();
	}

}
