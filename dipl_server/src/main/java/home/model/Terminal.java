/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package home.model;

import java.io.Serializable;
import java.util.List;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
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
@Table(name = "terminal")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Terminal.findAll", query = "SELECT t FROM Terminal t"),
    @NamedQuery(name = "Terminal.findById", query = "SELECT t FROM Terminal t WHERE t.id = :id"),
    @NamedQuery(name = "Terminal.findByIsActive", query = "SELECT t FROM Terminal t WHERE t.isActive = :isActive"),
    @NamedQuery(name = "Terminal.findBySpecialCode", query = "SELECT t FROM Terminal t WHERE t.specialCode = :specialCode")})
public class Terminal implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    @Column(name = "is_active")
    private Boolean isActive;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 128)
    @Column(name = "special_code")
    private String specialCode;
    @ManyToMany(fetch = FetchType.EAGER,mappedBy = "terminalList",cascade = CascadeType.ALL)
    private List<StaffMember> staffMemberList;
    @JoinColumn(name = "terminal_data", referencedColumnName = "id")
    @ManyToOne
    private TerminalData terminalData;
    @OneToMany(mappedBy = "terminal")
    private List<Event> eventList;

    public Terminal() {
    }

    public Terminal(Integer id) {
        this.id = id;
    }

    public Terminal(Integer id, String specialCode) {
        this.id = id;
        this.specialCode = specialCode;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Boolean getIsActive() {
        return isActive;
    }

    public void setIsActive(Boolean isActive) {
        this.isActive = isActive;
    }


    public String getSpecialCode() {
        return specialCode;
    }

    public void setSpecialCode(String specialCode) {
        this.specialCode = specialCode;
    }

    @XmlTransient
    public List<StaffMember> getStaffMemberList() {
        return staffMemberList;
    }

    public void setStaffMemberList(List<StaffMember> staffMemberList) {
        this.staffMemberList = staffMemberList;
    }

    public TerminalData getTerminalData() {
        return terminalData;
    }

    public void setTerminalData(TerminalData terminalData) {
        this.terminalData = terminalData;
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
        if (!(object instanceof Terminal)) {
            return false;
        }
        Terminal other = (Terminal) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "home.model.Terminal[ id=" + id + " ]";
    }
    
}
