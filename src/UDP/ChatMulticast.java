package UDP;

import UI.FormChat;
import UI.FormLogin;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.net.DatagramPacket;
import java.net.InetAddress;
import java.net.MulticastSocket;
import java.net.SocketException;
import java.util.ArrayList;

public class ChatMulticast extends JFrame implements ActionListener, Runnable {
    private static final long serialVersionUID = 1L;

    MulticastSocket ms;
    InetAddress grupo;
    int Puerto;

    String nombre;
    FormChat formCliente;

    JTextArea textArea;
    JTextField textField;
    JButton sendButton;
    JTextArea userListArea;
    JLabel userLabel;

    // Lista compartida de usuarios
    private static ArrayList<String> nombresUsuarios = new ArrayList<>();
    static byte[] buf = new byte[1000];

    public ChatMulticast(String nombre, MulticastSocket ms, InetAddress grupo, int puerto) {
        super(" CONEXIÓN DEL CLIENTE CHAT: " + nombre + "\n");
        setLayout(null);

        this.ms = ms;
        this.grupo = grupo;
        this.Puerto = puerto;

        formCliente = new FormChat();
        formCliente.setVisible(true);

        textArea = formCliente.getTextArea();
        textField = formCliente.getTextField();
        userListArea = formCliente.getUserListArea();
        userLabel = formCliente.getUserLabel();

        sendButton = formCliente.getSendButton();

        textArea.setEditable(false);
        sendButton.addActionListener(this);

        this.nombre = nombre;
        userLabel.setText(nombre);

        setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == sendButton) { // SE PULSA EL ENVIAR
            if (textField.getText().trim().isEmpty())
                return;
            String texto = nombre + ": " + textField.getText();
            try {
                // ENVIANDO mensaje al grupo
                DatagramPacket paquete = new DatagramPacket(texto.getBytes(),
                        texto.length(), grupo, Puerto);
                ms.send(paquete);
                textField.setText(""); // Limpiar el textField después de enviar el mensaje
            } catch (IOException e1) {
                e1.printStackTrace();
            }
        }
    }

    @Override
    public void run() {
        nombresUsuarios.add(nombre);
        enviarMensajeAUsuarios(nombre);

        System.out.println("ENTRO RUN");
        String texto;
        boolean continuar = true;
        while (continuar) {
            try {
                DatagramPacket p = new DatagramPacket(buf, buf.length);
                ms.receive(p);
                texto = new String(p.getData(), 0, p.getLength());

                System.out.println("Mensaje recibido: " + texto); // Agregar información de depuración

                if (texto.startsWith("CONEXION ")) {
                    String nombreNuevo = texto.substring(9);
                    actualizarListaUsuarios(nombreNuevo);
                    texto = " ";
                } else if (texto.equals("DESCONEXION")) {
                    // Si se recibe un mensaje de desconexión, detener el bucle
                    continuar = false;
                } else if (!texto.equals(" ")) {
                    textArea.append(texto + "\n");
                }
            } catch (SocketException s) {
                System.out.println(s.getMessage());
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    // Método para enviar mensaje de notificación a todos los usuarios
    private void enviarMensajeAUsuarios(String nombreNuevo) {
        String mensaje = "CONEXION " + nombreNuevo;
        try {
            // ENVIANDO mensaje al grupo
            DatagramPacket paquete = new DatagramPacket(mensaje.getBytes(),
                    mensaje.length(), grupo, Puerto);
            ms.send(paquete);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // Método para actualizar la lista de usuarios
    private void actualizarListaUsuarios(String nombre) {
        System.out.println("Actualizando lista de usuarios...");
        textArea.append("Se ha conectado... " + nombre + "\n");

        if (userListArea.getText().isEmpty()) {
            nombresUsuarios.forEach(n -> {
                userListArea.append(n + "\n");
            });
        } else {
            userListArea.append(nombre + "\n");
        }
        nombresUsuarios.add(nombre);
    }

    public static void main(String args[]) throws IOException {
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

                    if (nombre.trim().equals("")) {
                        JOptionPane.showMessageDialog(null, "El nombre está vacío....");
                    } else {
                        try {
                            int Puerto = 12345; // Puerto multicast
                            MulticastSocket ms = new MulticastSocket(Puerto);
                            InetAddress grupo = InetAddress.getByName("225.0.0.1");// Grupo
                            ms.joinGroup(grupo);

                            ChatMulticast server = new ChatMulticast(nombre, ms, grupo, Puerto);
                            server.setBounds(0, 0, 540, 400);
                            server.setVisible(true);
                            new Thread(server).start();

                            // Cierra el JFrame de inicio de sesión después de iniciar sesión exitosamente
                            frame.dispose();
                        } catch (IOException ex) {
                            System.out.println("Error al crear el socket multicast: " + ex.getMessage());
                        }
                    }
                }
            });

            frame.setVisible(true);
        });
    }
}
