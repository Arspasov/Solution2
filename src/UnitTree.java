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

public class UnitTree {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        //The unitID of the root
        String unitID = scanner.nextLine().trim();

        //HashMap for saving the unit elements from the xml file
        Map<String, Element> units = new HashMap<>();

        //The java DOM parser provides the classes to read and write an XML file.
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();

        //A set for saving the visited node units from the tree and a level variable for printing the level of the current node.
        int level = 0;
        Set<String> visited = new HashSet<>();

        try {
            DocumentBuilder builder = factory.newDocumentBuilder();
            Document document = builder.parse("content.xml");
            document.getDocumentElement().normalize();

            //First Solution with HashMap for saving the different unit elements from the xml file
            //This method adds the unit elements from the xml file to the map
            addUnitsToMap(document, units);
            //This method prints the tree with the required unit elements starting with the root element with unit id equal to the given one.
            printRequirements(unitID,  visited,  level , units);

            //Second solution without using HashMap.
            //printUnitTree(unitID, document, visited, level);

        } catch (ParserConfigurationException | SAXException | IOException e) {
            e.printStackTrace();
        }
    }

    //This method prints the tree with the required unit elements.
    private static void printRequirements(String unitID, Set<String> visited, int level, Map<String, Element> units) {
        //condition for checking if there is an element with this unitID.
        if (units.containsKey(unitID)) {
            //This loop prints a different output depending on the level the requirement is at.
            for (int itr = 0; itr < level; itr++) {
                System.out.print("--");
            }
            System.out.println(unitID);
           visited.add(unitID);

            //We traverse through the requirements of the current unit element.
            Element eElement = units.get(unitID);
            for (int j = 0; j < eElement.getElementsByTagName("required").getLength(); j++) {
                String requirement = (eElement.getElementsByTagName("required").item(j).getAttributes().getNamedItem("name").getNodeValue());
                String requirementNamespace = (eElement.getElementsByTagName("required").item(j).getAttributes().getNamedItem("namespace").getNodeValue());

                //This condition checks if the requirement is already visited and if the requirements namespace and name are valid.
                if (!visited.contains(requirement) && !requirement.equals(unitID) && (requirementNamespace.equals("org.eclipse.equinox.p2.iu") || requirementNamespace.equals("osgi.bundle"))) {
                    //If such and element is found the process is repeated with and increased depth level.
                    printRequirements(requirement, visited, level + 1 , units);
                }
            }
        }
    }



    //This method adds all the unit elements to a HashMap.
    private static void addUnitsToMap(Document document, Map<String, Element> unit) {
        NodeList list = document.getElementsByTagName("unit");

        //Loop for traversing all the unit elements in the xml file.
        for (int i = 0; i < list.getLength(); i++) {
            Node currentItem = list.item(i);
            String unitId = list.item(i).getAttributes().getNamedItem("id").getNodeValue();
            if (currentItem.getNodeType() == Node.ELEMENT_NODE) {

                List<String> requirements = new ArrayList<>();
                Element eElement = (Element) currentItem;

                //We put the unit element in the map. The unitId is the key and the value saved is a list of requirements for this unit.
                unit.put(unitId,eElement);
            }
        }
    }

    //This method is for the solution without HashMap for saving the unit elements.
    //This method finds the needed unit element in the node and traverses through its requirements. The method is recursive in order to find all the required unit elements.
    public static void printUnitTree(String NodeID, Document document, Set<String> visited, int level) {
        NodeList list = document.getElementsByTagName("unit");

        //This loop finds the element in the document
        for (int i = 0; i < list.getLength(); i++) {
            Node currentItem = list.item(i);

            //This condition checks if the given id matches the unitID.
            if (currentItem.getNodeType() == Node.ELEMENT_NODE && list.item(i).getAttributes().getNamedItem("id").getNodeValue().equals(NodeID)) {
                //This loop prints a different output depending on the level the requirement is at.
                for (int itr = 0; itr < level; itr++) {
                    System.out.print("--");
                }
                System.out.println(NodeID);
                visited.add(NodeID);

                //We traverse through the requirements of the current unit element.
                Element eElement = (Element) currentItem;
                for (int j = 0; j < ((Element) currentItem).getElementsByTagName("required").getLength(); j++) {
                    String requirement = (eElement.getElementsByTagName("required").item(j).getAttributes().getNamedItem("name").getNodeValue());
                    String requirementNamespace = (eElement.getElementsByTagName("required").item(j).getAttributes().getNamedItem("namespace").getNodeValue());

                    //This condition checks if the requirement is already visited and if the requirements namespace and name are valid.
                    if (!visited.contains(requirement) && !requirement.equals(NodeID) && (requirementNamespace.equals("org.eclipse.equinox.p2.iu") || requirementNamespace.equals("osgi.bundle"))) {
                        //If such and element is found the process is repeated with and increased depth level.
                        printUnitTree(requirement, document, visited, level + 1);
                    }
                }
                break;
            }
        }
    }
}