package domPackage;

import java.io.IOException;
import java.util.ArrayList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

public class My_DOM_Parser {
	
	public My_DOM_Parser() {
		
	}
	
	public ArrayList<String> getVariants(NodeList countryNode, String where, String weekYear) {
		// Going through every country node
		ArrayList<String> variants = new ArrayList<>();
		// For loops through each country node
		for (int i = 0; i < countryNode.getLength(); i++) {
			
			Node country = countryNode.item(i);
			// Screens for specific country, need to check element because this tells us we checking content not tag
			
			if (country.getTextContent() == where) {
				// Do something
			}
			
			if (country.getTextContent().equals(where)) {
				
				// Crawl along the XML tree and get relevant nodes
				
				//This should be the year week node
				Node c = country.getNextSibling().getNextSibling().getNextSibling().getNextSibling();
				// If true then we on specific week year and we want to parse the variants
				if(c != null && c.getTextContent().equals(weekYear)) {
					
					// Now grab variants if not null 
					Node variant = c.getNextSibling().getNextSibling().getNextSibling().getNextSibling().getNextSibling().getNextSibling().getNextSibling().getNextSibling().getNextSibling().getNextSibling().getNextSibling().getNextSibling();
					
					// Here we check if the variant if in our list and add if not 
					if (variant != null) {
						String current_variant = variant.getTextContent();
						
						//Check if multiple variants in element
						if(current_variant.contains("/")) {
							String[] vars = current_variant.split("/");
							
							//Add each variant
							for(String var : vars) {
								if(!variants.contains(var)) {
									variants.add(var);
								}
							}
						} else if (!variants.contains(current_variant)) {
							variants.add(current_variant);
						}
					}				
				}
			}
		}
		
		return variants;
		
	}
	
	//NodeList of fme:country elements, String where is the country, weekYear is the weekYear form yyyy-wkX
	public float countryWeekAvg(NodeList Aus, String where, String weekYear){
		// Going through every country node
		float result = 0;
		float length = 0;
		// For loops through each country node
		for (int i = 0; i < Aus.getLength(); i++) {
			
			Node country = Aus.item(i);
			// Screens for specific country, need to check element because this tells us we checking content not tag
			if (country.getTextContent().equals(where)) {
				
				// Crawl along the XML tree and get relevant nodes
				Node sibling = country.getNextSibling();
				while (sibling != null) {
					
					if (sibling.getNodeType() == Node.ELEMENT_NODE) {
						Element e = (Element) sibling;
						
						if(e.getTagName().equals("fme:year_week") && e.getTextContent().contains(weekYear)) {
							
							//Double get next sibling lol because get next sibling returns the text from the year-week node
							Node grab_val = sibling.getNextSibling().getNextSibling();
							
							if(grab_val.getNodeType() == Node.ELEMENT_NODE) {
								Element elementVal = (Element) grab_val;
	
								float f = Float.parseFloat(elementVal.getTextContent());
								result += f;
								length += 1;
							}
							
							break;
						}
						if(e.getTagName().equals("fme:year_week") && !e.getTextContent().contains(weekYear)) {
							break;
						}
					}
					sibling = sibling.getNextSibling();
				}
			}
		}
		
		return result/length;
	}
	
	//NodeList of fme:country elements, String where is the country, weekYear is the weekYear form yyyy-wkX
	// NOTE same code as finding country averge however this finds average for number detections variants
	public float countryNoDetVarAvg(NodeList Aus, String where, String weekYear){
		// Going through every country node
		float result = 0f;
		float length = 0f;
		// For loops through each country node
		for (int i = 0; i < Aus.getLength(); i++) {
			
			Node country = Aus.item(i);
			// Screens for specific country, need to check element because this tells us we checking content not tag
			if (country.getTextContent().equals(where)) {
				
				// Crawl along the XML tree and get relevant nodes
				Node sibling = country.getNextSibling();
				while (sibling != null) {
					
					if (sibling.getNodeType() == Node.ELEMENT_NODE) {
						Element e = (Element) sibling;
						
						if(e.getTagName().equals("fme:year_week") && e.getTextContent().contains(weekYear)) {
							
							//This has to go along 15 siblings to grab the number of detected variants for this specific node!
							Node grab_val = sibling.getNextSibling().getNextSibling().getNextSibling().getNextSibling().getNextSibling().getNextSibling().getNextSibling().getNextSibling().getNextSibling().getNextSibling().getNextSibling().getNextSibling().getNextSibling().getNextSibling();
							if (grab_val.getNodeType() == Node.ELEMENT_NODE) {
								Element gEl = (Element) grab_val;
								float f = Float.parseFloat(gEl.getTextContent());
								System.out.println(f);
								result += f;
								length += 1;
							}
							break;
						}
						if(e.getTagName().equals("fme:year_week") && !e.getTextContent().contains(weekYear)) {
							break;
						}
					}
					sibling = sibling.getNextSibling();
				}
			}
		}
		
		
		return result/length;
		
//		return result/length;
	}
	
	// NOTE: This function now iterates through the 'XML3' document in order to grab both country and country code!
	public ArrayList<String[]> getCountries(NodeList sheets){
		
		ArrayList<String[]> Countries = new ArrayList<>();
		
		//It is tough to do a .contains check for countries in the above array list format <String[]> so we check here
		ArrayList<String> countryReference = new ArrayList<>();
		
		for(int i = 0; i < sheets.getLength(); i++) {
			Node n = sheets.item(i);
			if (n.getNodeType() == Node.ELEMENT_NODE) {
				Element m = (Element) n;
				NodeList record = m.getChildNodes();
				
				for(int y = 0; y < record.getLength(); y++) {
					Node att = record.item(y);
					if(att.getNodeType() == Node.ELEMENT_NODE) {
						Element att1 = (Element) att;
						
						// Check if we are on the country tag, if so then add the country if we haven't already
						if (att1.getTagName() == "fme:country" && !countryReference.contains(att1.getTextContent())) {
							countryReference.add(att1.getTextContent());
							String[] arr = {att1.getTextContent(), att.getNextSibling().getNextSibling().getTextContent()};
							Countries.add(arr);
						}
					}
				}
			}
		}
		return Countries;
		
	}

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
		ArrayList<String> Countries = new ArrayList<>();

		
		try {
			DocumentBuilder builder = factory.newDocumentBuilder();
			Document doc = builder.parse("xml2.xml");
			NodeList sheets = doc.getElementsByTagName("fme:Sheet");
			NodeList Aus = doc.getElementsByTagName("fme:country");
			
			DocumentBuilder builder2 = factory.newDocumentBuilder();
			Document doc2 = builder2.parse("xml3.xml");
			NodeList sheets2 = doc2.getElementsByTagName("fme:Sheet");
			NodeList Aus2 = doc2.getElementsByTagName("fme:country");
			
			My_DOM_Parser dp = new My_DOM_Parser();
			
			System.out.println(dp.countryWeekAvg(Aus, "Hungry", "2022-W21"));
			System.out.println(dp.getCountries(sheets));
			System.out.println(dp.getVariants(Aus2, "Hungry", "2022-21"));
			System.out.println(dp.getVariants(Aus2, "Hungry", "2022-21"));
			
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
