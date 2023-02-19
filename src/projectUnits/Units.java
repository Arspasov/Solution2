package projectUnits;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;
import java.util.*;

public class Units {
    public static final Map<String, Element> units = createMap();

    //function for giving values to the units map.
    private static Map<String, Element> createMap() {
        Map<String, Element> aMap = new HashMap<>();
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        try {
            DocumentBuilder builder = factory.newDocumentBuilder();
            Document document = builder.parse("content.xml");
            document.getDocumentElement().normalize();

            addUnitsToMap(document, aMap);
        } catch (ParserConfigurationException | SAXException | IOException e) {
            e.printStackTrace();
        }
        return Collections.unmodifiableMap(aMap);
    }

    //This method adds all the unit elements to a HashMap.
    private static void addUnitsToMap(Document document, Map<String, Element> units) {
        NodeList list = document.getElementsByTagName("unit");

        //Loop for traversing all the unit elements in the xml file.
        for (int i = 0; i < list.getLength(); i++) {
            Node currentItem = list.item(i);
            String unitId = list.item(i).getAttributes().getNamedItem("id").getNodeValue();
            if (currentItem.getNodeType() == Node.ELEMENT_NODE) {

                List<String> requirements = new ArrayList<>();
                Element eElement = (Element) currentItem;

                //We put the unit element in the map. The unitId is the key and the value saved is a list of requirements for this unit.
                units.put(unitId,eElement);
            }
        }
    }
}
