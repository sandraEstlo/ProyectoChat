package TCP.Servidor;

import TCP.Data.ComunHilos;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashMap;

public class MainServidor {
    public static void main(String[] args) throws IOException {
        final int PUERTO = 44444;

        ServerSocket servidor = new ServerSocket(PUERTO);
        System.out.println("Servidor iniciado...");

        HashMap<Socket,String> conexiones = new HashMap<>();
        ComunHilos comun = new ComunHilos(conexiones);

        while(true){
            Socket socket = new Socket();
            socket = servidor.accept();

            BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            String nombre = in.readLine();

            if(!comun.ComprobarNombre(nombre)){
                comun.addConexion(socket, nombre);
                HiloServidorChat hilo = new HiloServidorChat(socket,comun,nombre);
                hilo.start();
            } else {
                PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
                out.println("El nombre ya está en uso. No se puede realizar la conexión...");
                socket.close();
            }
        }
    }
}
