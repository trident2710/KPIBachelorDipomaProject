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
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author trident
 */
@Entity
@Table(name = "staff_member")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "StaffMember.findAll", query = "SELECT s FROM StaffMember s"),
    @NamedQuery(name = "StaffMember.findById", query = "SELECT s FROM StaffMember s WHERE s.id = :id"),
    @NamedQuery(name = "StaffMember.findBySpecialCode", query = "SELECT s FROM StaffMember s WHERE s.specialCode = :specialCode"),
    @NamedQuery(name = "StaffMember.findByIsActive", query = "SELECT s FROM StaffMember s WHERE s.isActive = :isActive")})
public class StaffMember implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 128)
    @Column(name = "special_code")
    private String specialCode;
    @Column(name = "is_active")
    private Boolean isActive;
    @JoinTable(name = "terminal_staff_access", joinColumns = {
        @JoinColumn(name = "id_staff_member", referencedColumnName = "id")}, inverseJoinColumns = {
        @JoinColumn(name = "id_terminal", referencedColumnName = "id")})
    @ManyToMany(fetch = FetchType.EAGER)
    private List<Terminal> terminalList;
    @JoinColumn(name = "staff_info", referencedColumnName = "id")
    @ManyToOne
    private StaffInfo staffInfo;
    @JoinColumn(name = "staff_position", referencedColumnName = "id")
    @ManyToOne
    private StaffPosition staffPosition;
    @OneToMany(mappedBy = "staffMember")
    private List<Event> eventList;

    public StaffMember() {
    }

    public StaffMember(Integer id) {
        this.id = id;
    }

    public StaffMember(Integer id, String specialCode) {
        this.id = id;
        this.specialCode = specialCode;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getSpecialCode() {
        return specialCode;
    }

    public void setSpecialCode(String specialCode) {
        this.specialCode = specialCode;
    }

    public Boolean getIsActive() {
        return isActive;
    }

    public void setIsActive(Boolean isActive) {
        this.isActive = isActive;
    }

    @XmlTransient
    public List<Terminal> getTerminalList() {
        return terminalList;
    }

    public void setTerminalList(List<Terminal> terminalList) {
        this.terminalList = terminalList;
    }

    public StaffInfo getStaffInfo() {
        return staffInfo;
    }

    public void setStaffInfo(StaffInfo staffInfo) {
        this.staffInfo = staffInfo;
    }

    public StaffPosition getStaffPosition() {
        return staffPosition;
    }

    public void setStaffPosition(StaffPosition staffPosition) {
        this.staffPosition = staffPosition;
    }

    @XmlTransient
    public List<Event> getEventList() {
        return eventList;
    }

    public void setEventList(List<Event> eventList) {
        this.eventList = eventList;
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
        if (!(object instanceof StaffMember)) {
            return false;
        }
        StaffMember other = (StaffMember) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "home.model.StaffMember[ id=" + id + " ]";
    }
    
}
