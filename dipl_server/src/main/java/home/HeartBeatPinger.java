/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package home;

import home.dao.EventDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.scheduling.annotation.Scheduled;

/**
 *
 * @author trident
 */
public class HeartBeatPinger {
    private SimpMessagingTemplate template;
    

    public HeartBeatPinger(SimpMessagingTemplate template) {
      this.template = template;
    }

    @Scheduled(fixedDelay = 10000)
    public void pingUsers() {
        System.out.println("Heartbeat ping");
      template.convertAndSend("/broadcast/heartbeat/",new AliveMessage("alive"));
    }
}
