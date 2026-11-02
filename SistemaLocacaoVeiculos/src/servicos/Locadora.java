
package servicos;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

import entidades.Cliente;
import entidades.Locacao;
import entidades.Veiculo;
import repositorio.Repositorio;

// Gerencia clientes, veículos e locações.
public class Locadora {

    private Repositorio<Cliente> clientes;
    private Repositorio<Veiculo> veiculos;
    private Repositorio<Locacao> locacoes;

    // Injeção de dependência: os repositórios são recebidos externamente.
    public Locadora(
            Repositorio<Cliente> clientes,
            Repositorio<Veiculo> veiculos,
            Repositorio<Locacao> locacoes) {

        this.clientes = clientes;
        this.veiculos = veiculos;
        this.locacoes = locacoes;
    }

    // Retorna os repositórios.
    public Repositorio<Cliente> getClientes() {
        return clientes;
    }

    public Repositorio<Veiculo> getVeiculos() {
        return veiculos;
    }

    public Repositorio<Locacao> getLocacoes() {
        return locacoes;
    }

    // Cadastra um cliente.
    public void cadastrarCliente(Cliente cliente) {
        clientes.adicionar(cliente);
    }

    // Altera os dados de um cliente.
    public void alterarCliente(Cliente antigo, Cliente novo) {
        clientes.atualizar(antigo, novo);
    }

    // Exclui um cliente.
    public void excluirCliente(Cliente cliente) {
        clientes.remover(cliente);
    }

    // Busca um cliente pelo CPF.
    public Cliente buscarClientePorCpf(String cpf) {

        for (Cliente cliente : clientes.listar()) {

            if (cliente.getCpf().equalsIgnoreCase(cpf)) {
                return cliente;
            }
        }

        return null;
    }

    // Lista os clientes ordenados pelo nome.
    public List<Cliente> listarClientesOrdenados() {

        List<Cliente> lista = new ArrayList<>(clientes.listar());

        lista.sort(Comparator.comparing(
            Cliente::getNome,
            String.CASE_INSENSITIVE_ORDER
        ));

        return lista;
    }

    // Cadastra um veículo.
    public void cadastrarVeiculo(Veiculo veiculo) {
        veiculos.adicionar(veiculo);
    }

    // Altera um veículo.
    public void alterarVeiculo(Veiculo antigo, Veiculo novo) {
        veiculos.atualizar(antigo, novo);
    }

    // Exclui um veículo.
    public void excluirVeiculo(Veiculo veiculo) {
        veiculos.remover(veiculo);
    }

    // Busca um veículo pela placa.
    public Veiculo buscarPorPlaca(String placa) {

        for (Veiculo veiculo : veiculos.listar()) {

            if (veiculo.getPlaca().equalsIgnoreCase(placa)) {
                return veiculo;
            }
        }

        return null;
    }

    // Lista os veículos ordenados pela placa.
    public List<Veiculo> listarOrdenado() {

        List<Veiculo> lista = new ArrayList<>(veiculos.listar());

        lista.sort(Comparator.comparing(Veiculo::getPlaca));

        return lista;
    }

    // Realiza o aluguel de um veículo.
    public void alugar(Cliente cliente, Veiculo veiculo, int dias) {

        if (cliente == null || veiculo == null || dias <= 0) {
            throw new IllegalArgumentException("Dados inválidos.");
        }

        if (!clientes.listar().contains(cliente)) {
            throw new IllegalArgumentException("Cliente não cadastrado.");
        }

        if (!veiculos.listar().contains(veiculo)) {
            throw new IllegalArgumentException("Veículo não cadastrado.");
        }

        // Impede que um veículo alugado seja alugado novamente.
        for (Locacao locacao : locacoes.listar()) {

            if (locacao.getVeiculo() == veiculo) {
                throw new IllegalArgumentException("Veículo já está alugado.");
            }
        }

        locacoes.adicionar(new Locacao(cliente, veiculo, dias));
    }

    // Altera os dados de uma locação.
    public void alterarLocacao(Locacao antiga, Locacao nova) {
        locacoes.atualizar(antiga, nova);
    }

    // Devolve o veículo e calcula o valor da locação.
    public double devolver(Locacao locacao) {

        if (locacao == null || !locacoes.listar().contains(locacao)) {
            throw new IllegalArgumentException("Locação não encontrada.");
        }

        double valorTotal = locacao.calcularTotal();

        locacoes.remover(locacao);

        return valorTotal;
    }

    // Exclui uma locação sem realizar o cálculo de pagamento.
    public void excluirLocacao(Locacao locacao) {
        locacoes.remover(locacao);
    }

}
