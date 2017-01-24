/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package home.dao;

import home.model.TerminalData;
import org.springframework.data.repository.CrudRepository;

/**
 *
 * @author trident
 */
public interface TerminalDataDao extends CrudRepository<TerminalData, Integer>{
    
}
