package home.model;

import home.model.Event;
import home.model.StaffInfo;
import home.model.StaffPosition;
import home.model.Terminal;
import javax.annotation.Generated;
import javax.persistence.metamodel.ListAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="EclipseLink-2.5.2.v20140319-rNA", date="2016-05-27T19:10:02")
@StaticMetamodel(StaffMember.class)
public class StaffMember_ { 

    public static volatile ListAttribute<StaffMember, Event> eventList;
    public static volatile SingularAttribute<StaffMember, Integer> id;
    public static volatile SingularAttribute<StaffMember, Boolean> isActive;
    public static volatile ListAttribute<StaffMember, Terminal> terminalList;
    public static volatile SingularAttribute<StaffMember, String> specialCode;
    public static volatile SingularAttribute<StaffMember, StaffInfo> staffInfo;
    public static volatile SingularAttribute<StaffMember, StaffPosition> staffPosition;

}