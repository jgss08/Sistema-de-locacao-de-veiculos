
package repositorio;

import java.util.ArrayList;
import java.util.List;

// Implementa o repositório utilizando ArrayList e Generics.
public class RepositorioMemoria<T> implements Repositorio<T> {

    private ArrayList<T> itens;

    public RepositorioMemoria() {
        itens = new ArrayList<>();
    }

    @Override
    public void adicionar(T objeto) {
        itens.add(objeto);
    }

    @Override
    public void remover(T objeto) {
        itens.remove(objeto);
    }

    @Override
    public void atualizar(T objetoAntigo, T objetoNovo) {

        int indice = itens.indexOf(objetoAntigo);

        if (indice != -1) {
            itens.set(indice, objetoNovo);
        }
    }

    @Override
    public List<T> listar() {
        return new ArrayList<>(itens);
    }
}
