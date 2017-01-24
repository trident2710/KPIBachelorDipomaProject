/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package home;

import home.dao.EventDao;
import home.dao.EventTypeDao;
import home.dao.StaffInfoDao;
import home.dao.StaffMemberDao;
import home.dao.StaffPositionDao;
import home.dao.TerminalDao;
import home.dao.UserDao;
import home.dao.UserDataDao;
import home.model.EventType;
import home.model.StaffInfo;
import home.model.StaffMember;
import home.model.StaffPosition;
import home.model.Terminal;
import home.model.User;
import home.model.UserData;
import java.math.BigInteger;
import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Random;
import javax.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.stereotype.Service;
import home.dao.TerminalDataDao;
import home.model.TerminalData;

/**
 *
 * @author trident
 */
@Service
@Configurable
public class DBFiller {
    
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
    TerminalDataDao terminalDataDao;
    @Autowired
    UserDao userDao;
    @Autowired
    UserDataDao userDataDao;
    
    
    public DBFiller(){
        
    }
    
    public void createEventTypes(){
        EventType type = new EventType();
        type.setType("Access Allowed");
        eventTypeDao.save(type);
        
        type = new EventType();
        type.setType("Access Denied");
        eventTypeDao.save(type);
        
        type = new EventType();
        type.setType("Access Error");
        eventTypeDao.save(type);
        
        type = new EventType();
        type.setType("Access Admin");
        eventTypeDao.save(type);
        
        type = new EventType();
        type.setType("Dangerous Behaviour");
        eventTypeDao.save(type); 
    }
    
    public void createStaffPositions(){
        StaffPosition position = new StaffPosition();
        position.setPosition("Labourer");
        staffPositionDao.save(position);
        
        position = new StaffPosition();
        position.setPosition("Manager");
        staffPositionDao.save(position);
        
        position = new StaffPosition();
        position.setPosition("Administrator");
        staffPositionDao.save(position);
    }
    
   
    
    
    public void createRandomUserData(){
       
        UserData data = new UserData();
        data.setAge(21);
        data.setName("Andrew");
        data.setSurname("Dychka");
        userDataDao.save(data);
        
        data = new UserData();
        data.setAge(34);
        data.setName("Peter");
        data.setSurname("Morrison");
        userDataDao.save(data);
        
        data = new UserData();
        data.setAge(27);
        data.setName("Shon");
        data.setSurname("Stein");
        userDataDao.save(data);

    }
    
    public void createUsers(){
       
        ArrayList<UserData> infos = makeCollection(userDataDao.findAll());
        Random r = new Random();
        int i = 0;
        System.out.println("data size = "+infos.size());
        for(UserData d:infos){
            User user = new User();
            user.setUserData(d);
            user.setLogin("test"+(++i)+"@diploma.com");
            user.setPassword("password"+i);
            user.setSpecialCode(generateRandomCode());
            userDao.save(user);
        }
        
    }
    
    public void createTerminal(){
        Terminal terminal = new Terminal();
        TerminalData data  = new TerminalData();
        data.setName("Terminal 1");
        data.setDescription("Main Terminal");
        data.setLocation("KPI building 14, hall");
        terminalDataDao.save(data);
        terminal.setTerminalData(data);
        terminal.setIsActive(true);
        terminal.setSpecialCode(generateRandomCode());
        terminalDao.save(terminal);
        
        terminal = new Terminal();
        data  = new TerminalData();
        data.setName("Terminal 2");
        data.setDescription("Secondary Terminal");
        data.setLocation("KPI building 15, 3th floor");
        terminalDataDao.save(data);
        terminal.setTerminalData(data);
        terminal.setIsActive(true);
        terminal.setSpecialCode(generateRandomCode());
        terminalDao.save(terminal);
    }
    
    
    public void createRandomStaffInfo(){
        
        String[] surnames = new String[]{"Cena","Watson","Lewis","Clark","Petrenko","Gustaffson","Shevchenko","Mykolaychuk"};
        String[] names = new String[]{"John","Carlie","Andriana","Peter","George","Tanya","Mykola","Tamara"};
        Random r = new Random();
        for(int i=0;i<30;i++){
            StaffInfo info = new StaffInfo();
            info.setName(names[r.nextInt(8)]);
            info.setSurname(surnames[r.nextInt(8)]);
            info.setAge(""+(18+r.nextInt(20)));
           
            staffInfoDao.save(info);
        }
    }
    
