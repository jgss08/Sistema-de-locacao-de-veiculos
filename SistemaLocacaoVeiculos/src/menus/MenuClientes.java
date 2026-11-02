package menus;

import java.util.List;
import java.util.Scanner;

import entidades.*;
import servicos.Locadora;

public class MenuClientes {

    private Locadora locadora;
    private Scanner sc;

    public MenuClientes(Locadora locadora, Scanner sc) {
        this.locadora = locadora;
        this.sc = sc;
    }

    public void exibir() {

        int opcao;

        do {
            System.out.println("\n===== GERENCIAR CLIENTES =====");
            System.out.println("1 - Cadastrar cliente");
            System.out.println("2 - Alterar cliente");
            System.out.println("3 - Excluir cliente");
            System.out.println("4 - Listar clientes");
            System.out.println("5 - Buscar cliente por CPF");
            System.out.println("6 - Listar clientes ordenados");
            System.out.println("0 - Voltar");
            System.out.print("Opção: ");

            opcao = sc.nextInt();
            sc.nextLine();

            switch (opcao) {
                case 1: cadastrarCliente(); break;
                case 2: alterarCliente(); break;
                case 3: excluirCliente(); break;
                case 4: listarClientes(false); break;
                case 5: buscarCliente(); break;
                case 6: listarClientes(true); break;
                case 0: break;
                default: System.out.println("Opção inválida!");
            }

        } while (opcao != 0);
    }

    private void cadastrarCliente() {

        System.out.println("\n===== CADASTRO DE CLIENTE =====");

        System.out.print("Nome: ");
        String nome = sc.nextLine();

        System.out.print("CPF: ");
        String cpf = sc.nextLine();

        if (locadora.buscarClientePorCpf(cpf) != null) {
            System.out.println("CPF já cadastrado!");
            return;
        }

        System.out.print("Rua: ");
        String rua = sc.nextLine();

        System.out.print("Cidade: ");
        String cidade = sc.nextLine();

        Endereco endereco = new Endereco(rua, cidade);
        Cliente cliente = new Cliente(nome, cpf, endereco);

        locadora.cadastrarCliente(cliente);

        System.out.println("Cliente cadastrado com sucesso!");
    }

    private void alterarCliente() {

        System.out.print("Digite o CPF do cliente: ");
        String cpf = sc.nextLine();

        Cliente antigo = locadora.buscarClientePorCpf(cpf);

        if (antigo == null) {
            System.out.println("Cliente não encontrado!");
            return;
        }

        System.out.print("Novo nome: ");
        String nome = sc.nextLine();

        System.out.print("Novo CPF: ");
        String novoCpf = sc.nextLine();

        Cliente cpfExistente = locadora.buscarClientePorCpf(novoCpf);

        if (cpfExistente != null && cpfExistente != antigo) {
            System.out.println("Esse CPF já está cadastrado!");
            return;
        }

        System.out.print("Nova rua: ");
        String rua = sc.nextLine();

        System.out.print("Nova cidade: ");
        String cidade = sc.nextLine();

        Endereco endereco = new Endereco(rua, cidade);
        Cliente novo = new Cliente(nome, novoCpf, endereco);

        locadora.alterarCliente(antigo, novo);

        for (Locacao locacao : locadora.getLocacoes().listar()) {
            if (locacao.getCliente() == antigo) {
                locacao.setCliente(novo);
            }
        }

        System.out.println("Cliente alterado com sucesso!");
    }

    private void excluirCliente() {

        System.out.print("Digite o CPF do cliente: ");
        String cpf = sc.nextLine();

        Cliente cliente = locadora.buscarClientePorCpf(cpf);

        if (cliente == null) {
            System.out.println("Cliente não encontrado!");
            return;
        }

        for (Locacao locacao : locadora.getLocacoes().listar()) {
            if (locacao.getCliente() == cliente) {
                System.out.println("Não é possível excluir: cliente possui locação ativa.");
                return;
            }
        }

        locadora.excluirCliente(cliente);

        System.out.println("Cliente excluído com sucesso!");
    }

    private void listarClientes(boolean ordenado) {

        List<Cliente> clientes;

        if (ordenado) {
            clientes = locadora.listarClientesOrdenados();
        } else {
            clientes = locadora.getClientes().listar();
        }

        System.out.printf("%-20s %-15s %-20s %-15s%n",
            "NOME", "CPF", "RUA", "CIDADE");

        System.out.println("--------------------------------------------------------------------------");

        for (Cliente cliente : clientes) {
            System.out.printf("%-20s %-15s %-20s %-15s%n",
                cliente.getNome(),
                cliente.getCpf(),
                cliente.getEndereco().getRua(),
                cliente.getEndereco().getCidade()
            );
        }
    }

    private void buscarCliente() {

        System.out.print("Digite o CPF: ");
        String cpf = sc.nextLine();

        Cliente cliente = locadora.buscarClientePorCpf(cpf);

        if (cliente == null) {
            System.out.println("Cliente não encontrado!");
            return;
        }

        System.out.println("\nCliente encontrado:");
        System.out.println("Nome: " + cliente.getNome());
        System.out.println("CPF: " + cliente.getCpf());
        System.out.println("Rua: " + cliente.getEndereco().getRua());
        System.out.println("Cidade: " + cliente.getEndereco().getCidade());
    }
}