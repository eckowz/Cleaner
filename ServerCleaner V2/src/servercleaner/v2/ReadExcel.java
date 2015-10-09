package servercleaner.v2;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;

/**
 * 
 * @author gserafini
 */
public class ReadExcel {

    /**
     *
     * This method will return the full path of the selected file. If any file
     * is selected, the application will be closed.
     *
     * @return
     */
    public String BuscarArquivo() {
        JOptionPane.showMessageDialog(null, "Escolha o arquivo a ser aberto", "Atenção", JOptionPane.WARNING_MESSAGE);

        JFileChooser abrir = new JFileChooser();
        int retorno = abrir.showOpenDialog(null);
        String arquivo = null;
        if (retorno == JFileChooser.APPROVE_OPTION) {
            arquivo = abrir.getSelectedFile().getAbsolutePath();
        }
        if (arquivo == null) {
            JOptionPane.showMessageDialog(null, "Arquivo no selecionado. Sistema encerrado.", "Atenção", JOptionPane.WARNING_MESSAGE);
            System.exit(0);
        }
        return arquivo;
    }

    /**
     *
     * @throws IOException
     */
    public void visualizarArquivos() {
        String diretorio = "\\\\direcao1400\\F\\Bases";
        File file = new File(diretorio);
        File afile[] = file.listFiles();
        int i = 0;
        for (int j = afile.length; i < j; i++) {
            File arquivos = afile[i];
            System.out.println(arquivos.getName());
        }

    }

    public void Executa() {
        // An excel file name. You can create a file name with a full
        // path information.
        //C:\Users\tkanchanawanchai6403\Documents
        String filename = BuscarArquivo();

        //
        // Create an ArrayList to store the data read from excel sheet.
        //
        List sheetData = new ArrayList();
        FileInputStream fis = null;
        try {
            //
            // Create a FileInputStream that will be use to read the
            // excel file.
            //
            fis = new FileInputStream(filename);

            //
            // Create an excel workbook from the file system.
            //
            HSSFWorkbook workbook = new HSSFWorkbook(fis);
            //
            // Get the first sheet on the workbook.
            //
            HSSFSheet sheet = workbook.getSheetAt(0);

            //
            // When we have a sheet object in hand we can iterator on
            // each sheet's rows and on each row's cells. We store the
            // data read on an ArrayList so that we can printed the
            // content of the excel to the console.
            //
            Iterator rows = sheet.rowIterator();
            while (rows.hasNext()) {
                HSSFRow row = (HSSFRow) rows.next();
                Iterator cells = row.cellIterator();

                List data = new ArrayList();
                while (cells.hasNext()) {
                    HSSFCell cell = (HSSFCell) cells.next();
                    data.add(cell);
                }

                sheetData.add(data);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (fis != null) {
                try {
                    fis.close();
                } catch (IOException ex) {
                    Logger.getLogger(ReadExcel.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }

        showExcelData(sheetData);
    }

    private static void showExcelData(List sheetData) {
        //
        // Iterates the data and print it out to the console.
        //
        for (int i = 0; i < sheetData.size(); i++) {
            List list = (List) sheetData.get(i);
            for (int j = 0; j < list.size(); j++) {
                Cell cell = (Cell) list.get(j);
                if (cell.getCellType() == Cell.CELL_TYPE_NUMERIC) {
                    System.out.print(cell.getNumericCellValue());
                } else if (cell.getCellType() == Cell.CELL_TYPE_STRING) {
                    System.out.print(cell.getRichStringCellValue());
                } else if (cell.getCellType() == Cell.CELL_TYPE_BOOLEAN) {
                    System.out.print(cell.getBooleanCellValue());
                }
                if (j < list.size() - 1) {
                    System.out.print(", ");
                }
            }
            System.out.println("");
        }
    }

}
