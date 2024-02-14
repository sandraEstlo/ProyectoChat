package TCP.cliente;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import TCP.ui.FormChat;

public class ClienteChat extends JFrame implements ActionListener, Runnable {
    private static final long serialVersionUID = 1L;
    Socket socket = null;
    DataInputStream fentrada;
    DataOutputStream fsalida;
    String nombre;
    boolean repetir = true;
    FormChat formCliente;

    JTextArea textArea;
    JTextField textField;
    JButton sendButton;
    JTextArea userListArea;
    JLabel userLabel;

    public ClienteChat(Socket s, String nombre) {
        super(" CONEXIÓN DEL CLIENTE CHAT: " + nombre + "\n");
        setLayout(null);

        formCliente = new FormChat();
        formCliente.setVisible(true);

        textArea = formCliente.getTextArea();
        textField = formCliente.getTextField();
        userListArea = formCliente.getUserListArea();
        userLabel = formCliente.getUserLabel();

        sendButton = formCliente.getSendButton();

        textArea.setEditable(false);
        sendButton.addActionListener(this);

        socket = s;
        this.nombre = nombre;
        userLabel.setText(nombre);

        try {
            fentrada = new DataInputStream(socket.getInputStream());
            fsalida = new DataOutputStream(socket.getOutputStream());
            String texto = " > Entra en el Chat ... " + nombre;
            fsalida.writeUTF(texto);
        } catch (IOException e) {
            System.out.println("ERROR DE E/S");
            e.printStackTrace();
            System.exit(0);
        }

        setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);

    }

    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == sendButton) {
            if (textField.getText().trim().isEmpty())
                return;
            String texto = nombre + "> " + textField.getText();

            try {
                textField.setText("");
                fsalida.writeUTF(texto);
            } catch (IOException e1) {
                e1.printStackTrace();
            }
        }
    }

    public void run() {
        String texto = "";
        while (repetir) {
            try {
                texto = fentrada.readUTF();
                textArea.setText(texto);

            } catch (IOException e) {
                JOptionPane.showMessageDialog(null, "IMPOSIBLE CONECTAR CON EL SERVIDOR\n" + e.getMessage(),
                        "<<MENSAJE DE ERROR:2>>", JOptionPane.ERROR_MESSAGE);
                repetir = false;
            }
        }

        try {
            socket.close();
            System.exit(0);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
