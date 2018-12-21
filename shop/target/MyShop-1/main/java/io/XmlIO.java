package io;
import model.*;
import org.w3c.dom.*;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.File;
import java.util.ArrayList;

public class XmlIO {
    private ArrayList<Item> items = new ArrayList<>();

/*    public static void main(String[] args) {
        XmlIO xml = new XmlIO();
        ArrayList<Item> items = new ArrayList<>();ArrayList<Item> items1 = new ArrayList<>();
        items.add(new Item("Pomade1","1111", "Red","500", "50","0","Pomade"));
        items.add(new Item("Pomade2","1112", "Pink","300", "100","0","Pomade"));
        items.add(new Item("Pomade3","1113", "Hot pink", "300","10","0","Pomade"));
        items.add(new Item("Pomade4","1114", "New", "150","110","0","Pomade"));
        xml.saveItemsToFile("1.xml", items);
	xml.loadItemsFromFile("1.xml", items1);
    }*/

    public void loadItemsFromFile(String filename, ArrayList<Item> items) {
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
                    items.add(new Item(
eElement.getElementsByTagName("name").item(0).getTextContent(),
eElement.getElementsByTagName("art").item(0).getTextContent(),
eElement.getElementsByTagName("description").item(0).getTextContent(),
eElement.getElementsByTagName("price").item(0).getTextContent(),
eElement.getElementsByTagName("sale").item(0).getTextContent(),
eElement.getElementsByTagName("amount").item(0).getTextContent(),
eElement.getElementsByTagName("category").item(0).getTextContent()));
		    
		
                }
            }

        } catch (
                Exception e) {
            e.printStackTrace();
            throw new RuntimeException("ErrorTaskLoading");
        }
        System.out.println(items);
    }

    public void saveItemsToFile(String filename, ArrayList<Item> items){
        try {
            DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder docBuilder = docFactory.newDocumentBuilder();

            Document doc = docBuilder.newDocument();
            Element rootElement = doc.createElement("shop");
            doc.appendChild(rootElement);

            for (int i = 0; i < items.size(); i++) {
                Element staff = doc.createElement("item");
                rootElement.appendChild(staff);

                Element name = doc.createElement("name");
                name.appendChild(doc.createTextNode(items.get(i).getName()));
                staff.appendChild(name);

                Element art = doc.createElement("art");
                art.appendChild(doc.createTextNode(items.get(i).getArt()));
                staff.appendChild(art);

                Element description = doc.createElement("description");
                description.appendChild(doc.createTextNode(items.get(i).getDesc()));
                staff.appendChild(description);

                Element price = doc.createElement("price");
                price.appendChild(doc.createTextNode(items.get(i).getPrice()));
                staff.appendChild(price);

		Element sale = doc.createElement("sale");
                sale.appendChild(doc.createTextNode(items.get(i).getSale()));
                staff.appendChild(sale);

                Element amount = doc.createElement("amount");
                amount.appendChild(doc.createTextNode(items.get(i).getAmount()));
                staff.appendChild(amount);

                Element cat = doc.createElement("category");
                cat.appendChild(doc.createTextNode(items.get(i).getCat()));
                staff.appendChild(cat);

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
