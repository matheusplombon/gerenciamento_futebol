import javax.swing.*;
import java.awt.event.*;
import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class TesteGerenciamento extends JFrame {

    private static final String FILE_PATH = "C:\\Projetos IntelliJ\\gerenciamento\\jogadores.txt";

    public TesteGerenciamento() {
        setTitle("Gerenciador de Jogadores");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(400, 300);
        setLocationRelativeTo(null);

        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));

        JButton cadastrarButton = new JButton("Cadastrar jogador");
        cadastrarButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                cadastrarJogador();
            }
        });

        JButton listarButton = new JButton("Listar todos os jogadores");
        listarButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                listarJogadores();
            }
        });

        JButton alterarButton = new JButton("Ativar/Desativar um jogador");
        alterarButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                ativarDesativarJogador();
            }
        });

        panel.add(cadastrarButton);
        panel.add(listarButton);
        panel.add(alterarButton);

        add(panel);
        setVisible(true);
    }

    private void cadastrarJogador() {
        String nomeJogador = JOptionPane.showInputDialog(this, "Digite o nome do jogador:");
        if (nomeJogador != null && !nomeJogador.isEmpty()) {
            try (BufferedWriter writer = new BufferedWriter(new FileWriter(FILE_PATH, true))) {
                writer.write(nomeJogador + "(true)");
                writer.newLine();
                JOptionPane.showMessageDialog(this, "Jogador cadastrado com sucesso!");
            } catch (IOException ex) {
                JOptionPane.showMessageDialog(this, "Erro ao cadastrar jogador.");
                ex.printStackTrace();
            }
        } else {
            JOptionPane.showMessageDialog(this, "Nome do jogador inválido!");
        }
    }

    private void listarJogadores() {
        try (BufferedReader reader = new BufferedReader(new FileReader(FILE_PATH))) {
            StringBuilder jogadores = new StringBuilder();
            String linha;
            while ((linha = reader.readLine()) != null) {
                jogadores.append(linha).append("\n");
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
        String jogadorSelecionado = selecionarJogador();
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
                if (linha.startsWith(nomeJogador)) {
                    if (linha.endsWith("(true)")) {
                        linha = linha.replace("(true)", "(false)");
                    } else {
                        linha = linha.replace("(false)", "(true)");
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

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new TesteGerenciamento();
            }
        });
    }
}