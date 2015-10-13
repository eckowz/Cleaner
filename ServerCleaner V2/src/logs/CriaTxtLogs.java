package logs;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author gserafini
 */
public class CriaTxtLogs {

    public void criaArquivoTxt(String texto) {
        try {
            File diretorio = new File(System.getProperty("user.home") + "\\Desktop\\" + CriaDiretorioLogs.nomeDiretorio);
            System.out.println(diretorio);
            String nomeArq = (mostraDataHoraNomeArq() + ".txt");
            System.out.println(nomeArq);
            File arq = new File(diretorio, nomeArq);
            if (!arq.exists()) {
                arq.createNewFile();
            }
            FileWriter gravaNoArq = new FileWriter(arq, true);
            PrintWriter escreveNoArq = new PrintWriter(gravaNoArq);
            escreveNoArq.println(mostraDataHora() + texto);
            escreveNoArq.flush();
            escreveNoArq.close();
        } catch (IOException ex) {
            Logger.getLogger(CriaTxtLogs.class.getName()).log(Level.SEVERE, null, ex);
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
