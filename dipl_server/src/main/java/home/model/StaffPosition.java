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
@Table(name = "staff_position")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "StaffPosition.findAll", query = "SELECT s FROM StaffPosition s"),
    @NamedQuery(name = "StaffPosition.findById", query = "SELECT s FROM StaffPosition s WHERE s.id = :id"),
    @NamedQuery(name = "StaffPosition.findByPosition", query = "SELECT s FROM StaffPosition s WHERE s.position = :position")})
public class StaffPosition implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    @Size(max = 45)
    @Column(name = "position")
    private String position;
    @OneToMany(mappedBy = "staffPosition")
    private List<StaffMember> staffMemberList;

    public StaffPosition() {
    }

    public StaffPosition(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getPosition() {
        return position;
    }

    public void setPosition(String position) {
        this.position = position;
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
        if (!(object instanceof StaffPosition)) {
            return false;
        }
        StaffPosition other = (StaffPosition) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "home.model.StaffPosition[ id=" + id + " ]";
    }
    
}
