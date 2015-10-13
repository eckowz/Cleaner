/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package servercleaner.v2;

import logs.CriaDiretorioLogs;

/**
 *
 * @author gserafini
 */
public class ServerCleanerV2 {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        ReadExcel vaiPlaneta = new ReadExcel();
        vaiPlaneta.LerArquivoSomenteCodigo();
        vaiPlaneta.verificaDiretorios(ReadExcel.dirBr);
        vaiPlaneta.verificaDiretorios(ReadExcel.dirBb);

    }

}
