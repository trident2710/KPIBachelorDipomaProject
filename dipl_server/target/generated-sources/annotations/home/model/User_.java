package home.model;

import home.model.UserData;
import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="EclipseLink-2.5.2.v20140319-rNA", date="2016-05-27T19:10:02")
@StaticMetamodel(User.class)
public class User_ { 

    public static volatile SingularAttribute<User, String> password;
    public static volatile SingularAttribute<User, UserData> userData;
    public static volatile SingularAttribute<User, Integer> id;
    public static volatile SingularAttribute<User, String> login;
    public static volatile SingularAttribute<User, String> specialCode;

}