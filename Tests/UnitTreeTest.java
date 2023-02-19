import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;
import  projectUnits.UnitTree;
import projectUnits.Units;

import static org.junit.jupiter.api.Assertions.*;

class UnitTreeTest {
    @Test
    public void checkGivenExample()  {
        List<String> output = UnitTree.returnOutput("org.eclipse.equinox.p2.director", Units.units);

        if(output.size() == 7) {
            assertEquals("org.eclipse.equinox.p2.director", output.get(0));
            assertEquals("--org.eclipse.equinox.common", output.get(1));
            assertEquals("--org.eclipse.core.jobs", output.get(2));
            assertEquals("--org.eclipse.equinox.p2.metadata", output.get(3));
            assertEquals("----org.eclipse.equinox.p2.core", output.get(4));
            assertEquals("--org.sat4j.core", output.get(5));
            assertEquals("--org.sat4j.pb", output.get(6));
        }else{
            fail("The output is not the same!");
        }
    }

    //This test checks the programs output with invalid UnitID
    @Test
    public void nonExistentUnitID (){
        List<String> output = UnitTree.returnOutput("randomUnitID", Units.units);
        if(!output.isEmpty()){
            assertEquals("" , output.get(0));
        }
    }

    //This test is supposed to fail.
    @Test
    public void failedTestExample () {
        List<String> output = UnitTree.returnOutput("org.eclipse.equinox.p2.director", Units.units);
        assertEquals("org.eclipse.equinox.p2.director", output.get(0));
        assertEquals("--org.eclipse.equinox.common", output.get(1));
        assertEquals("--org.eclipse.core.jobs", output.get(2));
        assertEquals("--org.eclipse.equinox.p2.metadata", output.get(3));
        assertEquals("--org.eclipse.equinox.p2.core", output.get(4));
        assertEquals("--org.eclipse.core.jobs", output.get(5));
        assertEquals("--org.sat4j.pb", output.get(6));
    }

    @Test
    public void unitWithNoRequiredUnits () {
        List<String> output = UnitTree.returnOutput("org.sat4j.core" , Units.units);
        assertEquals("org.sat4j.core" , output.get(0));
        if(output.size()!= 1){
            fail("The output contains more than one unit. The test failed.");
        }
    }
}