package menus;

import java.util.Scanner;
import servicos.Locadora;

public class MenuPrincipal {

    private Locadora locadora;
    private Scanner sc;

    public MenuPrincipal(Locadora locadora, Scanner sc) {
        this.locadora = locadora;
        this.sc = sc;
    }

    public void exibir() {

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

            opcao = sc.nextInt();
            sc.nextLine();

            switch (opcao) {
                case 1:
                    new MenuClientes(locadora, sc).exibir();
                    break;

                case 2:
                    new MenuVeiculos(locadora, sc).exibir();
                    break;

                case 3:
                    new MenuLocacoes(locadora, sc).exibir();
                    break;

                case 0:
                    System.out.println("Sistema encerrado!");
                    break;

                default:
                    System.out.println("Opção inválida!");
            }

        } while (opcao != 0);
    }
}