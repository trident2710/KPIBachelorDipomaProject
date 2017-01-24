/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package home.dao;

import home.model.StaffInfo;
import home.model.UserData;
import org.springframework.data.repository.CrudRepository;

/**
 *
 * @author trident
 */
public interface UserDataDao extends CrudRepository<UserData, Integer>{
    
}