    public void createRandomStaffMembers(){
        ArrayList<StaffInfo> infos = makeCollection(staffInfoDao.findAll());
        ArrayList<StaffPosition> positions = makeCollection(staffPositionDao.findAll());
        Random random = new Random();
        for(StaffInfo i:infos){
            StaffMember m = new StaffMember();
            m.setIsActive(true);
            m.setSpecialCode(generateRandomCode());
            m.setStaffInfo(i);
            m.setStaffPosition(positions.get(random.nextInt(positions.size())));
            staffMemberDao.save(m);
        }
    }
    
    private static <E> ArrayList<E> makeCollection(Iterable<E> iter) {
        ArrayList<E> list = new ArrayList<E>();
        for (E item : iter) {
            list.add(item);
        }
        return list;
    }
    
    private String generateRandomCode(){
        SecureRandom random = new SecureRandom();
        return new BigInteger(130, random).toString(32);
    }
    
    
    private void addStaffMembersToTerminal(){
        ArrayList<StaffMember> members = makeCollection(staffMemberDao.findAll());
        Random random = new Random();
        int count  = 5+random.nextInt(members.size()-5);
        Terminal terminal = makeCollection(terminalDao.findAll()).get(0);
        for(int i = 0;i<count;i++){
            try{
                StaffMember member = members.get(random.nextInt(members.size()));
                member.getTerminalList().add(terminal);
                staffMemberDao.save(member); 
            } catch(Exception ex){ex.printStackTrace();}

            
        }
        terminalDao.save(terminal);
        
        Terminal terminal1 = terminalDao.findOne(1);
        Terminal terminal2 = terminalDao.findOne(2);
        StaffMember member = terminal1.getStaffMemberList().get(0);
        member.getTerminalList().add(terminal2);
        staffMemberDao.save(member);
        
        member = terminal1.getStaffMemberList().get(1);
        member.getTerminalList().add(terminal2);
        staffMemberDao.save(member);
        
        terminalDao.save(terminal2);
    }
    
   
    //@PostConstruct 
    public void generateRandomData(){
        
        
        try {
            System.out.println("\nCreate Event types");
            createEventTypes();
            System.out.println("Event types successfuly created");
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Event types were not created");
        }
        
        try {
            System.out.println("\nCreate Staff positions");
            createStaffPositions();
            System.out.println("Staff positions successfuly created");
        } catch (Exception e) {
            System.out.println("Staff positions were not created");
        }
        
       
        
        try {
            System.out.println("\nCreate User datas");
            createRandomUserData();
            System.out.println("User datas successfuly created");
        } catch (Exception e) {
            System.out.println("User datas were not created");
        }
        
        try {
            System.out.println("\nCreate Users");
            createUsers();
            System.out.println("Users successfuly created");
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Users were not created");
        }
        
        try {
            System.out.println("\nCreate Terminal");
            createTerminal();
            System.out.println("Terminal successfuly created");
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Terminal was not created");
        }
        
        try {
            System.out.println("\nCreate Staff infos");
            createRandomStaffInfo();
            System.out.println("Staff infos successfuly created");
        } catch (Exception e) {
            System.out.println("Staff infos were not created");
        }
        
        
        try {
            System.out.println("\nCreate Staff members");
            createRandomStaffMembers();
            System.out.println("Staff members successfuly created");
        } catch (Exception e) {
            System.out.println("Staff members were not created");
        }
        
         try {
            System.out.println("\nAdd staff members to terminal");
            addStaffMembersToTerminal();
            System.out.println("Staff members successfuly added");
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Staff members were not added");
        }
    }
    
    

    
    
}
