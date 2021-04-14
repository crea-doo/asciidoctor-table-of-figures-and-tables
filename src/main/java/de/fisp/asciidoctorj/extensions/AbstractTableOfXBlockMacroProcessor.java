package de.fisp.asciidoctorj.extensions;

import org.apache.commons.lang3.StringUtils;
import org.asciidoctor.ast.Section;
import org.asciidoctor.ast.StructuralNode;
import org.asciidoctor.extension.BlockMacroProcessor;
import org.asciidoctor.log.LogRecord;
import org.asciidoctor.log.Severity;

import java.util.HashMap;
import java.util.Map;

public class AbstractTableOfXBlockMacroProcessor extends BlockMacroProcessor {

    private final String numberedAttribute;
    private final String section;
    private final String titleAttribute;

    AbstractTableOfXBlockMacroProcessor(final String section, final String numberedAttribute, final String titleAttribute) {
        this.section = section;
        this.numberedAttribute = numberedAttribute;
        this.titleAttribute = titleAttribute;
    }

    @Override
    public Object process(final StructuralNode parent, final String target, final Map<String, Object> attributes) {
        try {
            boolean isNumbered = Util.getBoolAttributeValueOrDefault(parent.getDocument().getAttributes(), numberedAttribute);
            if (parent.getContext() != null && "preamble".equals(parent.getContext())) {
                isNumbered = false;
            }

            //final Section section = createSection(parent, parent.getLevel(), isNumbered, parent.getDocument().getOptions());
            final Section section = createSection(parent, parent.getLevel(), isNumbered, new HashMap<>());
            section.setId(this.section);

            // Set the title (parse target, attributes and document attributes)
            String title = null;
            if (StringUtils.isNotBlank(target)) {
                title = StringUtils.trim(target);
            }
            if (StringUtils.isBlank(title) && attributes.containsKey(Constants.ATTR_TITLE)) {
                title = Util.getStringAttributeValueOrDefault(attributes, Constants.ATTR_TITLE);
            }
            if (StringUtils.isBlank(title)) {
                title = Util.getStringAttributeValueOrDefault(parent.getDocument().getAttributes(), titleAttribute);
            }

            if (StringUtils.isNotBlank(title)) {
                section.setTitle(StringUtils.trim(title));
            }

            return section;
        } catch (Exception ex) {
            log(Severity.ERROR, "Error in '" + AbstractTableOfXBlockMacroProcessor.class.getName() + "': " + ex.getMessage());
        }

        return null;
    }

    private void log(final Severity severity, final String message) {
        try {
            log(new LogRecord(severity, message));
        } catch (Exception ex) {
            //
        }
    }

}
