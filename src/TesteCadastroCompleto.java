import javax.swing.*;
import java.awt.*;
import java.io.*;
import java.util.ArrayList;
import java.util.List;


public class TesteCadastroCompleto extends JFrame {

    private static final String FILE_PATH = "C:\\Projetos IntelliJ\\gerenciamento\\jogadores.txt";
    private String jogadorSelecionado;

    public TesteCadastroCompleto() {
        setTitle("Gerenciador de Jogadores");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(400, 400);
        setLocationRelativeTo(null);

        JPanel panel = new JPanel(new GridLayout(0, 1, 0, 10)); // GridLayout com uma coluna

        JButton cadastrarButton = new JButton("Cadastrar jogador");
        cadastrarButton.addActionListener(e -> cadastrarJogador());

        JButton listarButton = new JButton("Listar todos os jogadores");
        listarButton.addActionListener(e -> listarJogadores());

        JButton alterarButton = new JButton("Ativar/Desativar um jogador");
        alterarButton.addActionListener(e -> ativarDesativarJogador());

        JButton editarButton = new JButton("Editar jogador");
        editarButton.addActionListener(e -> editarJogador());


        panel.add(cadastrarButton);
        panel.add(listarButton);
        panel.add(alterarButton);
        panel.add(editarButton);


        panel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        add(panel);
        setVisible(true);
    }

