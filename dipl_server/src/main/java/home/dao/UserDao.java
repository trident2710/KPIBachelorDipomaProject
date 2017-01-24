/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package home.dao;

import home.model.User;
import org.springframework.data.repository.CrudRepository;

/**
 *
 * @author trident
 */
public interface UserDao extends CrudRepository<User, Integer>{
    public User findBySpecialCode(String specialCode);
}
