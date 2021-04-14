package de.fisp.asciidoctorj.extensions;

import org.apache.commons.lang3.StringUtils;
import org.asciidoctor.ast.Block;
import org.asciidoctor.ast.Document;
import org.asciidoctor.ast.Section;
import org.asciidoctor.ast.StructuralNode;
import org.asciidoctor.extension.Treeprocessor;
import org.asciidoctor.log.LogRecord;
import org.asciidoctor.log.Severity;

import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

public class TableOfXTreeProcessor extends Treeprocessor {

    protected final HashMap<Integer, String> figureMap = new HashMap<>();
    protected final HashMap<Integer, String> tableMap = new HashMap<>();

    private int tofCounter = 1;
    private int totCounter = 1;

    @Override
    public Document process(final Document document) {
        final String figureCaption = Util.getStringAttributeValueOrDefault(document.getAttributes(), Constants.TOF_ATTR_CAPTION);
        log(Severity.DEBUG, "Figure caption: '" + figureCaption + "'");
        final String tableCaption = Util.getStringAttributeValueOrDefault(document.getAttributes(), Constants.TOT_ATTR_CAPTION);
        log(Severity.DEBUG, "Table caption: '" + tableCaption + "'");
        final String captionSeparator = Util.getStringAttributeValueOrDefault(document.getAttributes(), Constants.ATTR_CAPTION_SEPARATOR);
        log(Severity.DEBUG, "Caption separator: '" + captionSeparator + "'");

        processBlock(document, figureCaption, tableCaption, captionSeparator);
        appendToF(document, figureCaption, captionSeparator);
        appendToT(document, tableCaption, captionSeparator);

        return document;
    }

    private void processBlock(final StructuralNode block, final String figureCaption, final String tableCaption, final String captionSeparator) {
        try {
            // Process the whole document to find
            for (final StructuralNode currentBlock : block.getBlocks()) {
                if (currentBlock != null && currentBlock.getContext() != null) {
                    if ("image".equals(currentBlock.getContext())) {
                        if (StringUtils.isNotBlank(currentBlock.getTitle())) {
                            currentBlock.setId(Constants.TOF_ANCHOR_PREFIX + formatCounterValue(tofCounter));
                            figureMap.put(tofCounter, StringUtils.trim(currentBlock.getTitle()));
                            tofCounter++;
                        } else {
                            if (currentBlock.getSourceLocation() != null) {
                                log(Severity.INFO, "Figure without caption found (" + currentBlock.getSourceLocation().toString() + ")");
                            } else {
                                log(Severity.INFO, "Figure without caption found (Set plugin configuration property \"<sourcemap>true</sourcemap>\" to get the exact location)");
                            }
                        }
                    } else if ("table".equals(currentBlock.getContext())) {
                        if (StringUtils.isNotBlank(currentBlock.getTitle())) {
                            currentBlock.setId(Constants.TOT_ANCHOR_PREFIX + formatCounterValue(totCounter));
                            tableMap.put(totCounter, StringUtils.trim(currentBlock.getTitle()));
                            totCounter++;
                        } else {
                            if (currentBlock.getSourceLocation() != null) {
                                log(Severity.INFO, "Table without caption found (" + currentBlock.getSourceLocation().toString() + ")");
                            } else {
                                log(Severity.INFO, "Table without caption found (Set plugin configuration property \"<sourcemap>true</sourcemap>\" to get the exact location)");
                            }
                        }
                    } else {
                        // It's not a paragraph, so recursively descend into the child node
                        processBlock(currentBlock, figureCaption, tableCaption, captionSeparator);
                    }
                }
            }

            // Search for annotated sections
            /*
            for (final StructuralNode currentBlock : block.getBlocks()) {
                if (currentBlock instanceof Section) {
                    final Section currentSection = (Section) currentBlock;
                    if ("tof".equals(currentSection.getSectionName())) {
                        final Block newBlock = createBlock(currentSection, "paragraph", generateTableOfFiguresContents(figureCaption, captionSeparator));
                        currentSection.getBlocks().set(0, newBlock);

                    } else if ("tot".equals(currentSection.getSectionName())) {
                        final Block newBlock = createBlock(currentSection, "paragraph", generateTableOfTablesContents(tableCaption, captionSeparator));
                        currentSection.getBlocks().set(0, newBlock);
                    }
                }
            }
            */
            final Iterator<StructuralNode> blockIteratorToF = block.getBlocks().iterator();
            while (blockIteratorToF.hasNext()) {
                final StructuralNode currentBlock = blockIteratorToF.next();
                if (currentBlock != null) {
                    if (currentBlock instanceof Section) {
                        final Section currentSection = (Section) currentBlock;
                        if ("tof".equals(currentSection.getSectionName())) {
                            final Block newBlock = createBlock(currentSection, "paragraph", generateTableOfFiguresContents(figureCaption, captionSeparator));
                            currentSection.getBlocks().set(0, newBlock);
                            break;
                        }
                    }
                }
            }
            final Iterator<StructuralNode> blockIteratorToT = block.getBlocks().iterator();
            while (blockIteratorToT.hasNext()) {
                final StructuralNode currentBlock = blockIteratorToT.next();
                if (currentBlock instanceof Section) {
                    final Section currentSection = (Section) currentBlock;
                    if ("tot".equals(currentSection.getSectionName())) {
                        final Block newBlock = createBlock(currentSection, "paragraph", generateTableOfTablesContents(tableCaption, captionSeparator));
                        currentSection.getBlocks().set(0, newBlock);
                        break;
                    }
                }
            }
        } catch (Exception ex) {
            log(Severity.ERROR, "Error in '" + AbstractTableOfXBlockMacroProcessor.class.getName() + "': " + ex.getMessage());
        }
    }

