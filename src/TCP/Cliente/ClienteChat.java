package TCP.Cliente;

import UI.FormChat;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

public class ClienteChat extends JFrame implements ActionListener, Runnable {
    //@Serial
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
            String texto = "Entra en el Chat ... " + nombre;
            System.out.println(texto);
            fsalida.writeUTF(texto);

        } catch (IOException e) {
            System.out.println("ERROR DE E/S");
            e.printStackTrace();
            System.exit(0);
        }
    }

    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == sendButton) {
            if (textField.getText().trim().isEmpty())
                return;
            String texto = nombre + ": " + textField.getText();

            try {
                textField.setText("");
                fsalida.writeUTF(texto);
            } catch (IOException e1) {
                e1.printStackTrace();
            }
        }
    }

    public void run(){
        String texto;
        while (repetir){
            try {
                texto = fentrada.readUTF();

                if(texto.startsWith("LISTA_NOMBRES ")){
                    System.out.println(texto);
                    String[] lista = texto.split(" ");

                    if (userListArea.getText().isEmpty() || userListArea.getText() == null) {
                        StringBuilder nombresConcatenados = new StringBuilder();
                        for (int i = 1; i < lista.length; i++) {
                            nombresConcatenados.append(lista[i]).append("\n");
                            System.out.println(lista[i]);
                        }
                        userListArea.setText(nombresConcatenados.toString());
                    } else {
                        System.out.println("El text area no está vacío, entro en el else.");
                        userListArea.append(lista[lista.length - 1] + "\n");
                    }
                }

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
