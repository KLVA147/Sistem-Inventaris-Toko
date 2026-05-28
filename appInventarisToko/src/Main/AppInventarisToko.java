/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Main;

import view.Login.*;
import javax.swing.SwingUtilities;

public class AppInventarisToko {
    public static void main(String[] args) {
        // TODO code application logic here
        System.out.println("check");
        SwingUtilities.invokeLater(() -> {
            System.out.println("[Main] Starting on thread: " + Thread.currentThread().getName());
            new LoginView();
        });
    }
}
