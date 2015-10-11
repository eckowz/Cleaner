package servercleaner.v2;

import java.io.File;

/**
 *
 * @author gserafini
 */
public class FiltroXlsx extends javax.swing.filechooser.FileFilter {

    @Override
    public boolean accept(File file) {
        String filename = file.getName();
        return filename.endsWith(".xlsx");
    }

    @Override
    public String getDescription() {
        return "*.xlsx";
    }

}
