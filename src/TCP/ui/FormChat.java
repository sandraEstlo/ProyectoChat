package TCP.ui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;

public class FormChat extends JFrame {
    private JPanel panel;
    private JTextArea textArea;
    private JTextField textField;
    private JButton sendButton;
    private JTextArea userListArea;
    private JLabel userLabel;

    public JPanel getPanel() {
        return panel;
    }

    public JTextArea getTextArea() {
        return textArea;
    }

    public JTextField getTextField() {
        return textField;
    }

    public JTextArea getUserListArea() {
        return userListArea;
    }

    public JLabel getUserLabel() {
        return userLabel;
    }

    public JButton getSendButton() {
        return sendButton;
    }

    public void setNombreUsuario(String nombre) {
        userLabel.setText(nombre);
    }

    public void aniadirMensaje(String mensaje) {
        textArea.append(mensaje + "\n");
    }

    public void configurarBotonEnviar(ActionListener listener) {
        sendButton.addActionListener(listener);
    }
    public FormChat() {
        setTitle("Chat Window");
        setSize(620, 480); // Hacer la ventana cuadrada
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // Crear el panel y establecer su diseño
        panel = new JPanel(new BorderLayout());
        JPanel mainPanel = new JPanel(null);
        mainPanel.setBackground(Color.WHITE);

        // Cargar la imagen del logo del chat y agregarla al panel en la parte superior izquierda
        ImageIcon icon = new ImageIcon(getClass().getResource("icono_chat.png"));
        JLabel imageLabel = new JLabel(icon);
        imageLabel.setBounds(-5, 20, 100, 50); // Ajuste las coordenadas según tus necesidades
        mainPanel.add(imageLabel);

        // Crear la etiqueta con el nombre de usuario y agregarla al panel a la derecha del icono del chat
        userLabel = new JLabel("Usuario");
        userLabel.setFont(new Font("Bell MT Bold", Font.PLAIN, 16));
        userLabel.setBounds(500, 40, 200, 20); // Ajuste las coordenadas según tus necesidades
        userLabel.setForeground(new Color(17, 19, 22));
        mainPanel.add(userLabel);

        // Crear el área de chat y agregarlo al panel dentro de un JScrollPane
        textArea = new JTextArea();
        textArea.setEditable(false);
        JScrollPane scrollPane = new JScrollPane(textArea);
        scrollPane.setBounds(10, 80, 380, 300); // Ajuste las coordenadas según tus necesidades
        mainPanel.add(scrollPane);

        // Crear el campo de texto para escribir mensajes y agregarlo al panel
        textField = new JTextField();
        textField.setBounds(10, 390, 320, 30); // Ajuste las coordenadas según tus necesidades
        mainPanel.add(textField);

        // Crear el área de lista de usuarios y agregarlo al panel en la parte derecha
        userListArea = new JTextArea();
        JScrollPane userScrollPane = new JScrollPane(userListArea);
        userScrollPane.setBounds(410, 80, 160, 340); // Ajuste las coordenadas según tus necesidades
        mainPanel.add(userScrollPane);



        // Cargar la imagen para el botón de enviar y agregar el botón al panel
        ImageIcon sendIcon = new ImageIcon(getClass().getResource("send-icon.png"));
        sendButton = new JButton(sendIcon);
        sendButton.setBounds(340, 390, 50, 30); // Ajuste las coordenadas según tus necesidades
        sendButton.addActionListener(e -> {
            // Manejar la acción del botón de enviar
            String mensaje = textField.getText().trim();
        });
        sendButton.setBackground(Color.WHITE);
        mainPanel.add(sendButton);

        // Agregar el panel principal al panel con un relleno de 10 píxeles en cada lado
        panel.add(mainPanel, BorderLayout.CENTER);
        panel.setBorder(BorderFactory.createEmptyBorder(0, 20, 0, 20));
        panel.setBackground(Color.WHITE);
        // Agregar el panel al JFrame
        add(panel);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            FormChat frame = new FormChat();
            frame.setVisible(true);
        });
    }
}
