import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        GerenciadorDeTarefas gerenciador = new GerenciadorDeTarefas();
        int opcao;

        do {
            System.out.println("\n===== MENU =====");
            System.out.println("1 - Adicionar tarefa");
            System.out.println("2 - Listar tarefas");
            System.out.println("3 - Marcar tarefa como concluída");
            System.out.println("4 - Listar tarefas concluídas");
            System.out.println("5 - Remover tarefa");
            System.out.println("0 - Sair");
            System.out.print("Escolha uma opção: ");

            opcao = scanner.nextInt();
            scanner.nextLine();

            switch (opcao) {

                case 1:
                    System.out.print("Digite o título: ");
                    String titulo = scanner.nextLine();

                    System.out.print("Digite a descrição: ");
                    String descricao = scanner.nextLine();

                    gerenciador.adicionarTarefa(titulo, descricao);

                    System.out.println("Tarefa adicionada com sucesso!");
                    break;

                case 2:
                    System.out.println("\n=== Todas as tarefas ===");
                    gerenciador.listarTarefas()
                            .forEach(System.out::println);
                    break;

                case 3:
                    System.out.print("Digite o ID da tarefa para marcar como concluída: ");
                    int idConcluir = scanner.nextInt();
                    gerenciador.marcarComoConcluida(idConcluir);
                    System.out.println("Operação finalizada.");
                    break;

                case 4:
                    System.out.println("\n=== Tarefas concluídas ===");
                    gerenciador.listarConcluidas()
                            .forEach(System.out::println);
                    break;

                case 5:
                    System.out.print("Digite o ID da tarefa para remover: ");
                    int idRemover = scanner.nextInt();

                    boolean removido = gerenciador.removerTarefa(idRemover);

                    if (removido) {
                        System.out.println("Tarefa removida com sucesso!");
                    } else {
                        System.out.println("Tarefa não encontrada.");
                    }
                    break;

                case 0:
                    System.out.println("Saindo...");
                    break;

                default:
                    System.out.println("Opção inválida!");
            }

        } while (opcao != 0);

        scanner.close();
    }
}