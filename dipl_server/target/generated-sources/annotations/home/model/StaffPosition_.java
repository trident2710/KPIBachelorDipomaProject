package home.model;

import home.model.StaffMember;
import javax.annotation.Generated;
import javax.persistence.metamodel.ListAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="EclipseLink-2.5.2.v20140319-rNA", date="2016-05-27T19:10:02")
@StaticMetamodel(StaffPosition.class)
public class StaffPosition_ { 

    public static volatile ListAttribute<StaffPosition, StaffMember> staffMemberList;
    public static volatile SingularAttribute<StaffPosition, Integer> id;
    public static volatile SingularAttribute<StaffPosition, String> position;

}