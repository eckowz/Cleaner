package servercleaner.v2;

import repositorio.RepositorioChamados;
import model.Chamados;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.io.File;
import java.io.FileInputStream;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

/**
 *
 * @author gserafini
 */
public class ReadExcel {

    private RepositorioChamados listaChamados;

    /**
     *
     * This method will return the full path of the selected file. If any file
     * is selected, the application will be closed.
     *
     * @return
     */
    public static String BuscarArquivo() {
        JOptionPane.showMessageDialog(null, "Escolha o arquivo a ser aberto", "Atenção", JOptionPane.WARNING_MESSAGE);

        JFileChooser fileChooser = new JFileChooser();
        File diretorio = new File(System.getProperty("user.home") + "\\Desktop\\");
        fileChooser.setCurrentDirectory(diretorio);
        int retorno = fileChooser.showOpenDialog(null);
        String arquivo = null;
        if (retorno == JFileChooser.APPROVE_OPTION) {
            arquivo = fileChooser.getSelectedFile().getAbsolutePath();
        }
        if (arquivo == null) {
            JOptionPane.showMessageDialog(null, "Arquivo não selecionado. Sistema encerrado.", "Atenção", JOptionPane.WARNING_MESSAGE);
            System.exit(0);
        }
        return arquivo;
    }

    public void verificaDiretorios() {
        String diretorio = "C:\\Users\\gserafini\\Desktop\\Diretorio de teste Server Cleaner\\B.Branca";
        File file = new File(diretorio);
        File listaArquivos[] = file.listFiles();
        int i = 0;
        for (int j = listaArquivos.length; i < j; i++) {
            File arquivo = listaArquivos[i];
            if (arquivo.isDirectory()) {
                File[] files = arquivo.listFiles();

            }
            System.out.println(arquivo.getName());
        }
    }

    public void removerArquivos(File f) {
        // Se o arquivo passado for um diretório
        if (f.isDirectory()) {
            /* Lista todos os arquivos do diretório em um array
             de objetos File */
            File[] files = f.listFiles();
            // Identa a lista (foreach) e deleta um por um
            for (File file : files) {
                file.delete();
            }
        }
    }

    public void removerArquivosComRaiz(File f) {
        // Se o arquivo passado for um diretório
        if (f.isDirectory()) {
            File[] files = f.listFiles();
            for (File file : files) {
                removerArquivosComRaiz(file);
            }
        }
        f.delete();
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
            // Create a FileInputStream that will be use to read the
            // excel file.
            fis = new FileInputStream(filename);

            //Create an excel workbook from the file system.
            HSSFWorkbook workbook = new HSSFWorkbook(fis);

            // Get the first sheet on the workbook.
            HSSFSheet sheet = workbook.getSheetAt(0);

            // When we have a sheet object in hand we can iterator on
            // each sheet's rows and on each row's cells. We store the
            // data read on an ArrayList so that we can printed the
            // content of the excel to the console.
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

        procuraChamadoNaPlanilha(sheetData);
    }

    public void procuraChamadoNaPlanilha(List sheetData) {
        //
        // Iterates the data and print it out to the console.
        //
        for (int linha = 0; linha < 3; linha++) {
            System.out.println("linha");
            List list = (List) sheetData.get(linha);
            for (int coluna = 0; coluna < list.size(); coluna++) {
                Cell cell = (Cell) list.get(coluna);
                if (coluna == 0 && linha != 0) {
                    if (cell.getCellType() == Cell.CELL_TYPE_NUMERIC) {
                        System.out.print("Chamado: " + cell.getNumericCellValue());
                    } else if (cell.getCellType() == Cell.CELL_TYPE_STRING) {
                        System.out.print("Chamado: " + cell.getRichStringCellValue());
                    } else if (cell.getCellType() == Cell.CELL_TYPE_BOOLEAN) {
                        System.out.print("Chamado: " + cell.getBooleanCellValue());
                    }
                }

                /**
                 * if (coluna < list.size() - 1) { System.out.print(", "); }
                 *
                 */
            }
            System.out.println("");
        }
    }

    /**
     *
     * @param sheetData
     */
    public void showExcelData(List sheetData) {
        //
        // Iterates the data and print it out to the console.
        //
        for (int i = 0; i < sheetData.size(); i++) {
            System.out.println("linha");
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

    /**
     * Este metodo irá ler o arquivo excel e jogará os chamados em um arrayList
     */
    public void LerArquivoSomenteCodigo() {
        try {
            FileInputStream file = new FileInputStream(new File(BuscarArquivo()));
            XSSFWorkbook arquivoExcel = new XSSFWorkbook(file);
            XSSFSheet folhaExcel = arquivoExcel.getSheetAt(0);
            for (Row rowFor : folhaExcel) {
                if (rowFor.getRowNum() != 0) {
                    for (Cell cellFor : rowFor) {
                        if (cellFor.getColumnIndex() == 1) {
                            listaChamados.addChamados(new Chamados(cellFor.getNumericCellValue()));
                        }

                    }
                }
            }

        } catch (Exception ex) {
            Logger.getLogger(ReadExcel.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    public void a() {

    }
}
