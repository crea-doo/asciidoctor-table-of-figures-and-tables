package de.fisp.asciidoctorj.extensions;

import org.asciidoctor.Asciidoctor;
import org.asciidoctor.extension.JavaExtensionRegistry;
import org.asciidoctor.jruby.extension.spi.ExtensionRegistry;

public class TableOfXExtension implements ExtensionRegistry {

    @Override
    public void register(final Asciidoctor asciidoctor) {
        final JavaExtensionRegistry javaExtensionRegistry = asciidoctor.javaExtensionRegistry();

        javaExtensionRegistry.treeprocessor(TableOfXTreeProcessor.class);
        javaExtensionRegistry.blockMacro(TableOfTablesBlockMacroProcessor.class);
        javaExtensionRegistry.blockMacro(TableOfFiguresBlockMacroProcessor.class);
    }
}

