package projectUnits;

import org.w3c.dom.Element;

import java.util.*;

public class UnitTree {

    //This method calls the printRequirements function and returns a String with the output.
    public static List<String> returnOutput (String unitID, Map<String, Element> units){
        //A set for saving the visited node units from the tree and a level variable for printing the level of the current node.
        Set<String> visited = new HashSet<>();
        //List for saving the output variables
        List<String> output = new ArrayList<>();

        printRequirements(unitID,0,units, output , visited);
        return output;
    }

    //This method prints the tree with the required unit elements.
    public static void printRequirements(String unitID, int level, Map<String, Element> units , List<String> output , Set<String> visited) {
        //condition for checking if there is an element with this unitID.
        if (units.containsKey(unitID)) {
            //This loop prints a different output depending on the level the requirement is at.
            String valueForOutput = "";
            //The output in the console here is not needed because the function returnOutput returns a list with the entire output. To be removed later.
            for (int itr = 0; itr < level; itr++) {
                System.out.print("--");
                valueForOutput = valueForOutput.concat("--");
            }
            //The output in the console here is not needed because the function returnOutput returns a list with the entire output. To be removed later.
            System.out.println(unitID);
            valueForOutput = valueForOutput.concat(unitID);
            output.add(valueForOutput);
            visited.add(unitID);

            //We traverse through the requirements of the current unit element.
            Element eElement = units.get(unitID);
            for (int j = 0; j < eElement.getElementsByTagName("required").getLength(); j++) {
                String requirement = (eElement.getElementsByTagName("required").item(j).getAttributes().getNamedItem("name").getNodeValue());
                String requirementNamespace = (eElement.getElementsByTagName("required").item(j).getAttributes().getNamedItem("namespace").getNodeValue());

                //This condition checks if the requirement is already visited and if the requirements namespace and name are valid.
                if (!visited.contains(requirement) && !requirement.equals(unitID) && (requirementNamespace.equals("org.eclipse.equinox.p2.iu") || requirementNamespace.equals("osgi.bundle"))) {
                    //If such and element is found the process is repeated with and increased depth level.
                    printRequirements(requirement, level + 1 , units , output , visited);
                }
            }
        }
    }
}
