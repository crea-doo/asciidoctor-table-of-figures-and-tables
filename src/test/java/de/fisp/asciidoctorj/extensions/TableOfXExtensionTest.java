package de.fisp.asciidoctorj.extensions;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Assertions;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class TableOfXExtensionTest {

    @Test
    public void registerExtensionClassesToAsciidocExtensionRegistryInstanceTest() {
        TestAsciidocInterface ascInterTest = new TestAsciidocInterface();
        TableOfXExtension tableOfXExtension = new TableOfXExtension();
        tableOfXExtension.register(ascInterTest);

        boolean tofxTree = false;
        boolean tofBlock = false;
        boolean totBlock = false;

        for (int i = 0; i < ascInterTest.processorList.size(); i++) {
            switch (ascInterTest.processorList.get(i)) {
                case "de.fisp.asciidoctorj.extensions.TableOfXTreeProcessor":
                    tofxTree = true;
                    break;
                case "de.fisp.asciidoctorj.extensions.TableOfFiguresBlockMacroProcessor":
                    tofBlock = true;
                    break;
                case "de.fisp.asciidoctorj.extensions.TableOfTablesBlockMacroProcessor":
                    totBlock = true;
                    break;
                default:
                    throw new IllegalStateException(String.format("Unbekannter Prozessor %s registriert", ascInterTest.processorList.get(i)));
            }
        }

        assertEquals(true, tofxTree);
        assertEquals(true, tofBlock);
        assertEquals(true, totBlock);
    }

}
