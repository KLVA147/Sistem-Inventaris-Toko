/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package view.Login;

import controller.ControllerUser;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.border.EmptyBorder;

public class LoginView extends JFrame {

    private final JTextField     inputUsername = new JTextField();
    private final JPasswordField inputPassword = new JPasswordField();
    private       ControllerUser controller;

    public LoginView() {
        setTitle("Sistem Inventaris Toko - Login");
        setSize(420, 300);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setResizable(false);

        JPanel panel = new JPanel(new GridBagLayout());
        panel.setBorder(new EmptyBorder(24, 32, 24, 32));
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(4, 0, 4, 0);

        JLabel labelTitle = new JLabel("🛒 Sistem Inventaris Toko", JLabel.CENTER);
        labelTitle.setFont(new Font("SansSerif", Font.BOLD, 18));
        gbc.gridx = 0; gbc.gridy = 0; gbc.gridwidth = 2; gbc.insets = new Insets(0, 0, 20, 0);
        panel.add(labelTitle, gbc);

        gbc.gridwidth = 1; gbc.insets = new Insets(4, 0, 4, 0);

        gbc.gridx = 0; gbc.gridy = 1; gbc.weightx = 0.3;
        panel.add(new JLabel("Username:"), gbc);
        gbc.gridx = 1; gbc.weightx = 0.7;
        panel.add(inputUsername, gbc);

        gbc.gridx = 0; gbc.gridy = 2; gbc.weightx = 0.3;
        panel.add(new JLabel("Password:"), gbc);
        gbc.gridx = 1; gbc.weightx = 0.7;
        panel.add(inputPassword, gbc);

        JButton btnLogin = new JButton("Login");
        btnLogin.setFont(new Font("SansSerif", Font.BOLD, 14));
        gbc.gridx = 0; gbc.gridy = 3; gbc.gridwidth = 2; gbc.insets = new Insets(18, 0, 0, 0);
        panel.add(btnLogin, gbc);

        add(panel);
        controller = new ControllerUser(this);

        ActionListener loginAction = e -> controller.login();
        btnLogin.addActionListener(loginAction);
        inputPassword.addActionListener(loginAction);

        setVisible(true);
    }

    public String getInputUsername() { return inputUsername.getText().trim(); }
    public String getInputPassword() { return new String(inputPassword.getPassword()); }
}
