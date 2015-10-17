package logs;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import javax.swing.JOptionPane;

/**
 *
 * @author gserafini
 */
public class CriaTxtLogs {

    /**
     * Cria e grava no arquivo de texto a String recebida como mensagem. Arquivo
     * será criado com o nome da data de sua geração.
     *
     * @param texto
     */
    public void criaArquivoTxt(String texto) {
        try {
            File diretorio = new File(System.getProperty("user.home") + "\\Desktop\\" + CriaDiretorioLogs.nomeDiretorio);
            //System.out.println(diretorio);
            String nomeArq = (mostraDataHoraNomeArq() + ".txt");
            //System.out.println(nomeArq);
            File arq = new File(diretorio, nomeArq);
            if (!arq.exists()) {
                arq.createNewFile();
            }
            FileWriter gravaNoArq = new FileWriter(arq, true);

            try (PrintWriter escreveNoArq = new PrintWriter(gravaNoArq)) {
                escreveNoArq.println(mostraDataHora() + texto);
                escreveNoArq.close();
            }
        } catch (IOException ex) {
            JOptionPane.showMessageDialog(null, "Não foi possivel criar o arquivo de log.", "ERRO", JOptionPane.WARNING_MESSAGE);
        }
    }

    private String mostraDataHoraNomeArq() {
        DateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
        Date date = new Date();
        return dateFormat.format(date);
    }

    private String mostraDataHora() {
        DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
        Date date = new Date();
        return dateFormat.format(date);
    }
}
