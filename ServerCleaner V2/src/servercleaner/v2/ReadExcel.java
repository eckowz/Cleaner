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

    /**
     * Diretório padrão para o dir1.
     */
    public static String dir1 = "C:\\Users\\gserafini\\Desktop\\Diretorio de teste Server Cleaner\\Petrobras";
    //public static String dir1 = "C:\\Users\\gserafini\\Desktop\\Diretorio de teste Server Cleaner\\Petrobras";

    /**
     * Diretório padrão para o dir2.
     */
    public static String dir2 = "C:\\Users\\gserafini\\Desktop\\Diretorio de teste Server Cleaner\\B.Branca";
    //public static String dir2 = "C:\\Users\\gserafini\\Desktop\\Diretorio de teste Server Cleaner\\B.Branca";

    /**
     * Inicializa os objetos necessários para que não ocorra
     * NullPointerException.
     */
    public ReadExcel() {
        this.listaChamados = new RepositorioChamados();
        guardaAtendimentos = new ArrayList<File>();
        criaDir.criaDiretorio();
    }

    /**
     *
     * This method will return the full path of the selected file.
     *
     * @return String <b>arquivo</b> - retorna o caminho absoluto do arquivo
     * selecionado.
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
     * @deprecated Não utilizado em nenhum momento.
     *
     * Metodo que remove os arquivos mas mantem a pasta.
     *
     * @param f Arquivo/Diretorio a ser removido
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
     * @param f Arquivo/Diretorio a ser removido.
     */
    public void removerArquivosComRaiz(File f) {
        // Se o DiretorioEstabelecimento passado for um diretório
        if (f.isDirectory()) {
            /* Lista todos os arquivos do diretório em um array
             de objetos File */
            File[] files = f.listFiles();
            for (File file : files) {
                long tamArqRemovido = file.length();
                criaArq.criaArquivoTxt("Nome do arquivo: " + file.getName()
                        + "\nTamanho aproximado do arquivo removido: "
                        + tamArqRemovido / 1000 + "Kb.");
                removerArquivosComRaiz(file);
            }
        }
        f.delete();
    }

    /**
     * Analisa o diretorio informado em busca de pastas com o mesmo nome do
     * informado na planilha.
     *
     * @param dir Diretório que será analisado de acordo com a planilha
     * disponibilizada.
     */
    public void verificaDiretorios(String dir) {
        int contGeral = 0;
        File diretorio = new File(dir);
        File[] files = diretorio.listFiles();
        for (File DiretorioEstabelecimento : files) {
            if (DiretorioEstabelecimento.isDirectory()) {
                //System.out.println("\nEmpresa: " + DiretorioEstabelecimento.getName());
                File[] files2 = DiretorioEstabelecimento.listFiles();
                for (File DiretorioAtendimento : files2) {
                    if (DiretorioAtendimento.isDirectory()) {
                        //System.out.println("\nAtendimento: " + DiretorioAtendimento.getName());
                        for (Chamados chamado : listaChamados.getListChamados()) {
                            if (chamado.getChamado().equals(DiretorioAtendimento.getName())) {
                                //System.out.println("Chamado encontrado." + chamado.getChamado());
                                criaArq.criaArquivoTxt(" Chamado finalizado: " + chamado.getChamado());
                                guardaAtendimentos.add(DiretorioAtendimento);
                                contGeral++;
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
     * com o nome de @param listaChamados
     *
     * @return boolean - arquivoSelecionado retorna se o arquivo foi lido com
     * sucesso ou não.
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
     * @param quantidade quantidade de pastas localizadas
     * @param dir diretorio onde a leitura foi realizada
     * @return boolean retorna se a limpeza deve ser realizada ou não.
     */
    public boolean confirmaLimpesa(int quantidade, String dir) {
        int op;
        op = JOptionPane.showConfirmDialog(null, "Confirma a limpeza de " + quantidade + " pastas de atendimento "
                + "do diretório: \n" + dir + "?",
                "Confirmação", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);

        return op == JOptionPane.YES_OPTION;
    }
}
