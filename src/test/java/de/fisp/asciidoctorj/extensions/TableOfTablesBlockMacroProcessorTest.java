package de.fisp.asciidoctorj.extensions;

import org.asciidoctor.Asciidoctor;
import org.asciidoctor.ast.Document;
import org.asciidoctor.ast.StructuralNode;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Assertions;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.File;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.asciidoctor.jruby.AsciidoctorJRuby.Factory.create;

public class TableOfTablesBlockMacroProcessorTest {
    @Test
    public void givenTotMacroThenSectionShouldBeAppendedTest() {
        Asciidoctor asciidoctor = create();
        Map<String, Object> map = new HashMap<>();
        File file = new File("src/test/docs/givenTotMacroThenSectionShouldBeAppendedTest.adoc");
        Document doc = asciidoctor.loadFile(file, map);
        iterateDocumentToFindTotSection(doc);
    }

    private void iterateDocumentToFindTotSection(StructuralNode block) {
        List<StructuralNode> blocks = block.getBlocks();
        for (final StructuralNode currentBlock : blocks) {
            if (Constants.TOT_ID.equals(currentBlock.getId())) {
                assertEquals("Tabellenverzeichnis", currentBlock.getTitle());
                break;
            } else {
                iterateDocumentToFindTotSection(currentBlock);
            }
        }
    }
}
