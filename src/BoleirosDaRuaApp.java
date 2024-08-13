import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.HashMap;
import java.util.Map;

public class BoleirosDaRuaApp {

    private static Map<String, String> users = new HashMap<>();
    private static Map<String, String> shirts = new HashMap<>();

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            JFrame frame = new JFrame("Boleiros da Rua");
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.setSize(400, 200);
            frame.setLocationRelativeTo(null);

            JPanel panel = new JPanel();
            panel.setLayout(new BorderLayout());

            JLabel titleLabel = new JLabel("Boleiros da Rua");
            titleLabel.setFont(new Font("Arial", Font.BOLD, 24));
            titleLabel.setHorizontalAlignment(SwingConstants.CENTER);

            JButton startButton = new JButton("Começar");
            startButton.setBackground(Color.GREEN);
            startButton.setForeground(Color.WHITE);
            startButton.setFocusPainted(false);
            startButton.setFont(new Font("Arial", Font.PLAIN, 16));

            panel.add(titleLabel, BorderLayout.NORTH);
            panel.add(startButton, BorderLayout.CENTER);

            frame.add(panel);

            startButton.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    frame.dispose();
                    abrirJanelaOpcoes();
                }
            });

            frame.setVisible(true);
        });
    }

    private static void abrirJanelaOpcoes() {
        JFrame frame = new JFrame("Opções");
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.setSize(400, 200);
        frame.setLocationRelativeTo(null);

        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(2, 1));

        JButton registerButton = new JButton("Cadastro");
        JButton loginButton = new JButton("Login");

        Font buttonFont = new Font("Arial", Font.PLAIN, 20);

        registerButton.setFont(buttonFont);
        loginButton.setFont(buttonFont);

        panel.add(registerButton);
        panel.add(loginButton);

        frame.add(panel);

        registerButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                abrirJanelaCadastro();
            }
        });

        loginButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                abrirJanelaLogin();
            }
        });

        frame.setVisible(true);
    }

    private static void abrirJanelaCadastro() {
        JFrame frame = new JFrame("Cadastro");
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.setSize(400, 300);
        frame.setLocationRelativeTo(null);

        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(7, 2));

        Font labelFont = new Font("Arial", Font.PLAIN, 16);
        Font fieldFont = new Font("Arial", Font.PLAIN, 16);

        JLabel nameLabel = new JLabel("Nome:");
        nameLabel.setFont(labelFont);
        JTextField nameField = new JTextField();
        nameField.setFont(fieldFont);

        JLabel ageLabel = new JLabel("Idade:");
        ageLabel.setFont(labelFont);
        JTextField ageField = new JTextField();
        ageField.setFont(fieldFont);

        JLabel positionLabel = new JLabel("Posição:");
        positionLabel.setFont(labelFont);
        String[] positions = {"Goleiro", "Zagueiro", "Meio Campo", "Lateral", "Atacante"};
        JComboBox<String> positionComboBox = new JComboBox<>(positions);
        positionComboBox.setFont(fieldFont);

        JLabel shirtNumberLabel = new JLabel("Número da Camisa:");
        shirtNumberLabel.setFont(labelFont);
        JTextField shirtNumberField = new JTextField();
        shirtNumberField.setFont(fieldFont);

        JLabel passwordLabel = new JLabel("Senha:");
        passwordLabel.setFont(labelFont);
        JPasswordField passwordField = new JPasswordField();
        passwordField.setFont(fieldFont);

        JLabel confirmPasswordLabel = new JLabel("Confirmação de Senha:");
        confirmPasswordLabel.setFont(labelFont);
        JPasswordField confirmPasswordField = new JPasswordField();
        confirmPasswordField.setFont(fieldFont);

        panel.add(nameLabel);
        panel.add(nameField);
        panel.add(ageLabel);
        panel.add(ageField);
        panel.add(positionLabel);
        panel.add(positionComboBox);
        panel.add(shirtNumberLabel);
        panel.add(shirtNumberField);
        panel.add(passwordLabel);
        panel.add(passwordField);
        panel.add(confirmPasswordLabel);
        panel.add(confirmPasswordField);

        JButton registerButton = new JButton("Registrar");
        registerButton.setFont(new Font("Arial", Font.PLAIN, 16));
        panel.add(new JLabel());
        panel.add(registerButton);

        frame.add(panel);

        registerButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String nome = nameField.getText();
                String numeroCamisa = shirtNumberField.getText();
                String senha = new String(passwordField.getPassword());
                String confirmacaoSenha = new String(confirmPasswordField.getPassword());

                if (nome.isEmpty() || numeroCamisa.isEmpty() || senha.isEmpty() || confirmacaoSenha.isEmpty()) {
                    JOptionPane.showMessageDialog(frame, "Preencha todos os campos.");
                } else if (users.containsKey(nome)) {
                    JOptionPane.showMessageDialog(frame, "Nome de usuário já cadastrado.");
                } else if (shirts.containsValue(numeroCamisa)) {
                    JOptionPane.showMessageDialog(frame, "Número de camisa já cadastrado.");
                } else if (nome.matches(".*\\d.*")) {
                    JOptionPane.showMessageDialog(frame, "O nome não pode conter números.");
                } else if (!numeroCamisa.matches("\\d{1,2}")) {
                    JOptionPane.showMessageDialog(frame, "Número de camisa inválido.");
                } else if (senha.length() < 8) {
                    JOptionPane.showMessageDialog(frame, "A senha deve ter no mínimo 8 caracteres.");
                } else if (!senha.equals(confirmacaoSenha)) {
                    JOptionPane.showMessageDialog(frame, "A senha e a confirmação de senha não coincidem.");
                } else {
                    users.put(nome, senha);
                    shirts.put(nome, numeroCamisa);
                    JOptionPane.showMessageDialog(frame, "Registro bem-sucedido!");
                    frame.dispose();
                }
            }
        });

        frame.setVisible(true);
    }

    private static void abrirJanelaLogin() {
        JFrame frame = new JFrame("Login");
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.setSize(400, 200);
        frame.setLocationRelativeTo(null);

        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(3, 2));

        Font labelFont = new Font("Arial", Font.PLAIN, 16);
        Font fieldFont = new Font("Arial", Font.PLAIN, 16);

        JLabel nameLabel = new JLabel("Nome de Usuário:");
        nameLabel.setFont(labelFont);
        JTextField nameField = new JTextField();
        nameField.setFont(fieldFont);

        JLabel passwordLabel = new JLabel("Senha:");
        passwordLabel.setFont(labelFont);
        JPasswordField passwordField = new JPasswordField();
        passwordField.setFont(fieldFont);

        panel.add(nameLabel);
        panel.add(nameField);
        panel.add(passwordLabel);
        panel.add(passwordField);

        JButton loginButton = new JButton("Login");
        loginButton.setFont(new Font("Arial", Font.PLAIN, 16));
        panel.add(new JLabel());
        panel.add(loginButton);

        frame.add(panel);

        loginButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String nome = nameField.getText();
                String senha = new String(passwordField.getPassword());

                if (nome.isEmpty() || senha.isEmpty()) {
                    JOptionPane.showMessageDialog(frame, "Preencha todos os campos.");
                } else if (users.containsKey(nome) && users.get(nome).equals(senha)) {
                    JOptionPane.showMessageDialog(frame, "Login bem-sucedido!");
                    frame.dispose();
                    TelaInicial telaInicial = new TelaInicial();
                    telaInicial.exibir();

                    frame.dispose();
                } else {
                    JOptionPane.showMessageDialog(frame, "Nome de usuário ou senha incorretos.");
                }
            }
        });

        frame.setVisible(true);

    }



}
