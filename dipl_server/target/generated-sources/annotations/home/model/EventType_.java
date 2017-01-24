package home.model;

import home.model.Event;
import javax.annotation.Generated;
import javax.persistence.metamodel.ListAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="EclipseLink-2.5.2.v20140319-rNA", date="2016-05-27T19:10:02")
@StaticMetamodel(EventType.class)
public class EventType_ { 

    public static volatile ListAttribute<EventType, Event> eventList;
    public static volatile SingularAttribute<EventType, Integer> id;
    public static volatile SingularAttribute<EventType, String> type;

}