
package repositorio;

import java.util.List;

// Define as operações que todo repositório deve possuir.
public interface Repositorio<T> {

    void adicionar(T objeto);

    void remover(T objeto);

    void atualizar(T objetoAntigo, T objetoNovo);

    List<T> listar();
}
