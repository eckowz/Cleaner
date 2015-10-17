package servercleaner.v2;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.filechooser.FileNameExtensionFilter;
import logs.CriaDiretorioLogs;
import logs.CriaTxtLogs;
import model.Chamados;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import repositorio.RepositorioChamados;

/**
 *
 * @author gserafini
 */
@SuppressWarnings("FieldMayBeFinal")
public class ReadExcel {

    private RepositorioChamados listaChamados;
    private CriaDiretorioLogs criaDir = new CriaDiretorioLogs();
    private CriaTxtLogs criaArq = new CriaTxtLogs();
    List<File> guardaAtendimentos;
    //public static String dir1 = "C:\\Users\\gserafini\\Desktop\\Diretorio de teste Server Cleaner\\Petrobras";
    //public static String dir2 = "C:\\Users\\gserafini\\Desktop\\Diretorio de teste Server Cleaner\\B.Branca";
    public static String dir1 = "C:\\Users\\gserafini\\Desktop\\Diretorio de teste Server Cleaner\\Petrobras";
    public static String dir2 = "C:\\Users\\gserafini\\Desktop\\Diretorio de teste Server Cleaner\\B.Branca";

    public ReadExcel() {
        this.listaChamados = new RepositorioChamados();
        guardaAtendimentos = new ArrayList<File>();
        criaDir.criaDiretorio();
    }

    /**
     *
     * This method will return the full path of the selected file. If any file
     * is selected, the application will be closed.
     *
     *
     * @return retorna o caminho absoluto do arquivo selecionado.
     */
    public String BuscarArquivo() {
        JOptionPane.showMessageDialog(null, "Escolha o arquivo a ser aberto", "Atenção", JOptionPane.WARNING_MESSAGE);
        JFileChooser fileChooser = new JFileChooser();

        /**
         * @param diretorio define a inicialização do fileChooser na area de
         * trabalho do usuário
         */
        File diretorio = new File(System.getProperty("user.home") + "\\Desktop\\");
        fileChooser.setCurrentDirectory(diretorio);

        fileChooser.setFileFilter(new FileNameExtensionFilter("Apenas XLSX", "xlsx"));
        fileChooser.setAcceptAllFileFilterUsed(false);
        int retorno = fileChooser.showOpenDialog(fileChooser);
        String arquivo = null;
        if (retorno == JFileChooser.APPROVE_OPTION) {
            arquivo = fileChooser.getSelectedFile().getAbsolutePath();
        } else {
            JOptionPane.showMessageDialog(null, "Arquivo não selecionado.", "Atenção", JOptionPane.WARNING_MESSAGE);
        }
        return arquivo;
    }

    /**
     * Metodo que remove os arquivos mas mantem a pasta.
     *
     * @param f
     */
    public void removerArquivos(File f) {
        // Se o DiretorioEstabelecimento passado for um diretório
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

    /**
     * Metodo que remove os arquivos e também a pasta.
     *
     * @param f
     */
    public void removerArquivosComRaiz(File f) {
        // Se o DiretorioEstabelecimento passado for um diretório
        if (f.isDirectory()) {
            /* Lista todos os arquivos do diretório em um array
             de objetos File */
            File[] files = f.listFiles();
            for (File file : files) {
                removerArquivosComRaiz(file);
            }
        }
        f.delete();
    }

    public void verificaDiretorios(String dir) {
        int contGeral = 0, contInt = 0, cont = 0;
        File diretorio = new File(dir);
        File[] files = diretorio.listFiles();
        for (File DiretorioEstabelecimento : files) {
            contGeral = contGeral + contInt;
            contInt = 0;
            if (DiretorioEstabelecimento.isDirectory()) {
                //System.out.println("\nEmpresa: " + DiretorioEstabelecimento.getName());

                File[] files2 = DiretorioEstabelecimento.listFiles();
                for (File DiretorioAtendimento : files2) {
                    contInt = contInt + cont;
                    cont = 0;
                    if (DiretorioAtendimento.isDirectory()) {
                        //System.out.println("\nAtendimento: " + DiretorioAtendimento.getName());
                        for (Chamados chamado : listaChamados.getListChamados()) {
                            if (chamado.getChamado().equals(DiretorioAtendimento.getName())) {
                                //System.out.println("Chamado encontrado." + chamado.getChamado());
                                criaArq.criaArquivoTxt(" Chamado finalizado: " + chamado.getChamado());
                                guardaAtendimentos.add(DiretorioAtendimento);
                                cont++;
                            }
                        }
                    }
                }
            }
        }

        if (dir.equals(dir1)) {
            criaArq.criaArquivoTxt("Chamados encontrados no diretório BR: " + contGeral);
            if (confirmaLimpesa(contGeral, dir1)) {
                for (File atendimento : guardaAtendimentos) {
                    removerArquivosComRaiz(atendimento);
                }
            }
        } else {
            criaArq.criaArquivoTxt("Chamados encontrados no diretório BB: " + contGeral);
            if (confirmaLimpesa(contGeral, dir2)) {
                for (File atendimento : guardaAtendimentos) {
                    removerArquivosComRaiz(atendimento);
                }
            }
        }
    }

    /**
     * Este metodo irá ler a planilha excel e jogará os chamados em um arrayList
     */
    public boolean LerArquivoSomenteCodigo() {
        boolean arquivoSelecionado = true;
        try {
            File arquivo = new File(BuscarArquivo());
            FileInputStream file = new FileInputStream(arquivo);
            XSSFWorkbook arquivoExcel = new XSSFWorkbook(file);
            XSSFSheet folhaExcel = arquivoExcel.getSheetAt(0);
            for (Row rowFor : folhaExcel) {
                if (rowFor.getRowNum() != 0) {
                    for (Cell cellFor : rowFor) {
                        if (cellFor.getColumnIndex() == 0) {
                            cellFor.setCellType(Cell.CELL_TYPE_STRING);
                            listaChamados.addChamados(new Chamados(cellFor.getStringCellValue()));
                        }
                    }
                }
            }
        } catch (NullPointerException | FileNotFoundException ex) {
            criaArq.criaArquivoTxt("Arquivo não selecionado.");
            arquivoSelecionado = false;
        } catch (IOException ex) {
            criaArq.criaArquivoTxt("Ocorreu um erro.");
        }

        return arquivoSelecionado == true;
    }

    /**
     *
     * @param quantidade
     * @return
     */
    public boolean confirmaLimpesa(int quantidade, String dir) {
        int op;
        op = JOptionPane.showConfirmDialog(null, "Confirma a limpeza de " + quantidade + " pastas de atendimento "
                + "do diretório: \n" + dir + "?",
                "Confirmação", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);

        if (op == JOptionPane.YES_OPTION) {
            return true;
        } else {
            return false;
        }
    }
}
