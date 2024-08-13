import javax.swing.*;

public class ExibirImagem {

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                JFrame frame = new JFrame("Exibir Imagem");
                frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                frame.setSize(800, 600);
                frame.setLocationRelativeTo(null);

                ImageIcon imageIcon = new ImageIcon("futebol.png");

                JLabel label = new JLabel();
                label.setIcon(imageIcon);

                frame.add(label);

                frame.setVisible(true);
            }
        });
    }
}
