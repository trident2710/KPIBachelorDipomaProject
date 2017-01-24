/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package home.controller;

import home.dao.EventDao;
import home.model.Event;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;

/**
 *
 * @author trident
 */
@Controller
public class WebSocketController {
    @Autowired
    EventDao eventDao;
    
    @MessageMapping("broadcast/events/get/all")
    @SendTo("broadcast/events")
    public Iterable<Event> getEvents() throws Exception {
        System.out.println("I invoked");
        return eventDao.findAll();
    }
}
