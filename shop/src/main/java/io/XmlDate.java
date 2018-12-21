package io;
import org.w3c.dom.*;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.File;
import java.util.ArrayList;
import model.*;
public class XmlDate {
    private ArrayList<MyDate> dates = new ArrayList<MyDate>();

    public void loadDatesFromFile(ArrayList<MyDate> dates) {
        try {
            File fXmlFile = new File("date.xml");
            DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
            Document doc = dBuilder.parse(fXmlFile);

            doc.getDocumentElement().normalize();
	
            NodeList nList = doc.getElementsByTagName("date");
            for (int temp = 0; temp < nList.getLength(); temp++) {
                Node nNode = nList.item(temp);
                if (nNode.getNodeType() == Node.ELEMENT_NODE) {
		    int a = 0; int b = 0; int c = 0; int d = 0; 
			Element eElement = (Element) nNode;
		    try{
			a = Integer.parseInt(eElement.getElementsByTagName("day").item(0).getTextContent());
			b = Integer.parseInt(eElement.getElementsByTagName("month").item(0).getTextContent());
			c = Integer.parseInt(eElement.getElementsByTagName("year").item(0).getTextContent());
			d = Integer.parseInt(eElement.getElementsByTagName("money").item(0).getTextContent());
			}


		    catch(Exception e){
			}
                   
                    dates.add(new MyDate(a,b,c,d));
		    
		
                }
            }

        } catch (
                Exception e) {
            e.printStackTrace();
            throw new RuntimeException("ErrorTaskLoading");
        }
 
    }

    public void saveDatesToFile(ArrayList<MyDate> dates){
        try {
            DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder docBuilder = docFactory.newDocumentBuilder();

            Document doc = docBuilder.newDocument();
            Element rootElement = doc.createElement("calendar");
            doc.appendChild(rootElement);

            for (int i = 0; i < dates.size(); i++) {
                Element date = doc.createElement("date");
                rootElement.appendChild(date);

                Element day = doc.createElement("day");
                day.appendChild(doc.createTextNode(String.valueOf(dates.get(i).getDay())));
                date.appendChild(day);

                Element month = doc.createElement("month");
                month.appendChild(doc.createTextNode(String.valueOf(dates.get(i).getMonth())));
                date.appendChild(month);

                Element year = doc.createElement("year");
                year.appendChild(doc.createTextNode(String.valueOf(dates.get(i).getYear())));
                date.appendChild(year);

                Element money = doc.createElement("money");
                money.appendChild(doc.createTextNode(String.valueOf(dates.get(i).getMoney())));
                date.appendChild(money);

            }
            TransformerFactory transformerFactory = TransformerFactory.newInstance();
            Transformer transformer = transformerFactory.newTransformer();
            DOMSource source = new DOMSource(doc);
            StreamResult result = new StreamResult(new File("date.xml"));
            transformer.transform(source,result);
        } catch (Exception e) {
            throw new RuntimeException("ErrorTaskSaving");
        }
    }
	
}
