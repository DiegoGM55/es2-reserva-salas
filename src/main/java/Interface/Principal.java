/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Interface;
import Controlador.AdministradorController;
import Controlador.EspacoFisicoController;
import Controlador.ReservaController;
import Controlador.UsuarioController;
import java.awt.Color;
import java.io.IOException;

public class Principal {
        public static void main(String[] args) throws IOException {
            
        AdministradorController admController = new AdministradorController();
        admController.registrarAdmin("adm", "adm", "adm", "adm", "adm");
        UsuarioController userController = new UsuarioController();
        EspacoFisicoController espacoController = new EspacoFisicoController();
        ReservaController reservaController = new ReservaController();
        UIHome home = new UIHome(admController,userController,espacoController,reservaController);
        home.setVisible(true);
        home.setTitle("Sistema de reserva de salas");
        home.toFront();
        home.setResizable(false);
        home.getContentPane().setBackground(Color.white);
        home.setLocationRelativeTo(null);   
    }
}
