
package clientecalcv2;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JButton;
import javax.swing.JTextField;

public class Metodos {
    //Boleano que se pone true cuando recibe datos
    boolean recibe = false;

    static Socket SocketCliente;
    int connx;
    //Metodo que crea la conexion cogiendo el puerto introducido
    public void CreaConex(JTextField port, JButton boton, JTextField field) throws IOException {
        SocketCliente = new Socket();
        System.out.println("Crea Socket y conecta");
        connx = Integer.parseInt(port.getText());
        InetSocketAddress addr = new InetSocketAddress("localhost", connx);
        SocketCliente.connect(addr);
        boton.setEnabled(false);
        field.setEnabled(false);
    }
    //Metodo conexion
    public void connect() {
        try {
            SocketCliente = new Socket();
            InetSocketAddress addr = new InetSocketAddress("localhost", connx);
            SocketCliente.connect(addr);
        } catch (IOException ex) {
            Logger.getLogger(Metodos.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    //Envia los mensajes(numeros) 
    public void enviaMsj(String mensaje, JTextField resultado) {
        String resulta = "0";
        try {
            connect();
            OutputStream os = SocketCliente.getOutputStream();
            os.write(mensaje.getBytes());
            System.out.println("Enviado");

            InputStream is = SocketCliente.getInputStream();
            byte[] msjrecibido = new byte[25];
            is.read(msjrecibido);
            //El format para decimas, la segunda decima la redondea
            resultado.setText(String.format("%.2f", Float.parseFloat(new String(msjrecibido))));
            System.out.println("Recibido: " + new String(msjrecibido));
            is.close();
            os.close();
        } catch (IOException ex) {
            System.out.println("Error:" + ex);
        } finally {
            cerrarConexion();
        }
    }

    public void cerrarConexion() {
        try {
            System.out.println("Cierra socket");
            SocketCliente.close();
        } catch (IOException ex) {
            System.out.println("Error connx");
        }
    }
}
