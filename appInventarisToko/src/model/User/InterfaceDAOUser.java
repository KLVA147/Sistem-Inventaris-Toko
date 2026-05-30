/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model.User;

import model.InterfaceDAO;

public interface InterfaceDAOUser extends InterfaceDAO<ModelUser> {
    ModelUser login(String username, String password);
    boolean usernameExists(String username);
}
