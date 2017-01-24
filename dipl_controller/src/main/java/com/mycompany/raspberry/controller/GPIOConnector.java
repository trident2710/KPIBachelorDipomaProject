/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.raspberry.controller;

import static java.lang.Thread.sleep;

/**
 *
 * @author trident
 */
public class GPIOConnector {
    
    public static void prepare(){
        try {
            Runtime rt = Runtime.getRuntime();
            Process pr = rt.exec("gpio mode 0 out");
            pr = rt.exec("gpio mode 1 out");
            pr = rt.exec("gpio mode 3 out");
        } catch (Exception e) {
            System.out.println("Exception in prepare");
            e.printStackTrace();
        }
        
    }
    
    public static void waiting(){
        try {
            Runtime rt = Runtime.getRuntime();
            Process pr = rt.exec("gpio write 3 1"); //yellow
            pr = rt.exec("gpio write 1 0"); //green 
            pr = rt.exec("gpio write 0 0"); //red
        } catch (Exception e) {
            System.out.println("Exception in waiting");
            e.printStackTrace();
        }
        
    }
    
    public static void allowAccess(){
        try {
            Thread t  = new Thread(){
                @Override
                public void run(){
                    try {
                        Runtime rt = Runtime.getRuntime();
                        Process pr = rt.exec("gpio write 3 0"); //yellow
                        pr = rt.exec("gpio write 1 1"); //green 
                        pr = rt.exec("gpio write 0 0"); //red
                        System.out.println("waiting");
                        Thread.sleep(5000);
                        System.out.println("restore");
                        waiting();
                    } catch (Exception e) {
                        e.printStackTrace();
                        System.out.println("Exception in allow thread");
                    }
                    
                }
            };
            t.start();
        } catch (Exception e) {
            System.out.println("Exception in allow");
            e.printStackTrace();
        }
        
    }
    
    public static void rejectAccess(){
        try {
            Thread t  = new Thread(){
                @Override
                public void run(){
                    try {
                        Runtime rt = Runtime.getRuntime();
                        Process pr = rt.exec("gpio write 3 0"); //yellow
                        pr = rt.exec("gpio write 1 0"); //green 
                        pr = rt.exec("gpio write 0 1"); //red
                        System.out.println("waiting");
                        Thread.sleep(5000);
                        System.out.println("restore");
                        waiting();
                    } catch (Exception e) {
                        e.printStackTrace();
                        System.out.println("Exception in reject thread");
                    }
                    
                }
            };
            t.start();
        } catch (Exception e) {
            System.out.println("Exception in reject");
            e.printStackTrace();
        }
        
    }
    
}
