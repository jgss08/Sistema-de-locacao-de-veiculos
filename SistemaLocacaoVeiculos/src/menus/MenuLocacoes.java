package menus;

import java.util.List;
import java.util.Scanner;

import entidades.*;
import servicos.Locadora;

public class MenuLocacoes {

    private Locadora locadora;
    private Scanner sc;

    public MenuLocacoes(Locadora locadora, Scanner sc) {
        this.locadora = locadora;
        this.sc = sc;
    }

    public void exibir() {

        int opcao;

        do {
            System.out.println("\n===== GERENCIAR LOCAÇÕES =====");
            System.out.println("1 - Realizar aluguel");
            System.out.println("2 - Alterar locação");
            System.out.println("3 - Excluir locação");
            System.out.println("4 - Listar locações");
            System.out.println("5 - Devolver veículo");
            System.out.println("0 - Voltar");
            System.out.print("Opção: ");

            opcao = sc.nextInt();
            sc.nextLine();

            switch (opcao) {
                case 1: realizarAluguel(); break;
                case 2: alterarLocacao(); break;
                case 3: excluirLocacao(); break;
                case 4: listarLocacoes(); break;
                case 5: devolverVeiculo(); break;
                case 0: break;
                default: System.out.println("Opção inválida!");
            }

        } while (opcao != 0);
    }

    private void realizarAluguel() {

        System.out.print("CPF do cliente: ");
        String cpf = sc.nextLine();

        Cliente cliente = locadora.buscarClientePorCpf(cpf);

        if (cliente == null) {
            System.out.println("Cliente não encontrado!");
            return;
        }

        System.out.print("Placa do veículo: ");
        String placa = sc.nextLine();

        Veiculo veiculo = locadora.buscarPorPlaca(placa);

        if (veiculo == null) {
            System.out.println("Veículo não encontrado!");
            return;
        }

        System.out.print("Quantidade de dias: ");
        int dias = sc.nextInt();
        sc.nextLine();

        if (dias <= 0) {
            System.out.println("Quantidade de dias inválida!");
            return;
        }

        for (Locacao locacao : locadora.getLocacoes().listar()) {

            if (locacao.getVeiculo() == veiculo) {
                System.out.println("Veículo já está alugado!");
                return;
            }
        }

        locadora.alugar(cliente, veiculo, dias);

        System.out.println("Aluguel realizado com sucesso!");
    }

    private void alterarLocacao() {

        List<Locacao> locacoes = locadora.getLocacoes().listar();

        if (locacoes.isEmpty()) {
            System.out.println("Não existem locações!");
            return;
        }

        listarLocacoes();

        System.out.print("Número da locação: ");
        int indice = sc.nextInt() - 1;
        sc.nextLine();

        if (indice < 0 || indice >= locacoes.size()) {
            System.out.println("Locação inválida!");
            return;
        }

        Locacao antiga = locacoes.get(indice);

        System.out.print("Novo CPF do cliente: ");
        Cliente cliente = locadora.buscarClientePorCpf(sc.nextLine());

        if (cliente == null) {
            System.out.println("Cliente não encontrado!");
            return;
        }

        System.out.print("Nova placa do veículo: ");
        Veiculo veiculo = locadora.buscarPorPlaca(sc.nextLine());

        if (veiculo == null) {
            System.out.println("Veículo não encontrado!");
            return;
        }

        System.out.print("Nova quantidade de dias: ");
        int dias = sc.nextInt();
        sc.nextLine();

        if (dias <= 0) {
            System.out.println("Quantidade de dias inválida!");
            return;
        }

        for (Locacao outra : locacoes) {

            if (outra != antiga && outra.getVeiculo() == veiculo) {
                System.out.println("Veículo já está alugado!");
                return;
            }
        }

        Locacao nova = new Locacao(cliente, veiculo, dias);

        locadora.alterarLocacao(antiga, nova);

        System.out.println("Locação alterada com sucesso!");
    }

    private void excluirLocacao() {

        List<Locacao> locacoes = locadora.getLocacoes().listar();

        if (locacoes.isEmpty()) {
            System.out.println("Não existem locações!");
            return;
        }

        listarLocacoes();

        System.out.print("Número da locação: ");
        int indice = sc.nextInt() - 1;
        sc.nextLine();

        if (indice < 0 || indice >= locacoes.size()) {
            System.out.println("Locação inválida!");
            return;
        }

        locadora.excluirLocacao(locacoes.get(indice));

        System.out.println("Locação excluída com sucesso!");
    }

    private void listarLocacoes() {

        List<Locacao> locacoes = locadora.getLocacoes().listar();

        if (locacoes.isEmpty()) {
            System.out.println("Não existem locações cadastradas!");
            return;
        }

        System.out.printf("%-8s %-18s %-12s %-22s %-10s %-12s%n",
            "NÚMERO", "CLIENTE", "PLACA", "VEÍCULO", "DIAS", "TOTAL");

        System.out.println("--------------------------------------------------------------------------------------");

        for (int i = 0; i < locacoes.size(); i++) {

            Locacao locacao = locacoes.get(i);

            System.out.printf("%-8d %-18s %-12s %-22s %-10d R$ %-10.2f%n",
                i + 1,
                locacao.getCliente().getNome(),
                locacao.getVeiculo().getPlaca(),
                locacao.getVeiculo().getModelo(),
                locacao.getDias(),
                locacao.calcularTotal()
            );
        }
    }

    private void devolverVeiculo() {

        List<Locacao> locacoes = locadora.getLocacoes().listar();

        if (locacoes.isEmpty()) {
            System.out.println("Não existem locações!");
            return;
        }

        listarLocacoes();

        System.out.print("Número da locação: ");
        int indice = sc.nextInt() - 1;
        sc.nextLine();

        if (indice < 0 || indice >= locacoes.size()) {
            System.out.println("Locação inválida!");
            return;
        }

        Locacao locacao = locacoes.get(indice);

        double total = locadora.devolver(locacao);

        System.out.println("\n===== DEVOLUÇÃO REALIZADA =====");
        System.out.println("Cliente: " + locacao.getCliente().getNome());
        System.out.println("Veículo: " + locacao.getVeiculo().getModelo());
        System.out.printf("Valor total: R$ %.2f%n", total);
        System.out.println("Veículo devolvido com sucesso!");
    }
}