/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package clientecalcv2;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Juan Martínez
 */
public class ClienteCalcv2 extends Thread {

    public static void main(String[] args) throws IOException {
        
            Interfaz inter = new Interfaz();
            inter.setVisible(true);
    }
}
