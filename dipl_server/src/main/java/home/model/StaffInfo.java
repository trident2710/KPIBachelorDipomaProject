/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package home.model;

import java.io.Serializable;
import java.util.List;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author trident
 */
@Entity
@Table(name = "staff_info")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "StaffInfo.findAll", query = "SELECT s FROM StaffInfo s"),
    @NamedQuery(name = "StaffInfo.findById", query = "SELECT s FROM StaffInfo s WHERE s.id = :id"),
    @NamedQuery(name = "StaffInfo.findByName", query = "SELECT s FROM StaffInfo s WHERE s.name = :name"),
    @NamedQuery(name = "StaffInfo.findBySurname", query = "SELECT s FROM StaffInfo s WHERE s.surname = :surname"),
    @NamedQuery(name = "StaffInfo.findByAge", query = "SELECT s FROM StaffInfo s WHERE s.age = :age")})
public class StaffInfo implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    @Size(max = 45)
    @Column(name = "name")
    private String name;
    @Size(max = 45)
    @Column(name = "surname")
    private String surname;
    @Size(max = 45)
    @Column(name = "age")
    private String age;
    @OneToMany(mappedBy = "staffInfo")
    private List<StaffMember> staffMemberList;
    
    
    public String getFullName(){
        return name+" "+surname;
    }

    public StaffInfo() {
    }

    public StaffInfo(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public String getAge() {
        return age;
    }

    public void setAge(String age) {
        this.age = age;
    }

    @XmlTransient
    public List<StaffMember> getStaffMemberList() {
        return staffMemberList;
    }

    public void setStaffMemberList(List<StaffMember> staffMemberList) {
        this.staffMemberList = staffMemberList;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof StaffInfo)) {
            return false;
        }
        StaffInfo other = (StaffInfo) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "home.model.StaffInfo[ id=" + id + " ]";
    }
    
}
