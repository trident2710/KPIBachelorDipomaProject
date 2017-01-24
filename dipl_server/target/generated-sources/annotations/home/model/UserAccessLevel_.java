package home.model;

import home.model.Terminal;
import home.model.User;
import javax.annotation.Generated;
import javax.persistence.metamodel.ListAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="EclipseLink-2.5.2.v20140319-rNA", date="2016-05-05T22:14:15")
@StaticMetamodel(UserAccessLevel.class)
public class UserAccessLevel_ { 

    public static volatile ListAttribute<UserAccessLevel, User> userList;
    public static volatile SingularAttribute<UserAccessLevel, Integer> idAccessLevel;
    public static volatile SingularAttribute<UserAccessLevel, String> name;
    public static volatile ListAttribute<UserAccessLevel, Terminal> terminalList;

}