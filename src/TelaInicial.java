import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;


class Time {
    private String nome;
    private String sigla;

    public Time(String nome, String sigla) {
        this.nome = nome;
        this.sigla = sigla;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getSigla() {
        return sigla;
    }

    public void setSigla(String sigla) {
        this.sigla = sigla;
    }
}

public class TelaInicial extends JFrame {

    private List<Time> times = new ArrayList<>(); // Lista de times
    private JLabel nomeTimeLabel = new JLabel("Time Atual: Nenhum"); // Label para exibir o nome do time

    public TelaInicial() {
        setTitle("Tela Inicial");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(800, 600);
        setLocationRelativeTo(null); // Centraliza a janela na tela


        JPanel panel = new JPanel(null);

        // Cor de fundo verde meio escuro
        panel.setBackground(new Color(0, 128, 0)); // RGB: Verde

        // Adicione os botões centralizados
        JButton criarTimeButton = new JButton("Criar Partida");
        JButton gerenciarTimeButton = new JButton("Gerenciar Time");
        JButton rankingButton = new JButton("Ranking");
        JButton desenvolvedoresButton = new JButton("Desenvolvedores");

        Font buttonFont = new Font("Arial", Font.PLAIN, 24);
        criarTimeButton.setFont(buttonFont);
        gerenciarTimeButton.setFont(buttonFont);
        rankingButton.setFont(buttonFont);
        desenvolvedoresButton.setFont(buttonFont);

        criarTimeButton.setBounds(250, 100, 300, 50);
        gerenciarTimeButton.setBounds(250, 200, 300, 50);
        rankingButton.setBounds(250, 300, 300, 50);
        desenvolvedoresButton.setBounds(500, 500, 250, 40);

        //Cor das caixas brancas
        criarTimeButton.setBackground(Color.WHITE);
        gerenciarTimeButton.setBackground(Color.WHITE);
        rankingButton.setBackground(Color.WHITE);
        desenvolvedoresButton.setBackground(Color.WHITE);

        //Cor da fonte dos botões
        criarTimeButton.setForeground(new Color(0, 128, 0)); // Verde escuro
        gerenciarTimeButton.setForeground(new Color(0, 128, 0));
        rankingButton.setForeground(new Color(0, 128, 0));
        desenvolvedoresButton.setForeground(new Color(0, 128, 0));


        criarTimeButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Instancia e exibe o JFrame da classe SorteioApp
                SorteioApp sorteioApp = new SorteioApp();
                sorteioApp.setVisible(true);
            }
        });


        gerenciarTimeButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                TesteCadastroCompleto testeCadastroCompleto = new TesteCadastroCompleto();
                testeCadastroCompleto.setVisible(true);
                testeCadastroCompleto.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
            }
        });


        desenvolvedoresButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                exibirDireitosReservados();
            }
        });


        rankingButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                abrirJanelaRankingOpcoes();
            }
        });


        panel.add(criarTimeButton);
        panel.add(gerenciarTimeButton);
        panel.add(rankingButton);
        panel.add(desenvolvedoresButton);


        nomeTimeLabel.setFont(new Font("Arial", Font.BOLD, 18));
        nomeTimeLabel.setForeground(new Color(0, 128, 0));
        nomeTimeLabel.setHorizontalAlignment(SwingConstants.CENTER);
        nomeTimeLabel.setBounds(250, 500, 300, 30);
        panel.add(nomeTimeLabel);

        add(panel);

        // Defina a janela como visível
        setVisible(true);
    }

    private void abrirJanelaRankingOpcoes() {
        JFrame frame = new JFrame("Opções de Ranking");
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.setSize(400, 200);
        frame.setLocationRelativeTo(null); // Centraliza a janela na tela

        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(3, 1));

        JButton golMaisBonitoButton = new JButton("Gol Mais Bonito");
        JButton melhorJogadorButton = new JButton("Melhor Jogador");
        JButton artilheiroButton = new JButton("Artilheiro");

        Font buttonFont = new Font("Arial", Font.PLAIN, 24); // Fonte aumentada
        golMaisBonitoButton.setFont(buttonFont);
        melhorJogadorButton.setFont(buttonFont);
        artilheiroButton.setFont(buttonFont);

        panel.add(golMaisBonitoButton);
        panel.add(melhorJogadorButton);
        panel.add(artilheiroButton);

        frame.add(panel);

        // Adicione a ação para cada opção de ranking
        golMaisBonitoButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                abrirArquivoRanking("C:\\Projetos IntelliJ\\gerenciamento\\gol_mais_bonito.txt", "Ranking Gol Mais Bonito");
            }
        });

        melhorJogadorButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                abrirArquivoRanking("C:\\Projetos IntelliJ\\gerenciamento\\melhor_jogador.txt", "Ranking Melhor Jogador");
            }
        });

        artilheiroButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JOptionPane.showMessageDialog(null, "Em breve, ainda em desenvolvimento!", "Aviso", JOptionPane.INFORMATION_MESSAGE);
            }
        });

        frame.setVisible(true);
    }

    private void abrirArquivoRanking(String filePath, String tituloJanela) {
        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            List<String> linhas = new ArrayList<>();
            String linha;

            while ((linha = reader.readLine()) != null) {
                linhas.add(linha);
            }

            linhas.sort((a, b) -> {

                return Integer.compare(getVotos(b), getVotos(a));
            });

            JTextArea textArea = new JTextArea(20, 30);
            textArea.setEditable(false);

            for (String linhaRanking : linhas) {
                textArea.append(linhaRanking + "\n");
            }

            JScrollPane scrollPane = new JScrollPane(textArea);
            JOptionPane.showMessageDialog(null, scrollPane, tituloJanela, JOptionPane.INFORMATION_MESSAGE);
        } catch (IOException ex) {
            JOptionPane.showMessageDialog(null, "Erro ao abrir o arquivo: " + ex.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
        }
    }

    private int getVotos(String linha) {

        return 0;
    }

    private void exibirDireitosReservados() {
        JPanel panel = new JPanel(new BorderLayout());

        String linha1 = "© Todos os direitos reservados a:";
        String linha2 = "Matheus de Almeida Plombon e Rafael Veiga de Moraes";
        String linha3 = "©2023©";

        JLabel linha1Label = new JLabel(linha1, SwingConstants.CENTER);
        JLabel linha2Label = new JLabel(linha2, SwingConstants.CENTER);
        JLabel linha3Label = new JLabel(linha3, SwingConstants.CENTER);

        panel.add(linha1Label, BorderLayout.NORTH);
        panel.add(linha2Label, BorderLayout.CENTER);
        panel.add(linha3Label, BorderLayout.SOUTH);

        JOptionPane.showMessageDialog(this, panel, "Direitos Reservados", JOptionPane.INFORMATION_MESSAGE);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new TelaInicial();
            }
        });
    }

    public void exibir() {
    }
}
