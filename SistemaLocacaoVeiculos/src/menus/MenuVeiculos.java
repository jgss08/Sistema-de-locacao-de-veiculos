package menus;

import java.util.List;
import java.util.Scanner;

import entidades.*;
import servicos.Locadora;

public class MenuVeiculos {

    private Locadora locadora;
    private Scanner sc;

    public MenuVeiculos(Locadora locadora, Scanner sc) {
        this.locadora = locadora;
        this.sc = sc;
    }

    public void exibir() {

        int opcao;

        do {
            System.out.println("\n===== GERENCIAR VEÍCULOS =====");
            System.out.println("1 - Cadastrar veículo");
            System.out.println("2 - Alterar veículo");
            System.out.println("3 - Excluir veículo");
            System.out.println("4 - Listar veículos");
            System.out.println("5 - Buscar veículo pela placa");
            System.out.println("6 - Listar veículos ordenados");
            System.out.println("0 - Voltar");
            System.out.print("Opção: ");

            opcao = sc.nextInt();
            sc.nextLine();

            switch (opcao) {
                case 1: cadastrarVeiculo(); break;
                case 2: alterarVeiculo(); break;
                case 3: excluirVeiculo(); break;
                case 4: listarVeiculos(false); break;
                case 5: buscarVeiculo(); break;
                case 6: listarVeiculos(true); break;
                case 0: break;
                default: System.out.println("Opção inválida!");
            }

        } while (opcao != 0);
    }

    private void cadastrarVeiculo() {

        System.out.println("\n1 - Moto");
        System.out.println("2 - Carro");
        System.out.println("3 - Caminhão");
        System.out.print("Tipo de veículo: ");

        int tipo = sc.nextInt();
        sc.nextLine();

        if (tipo < 1 || tipo > 3) {
            System.out.println("Tipo inválido!");
            return;
        }

        System.out.print("Placa: ");
        String placa = sc.nextLine();

        if (locadora.buscarPorPlaca(placa) != null) {
            System.out.println("Placa já cadastrada!");
            return;
        }

        System.out.print("Modelo: ");
        String modelo = sc.nextLine();

        System.out.print("Cor: ");
        String cor = sc.nextLine();

        System.out.print("Valor da diária: ");
        double diaria = sc.nextDouble();
        sc.nextLine();

        if (diaria <= 0) {
            System.out.println("Valor inválido!");
            return;
        }

        Veiculo veiculo;

        if (tipo == 1) {

            System.out.print("Cilindradas: ");
            int cilindradas = sc.nextInt();
            sc.nextLine();

            veiculo = new Moto(placa, modelo, cor, diaria, cilindradas);

        } else if (tipo == 2) {

            System.out.print("Quantidade de portas: ");
            int portas = sc.nextInt();
            sc.nextLine();

            veiculo = new Carro(placa, modelo, cor, diaria, portas);

        } else {

            System.out.print("Capacidade em Kg: ");
            double capacidade = sc.nextDouble();
            sc.nextLine();

            veiculo = new Caminhao(placa, modelo, cor, diaria, capacidade);
        }

        locadora.cadastrarVeiculo(veiculo);

        System.out.println("Veículo cadastrado com sucesso!");
    }

    private void alterarVeiculo() {

        System.out.print("Digite a placa do veículo: ");
        String placa = sc.nextLine();

        Veiculo antigo = locadora.buscarPorPlaca(placa);

        if (antigo == null) {
            System.out.println("Veículo não encontrado!");
            return;
        }

        System.out.print("Novo modelo: ");
        String modelo = sc.nextLine();

        System.out.print("Nova cor: ");
        String cor = sc.nextLine();

        System.out.print("Nova diária: ");
        double diaria = sc.nextDouble();
        sc.nextLine();

        if (diaria <= 0) {
            System.out.println("Valor inválido!");
            return;
        }

        System.out.print("Nova placa: ");
        String novaPlaca = sc.nextLine();

        Veiculo existente = locadora.buscarPorPlaca(novaPlaca);

        if (existente != null && existente != antigo) {
            System.out.println("Placa já cadastrada!");
            return;
        }

        Veiculo novo;

        if (antigo instanceof Moto) {

            Moto moto = (Moto) antigo;
            novo = new Moto(novaPlaca, modelo, cor, diaria, moto.getCilindradas());

        } else if (antigo instanceof Carro) {

            Carro carro = (Carro) antigo;
            novo = new Carro(novaPlaca, modelo, cor, diaria, carro.getPortas());

        } else {

            Caminhao caminhao = (Caminhao) antigo;
            novo = new Caminhao(novaPlaca, modelo, cor, diaria, caminhao.getCapacidadeKg());
        }

        for (Locacao locacao : locadora.getLocacoes().listar()) {
            if (locacao.getVeiculo() == antigo) {
                locacao.setVeiculo(novo);
            }
        }

        locadora.alterarVeiculo(antigo, novo);

        System.out.println("Veículo alterado com sucesso!");
    }

    private void excluirVeiculo() {

        System.out.print("Digite a placa: ");
        String placa = sc.nextLine();

        Veiculo veiculo = locadora.buscarPorPlaca(placa);

        if (veiculo == null) {
            System.out.println("Veículo não encontrado!");
            return;
        }

        for (Locacao locacao : locadora.getLocacoes().listar()) {
            if (locacao.getVeiculo() == veiculo) {
                System.out.println("Não é possível excluir: veículo alugado.");
                return;
            }
        }

        locadora.excluirVeiculo(veiculo);

        System.out.println("Veículo excluído com sucesso!");
    }

    private void listarVeiculos(boolean ordenado) {

        List<Veiculo> veiculos;

        if (ordenado) {
            veiculos = locadora.listarOrdenado();
        } else {
            veiculos = locadora.getVeiculos().listar();
        }

        System.out.printf("%-12s %-22s %-12s %-12s %-12s%n",
            "PLACA", "MODELO", "COR", "DIÁRIA", "TIPO");

        System.out.println("--------------------------------------------------------------------------");

        for (Veiculo veiculo : veiculos) {

            String tipo = veiculo instanceof Moto ? "Moto" :
                          veiculo instanceof Carro ? "Carro" : "Caminhão";

            System.out.printf("%-12s %-22s %-12s R$ %-9.2f %-12s%n",
                veiculo.getPlaca(),
                veiculo.getModelo(),
                veiculo.getCor(),
                veiculo.getValorDiaria(),
                tipo
            );
        }
    }

    private void buscarVeiculo() {

        System.out.print("Digite a placa: ");
        String placa = sc.nextLine();

        Veiculo veiculo = locadora.buscarPorPlaca(placa);

        if (veiculo == null) {
            System.out.println("Veículo não encontrado!");
            return;
        }

        System.out.println("\nVeículo encontrado:");
        System.out.println("Placa: " + veiculo.getPlaca());
        System.out.println("Modelo: " + veiculo.getModelo());
        System.out.println("Cor: " + veiculo.getCor());
        System.out.println("Diária: R$ " + veiculo.getValorDiaria());

        if (veiculo instanceof Moto) {

            Moto moto = (Moto) veiculo;
            System.out.println("Cilindradas: " + moto.getCilindradas());

        } else if (veiculo instanceof Carro) {

            Carro carro = (Carro) veiculo;
            System.out.println("Portas: " + carro.getPortas());

        } else if (veiculo instanceof Caminhao) {

            Caminhao caminhao = (Caminhao) veiculo;
            System.out.println("Capacidade: " + caminhao.getCapacidadeKg() + " Kg");
        }
    }
}