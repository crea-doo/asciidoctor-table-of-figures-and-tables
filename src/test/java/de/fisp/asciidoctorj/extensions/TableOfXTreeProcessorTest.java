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

public class TableOfXTreeProcessorTest {

    @Test
    public void givenFiguresInDocThenMapShouldContainValuesTest() {
        TableOfXTreeProcessor treeProcessor = new TableOfXTreeProcessor();
        Asciidoctor asciidoctor = create();
        Map<String, Object> map = new HashMap<>();
        File file = new File("src/test/docs/givenFiguresInDocThenMapShouldContainValuesTest.adoc");
        Document doc = asciidoctor.loadFile(file, map);
        treeProcessor.process(doc);
        assertEquals("Middle Office Service Partner Startseite1", treeProcessor.figureMap.get(1));
        //assertNull(treeProcessor.figureMap.get(2));
        assertEquals(1, treeProcessor.figureMap.size());
    }

    @Test
    public void givenTablesInDocThenMapShouldContainValuesTest() {
        TableOfXTreeProcessor treeProcessor = new TableOfXTreeProcessor();
        Asciidoctor asciidoctor = create();
        Map<String, Object> map = new HashMap<>();
        File file = new File("src/test/docs/givenTablesinDocThenMapShouldContainValuesTest.adoc");
        Document doc = asciidoctor.loadFile(file, map);
        treeProcessor.process(doc);
        assertEquals("Standard Vorgangssuche1", treeProcessor.tableMap.get(1));
        //Assert.assertNull(treeProcessor.tableMap.get(2));
        assertEquals(1, treeProcessor.tableMap.size());
    }

    @Test
    public void givenBlockAndInlineMacroThenOnlyShouldListBlockMacroTest() {
        TableOfXTreeProcessor treeProcessor = new TableOfXTreeProcessor();
        Asciidoctor asciidoctor = create();
        Map<String, Object> map = new HashMap<>();
        File file = new File("src/test/docs/givenBlockAndInlineMacroThenOnlyShouldListBlockMacroTest.adoc");
        Document doc = asciidoctor.loadFile(file, map);
        treeProcessor.process(doc);
        assertEquals(1, treeProcessor.figureMap.size());
        assertEquals("Middle Office Service Partner Startseite1", treeProcessor.figureMap.get(1));
    }

    @Test
    public void givenNoImagesOrTablesThenMapShouldBeEmptyTest() {
        TableOfXTreeProcessor treeProcessor = new TableOfXTreeProcessor();
        Asciidoctor asciidoctor = create();
        Map<String, Object> map = new HashMap<>();
        File file = new File("src/test/docs/givenNoImagesOrTablesThenMapShouldBeEmptyTest.adoc");
        Document doc = asciidoctor.loadFile(file, map);
        treeProcessor.process(doc);
        assertEquals(0, treeProcessor.figureMap.size());
        assertEquals(0, treeProcessor.tableMap.size());
    }

    @Test
    public void givenTofSectionInDocThenSectionShouldHaveValuesTest() {
        Asciidoctor asciidoctor = create();
        Map<String, Object> map = new HashMap<>();
        File file = new File("src/test/docs/givenTofSectionInDocThenSectionShouldHaveValuesTest.adoc");
        Document doc = asciidoctor.loadFile(file, map);
        String content = "<div class=\"paragraph\">\n" +
                "<p><a anchor=\"_tof00001\">Figure 1: Middle Office Service Partner Startseite1</a><br />\n" +
                "<a anchor=\"_tof00002\">Figure 2: Middle Office Service Partner Startseite2</a><br /></p>\n" +
                "</div>";
        sectionContentTest(doc, Constants.TOF_ID, content);
    }

    @Test
    public void givenTotSectionInDocThenSectionShouldHaveValuesTest() {
        Asciidoctor asciidoctor = create();
        Map<String, Object> map = new HashMap<>();
        File file = new File("src/test/docs/givenTotSectionInDocThenSectionShouldHaveValuesTest.adoc");
        Document doc = asciidoctor.loadFile(file, map);

        String content = "<div class=\"paragraph\">\n" +
                "<p><a anchor=\"_tot00001\">Table 1: Standard Vorgangssuche1</a><br />\n" +
                "<a anchor=\"_tot00002\">Table 2: Standard Vorgangssuche2</a><br /></p>\n" +
                "</div>";
        sectionContentTest(doc, Constants.TOT_ID, content);
    }

    private void sectionContentTest(final StructuralNode block, final String sectId, final String content) {
        final List<StructuralNode> blocks = block.getBlocks();
        for (final StructuralNode currentBlock : blocks) {
            if (sectId.equals(currentBlock.getId())) {
                String proof = currentBlock.getContent().toString();
                assertEquals(content, proof);
            } else {
                sectionContentTest(currentBlock, sectId, content);
            }
        }
    }
}
