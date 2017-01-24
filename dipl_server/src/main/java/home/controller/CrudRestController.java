/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package home.controller;

import com.google.gson.JsonObject;
import home.EncryptionModule;
import home.InformationSecuity;
import home.SecuredData;
import home.dao.EventDao;
import home.dao.EventTypeDao;
import home.dao.StaffInfoDao;
import home.model.StaffMember;
import home.model.Terminal;
import home.dao.StaffMemberDao;
import home.dao.StaffPositionDao;
import home.dao.TerminalDao;
import home.dao.UserDao;
import home.dao.UserDataDao;
import home.model.Event;
import home.model.StaffInfo;
import home.model.User;
import java.security.PublicKey;
import java.util.Base64;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import home.dao.TerminalDataDao;
import home.model.EventType;
import java.nio.charset.StandardCharsets;
import java.security.KeyFactory;
import java.security.spec.X509EncodedKeySpec;
import java.util.Date;
import org.springframework.web.bind.annotation.RequestParam;


// rest controller for CRUD operations with DB entities
@RestController
@RequestMapping("/rest-public")
public class CrudRestController {
    
    // declare the autowired CRUD repositories for the entities
    @Autowired
    TerminalDao terminalDao;
    @Autowired
    StaffMemberDao staffMemberDao;
    @Autowired
    StaffInfoDao staffInfoDao;
    @Autowired
    StaffPositionDao staffPositionDao;
    @Autowired
    EventDao eventDao;
    @Autowired
    EventTypeDao eventTypeDao;
    @Autowired
    TerminalDataDao userAccessLevelDao;
    @Autowired
    UserDao userDao;
    @Autowired
    UserDataDao userDataDao;
    
    @ResponseStatus(HttpStatus.NOT_FOUND)
    class NotFoundException extends RuntimeException {

            public NotFoundException(String id) {
                    super("could not find the requested item '" + id + "'.");
            }
    }
    
    @ResponseStatus(HttpStatus.FORBIDDEN)
    class UnauthorizedException extends RuntimeException {

            public UnauthorizedException(String message) {
                    super("not allowed: "+message);
            }
    }
    
    
    
    //get the terminal by id
    @RequestMapping(value = "/terminals/get/{terminalId}", method = RequestMethod.GET)
    public Terminal getTerminalById(@PathVariable  int terminalId){
        Terminal terminal = terminalDao.findOne(terminalId);
        if(terminal!=null)
            return terminal;
        else throw new NotFoundException(""+terminalId);
    }
    
