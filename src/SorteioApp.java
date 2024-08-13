import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class SorteioApp extends JFrame{
    private final List<String> jogadores = new ArrayList<>();
    private final DefaultListModel<String> listModel;
    private final JList<String> jogadoresList;

    public SorteioApp() {
        carregarJogadoresDoArquivo("C:\\Projetos IntelliJ\\gerenciamento\\jogadores.txt");

        listModel = new DefaultListModel<>();
        for (String jogador : jogadores) {
            listModel.addElement(jogador);
        }

        JFrame frame = new JFrame("Sorteio de Times de Futebol");
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.setSize(400, 400);
        frame.setLocationRelativeTo(null);

        jogadoresList = new JList<>(listModel);

        JScrollPane scrollPane = new JScrollPane(jogadoresList);
        JButton sortearButton = new JButton("Sortear Times");

        sortearButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                List<String> jogadoresSelecionados = jogadoresList.getSelectedValuesList();

                if (jogadoresSelecionados.isEmpty()) {
                    JOptionPane.showMessageDialog(frame, "Por favor, selecione de 14 a 20 jogadores");
                } else if (jogadoresSelecionados.size() < 14 || jogadoresSelecionados.size() > 20) {
                    JOptionPane.showMessageDialog(frame, "Selecione entre 14 e 20 jogadores para formar os times.");
                } else {
                    Collections.shuffle(jogadoresSelecionados);

                    int metade = jogadoresSelecionados.size() / 2;
                    List<String> time1 = jogadoresSelecionados.subList(0, metade);
                    List<String> time2 = jogadoresSelecionados.subList(metade, jogadoresSelecionados.size());

                    exibirTelaTimesSeparados(time1, time2);
                }
            }
        });


        JPanel panel = new JPanel(new BorderLayout());
        panel.add(scrollPane, BorderLayout.CENTER);

        frame.add(panel, BorderLayout.CENTER);
        frame.add(sortearButton, BorderLayout.SOUTH);

        frame.setVisible(true);
    }

    private void exibirTelaTimesSeparados(List<String> time1, List<String> time2) {
        JFrame frame = new JFrame("Resultados do Sorteio");
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.setSize(600, 400);
        frame.setLocationRelativeTo(null);

        DefaultListModel<String> time1Model = new DefaultListModel<>();
        DefaultListModel<String> time2Model = new DefaultListModel<>();

        for (String jogador : time1) {
            time1Model.addElement(jogador);
        }

        for (String jogador : time2) {
            time2Model.addElement(jogador);
        }

        JList<String> time1List = new JList<>(time1Model);
        JList<String> time2List = new JList<>(time2Model);
        JTextField placarTime1 = new JTextField("0", 5);
        JTextField placarTime2 = new JTextField("0", 5);
        JButton concluirPartidaButton = new JButton("Concluir Partida");

        JPanel panel = new JPanel(new BorderLayout());
        JPanel timesPanel = new JPanel(new GridLayout(1, 2));

        JScrollPane time1ScrollPane = new JScrollPane(time1List);
        JScrollPane time2ScrollPane = new JScrollPane(time2List);

        timesPanel.add(time1ScrollPane);
        timesPanel.add(time2ScrollPane);

        JPanel placarPanel = new JPanel();
        placarPanel.add(new JLabel("Placar Time A:"));
        placarPanel.add(placarTime1);
        placarPanel.add(new JLabel("Placar Time B:"));
        placarPanel.add(placarTime2);

        panel.add(timesPanel, BorderLayout.CENTER);
        panel.add(placarPanel, BorderLayout.NORTH);
        panel.add(concluirPartidaButton, BorderLayout.SOUTH);

        concluirPartidaButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int placarA = Integer.parseInt(placarTime1.getText());
                int placarB = Integer.parseInt(placarTime2.getText());

                exibirTelaPreencherGols(placarA, placarB, time1, time2);
                frame.dispose();
            }
        });

        frame.add(panel);
        frame.setVisible(true);
    }

    private void exibirTelaPreencherGols(int placarA, int placarB, List<String> time1, List<String> time2) {
        JFrame frame = new JFrame("Preencher Gols");
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.setSize(800, 400);
        frame.setLocationRelativeTo(null);

        JPanel panel = new JPanel(new GridLayout(1, 2, 10, 5));

        JPanel panelTimeA = new JPanel(new BorderLayout());
        panelTimeA.add(new JLabel("Time A - Total de Gols: " + placarA), BorderLayout.NORTH);
        JPanel timeAPanel = new JPanel(new GridLayout(0, 2, 10, 5));
        List<JTextField> golsTimeA = new ArrayList<>();
        for (String jogador : time1) {
            JLabel jogadorLabel = new JLabel(jogador);
            JTextField golsTextField = new JTextField(5);
            golsTextField.setHorizontalAlignment(JTextField.CENTER);
            timeAPanel.add(jogadorLabel);
            timeAPanel.add(golsTextField);
            golsTimeA.add(golsTextField);
        }
        panelTimeA.add(new JScrollPane(timeAPanel), BorderLayout.CENTER);

        JPanel panelTimeB = new JPanel(new BorderLayout());
        panelTimeB.add(new JLabel("Time B - Total de Gols: " + placarB), BorderLayout.NORTH);
        JPanel timeBPanel = new JPanel(new GridLayout(0, 2, 10, 5));
        List<JTextField> golsTimeB = new ArrayList<>();
        for (String jogador : time2) {
            JLabel jogadorLabel = new JLabel(jogador);
            JTextField golsTextField = new JTextField(5);
            golsTextField.setHorizontalAlignment(JTextField.CENTER);
            timeBPanel.add(jogadorLabel);
            timeBPanel.add(golsTextField);
            golsTimeB.add(golsTextField);
        }
        panelTimeB.add(new JScrollPane(timeBPanel), BorderLayout.CENTER);

        JButton confirmarButton = new JButton("Confirmar");
        confirmarButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int totalGolsTimeA = 0;
                int totalGolsTimeB = 0;
                boolean golsPreenchidosCorretamente = true;
                List<String> jogadoresGolsTimeA = new ArrayList<>();
                List<String> jogadoresGolsTimeB = new ArrayList<>();

                for (JTextField textField : golsTimeA) {
                    try {
                        int gols = Integer.parseInt(textField.getText());
                        totalGolsTimeA += gols;
                        if (gols > 0) {
                            jogadoresGolsTimeA.add(((JLabel) timeAPanel.getComponent(timeAPanel.getComponentZOrder(textField) - 1)).getText());
                        }
                    } catch (NumberFormatException ex) {
                        golsPreenchidosCorretamente = false;
                        JOptionPane.showMessageDialog(frame, "Preencha corretamente os gols para cada jogador do Time A.");
                        break;
                    }
                }

                for (JTextField textField : golsTimeB) {
                    try {
                        int gols = Integer.parseInt(textField.getText());
                        totalGolsTimeB += gols;
                        if (gols > 0) {
                            jogadoresGolsTimeB.add(((JLabel) timeBPanel.getComponent(timeBPanel.getComponentZOrder(textField) - 1)).getText());
                        }
                    } catch (NumberFormatException ex) {
                        golsPreenchidosCorretamente = false;
                        JOptionPane.showMessageDialog(frame, "Preencha corretamente os gols para cada jogador do Time B.");
                        break;
                    }
                }

                if (golsPreenchidosCorretamente && totalGolsTimeA == placarA && totalGolsTimeB == placarB) {
                    exibirTelaVotacao(jogadoresGolsTimeA, jogadoresGolsTimeB);
                    frame.dispose();
                } else {
                    JOptionPane.showMessageDialog(frame, "A quantidade de gols dos jogadores não corresponde ao placar informado.");
                }
            }
        });

        panel.add(panelTimeA);
        panel.add(panelTimeB);

        JPanel panelConfirmButton = new JPanel(new FlowLayout(FlowLayout.CENTER));
        panelConfirmButton.add(confirmarButton);

        frame.add(panel, BorderLayout.CENTER);
        frame.add(panelConfirmButton, BorderLayout.SOUTH);

        frame.setVisible(true);
    }

    private void exibirTelaVotacao(List<String> jogadoresGolsTimeA, List<String> jogadoresGolsTimeB) {
        JFrame frame = new JFrame("Votação");
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.setSize(300, 200);
        frame.setLocationRelativeTo(null);

        JButton votarGolBonitoButton = new JButton("Votar no Gol Mais Bonito");
        JButton votarMelhorJogadorButton = new JButton("Votar no Melhor Jogador");

        votarGolBonitoButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (!jogadoresGolsTimeA.isEmpty() || !jogadoresGolsTimeB.isEmpty()) {
                    List<String> jogadoresComGols = new ArrayList<>();
                    jogadoresComGols.addAll(jogadoresGolsTimeA);
                    jogadoresComGols.addAll(jogadoresGolsTimeB);

                    String[] jogadoresArray = jogadoresComGols.toArray(new String[0]);
                    String escolha = (String) JOptionPane.showInputDialog(
                            frame,
                            "Selecione o jogador para votar no gol mais bonito:",
                            "Votação Gol Mais Bonito",
                            JOptionPane.QUESTION_MESSAGE,
                            null,
                            jogadoresArray,
                            jogadoresArray[0]);

                    if (escolha != null) {
                        registrarVoto("gol_mais_bonito.txt", escolha);
                        JOptionPane.showMessageDialog(frame, "Voto registrado para o gol mais bonito em: " + escolha);
                    }
                } else {
                    JOptionPane.showMessageDialog(frame, "Não há jogadores elegíveis para votar no gol mais bonito.");
                }
            }
        });

        votarMelhorJogadorButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                List<String> todosJogadores = new ArrayList<>();
                todosJogadores.addAll(jogadoresGolsTimeA);
                todosJogadores.addAll(jogadoresGolsTimeB);

                String[] jogadoresArray = todosJogadores.toArray(new String[0]);
                String escolha = (String) JOptionPane.showInputDialog(
                        frame,
                        "Selecione o jogador para votar no melhor jogador:",
                        "Votação Melhor Jogador",
                        JOptionPane.QUESTION_MESSAGE,
                        null,
                        jogadoresArray,
                        jogadoresArray[0]);

                if (escolha != null) {
                    registrarVoto("melhor_jogador.txt", escolha);
                    JOptionPane.showMessageDialog(frame, "Voto registrado para o melhor jogador em: " + escolha);
                } else {
                    JOptionPane.showMessageDialog(frame, "Não há jogadores elegíveis para votar no melhor jogador.");
                }
            }
        });

        JPanel panel = new JPanel(new GridLayout(2, 1));
        panel.add(votarGolBonitoButton);
        panel.add(votarMelhorJogadorButton);

        frame.add(panel);
        frame.setVisible(true);
    }

    private void registrarVoto(String nomeArquivo, String jogador) {
        File arquivoVotos = new File("C:\\Projetos IntelliJ\\gerenciamento\\" + nomeArquivo);

        try {
            if (!arquivoVotos.exists()) {
                arquivoVotos.createNewFile();
            }

            BufferedReader reader = new BufferedReader(new FileReader(arquivoVotos));
            StringBuilder stringBuilder = new StringBuilder();
            String linha;
            boolean jogadorEncontrado = false;

            while ((linha = reader.readLine()) != null) {
                String[] partes = linha.split(" ");
                if (partes[0].equals(jogador)) {
                    jogadorEncontrado = true;
                    int votos = Integer.parseInt(partes[1]) + 1;
                    stringBuilder.append(jogador).append(" ").append(votos).append("\n");
                } else {
                    stringBuilder.append(linha).append("\n");
                }
            }

            reader.close();

            if (!jogadorEncontrado) {
                stringBuilder.append(jogador).append(" 1\n");
            }

            BufferedWriter writer = new BufferedWriter(new FileWriter(arquivoVotos));
            writer.write(stringBuilder.toString());
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }




    private void carregarJogadoresDoArquivo(String filePath) {
        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            String linha;
            while ((linha = reader.readLine()) != null) {
                if (linha.contains("(ativo)")) {
                    String[] partes = linha.split(",");
                    jogadores.add(partes[0].replaceAll("\\(ativo\\)", "").trim());
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(SorteioApp::new);
    }
}