    private void cadastrarJogador() {
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));

        JTextField nomeField = new JTextField(15);
        JTextField posicaoField = new JTextField(15);
        JTextField idadeField = new JTextField(5);
        JTextField numeroCamisaField = new JTextField(5);

        panel.add(new JLabel("Nome do jogador:"));
        panel.add(nomeField);
        panel.add(new JLabel("Posição do jogador:"));
        panel.add(posicaoField);
        panel.add(new JLabel("Idade do jogador:"));
        panel.add(idadeField);
        panel.add(new JLabel("Número da camisa do jogador:"));
        panel.add(numeroCamisaField);

        int result;
        do {
            result = JOptionPane.showConfirmDialog(null, panel, "Cadastro de Jogador",
                    JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);

            if (result == JOptionPane.OK_OPTION) {
                String nomeJogador = nomeField.getText();
                String posicao = posicaoField.getText();
                String idadeStr = idadeField.getText();
                String numeroCamisaStr = numeroCamisaField.getText();

                if (!nomeJogador.isEmpty() && !posicao.isEmpty() && !idadeStr.isEmpty() && !numeroCamisaStr.isEmpty()) {
                    try {
                        int idade = Integer.parseInt(idadeStr);
                        int numeroCamisa = Integer.parseInt(numeroCamisaStr);

                        // Validação de nome de jogador existente
                        if (existeJogador(nomeJogador)) {
                            JOptionPane.showMessageDialog(this, "Esse nome de jogador já está em uso.");
                        } else if (existeNumeroCamisa(numeroCamisa)) {
                            JOptionPane.showMessageDialog(this, "Esse número de camisa já está em uso.");
                        } else if (idade > 99 || numeroCamisa > 99) {
                            JOptionPane.showMessageDialog(this, "A idade e o número da camisa devem ter no máximo dois dígitos.");
                        } else {
                            // Se passar em todas as validações, cadastra o jogador
                            try (BufferedWriter writer = new BufferedWriter(new FileWriter(FILE_PATH, true))) {
                                writer.write(nomeJogador + "(ativo)" + ",Posição: " + posicao + ",Idade: " + idade + ",Número da camisa: " + numeroCamisa);
                                writer.newLine();
                                JOptionPane.showMessageDialog(this, "Jogador cadastrado com sucesso!");
                            } catch (IOException ex) {
                                JOptionPane.showMessageDialog(this, "Erro ao cadastrar jogador.");
                                ex.printStackTrace();
                            }
                        }
                    } catch (NumberFormatException ex) {
                        JOptionPane.showMessageDialog(this, "Idade ou número da camisa inválidos!");
                    }
                } else {
                    JOptionPane.showMessageDialog(this, "Preencha todos os campos para cadastrar o jogador.");
                }
            }
        } while (result != JOptionPane.CANCEL_OPTION && result != JOptionPane.CLOSED_OPTION);
    }

    private boolean existeJogador(String nome) {
        try (BufferedReader reader = new BufferedReader(new FileReader(FILE_PATH))) {
            String linha;
            while ((linha = reader.readLine()) != null) {
                String[] partes = linha.split("\\(");
                String nomeJogador = partes[0];

                if (nome.equals(nomeJogador)) {
                    return true;
                }
            }
        } catch (IOException ex) {
            JOptionPane.showMessageDialog(this, "Erro ao verificar jogador existente.");
            ex.printStackTrace();
        }
        return false;
    }

    private boolean existeNumeroCamisa(int numeroCamisa) {
        try (BufferedReader reader = new BufferedReader(new FileReader(FILE_PATH))) {
            String linha;
            while ((linha = reader.readLine()) != null) {
                String[] partes = linha.split("Número da camisa: ");
                String numeroStr = partes[1];
                int numero = Integer.parseInt(numeroStr);

                if (numero == numeroCamisa) {
                    return true;
                }
            }
        } catch (IOException ex) {
            JOptionPane.showMessageDialog(this, "Erro ao verificar número de camisa existente.");
            ex.printStackTrace();
        }
        return false;
    }

    private void listarJogadores() {
        try (BufferedReader reader = new BufferedReader(new FileReader(FILE_PATH))) {
            StringBuilder jogadores = new StringBuilder();
            String linha;
            while ((linha = reader.readLine()) != null) {
                String[] partes = linha.split(",");
                String nome = partes[0].substring(0, partes[0].indexOf("("));
                String status = partes[0].substring(partes[0].indexOf("(") + 1, partes[0].indexOf(")"));

                jogadores.append(nome).append(" - ").append(status).append("\n");
            }
            if (jogadores.length() > 0) {
                JOptionPane.showMessageDialog(this, "Lista de Jogadores:\n" + jogadores.toString());
            } else {
                JOptionPane.showMessageDialog(this, "Não há jogadores cadastrados.");
            }
        } catch (IOException ex) {
            JOptionPane.showMessageDialog(this, "Erro ao listar jogadores.");
            ex.printStackTrace();
        }
    }

    private void ativarDesativarJogador() {
        jogadorSelecionado = selecionarJogador();
        if (jogadorSelecionado != null && !jogadorSelecionado.isEmpty()) {
            alterarStatusJogador(jogadorSelecionado);
        } else {
            JOptionPane.showMessageDialog(this, "Selecione um jogador para ativar/desativar.");
        }
    }

    private String selecionarJogador() {
        try (BufferedReader reader = new BufferedReader(new FileReader(FILE_PATH))) {
            List<String> jogadores = new ArrayList<>();
            String linha;
            while ((linha = reader.readLine()) != null) {
                jogadores.add(linha.split("\\(")[0]);
            }
            String[] options = jogadores.toArray(new String[0]);
            return (String) JOptionPane.showInputDialog(
                    this,
                    "Selecione um jogador:",
                    "Selecionar Jogador",
                    JOptionPane.PLAIN_MESSAGE,
                    null,
                    options,
                    options[0]);
        } catch (IOException ex) {
            JOptionPane.showMessageDialog(this, "Erro ao listar jogadores.");
            ex.printStackTrace();
        }
        return null;
    }

    private void alterarStatusJogador(String nomeJogador) {
        List<String> linhas = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(FILE_PATH))) {
            String linha;
            while ((linha = reader.readLine()) != null) {
                String[] partes = linha.split("\\(");
                String nome = partes[0];

                if (nome.equals(nomeJogador)) {
                    if (partes.length > 1) {
                        String status = partes[1].substring(0, partes[1].indexOf(")"));
                        if (status.equals("ativo")) {
                            linha = nome + "(inativo)" + linha.substring(linha.indexOf(","));
                        } else {
                            linha = nome + "(ativo)" + linha.substring(linha.indexOf(","));
                        }
                    }
                }
                linhas.add(linha);
            }
        } catch (IOException ex) {
            JOptionPane.showMessageDialog(this, "Erro ao ativar/desativar jogador.");
            ex.printStackTrace();
        }

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(FILE_PATH))) {
            for (String linha : linhas) {
                writer.write(linha);
                writer.newLine();
            }
            JOptionPane.showMessageDialog(this, "Status do jogador alterado com sucesso!");
        } catch (IOException ex) {
            JOptionPane.showMessageDialog(this, "Erro ao salvar alterações.");
            ex.printStackTrace();
        }
    }

    private void editarJogador() {
        jogadorSelecionado = selecionarJogador();
        if (jogadorSelecionado != null && !jogadorSelecionado.isEmpty()) {
            JPanel panel = new JPanel();
            panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));

            JTextField nomeField = new JTextField(15);
            JTextField posicaoField = new JTextField(15);
            JTextField idadeField = new JTextField(5);
            JTextField numeroCamisaField = new JTextField(5);

            panel.add(new JLabel("Nome do jogador:"));
            panel.add(nomeField);
            panel.add(new JLabel("Posição do jogador:"));
            panel.add(posicaoField);
            panel.add(new JLabel("Idade do jogador:"));
            panel.add(idadeField);
            panel.add(new JLabel("Número da camisa do jogador:"));
            panel.add(numeroCamisaField);

            int result = JOptionPane.showConfirmDialog(null, panel, "Editar Jogador",
                    JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);

            if (result == JOptionPane.OK_OPTION) {
                String novoNome = nomeField.getText();
                String novaPosicao = posicaoField.getText();
                String novaIdadeStr = idadeField.getText();
                String novoNumeroCamisaStr = numeroCamisaField.getText();

                if (!novoNome.isEmpty() && !novaPosicao.isEmpty() && !novaIdadeStr.isEmpty() && !novoNumeroCamisaStr.isEmpty()) {
                    try {
                        int novaIdade = Integer.parseInt(novaIdadeStr);
                        int novoNumeroCamisa = Integer.parseInt(novoNumeroCamisaStr);

                        editarJogadorNoArquivo(novoNome, novaPosicao, novaIdade, novoNumeroCamisa);
                    } catch (NumberFormatException ex) {
                        JOptionPane.showMessageDialog(this, "Idade ou número da camisa inválidos!");
                    }
                } else {
                    JOptionPane.showMessageDialog(this, "Preencha todos os campos para editar o jogador.");
                }
            }
        } else {
            JOptionPane.showMessageDialog(this, "Selecione um jogador para editar.");
        }
    }

    private void editarJogadorNoArquivo(String novoNome, String novaPosicao, int novaIdade, int novoNumeroCamisa) {
        List<String> linhas = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(FILE_PATH))) {
            String linha;
            while ((linha = reader.readLine()) != null) {
                String[] partes = linha.split(",");
                String nome = partes[0].substring(0, partes[0].indexOf("("));

                if (nome.equals(jogadorSelecionado)) {
                    linhas.add(novoNome + "(ativo)" + ",Posição: " + novaPosicao + ",Idade: " + novaIdade + ",Número da camisa: " + novoNumeroCamisa);
                } else {
                    linhas.add(linha);
                }
            }
        } catch (IOException ex) {
            JOptionPane.showMessageDialog(this, "Erro ao editar jogador.");
            ex.printStackTrace();
        }

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(FILE_PATH))) {
            for (String linha : linhas) {
                writer.write(linha);
                writer.newLine();
            }
            JOptionPane.showMessageDialog(this, "Jogador editado com sucesso!");
        } catch (IOException ex) {
            JOptionPane.showMessageDialog(this, "Erro ao salvar alterações.");
            ex.printStackTrace();
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(TesteCadastroCompleto::new);
    }
}
