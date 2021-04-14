package de.fisp.asciidoctorj.extensions;

import org.asciidoctor.extension.Name;

@Name("tot")
public class TableOfTablesBlockMacroProcessor extends AbstractTableOfXBlockMacroProcessor {

    public TableOfTablesBlockMacroProcessor() {
        super(Constants.TOT_ID, Constants.TOT_ATTR_NUMBERED, Constants.TOT_ATTR_TITLE);
    }

}
