/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package home.controller;

import home.dao.StaffMemberDao;
import home.dao.TerminalDao;
import home.model.Terminal;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 *
 * @author trident
 */
@Controller
public class ViewController {
    @Autowired
    TerminalDao terminalDao;
    @Autowired
    StaffMemberDao staffMemberDao;
    
    
    @RequestMapping("/terminals")
    public String renderTerminalsPage(Model model) {
        model.addAttribute("terminalsList", terminalDao.findAll());
        return "terminals";
    }
    @RequestMapping("/terminals/{id}")
    public String renderTerminalPage(@PathVariable Integer id,Model model) {
        model.addAttribute("terminal",terminalDao.findOne(id));
        return "terminal";
    }
    @RequestMapping("/staff")
    public String renderStaffListPage(Model model) {
        model.addAttribute("staffList", staffMemberDao.findAll());
        return "staff_list";
    }
    @RequestMapping("/staff/{id}")
    public String renderStaffPage(@PathVariable Integer id,Model model) {   
        model.addAttribute("staff",staffMemberDao.findOne(id));
        return "staff";
    }
}
