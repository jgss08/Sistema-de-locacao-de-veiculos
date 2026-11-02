import java.util.List;
import java.util.Locale;

import entidades.*;
import repositorio.*;
import servicos.Locadora;
import menus.MenuPrincipal;

public class Programa {

    public static void main(String[] args) {

        java.util.Scanner sc = new java.util.Scanner(System.in);
        sc.useLocale(Locale.US);

        Repositorio<Cliente> repositorioClientes = new RepositorioMemoria<>();
        Repositorio<Veiculo> repositorioVeiculos = new RepositorioMemoria<>();
        Repositorio<Locacao> repositorioLocacoes = new RepositorioMemoria<>();

        Locadora locadora = new Locadora(
            repositorioClientes,
            repositorioVeiculos,
            repositorioLocacoes
        );

        cadastrarDadosIniciais(locadora);

        MenuPrincipal menu = new MenuPrincipal(locadora, sc);
        menu.exibir();

        sc.close();
    }

    private static void cadastrarDadosIniciais(Locadora locadora) {

        Cliente[] clientes = new Cliente[7];

        String[] nomes = {
            "Joao", "Maria", "Pedro", "Ana",
            "Carlos", "Julia", "Lucas"
        };

        for (int i = 0; i < 7; i++) {

            Endereco endereco = new Endereco(
                "Rua " + (char) ('A' + i),
                "Salvador"
            );

            clientes[i] = new Cliente(
                nomes[i],
                String.format("%011d", 10000000000L + i),
                endereco
            );

            locadora.cadastrarCliente(clientes[i]);
        }

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

        List<Veiculo> veiculos = locadora.getVeiculos().listar();

        for (int i = 0; i < 7; i++) {
            locadora.alugar(clientes[i], veiculos.get(i), i + 1);
        }

        System.out.println("Dados iniciais cadastrados com sucesso!");
    }
}