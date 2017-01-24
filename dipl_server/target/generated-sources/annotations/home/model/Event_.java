package home.model;

import home.model.EventType;
import home.model.StaffMember;
import home.model.Terminal;
import java.util.Date;
import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="EclipseLink-2.5.2.v20140319-rNA", date="2016-05-27T19:10:02")
@StaticMetamodel(Event.class)
public class Event_ { 

    public static volatile SingularAttribute<Event, StaffMember> staffMember;
    public static volatile SingularAttribute<Event, Integer> id;
    public static volatile SingularAttribute<Event, EventType> eventType;
    public static volatile SingularAttribute<Event, Terminal> terminal;
    public static volatile SingularAttribute<Event, Date> creationDate;

}