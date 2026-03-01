import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class GerenciadorDeTarefas {

    private List<Tarefa> tarefas;
    private int proximoId;
    private final String ARQUIVO = "tarefas.txt";

    public GerenciadorDeTarefas() {
        tarefas = new ArrayList<>();
        carregarDoArquivo();
    }

    public void adicionarTarefa(String titulo, String descricao) {
        Tarefa tarefa = new Tarefa(proximoId, titulo, descricao);
        tarefas.add(tarefa);
        proximoId++;
        salvarNoArquivo();
    }

    public List<Tarefa> listarTarefas() {
        return new ArrayList<>(tarefas);
    }

    public Optional<Tarefa> buscarPorId(int id) {
        return tarefas.stream()
                .filter(t -> t.getId() == id)
                .findFirst();
    }

    public boolean removerTarefa(int id) {
        boolean removido = tarefas.removeIf(t -> t.getId() == id);
        if (removido) {
            salvarNoArquivo();
        }
        return removido;
    }

    public List<Tarefa> listarConcluidas() {
        return tarefas.stream()
                .filter(Tarefa::isConcluida)
                .collect(Collectors.toList());
    }

    public void marcarComoConcluida(int id) {
        buscarPorId(id).ifPresent(t -> {
            t.marcarComoConcluida();
            salvarNoArquivo();
        });
    }

    private void salvarNoArquivo() {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(ARQUIVO))) {
            for (Tarefa t : tarefas) {
                writer.write(t.getId() + ";" +
                        t.getTitulo() + ";" +
                        t.getDescricao() + ";" +
                        t.isConcluida());
                writer.newLine();
            }
        } catch (IOException e) {
            System.out.println("Erro ao salvar arquivo: " + e.getMessage());
        }
    }

    private void carregarDoArquivo() {
        File file = new File(ARQUIVO);

        if (!file.exists()) {
            proximoId = 1;
            return;
        }

        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {

            String linha;
            int maiorId = 0;

            while ((linha = reader.readLine()) != null) {

                String[] partes = linha.split(";");

                int id = Integer.parseInt(partes[0]);
                String titulo = partes[1];
                String descricao = partes[2];
                boolean concluida = Boolean.parseBoolean(partes[3]);

                Tarefa tarefa = new Tarefa(id, titulo, descricao);

                if (concluida) {
                    tarefa.marcarComoConcluida();
                }

                tarefas.add(tarefa);

                if (id > maiorId) {
                    maiorId = id;
                }
            }

            proximoId = maiorId + 1;

        } catch (IOException e) {
            System.out.println("Erro ao carregar arquivo: " + e.getMessage());
        }
    }
}