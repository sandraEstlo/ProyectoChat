package TCP.ui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;

public class FormLogin extends JPanel {
    private JTextField txtName;
    private JButton logInButton;

    public FormLogin() {
        setLayout(null);
        setBackground(Color.WHITE);

        // Text Field
        txtName = new JTextField();
        txtName.setBounds(120, 116, 310, 29);
        add(txtName);

        // Label
        JLabel lblName = new JLabel("Nombre:");
        lblName.setFont(new Font("System", Font.PLAIN, 14));
        lblName.setBounds(46, 135, 70, 17);
        add(lblName);

        // Button
        logInButton = new JButton("LogIn");
        logInButton.setBounds(447, 115, 62, 30);
        logInButton.setBackground(new Color(3, 169, 244));
        logInButton.setForeground(Color.WHITE);
        logInButton.setFont(new Font("System Bold", Font.PLAIN, 14));
        add(logInButton);

        // Image Icon
        ImageIcon icon = new ImageIcon("icono_chat.png");
        JLabel imageLabel = new JLabel(icon);
        imageLabel.setBounds(20, 14, 96, 84);
        add(imageLabel);
    }

    public String getUserName() {
        return txtName.getText();
    }

    public void setLogInButtonActionListener(ActionListener listener) {
        logInButton.addActionListener(listener);
    }
}
