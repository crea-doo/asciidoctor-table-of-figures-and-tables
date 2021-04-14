package de.fisp.asciidoctorj.extensions;

import org.asciidoctor.extension.Name;

@Name("tof")
public class TableOfFiguresBlockMacroProcessor extends AbstractTableOfXBlockMacroProcessor {

    public TableOfFiguresBlockMacroProcessor() {
        super(Constants.TOF_ID, Constants.TOF_ATTR_NUMBERED, Constants.TOF_ATTR_TITLE);
    }

}

