package com.gaurav;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.sql.Date;
import java.util.ArrayList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import com.gaurav.dao.UserDao;
import com.gaurav.dao.UserDaoImpl;
import com.gaurav.model.User;

import domPackage.My_DOM_Parser;

public class App 
{
    public static void main( String[] args )
    {
    	UserDao dao = new UserDaoImpl();

		// TODO Auto-generated method stub
		DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
		
		try {
			// Doc builder object
			DocumentBuilder builder = factory.newDocumentBuilder();
			DocumentBuilder builder2 = factory.newDocumentBuilder();
			
			// Parse XML to tree 
			Document doc = builder.parse("xml2.xml");
			Document doc2 = builder2.parse("xml3.xml");
			
			// My own XML parser 
			My_DOM_Parser dp = new My_DOM_Parser();
			
			// Grabs nodes from sheet nodes and country nodes from XML1
			NodeList sheets = doc.getElementsByTagName("fme:Sheet");
			NodeList Aus = doc.getElementsByTagName("fme:country");
			
			// Grabs nodes from XML2 for sheet and country nodes
			NodeList sheets2 = doc2.getElementsByTagName("fme:Sheet");
			NodeList Aus2 = doc2.getElementsByTagName("fme:country");		
	
			//Get all countries
			ArrayList<String[]> countries = dp.getCountries(sheets2);
			
			// Here we grab relevant data for each country for the last 10 weeks using for loop
			for (String[] country : countries) {
				
				// Note this code currently checks only 2022 we can edit for more info!
				for (int i = 30; i > 21; i--) {
					
					// Must convert i to string to pass to relevant functions
					String week = String.valueOf(i);
					
					// Fetch XML data
					float NoCases = dp.countryWeekAvg(Aus, country[0], "2020-W"+week);
					float noDetVarsAvg = dp.countryNoDetVarAvg(Aus2, country[0], "2022-"+week);
					ArrayList<String> variantsCountry = dp.getVariants(Aus2, country[0], "2022-"+week);
					
					//Now we concat each variant into one big string 
					String varObj = "";
					for (String var : variantsCountry) {
						varObj += " " + var;
					}
					
					if(NoCases == 0.0f) {
						NoCases = -1f;
						System.out.println("NULL CASES");
					}
					if(varObj == "") {
						varObj = "NA";
						System.out.println("Null var");
					}
					
					// Add to the SQL table using our hibernate object !
					System.out.println(country[0]);
					System.out.println(NoCases);
					System.out.println(varObj);
					System.out.println("2022-W"+week);
					System.out.println(country[1]);
					System.out.println(noDetVarsAvg);
			    	User user = new User(country[0], NoCases, varObj, "2022-W"+week, country[1], noDetVarsAvg);
			    	dao.saveUser(user);
					
				}
			}
			
		} catch (ParserConfigurationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SAXException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	
    }
}



//UserDao dao = new UserDaoImpl();
//BufferedReader input = new BufferedReader(new InputStreamReader(System.in));
//while(true) {
//	try {
//        System.out.println("==================MENU=================");
//        System.out.println("1. Create a new user");
//        System.out.println("2. See a user");
//        System.out.println("3. See all the users");
//        System.out.println("4. Update a user information");
//        System.out.println("5. Delete a user");
//        System.out.println("6. Exit");
//        System.out.print("Enter your choice: ");
//        int choice = Integer.valueOf(input.readLine());
//
//        switch(choice) {
//	        case 1:
//	        {
//	        	System.out.print("Enter the firstname: ");
//	        	String firstname = input.readLine().trim();
//	        	System.out.print("Enter the lastname: ");
//	        	String lastname = input.readLine().trim();
//	        	System.out.println();
//	        	System.out.println("Dob format will be yyyy-mm-dd");
//	        	System.out.print("Enter the dob: ");
//	        	String date = input.readLine().trim();
//	        	User user = new User(firstname, lastname, Date.valueOf(date));
//	        	System.out.println("\nAdding the user.........");
//	        	dao.saveUser(user);
//	        	System.out.println("User added successfully!");
//	        	break;
//	        }
//	        case 2:
//	        {
//	        	System.out.print("Enter the userId: ");
//	        	long id = Long.valueOf(input.readLine());
//	        	System.out.println(dao.getUserById(id));
//	        	break;
//	        }
//	        case 3:
//	        {
//	        	dao.getAllUsers().forEach(u -> System.out.println(u));
//	        	break;
//	        }
//	        case 4:
//	        {
//	        	System.out.println("Enter the userId: ");
//	        	long id = Long.valueOf(input.readLine());
//	        	User user = dao.getUserById(id);
//	        	if(user == null) {
//	        		System.out.println("Sorry! The user does not exit.");
//	        		break;
//	        	}
//	        	System.out.println("Leave blank if don't want to change.");
//	        	System.out.print("Enter the firstname: ");
//	        	String firstname = input.readLine().trim();
//	        	if(firstname != "")
//	        		user.setFirstname(firstname);
//	        	System.out.print("Enter the lastname: ");
//	        	String lastname = input.readLine().trim();
//	        	if(lastname != "")
//	        		user.setLastname(lastname);
//	        	System.out.println();
//	        	System.out.println("Dob format will be yyyy-mm-dd");
//	        	System.out.print("Enter the dob: ");
//	        	String date = input.readLine().trim();
//	        	if(date != "")
//	        		user.setDob(Date.valueOf(date));
//	        	System.out.println("\nUpdating the user.........");
//	        	dao.updateUser(user);
//	        	System.out.println("User updated successfully!");
//	        	break;
//	        }
//	        case 5:
//	        {
//	        	System.out.println("Enter the userId: ");
//	        	long id = Long.valueOf(input.readLine());
//	        	System.out.println("Deleting the user.......");
//	        	dao.deleteUserById(id);
//	        	System.out.println("User deleted successfully!");
//	        	break;
//	        }
//	        case 6:
//	        	System.exit(0);
//        }
//	} catch (Exception e) {
//		e.printStackTrace();
//	}
//}