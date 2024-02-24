package TCP.Cliente;

import UI.FormLogin;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;

public class MainCliente {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            JFrame frame = new JFrame("Login");
            frame.setSize(520, 220);
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

            FormLogin loginPanel = new FormLogin();
            frame.add(loginPanel);

            loginPanel.setLogInButtonActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    String nombre = loginPanel.getUserName();

                    if (nombre.trim().length() == 0) {
                        JOptionPane.showMessageDialog(null, "El nombre está vacío....");
                        return;
                    }

                    int puerto = 44444;
                    Socket s = null;

                    try {
                        s = new Socket("localhost", puerto);
                        PrintWriter out = new PrintWriter(s.getOutputStream(), true);
                        out.println(nombre);

                        ClienteChat cliente = new ClienteChat(s, nombre);
                        cliente.pack(); // Ajustar el tamaño del JFrame según los componentes
                        cliente.setVisible(true);
                        new Thread(cliente).start();

                        // Cierra el JFrame de inicio de sesión después de iniciar sesión exitosamente
                        frame.dispose();

                    } catch (IOException ex) {
                        JOptionPane.showMessageDialog(null, "IMPOSIBLE CONECTAR CON EL SERVIDOR\n" + ex.getMessage(),
                                "<<MENSAJE DE ERROR:1>>", JOptionPane.ERROR_MESSAGE);
                    }
                }
            });

            frame.setVisible(true);
        });
    }
}
