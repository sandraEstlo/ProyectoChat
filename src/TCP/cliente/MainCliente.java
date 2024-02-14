package TCP.cliente;

import javax.swing.*;
import java.io.IOException;
import java.net.Socket;

public class MainCliente {
    public static void main(String args[]) {
        int puerto = 44444;
        Socket s = null;

        String nombre = JOptionPane.showInputDialog("Introduce tu nombre o nick:");

        if (nombre.trim().length() == 0) {
            System.out.println("El nombre está vacío....");
            return;
        }

        try {
            s = new Socket("localhost", puerto);
            ClienteChat cliente = new ClienteChat(s, nombre);
            cliente.pack(); // Ajustar el tamaño del JFrame según los componentes
            cliente.setVisible(true);
            new Thread(cliente).start();

        } catch (IOException e) {
            JOptionPane.showMessageDialog(null, "IMPOSIBLE CONECTAR CON EL SERVIDOR\n" + e.getMessage(),
                    "<<MENSAJE DE ERROR:1>>", JOptionPane.ERROR_MESSAGE);
        }
    }
}
