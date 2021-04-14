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

public class TableOfFiguresBlockMacroProcessorTest {
    @Test
    public void givenTofMacroThenSectionShouldBeAppendedTest() {
        Asciidoctor asciidoctor = create();
        Map<String, Object> map = new HashMap<>();
        File file = new File("src/test/docs/givenTofMacroThenSectionShouldBeAppendedTest.adoc");
        Document doc = asciidoctor.loadFile(file, map);
        iterateDocumentToFindTofSection(doc);
    }

    private void iterateDocumentToFindTofSection(StructuralNode block) {
        List<StructuralNode> blocks = block.getBlocks();
        for (final StructuralNode currentBlock : blocks) {
            if (Constants.TOF_ID.equals(currentBlock.getId())) {
                assertEquals("Abbildungsverzeichnis", currentBlock.getTitle());
                break;
            } else {
                iterateDocumentToFindTofSection(currentBlock);
            }
        }
    }
}