    //get the terminal by id
    @RequestMapping(value = "/terminals/get/all", method = RequestMethod.GET)
    public Iterable<Terminal> getTerminalAll(){
        Iterable<Terminal> it  = terminalDao.findAll();
        if(it!=null)
            return it;
        else throw new NotFoundException("");
    }
    
//    // delete the terminal by id
//    @RequestMapping(value = "/terminals/delete/{terminalId}", method = RequestMethod.DELETE)
//    public boolean deleteTerminalById(@PathVariable  int terminalId){
//        Terminal terminal = terminalDao.findOne(terminalId);
//        if(terminal!=null){
//            
//            for(Event event: terminal.getEventList()){
//                eventDao.delete(event);
//            }
//            for(StaffMember member:terminal.getStaffMemberList()){
//                member.getTerminalList().remove(terminal);
//                staffMemberDao.save(member);
//            }
//            
//            terminalDao.delete(terminal);
//            return true;
//        }
//        else throw new NotFoundException(""+terminalId);
//    }
//    
//    @RequestMapping(value = "/terminals/update/{terminalId}", method = RequestMethod.PUT)
//    public boolean updateTerminalById(@PathVariable  int terminalId,@RequestBody Terminal body){
//        Terminal terminal = terminalDao.findOne(terminalId);
//        if(terminal!=null){
//            if(body.getIsActive()!=null)
//                terminal.setIsActive(body.getIsActive());
//            if(body.getDescription()!=null)
//                terminal.setDescription(body.getDescription());
//            if(body.getSpecialCode()!=null){
//                terminal.setSpecialCode(body.getSpecialCode());
//            }
//            terminalDao.save(terminal);
//            return true;
//        }
//        else throw new NotFoundException(""+terminalId);
//    }
//    
//    @RequestMapping(value = "/terminals/add", method = RequestMethod.POST)
//    public boolean addTerminal(@RequestBody Terminal body){
//            terminalDao.save(body);
//            return true;
//    }
//    
    @RequestMapping(value = "/security/public_key",method = RequestMethod.GET)
    public String getPublicKey(){
        try {
            return Base64.getEncoder().encodeToString(EncryptionModule.getPublicKey().getEncoded());
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
        
    }
    @RequestMapping(value = "/event/permission_request",method = RequestMethod.POST)
    public String checkPermission(@RequestBody SecuredData encryptedData){
        try{
            System.out.println("data: "+encryptedData.toString());
        InformationSecuity secuity = new InformationSecuity();
        PublicKey publicKey = 
        KeyFactory.getInstance("RSA").generatePublic(new X509EncodedKeySpec(Base64.getDecoder().decode(encryptedData.assKey)));
        
        System.out.println("obj: "+encryptedData.toString());

        String[] value = secuity.decodeSecuredData(encryptedData).split(";");
        String terminalCode = value[0];
        String staffCode = value[1];
        System.out.println("terminal code: "+terminalCode);
        System.out.println("staff code: "+staffCode);
        
        
        Terminal terminal = null;
        Iterable<Terminal> t = terminalDao.findAll();
        for(Terminal term:t){
            if(term.getSpecialCode().equals(terminalCode)){
                terminal = term;
                break;
            }
        }
        StaffMember member= null;
        Iterable<StaffMember> t1 = staffMemberDao.findAll();
        for(StaffMember term:t1){
            if(term.getSpecialCode().equals(staffCode)){
                member = term;
                break;
            }
        }
        User possibleAdmin= null;
        Iterable<User> t2 = userDao.findAll();
        for(User term:t2){
            if(term.getSpecialCode().equals(staffCode)){
                possibleAdmin = term;
                break;
            }
        }
        
//        Terminal terminal = terminalDao.findBySpecialCode(terminalCode);
//        System.out.println("here 1");
//        StaffMember member = staffMemberDao.findBySpecialCode(staffCode);
//        System.out.println("here 2");
//        User possibleAdmin = null;// = userDao.findBySpecialCode(staffCode);
        
        JsonObject object = new JsonObject();
        
        
        if(terminal==null){
            System.out.println("terminal is null");
            saveEvent(getEventType(EventType.ERROR), null, null);
            throw new UnauthorizedException("terminal does not exist");
        }
        if(!terminal.getIsActive()){
            System.out.println("terminal is not active");
            saveEvent(getEventType(EventType.DENIED), null, terminal);
            throw new UnauthorizedException("Terminal is not active");
        }
        if(member==null&&possibleAdmin==null){
            System.out.println("user is null");
            saveEvent(getEventType(EventType.DENIED), null, terminal);
            throw new UnauthorizedException("Not exist");
        }
        if(possibleAdmin!=null){
            System.out.println("user is admin");
            saveEvent(getEventType(EventType.ADMIN), null, terminal);
            object.addProperty("accessStatus", "admin");
            return "admin";
        } 
        if(member==null){
            System.out.println("staff is null");
            saveEvent(getEventType(EventType.DENIED), null, terminal);
            throw new UnauthorizedException("Staff member does not exist");
        }
        
        System.out.println("here 3");
        object.addProperty("accessStatus", member.getIsActive()&&terminal.getStaffMemberList()!=null&&terminal.getStaffMemberList().contains(member)?"granted":"rejected");
        
        EventType type = null;
        switch(object.get("accessStatus").getAsString()){
            case "granted":
                type = getEventType(EventType.ALLOWED);
                break;
            case "rejected":
                type = getEventType(EventType.DENIED);
                break;
        }
        saveEvent(type, member, terminal);

        return object.get("accessStatus").getAsString(); 
        } catch(Exception ex){
            ex.printStackTrace();
            System.out.println("some error");
            return "rejected";
        }    
    }
    
    public EventType getEventType(String label){
        Iterable<EventType> types = eventTypeDao.findAll();
        for(EventType t:types){
            if(t.getType().equals(label)) return t;
        }
        return  null;
    }
    
    private void saveEvent(EventType type,StaffMember member,Terminal terminal){
//        System.out.println("Type: "+type!=null?type.getType():"");
//        System.out.println("Member: "+member!=null?member.getSpecialCode():"");
//        System.out.println("Terminal: "+terminal!=null?terminal.getSpecialCode():"");
        Event event = new Event();
        event.setCreationDate(new Date(System.currentTimeMillis()));
        if(type!=null)event.setEventType(type);
        event.setTerminal(terminal);
        event.setStaffMember(member);
        eventDao.save(event);
    }
    
    @RequestMapping(value = "/terminal/setup_request")
    public Boolean checkTerminalSetupPermission(@RequestParam(name = "data",defaultValue = "") String encryptedData) throws Exception{
        String[] value = new String(EncryptionModule.decrypt(Base64.getDecoder().decode(encryptedData))).split(";");
        String terminalCode = value[0];
        String adminCode = value[1];
        
        Terminal terminal = terminalDao.findBySpecialCode(terminalCode);
        User user = userDao.findBySpecialCode(adminCode);
        
        if(terminal==null||user==null){
            throw new UnauthorizedException("not exist");
        }
        
        return true; 
    }
    
}