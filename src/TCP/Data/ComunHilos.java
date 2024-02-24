package TCP.Data;

import java.net.Socket;
import java.util.ArrayList;
import java.util.HashMap;

public class ComunHilos {
    HashMap<Socket,String> conexiones;
    String mensajes;

    public ComunHilos(HashMap<Socket,String> conexiones){
        this.conexiones = conexiones;
        this.mensajes = "";
    }
    public ComunHilos() { super(); }

    public HashMap<Socket, String> getConexiones() { return conexiones; }
    public void setConexiones(HashMap<Socket, String> conexiones) {
        this.conexiones = conexiones;
    }
    public void addConexion(Socket socket, String nombre){
        conexiones.put(socket,nombre);
    }

    public String getMensajes() {
        return mensajes;
    }
    public void setMensajes(String mensajes) {
        this.mensajes = mensajes;
    }

    public ArrayList<String> DevolverNombresUsuarios(){
        return new ArrayList<>(conexiones.values());
    }

    public boolean ComprobarNombre(String nombre){
        String busqueda = conexiones.values()
                .stream()
                .filter((n) -> n.equalsIgnoreCase(nombre))
                .findFirst()
                .orElse(null);

        return busqueda != null;
    }
}

