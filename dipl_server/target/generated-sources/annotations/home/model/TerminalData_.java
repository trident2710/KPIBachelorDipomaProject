package home.model;

import home.model.Terminal;
import javax.annotation.Generated;
import javax.persistence.metamodel.ListAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="EclipseLink-2.5.2.v20140319-rNA", date="2016-05-27T19:10:02")
@StaticMetamodel(TerminalData.class)
public class TerminalData_ { 

    public static volatile SingularAttribute<TerminalData, String> name;
    public static volatile SingularAttribute<TerminalData, String> description;
    public static volatile SingularAttribute<TerminalData, String> location;
    public static volatile SingularAttribute<TerminalData, Integer> id;
    public static volatile ListAttribute<TerminalData, Terminal> terminalList;

}