package TCP.Servidor;

import TCP.Data.ComunHilos;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

public class HiloServidorChat extends Thread {
    DataInputStream fentrada;
    Socket socket = null;
    ComunHilos comun;
    String nombre;

    public HiloServidorChat(Socket s, ComunHilos comun, String nombre){
        this.socket = s;
        this.comun = comun;
        this.nombre = nombre;

        try{
            fentrada = new DataInputStream(socket.getInputStream());
        }catch(IOException e){
            System.out.println("ERROR DE E/S");
            e.printStackTrace();
        }
    } // constructor

    @Override
    public void run() {
        System.out.println("NUMERO DE CONEXIONES ACTUALES: " + comun.getConexiones().size());

        String texto = comun.getMensajes();
        System.out.println(texto);

        EnviarMensajesTodos(texto);
        EnviarListaUsuarios();

        while(true){
            String cadena = "";
            try {
                cadena = fentrada.readUTF();
                comun.setMensajes(comun.getMensajes() + cadena + "\n");
                System.out.println(comun.getMensajes());
                EnviarMensajesTodos(comun.getMensajes());
            } catch (IOException e) {
                e.printStackTrace();
                break;
            }
        }

        try{
            socket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public void EnviarListaUsuarios(){
        StringBuilder listaNombres = new StringBuilder();
        listaNombres.append("LISTA_NOMBRES ");
        comun.DevolverNombresUsuarios().forEach((nombre) -> listaNombres.append(nombre).append(" "));

        comun.getConexiones().forEach((k,v)->{
            if(!k.isClosed()){
                try {
                    System.out.println(listaNombres.toString());
                    DataOutputStream fsalida = new DataOutputStream(k.getOutputStream());
                    fsalida.writeUTF(listaNombres.toString());
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
        });
    }

    public void EnviarMensajesTodos(String texto){
        comun.getConexiones().forEach((k,v) -> {
            if(!k.isClosed()){
                try {
                    DataOutputStream fsalida = new DataOutputStream(k.getOutputStream());
                    fsalida.writeUTF(texto);
                } catch (IOException e) {
                    System.out.println(e);
                }
            }
        });
    }
}
