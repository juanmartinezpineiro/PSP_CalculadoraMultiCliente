
package calculadorav2;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;

/**
 *
 * @author Juan Martínez
 */
public class Calculadorav2 {

    public static void main(String[] args) {
        
        int port = Integer.parseInt(JOptionPane.showInputDialog("Puerto?"));

        try {
            //Socket establecido
            System.out.println("Socket servidor");
            ServerSocket serverSocket = new ServerSocket(port);

            // A la espera
            while (true) {
                System.out.println("Esperando conexiones");
                Socket newSocket = serverSocket.accept();
                //crea thread
                new ClienteRecibido(newSocket).start();
            }
        } catch (IOException ex) {
            System.out.println("Error en conexiones entrantes");
        }
    }
}
//Cliente conectado
class ClienteRecibido extends Thread {
//Transferencia de datos
    private Socket socket;
    private InputStream is;
    private OutputStream os;

    public ClienteRecibido(Socket socket) throws IOException {
        this.socket = socket;
        is = socket.getInputStream();
        os = socket.getOutputStream();
        System.out.println("Connx recibida");
    }
    //Metodo Suma
    public static double suma(double n1, double n2) {
        System.out.println("Suma " + n1 + " + " + n2);
        return n1 + n2;
    }
    //Metodo Resta
    public static double resta(double n1, double n2) {
        System.out.println("Resta " + n1 + " - " + n2);
        return n1 - n2;
    }
    //Metodo Multiplica
    public static double multiplica(double n1, double n2) {
        System.out.println("Multiplica " + n1 + " * " + n2);
        return n1 * n2;
    }
    //Metodo Divide
    public static double divide(double n1, double n2) {
        System.out.println("Divide " + n1 + " / " + n2);
        return n1 / n2;
    }

    //Metodo Raiz cuadrada
    public static double rCuadrada(double n1) {
        System.out.println("Rcuadr " + n1 + "√");
        double numRaiz = n1;
        return Math.sqrt(numRaiz);
    }

    @Override
    
    //Metodo que se ejecuta y recibe los datos. Notifica por consola
    public void run() {
        try {
            
            byte[] mRcbido = new byte[25];
            is.read(mRcbido);
            System.out.println("Recibido: " + new String(mRcbido));

            String[] operacion = new String(mRcbido).split(" ");
            double resultado = 0;
            //equalsIgnoreCase sirve para realizar el split de manera correcta
            if (operacion[0].equalsIgnoreCase("Off")) {
                socket.close();
            } else {
                //Selector de operacion
                switch (operacion[1]) {
                    
                    case "+":
                        resultado = suma(Double.valueOf(operacion[0]), Double.valueOf(operacion[2]));
                        break;
                        
                    case "-":
                        resultado = resta(Double.valueOf(operacion[0]), Double.valueOf(operacion[2]));
                        break;
                        
                    case "*":
                        resultado = multiplica(Double.valueOf(operacion[0]), Double.valueOf(operacion[2]));
                        break;
                        
                    case "/":
                        resultado = divide(Double.valueOf(operacion[0]), Double.valueOf(operacion[2]));
                        break;
                        
                    case "√":
                        resultado = rCuadrada(Double.valueOf(operacion[0]));
                        break;
                }
            }
            //Notificaciones
            System.out.println("Envia " + resultado);
            String msjEnv = String.valueOf(resultado);
            os.write(msjEnv.getBytes());
            System.out.println("Enviado");
        } catch (IOException ex) {
            System.out.println(ex);
        } finally {
            try {
                //chapa la conexion
                socket.close();
            } catch (IOException ex) {
                System.out.println("Error al cerrar la conexión");
            }
        }
    }
}
