
import java.util.List;
import java.util.Scanner;

import entidades.*;
import repositorio.*;
import servicos.Locadora;

// Classe principal responsável pela execução do sistema.
public class Programa {

    private static Scanner entrada = new Scanner(System.in);
    private static Locadora locadora;

    public static void main(String[] args) {

        // Criação dos repositórios.
        Repositorio<Cliente> repositorioClientes = new RepositorioMemoria<>();
        Repositorio<Veiculo> repositorioVeiculos = new RepositorioMemoria<>();
        Repositorio<Locacao> repositorioLocacoes = new RepositorioMemoria<>();

        // Injeção de dependência na Locadora.
        locadora = new Locadora(
            repositorioClientes,
            repositorioVeiculos,
            repositorioLocacoes
        );

        // Cadastra os dados iniciais automaticamente.
        cadastrarDadosIniciais();

        int opcao;

        do {
            System.out.println("\n================================");
            System.out.println("       SISTEMA DE LOCADORA");
            System.out.println("================================");
            System.out.println("1 - Gerenciar clientes");
            System.out.println("2 - Gerenciar veículos");
            System.out.println("3 - Gerenciar locações");
            System.out.println("0 - Sair");
            System.out.print("Escolha uma opção: ");

            opcao = lerInt();

            switch (opcao) {
                case 1:
                    menuClientes();
                    break;

                case 2:
                    menuVeiculos();
                    break;

                case 3:
                    menuLocacoes();
                    break;

                case 0:
                    System.out.println("Sistema encerrado!");
                    break;

                default:
                    System.out.println("Opção inválida!");
            }

        } while (opcao != 0);

        entrada.close();
    }

    // Lê números inteiros evitando problemas com o Scanner.
    private static int lerInt() {
        while (true) {
            try {
                return Integer.parseInt(entrada.nextLine());
            } catch (NumberFormatException e) {
                System.out.print("Digite um número válido: ");
            }
        }
    }

    // Lê números decimais.
    private static double lerDouble() {
        while (true) {
            try {
                return Double.parseDouble(
                    entrada.nextLine().replace(",", ".")
                );
            } catch (NumberFormatException e) {
                System.out.print("Digite um valor válido: ");
            }
        }
    }

    // Inicializa sete clientes, sete endereços,
    // sete motos, sete carros, sete caminhões e sete locações.
    private static void cadastrarDadosIniciais() {

        Cliente[] clientes = new Cliente[7];

        String[] nomes = {
            "Joao", "Maria", "Pedro", "Ana",
            "Carlos", "Julia", "Lucas"
        };

        for (int i = 0; i < 7; i++) {

            Endereco endereco = new Endereco(
                "Rua " + (char)('A' + i),
                "Salvador"
            );

            clientes[i] = new Cliente(
                nomes[i],
                String.format("%011d", 10000000000L + i),
                endereco
            );

            locadora.cadastrarCliente(clientes[i]);
        }

        // Cadastro de sete motos.
        for (int i = 1; i <= 7; i++) {

            Moto moto = new Moto(
                "MOT" + i + "001",
                "Honda CG " + (i * 10),
                "Preta",
                70.0 + (i * 5),
                160
            );

            locadora.cadastrarVeiculo(moto);
        }

        // Cadastro de sete carros.
        for (int i = 1; i <= 7; i++) {

            Carro carro = new Carro(
                "CAR" + i + "001",
                "Toyota Corolla " + i,
                "Prata",
                120.0 + (i * 10),
                4
            );

            locadora.cadastrarVeiculo(carro);
        }

        // Cadastro de sete caminhões.
        for (int i = 1; i <= 7; i++) {

            Caminhao caminhao = new Caminhao(
                "CAM" + i + "001",
                "Volvo FH " + i,
                "Branco",
                250.0 + (i * 20),
                15000.0
            );

            locadora.cadastrarVeiculo(caminhao);
        }

        // Cadastro automático de sete locações.
        List<Veiculo> veiculos = locadora.getVeiculos().listar();

        for (int i = 0; i < 7; i++) {
            locadora.alugar(
                clientes[i],
                veiculos.get(i),
                i + 1
            );
        }

        System.out.println("Dados iniciais cadastrados com sucesso!");
    }

