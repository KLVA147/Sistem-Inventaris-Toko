/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package view.Login;

import controller.ControllerUser;
import view.theme.MetroTheme;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.border.EmptyBorder;

public class LoginView extends JFrame {

    private final JTextField     inputUsername = new JTextField();
    private final JPasswordField inputPassword = new JPasswordField();
    private       ControllerUser controller;

    public LoginView() {
        MetroTheme.install();

        setTitle("Sistem Inventaris Toko - Login");
        setSize(440, 340);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setResizable(false);

        JPanel root = new JPanel(new BorderLayout());
        root.setBackground(MetroTheme.BG_DARK);
        setContentPane(root);

        JPanel strip = new JPanel();
        strip.setBackground(MetroTheme.ACCENT);
        strip.setPreferredSize(new Dimension(0, 4));
        root.add(strip, BorderLayout.NORTH);

        JPanel card = new JPanel(new GridBagLayout());
        card.setBackground(MetroTheme.BG_SURFACE);
        card.setBorder(new EmptyBorder(36, 44, 36, 44));
        root.add(card, BorderLayout.CENTER);

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(4, 0, 4, 0);

        JLabel icon = new JLabel("🛒", JLabel.CENTER);
        icon.setFont(new Font("Segoe UI Emoji", Font.PLAIN, 32));
        gbc.gridx = 0; gbc.gridy = 0; gbc.gridwidth = 2;
        gbc.insets = new Insets(0, 0, 4, 0);
        card.add(icon, gbc);

        JLabel labelTitle = MetroTheme.titleLabel("Inventaris Toko");
        labelTitle.setHorizontalAlignment(JLabel.CENTER);
        gbc.gridy = 1;
        card.add(labelTitle, gbc);

        JLabel sub = MetroTheme.mutedLabel("Masuk ke sistem manajemen inventaris");
        sub.setHorizontalAlignment(JLabel.CENTER);
        gbc.gridy = 2; gbc.insets = new Insets(0, 0, 24, 0);
        card.add(sub, gbc);

        gbc.gridwidth = 1; gbc.insets = new Insets(5, 0, 5, 0);

        gbc.gridx = 0; gbc.gridy = 3; gbc.weightx = 0.28;
        card.add(MetroTheme.bodyLabel("Username"), gbc);
        gbc.gridx = 1; gbc.weightx = 0.72;
        MetroTheme.styleTextField(inputUsername);
        card.add(inputUsername, gbc);

        gbc.gridx = 0; gbc.gridy = 4; gbc.weightx = 0.28;
        card.add(MetroTheme.bodyLabel("Password"), gbc);
        gbc.gridx = 1; gbc.weightx = 0.72;
        MetroTheme.stylePasswordField(inputPassword);
        card.add(inputPassword, gbc);

        JButton btnLogin = MetroTheme.primaryButton("  Masuk  ");
        btnLogin.setFont(new Font("Segoe UI", Font.BOLD, 15));
        gbc.gridx = 0; gbc.gridy = 5; gbc.gridwidth = 2;
        gbc.insets = new Insets(22, 0, 0, 0);
        gbc.fill = GridBagConstraints.HORIZONTAL;
        card.add(btnLogin, gbc);

        controller = new ControllerUser(this);
        ActionListener loginAction = e -> controller.login();
        btnLogin.addActionListener(loginAction);
        inputPassword.addActionListener(loginAction);

        setVisible(true);
    }

    public String getInputUsername() { return inputUsername.getText().trim(); }
    public String getInputPassword() { return new String(inputPassword.getPassword()); }
}
