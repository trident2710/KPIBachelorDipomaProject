/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package home.dao;

import home.model.EventType;
import home.model.StaffInfo;
import org.springframework.data.repository.CrudRepository;

/**
 *
 * @author trident
 */
public interface EventTypeDao extends CrudRepository<EventType, Integer>{
    public EventType findByType(String type);
}