    private List<String> generateTableOfFiguresContents(final String caption, final String captionSeparator) {
        final List<String> lines = new LinkedList<>();
        for (int i = 1; i <= figureMap.size(); i++) {
            final String line = "<a anchor=\"" + Constants.TOF_ANCHOR_PREFIX + formatCounterValue(i) + "\">" + caption + " " + i + captionSeparator + " " + figureMap.get(i) + "</a>";
            //final String line = "<a href=\"#" + Constants.TOF_ANCHOR_PREFIX + formatCounterValue(i) + "\">" + caption + " " + i + captionSeparator + " " + figureMap.get(i) + "</a>";
            lines.add(line + "<br />");
        }
        return lines;
    }

    private List<String> generateTableOfTablesContents(final String caption, final String captionSeparator) {
        final List<String> lines = new LinkedList<>();
        for (int i = 1; i <= tableMap.size(); i++) {
            final String line = "<a anchor=\"" + Constants.TOT_ANCHOR_PREFIX + formatCounterValue(i) + "\">" + caption + " " + i + captionSeparator + " " + tableMap.get(i) + "</a>";
            //final String line = "<a href=\"#" + Constants.TOT_ANCHOR_PREFIX + formatCounterValue(i) + "\">" + caption + " " + i + captionSeparator + " " + tableMap.get(i) + "</a>";
            lines.add(line + "<br />");
        }
        return lines;
    }

    private void appendToF(final StructuralNode block, final String caption, final String captionSeparator) {
        final List<StructuralNode> blocks = block.getBlocks();
        for (final StructuralNode currentBlock : blocks) {
            if (Constants.TOF_ID.equals(currentBlock.getId())) {
                final Block newBlock = createBlock(currentBlock, "paragraph", generateTableOfFiguresContents(caption, captionSeparator), currentBlock.getDocument().getAttributes());
                currentBlock.append(newBlock);
                break;
            } else {
                appendToF(currentBlock, caption, captionSeparator);
            }
        }
    }

    private void appendToT(final StructuralNode block, final String caption, final String captionSeparator) {
        final List<StructuralNode> blocks = block.getBlocks();
        for (final StructuralNode currentBlock : blocks) {
            if (Constants.TOT_ID.equals(currentBlock.getId())) {
                final Block newBlock = createBlock(currentBlock, "paragraph", generateTableOfTablesContents(caption, captionSeparator), currentBlock.getDocument().getAttributes());
                currentBlock.append(newBlock);
                break;
            } else {
                appendToT(currentBlock, caption, captionSeparator);
            }
        }
    }

    private String formatCounterValue(final int counter) {
        return String.format("%05d", counter);
    }

    private void log(final Severity severity, final String message) {
        try {
            log(new LogRecord(severity, message));
        } catch (Exception ex) {
            //
        }
    }

}






