/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Interface;
import java.awt.Color;
import java.io.IOException;

/**
 *
 * @author lucas
 */
public class Principal {
        public static void main(String[] args) throws IOException {
        UIHome home = new UIHome();
        home.setVisible(true);
        home.setTitle("Sistema de reserva de salas");
        home.toFront();
        home.setResizable(false);
        home.getContentPane().setBackground(Color.white);
        home.setLocationRelativeTo(null);
        
    }
}