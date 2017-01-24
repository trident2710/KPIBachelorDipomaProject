package home.model;

import home.model.User;
import javax.annotation.Generated;
import javax.persistence.metamodel.ListAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="EclipseLink-2.5.2.v20140319-rNA", date="2016-05-27T19:10:02")
@StaticMetamodel(UserData.class)
public class UserData_ { 

    public static volatile ListAttribute<UserData, User> userList;
    public static volatile SingularAttribute<UserData, String> surname;
    public static volatile SingularAttribute<UserData, String> name;
    public static volatile SingularAttribute<UserData, Integer> id;
    public static volatile SingularAttribute<UserData, Integer> age;

}