package projectUnits;
import java.util.*;

public class UnitTreeMain {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        //The unitID of the root
        String unitID = scanner.nextLine().trim();

        Set<String> visited = new HashSet<>();
        List<String> output = new ArrayList<>();
            //This method prints the tree with the required unit elements starting with the root element with unit id equal to the given one.
           UnitTree.printRequirements(unitID, 0 , Units.units , output , visited);

    }
}