    // Menu de gerenciamento de clientes.
    private static void menuClientes() {

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

            opcao = lerInt();

            switch (opcao) {
                case 1:
                    cadastrarCliente();
                    break;
                case 2:
                    alterarCliente();
                    break;
                case 3:
                    excluirCliente();
                    break;
                case 4:
                    listarClientes(false);
                    break;
                case 5:
                    buscarCliente();
                    break;
                case 6:
                    listarClientes(true);
                    break;
                case 0:
                    break;
                default:
                    System.out.println("Opção inválida!");
            }

        } while (opcao != 0);
    }

    // Cadastra um novo cliente.
    private static void cadastrarCliente() {

        System.out.println("\n===== CADASTRO DE CLIENTE =====");

        System.out.print("Nome: ");
        String nome = entrada.nextLine();

        System.out.print("CPF: ");
        String cpf = entrada.nextLine();

        if (locadora.buscarClientePorCpf(cpf) != null) {
            System.out.println("CPF já cadastrado!");
            return;
        }

        System.out.print("Rua: ");
        String rua = entrada.nextLine();

        System.out.print("Cidade: ");
        String cidade = entrada.nextLine();

        Endereco endereco = new Endereco(rua, cidade);

        Cliente cliente = new Cliente(nome, cpf, endereco);

        locadora.cadastrarCliente(cliente);

        System.out.println("Cliente cadastrado com sucesso!");
    }

    // Altera os dados de um cliente.
    private static void alterarCliente() {

        System.out.print("Digite o CPF do cliente: ");
        String cpf = entrada.nextLine();

        Cliente antigo = locadora.buscarClientePorCpf(cpf);

        if (antigo == null) {
            System.out.println("Cliente não encontrado!");
            return;
        }

        System.out.print("Novo nome: ");
        String nome = entrada.nextLine();

        System.out.print("Novo CPF: ");
        String novoCpf = entrada.nextLine();

        Cliente cpfExistente = locadora.buscarClientePorCpf(novoCpf);

        if (cpfExistente != null && cpfExistente != antigo) {
            System.out.println("Esse CPF já está cadastrado!");
            return;
        }

        System.out.print("Nova rua: ");
        String rua = entrada.nextLine();

        System.out.print("Nova cidade: ");
        String cidade = entrada.nextLine();

        Endereco endereco = new Endereco(rua, cidade);

        Cliente novo = new Cliente(nome, novoCpf, endereco);

        locadora.alterarCliente(antigo, novo);

        // Atualiza a referência do cliente nas locações existentes.
        for (Locacao locacao : locadora.getLocacoes().listar()) {
            if (locacao.getCliente() == antigo) {
                locacao.setCliente(novo);
            }
        }

        System.out.println("Cliente alterado com sucesso!");
    }

    // Exclui um cliente.
    private static void excluirCliente() {

        System.out.print("Digite o CPF do cliente: ");
        String cpf = entrada.nextLine();

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

    // Exibe os clientes em formato tabular.
    private static void listarClientes(boolean ordenado) {

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

    // Busca um cliente pelo CPF.
    private static void buscarCliente() {

        System.out.print("Digite o CPF: ");
        String cpf = entrada.nextLine();

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

    // Menu de gerenciamento de veículos.
    private static void menuVeiculos() {

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

            opcao = lerInt();

            switch (opcao) {
                case 1:
                    cadastrarVeiculo();
                    break;
                case 2:
                    alterarVeiculo();
                    break;
                case 3:
                    excluirVeiculo();
                    break;
                case 4:
                    listarVeiculos(false);
                    break;
                case 5:
                    buscarVeiculo();
                    break;
                case 6:
                    listarVeiculos(true);
                    break;
                case 0:
                    break;
                default:
                    System.out.println("Opção inválida!");
            }

        } while (opcao != 0);
    }

    // Cadastra moto, carro ou caminhão.
    private static void cadastrarVeiculo() {

        System.out.println("\n1 - Moto");
        System.out.println("2 - Carro");
        System.out.println("3 - Caminhão");
        System.out.print("Tipo de veículo: ");

        int tipo = lerInt();

        if (tipo < 1 || tipo > 3) {
            System.out.println("Tipo inválido!");
            return;
        }

        System.out.print("Placa: ");
        String placa = entrada.nextLine();

        if (locadora.buscarPorPlaca(placa) != null) {
            System.out.println("Placa já cadastrada!");
            return;
        }

        System.out.print("Modelo: ");
        String modelo = entrada.nextLine();

        System.out.print("Cor: ");
        String cor = entrada.nextLine();

        System.out.print("Valor da diária: ");
        double diaria = lerDouble();

        if (diaria <= 0) {
            System.out.println("Valor inválido!");
            return;
        }

        Veiculo veiculo;

        if (tipo == 1) {

            System.out.print("Cilindradas: ");
            int cilindradas = lerInt();

            veiculo = new Moto(
                placa, modelo, cor, diaria, cilindradas
            );

        } else if (tipo == 2) {

            System.out.print("Quantidade de portas: ");
            int portas = lerInt();

            veiculo = new Carro(
                placa, modelo, cor, diaria, portas
            );

        } else {

            System.out.print("Capacidade em Kg: ");
            double capacidade = lerDouble();

            veiculo = new Caminhao(
                placa, modelo, cor, diaria, capacidade
            );
        }

        locadora.cadastrarVeiculo(veiculo);

        System.out.println("Veículo cadastrado com sucesso!");
    }

    // Altera um veículo mantendo seu tipo.
    private static void alterarVeiculo() {

        System.out.print("Digite a placa do veículo: ");
        String placa = entrada.nextLine();

        Veiculo antigo = locadora.buscarPorPlaca(placa);

        if (antigo == null) {
            System.out.println("Veículo não encontrado!");
            return;
        }

        System.out.print("Novo modelo: ");
        String modelo = entrada.nextLine();

        System.out.print("Nova cor: ");
        String cor = entrada.nextLine();

        System.out.print("Nova diária: ");
        double diaria = lerDouble();

        if (diaria <= 0) {
            System.out.println("Valor inválido!");
            return;
        }

        System.out.print("Nova placa: ");
        String novaPlaca = entrada.nextLine();

        Veiculo existente = locadora.buscarPorPlaca(novaPlaca);

        if (existente != null && existente != antigo) {
            System.out.println("Placa já cadastrada!");
            return;
        }

        Veiculo novo;

        if (antigo instanceof Moto) {

            Moto moto = (Moto) antigo;

            novo = new Moto(
                novaPlaca, modelo, cor, diaria,
                moto.getCilindradas()
            );

        } else if (antigo instanceof Carro) {

            Carro carro = (Carro) antigo;

            novo = new Carro(
                novaPlaca, modelo, cor, diaria,
                carro.getPortas()
            );

        } else {

            Caminhao caminhao = (Caminhao) antigo;

            novo = new Caminhao(
                novaPlaca, modelo, cor, diaria,
                caminhao.getCapacidadeKg()
            );
        }

        // Atualiza também as locações que utilizam o veículo.
        for (Locacao locacao : locadora.getLocacoes().listar()) {
            if (locacao.getVeiculo() == antigo) {
                locacao.setVeiculo(novo);
            }
        }

        locadora.alterarVeiculo(antigo, novo);

        System.out.println("Veículo alterado com sucesso!");
    }

    // Exclui um veículo que não esteja alugado.
    private static void excluirVeiculo() {

        System.out.print("Digite a placa: ");
        String placa = entrada.nextLine();

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

    // Lista os veículos em formato tabular.
    private static void listarVeiculos(boolean ordenado) {

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

    // Busca um veículo pela placa.
    private static void buscarVeiculo() {

        System.out.print("Digite a placa: ");
        String placa = entrada.nextLine();

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

    // Menu de gerenciamento de locações.
    private static void menuLocacoes() {

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

            opcao = lerInt();

            switch (opcao) {
                case 1:
                    realizarAluguel();
                    break;
                case 2:
                    alterarLocacao();
                    break;
                case 3:
                    excluirLocacao();
                    break;
                case 4:
                    listarLocacoes();
                    break;
                case 5:
                    devolverVeiculo();
                    break;
                case 0:
                    break;
                default:
                    System.out.println("Opção inválida!");
            }

        } while (opcao != 0);
    }

    // Realiza o aluguel utilizando CPF e placa.
    private static void realizarAluguel() {

        System.out.print("CPF do cliente: ");
        String cpf = entrada.nextLine();

        Cliente cliente = locadora.buscarClientePorCpf(cpf);

        if (cliente == null) {
            System.out.println("Cliente não encontrado!");
            return;
        }

        System.out.print("Placa do veículo: ");
        String placa = entrada.nextLine();

        Veiculo veiculo = locadora.buscarPorPlaca(placa);

        if (veiculo == null) {
            System.out.println("Veículo não encontrado!");
            return;
        }

        System.out.print("Quantidade de dias: ");
        int dias = lerInt();

        try {
            locadora.alugar(cliente, veiculo, dias);
            System.out.println("Aluguel realizado com sucesso!");
        } catch (IllegalArgumentException e) {
            System.out.println(e.getMessage());
        }
    }

    // Altera os dados de uma locação.
    private static void alterarLocacao() {

        List<Locacao> locacoes = locadora.getLocacoes().listar();

        if (locacoes.isEmpty()) {
            System.out.println("Não existem locações!");
            return;
        }

        listarLocacoes();

        System.out.print("Número da locação: ");
        int indice = lerInt() - 1;

        if (indice < 0 || indice >= locacoes.size()) {
            System.out.println("Locação inválida!");
            return;
        }

        Locacao antiga = locacoes.get(indice);

        System.out.print("Novo CPF do cliente: ");
        Cliente cliente = locadora.buscarClientePorCpf(entrada.nextLine());

        if (cliente == null) {
            System.out.println("Cliente não encontrado!");
            return;
        }

        System.out.print("Nova placa do veículo: ");
        Veiculo veiculo = locadora.buscarPorPlaca(entrada.nextLine());

        if (veiculo == null) {
            System.out.println("Veículo não encontrado!");
            return;
        }

        System.out.print("Nova quantidade de dias: ");
        int dias = lerInt();

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

    // Exclui uma locação.
    private static void excluirLocacao() {

        List<Locacao> locacoes = locadora.getLocacoes().listar();

        if (locacoes.isEmpty()) {
            System.out.println("Não existem locações!");
            return;
        }

        listarLocacoes();

        System.out.print("Número da locação: ");
        int indice = lerInt() - 1;

        if (indice < 0 || indice >= locacoes.size()) {
            System.out.println("Locação inválida!");
            return;
        }

        locadora.excluirLocacao(locacoes.get(indice));

        System.out.println("Locação excluída com sucesso!");
    }

    // Lista as locações em formato tabular.
    private static void listarLocacoes() {

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

    // Finaliza a locação e apresenta o valor a pagar.
    private static void devolverVeiculo() {

        List<Locacao> locacoes = locadora.getLocacoes().listar();

        if (locacoes.isEmpty()) {
            System.out.println("Não existem locações!");
            return;
        }

        listarLocacoes();

        System.out.print("Número da locação: ");
        int indice = lerInt() - 1;

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
