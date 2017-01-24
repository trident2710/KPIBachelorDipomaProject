/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package home;

import com.google.inject.Inject;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.SchedulingConfigurer;
import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler;
import org.springframework.scheduling.config.ScheduledTaskRegistrar;

/**
 *
 * @author trident
 */
@Configuration
@EnableScheduling
public class HeartBeat implements SchedulingConfigurer{

    @Bean
    public ThreadPoolTaskScheduler taskScheduler() {
       return new ThreadPoolTaskScheduler();
    }
    @Override
    public void configureTasks(ScheduledTaskRegistrar str) {
       str.setTaskScheduler(taskScheduler()); 
    }
    @Bean
    @Inject
    public EventsPinger activeUserPinger(SimpMessagingTemplate template) {
      return new EventsPinger(template);
    }
    @Bean
    @Inject
    public HeartBeatPinger heartBeatPinger(SimpMessagingTemplate template) {
      return new HeartBeatPinger(template);
    }
    
}
