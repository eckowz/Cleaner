
package repositorio;

import java.util.ArrayList;
import java.util.List;
import model.Chamados;

/**
 *
 * @author gserafini
 */
public class RepositorioChamados {
    private List<Chamados> listaChamados;

    public RepositorioChamados() {
        listaChamados = new ArrayList<Chamados>();
    }

    public boolean addChamados(Chamados chamado) {
        return (listaChamados.add(chamado));
    }
}
