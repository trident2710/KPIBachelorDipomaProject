package home.model;

import home.model.Event;
import home.model.StaffMember;
import home.model.TerminalData;
import javax.annotation.Generated;
import javax.persistence.metamodel.ListAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="EclipseLink-2.5.2.v20140319-rNA", date="2016-05-27T19:10:02")
@StaticMetamodel(Terminal.class)
public class Terminal_ { 

    public static volatile ListAttribute<Terminal, StaffMember> staffMemberList;
    public static volatile ListAttribute<Terminal, Event> eventList;
    public static volatile SingularAttribute<Terminal, TerminalData> terminalData;
    public static volatile SingularAttribute<Terminal, Integer> id;
    public static volatile SingularAttribute<Terminal, Boolean> isActive;
    public static volatile SingularAttribute<Terminal, String> specialCode;

}