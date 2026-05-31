/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package model;

import java.util.List;

public interface InterfaceDAO <T> {
    void insert(T entity);
    void update(T entity);
    void delete(int id);
    T getById(int id);
    List<T> getAll();
    List<T> search(String keyword);
}
