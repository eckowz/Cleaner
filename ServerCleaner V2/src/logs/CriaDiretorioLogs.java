package logs;

import java.io.File;
import javax.swing.JOptionPane;

/**
 *
 * @author gserafini
 */
public class CriaDiretorioLogs {

    public static String nomeDiretorio = "Cleaner logs";

    public void criaDiretorio() {

        //String separador = java.io.File.separator;
        try {
            File diretorio = new File(System.getProperty("user.home") + "\\Desktop\\" + nomeDiretorio);
            if (!diretorio.exists()) { // Verifica se o diretório existe. 
                diretorio.mkdir();   // Cria o diretório 
            } else {

            }
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(null, "Err", "Erro ao criar o diretório" + ex.toString(), JOptionPane.ERROR_MESSAGE);
        }
    }

}
