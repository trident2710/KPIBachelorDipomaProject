package home.model;

import home.model.StaffMember;
import javax.annotation.Generated;
import javax.persistence.metamodel.ListAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="EclipseLink-2.5.2.v20140319-rNA", date="2016-05-27T19:10:02")
@StaticMetamodel(StaffInfo.class)
public class StaffInfo_ { 

    public static volatile ListAttribute<StaffInfo, StaffMember> staffMemberList;
    public static volatile SingularAttribute<StaffInfo, String> surname;
    public static volatile SingularAttribute<StaffInfo, String> name;
    public static volatile SingularAttribute<StaffInfo, Integer> id;
    public static volatile SingularAttribute<StaffInfo, String> age;

}