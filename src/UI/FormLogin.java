package UI;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.util.Objects;

public class FormLogin extends JPanel {
    private JTextField txtName;
    private JButton logInButton;

    public FormLogin() {
        setLayout(null);
        setBackground(Color.WHITE);

        ImageIcon icon = new ImageIcon(Objects.requireNonNull(getClass().getResource("logo_chat.png")));
        JLabel imageLabel = new JLabel(icon);
        imageLabel.setBounds(50, 10, 100, 50); // Ajuste las coordenadas según tus necesidades
        add(imageLabel);

        // Label
        JLabel lblName = new JLabel("Nombre:");
        lblName.setFont(new Font("Bell MT Bold", Font.PLAIN, 14));
        lblName.setBounds(60, 85, 70, 17);
        add(lblName);

        // Text Field
        txtName = new JTextField();
        txtName.setBounds(60, 116, 310, 30);
        add(txtName);

        // Button
        logInButton = new JButton("LogIn");
        logInButton.setBounds(380, 116, 90, 30);
        logInButton.setBackground(Color.BLACK);
        logInButton.setForeground(Color.WHITE);
        logInButton.setFont(new Font("Bell MT Bold", Font.PLAIN, 14));
        add(logInButton);
    }

    public String getUserName() {
        return txtName.getText();
    }

    public void setLogInButtonActionListener(ActionListener listener) {
        logInButton.addActionListener(listener);
    }
}
