package io;
import org.w3c.dom.*;
import model.*;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.File;
import java.util.ArrayList;

public class XmlItem {
    private ArrayList<BasketItem> items = new ArrayList<BasketItem>();


    public void loadItemsFromFile(String filename, ArrayList<BasketItem> items) {
        try {
            File fXmlFile = new File(filename);
            DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
            Document doc = dBuilder.parse(fXmlFile);

            doc.getDocumentElement().normalize();

            NodeList nList = doc.getElementsByTagName("item");
            for (int temp = 0; temp < nList.getLength(); temp++) {
                Node nNode = nList.item(temp);
                if (nNode.getNodeType() == Node.ELEMENT_NODE) {
                    Element eElement = (Element) nNode;
                    items.add(new BasketItem(
			eElement.getElementsByTagName("username").item(0).getTextContent(),
			eElement.getElementsByTagName("name").item(0).getTextContent(),
			eElement.getElementsByTagName("art").item(0).getTextContent(),
			eElement.getElementsByTagName("price").item(0).getTextContent(),
			eElement.getElementsByTagName("amount").item(0).getTextContent()));
                }
            }

        } catch (
                Exception e) {
            e.printStackTrace();
            throw new RuntimeException("ErrorTaskLoading");
        }
        System.out.println(items);
    }

    public void saveItemsToFile(String filename, ArrayList<BasketItem> items){
        try {
            DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder docBuilder = docFactory.newDocumentBuilder();

            Document doc = docBuilder.newDocument();
            Element rootElement = doc.createElement("basket");
            doc.appendChild(rootElement);

            for (int i = 0; i < items.size(); i++) {
                Element staff = doc.createElement("item");
                rootElement.appendChild(staff);

                Element art = doc.createElement("art");
                art.appendChild(doc.createTextNode(items.get(i).getArt()));
                staff.appendChild(art);
		Element us = doc.createElement("username");
                us.appendChild(doc.createTextNode(items.get(i).getUsername()));
                staff.appendChild(us);

                Element description = doc.createElement("price");
                description.appendChild(doc.createTextNode(items.get(i).getPrice()));
                staff.appendChild(description);

                Element price = doc.createElement("name");
                price.appendChild(doc.createTextNode(items.get(i).getName()));
                staff.appendChild(price);

                Element amount = doc.createElement("amount");
                amount.appendChild(doc.createTextNode(items.get(i).getAmount()));
                staff.appendChild(amount);
            }
            TransformerFactory transformerFactory = TransformerFactory.newInstance();
            Transformer transformer = transformerFactory.newTransformer();
            DOMSource source = new DOMSource(doc);
            StreamResult result = new StreamResult(new File(filename));
            transformer.transform(source,result);
        } catch (Exception e) {
            throw new RuntimeException("ErrorTaskSaving");
        }
    }
}
