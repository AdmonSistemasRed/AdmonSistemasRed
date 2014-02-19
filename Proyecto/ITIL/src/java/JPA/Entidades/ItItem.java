/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package JPA.Entidades;

import java.io.Serializable;
import java.util.Collection;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author madman
 */
@Entity
@Table(name = "it_item")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "ItItem.findAll", query = "SELECT i FROM ItItem i")})
public class ItItem implements Serializable {
    private static final long serialVersionUID = 1L;
    @EmbeddedId
    protected ItItemPK itItemPK;
    @Size(max = 45)
    @Column(name = "modelo")
    private String modelo;
    @JoinTable(name = "it_item_has_empleado", joinColumns = {
        @JoinColumn(name = "IT_item_it_serie", referencedColumnName = "it_serie"),
        @JoinColumn(name = "IT_item_it_marca", referencedColumnName = "it_marca")}, inverseJoinColumns = {
        @JoinColumn(name = "empleado_emp_NoEmpleado", referencedColumnName = "emp_NoEmpleado")})
    @ManyToMany
    private Collection<Empleado> empleadoCollection;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "itItem")
    private Collection<Servidor> servidorCollection;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "itItem")
    private Collection<Software> softwareCollection;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "itItem")
    private Collection<Telecommunications> telecommunicationsCollection;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "itItem")
    private Collection<Perifericos> perifericosCollection;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "itItem")
    private Collection<Laptops> laptopsCollection;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "itItem")
    private Collection<Workstation> workstationCollection;

    public ItItem() {
    }

    public ItItem(ItItemPK itItemPK) {
        this.itItemPK = itItemPK;
    }

    public ItItem(String itSerie, String itMarca) {
        this.itItemPK = new ItItemPK(itSerie, itMarca);
    }

    public ItItemPK getItItemPK() {
        return itItemPK;
    }

    public void setItItemPK(ItItemPK itItemPK) {
        this.itItemPK = itItemPK;
    }

    public String getModelo() {
        return modelo;
    }

    public void setModelo(String modelo) {
        this.modelo = modelo;
    }

    @XmlTransient
    public Collection<Empleado> getEmpleadoCollection() {
        return empleadoCollection;
    }

    public void setEmpleadoCollection(Collection<Empleado> empleadoCollection) {
        this.empleadoCollection = empleadoCollection;
    }

    @XmlTransient
    public Collection<Servidor> getServidorCollection() {
        return servidorCollection;
    }

    public void setServidorCollection(Collection<Servidor> servidorCollection) {
        this.servidorCollection = servidorCollection;
    }

    @XmlTransient
    public Collection<Software> getSoftwareCollection() {
        return softwareCollection;
    }

    public void setSoftwareCollection(Collection<Software> softwareCollection) {
        this.softwareCollection = softwareCollection;
    }

    @XmlTransient
    public Collection<Telecommunications> getTelecommunicationsCollection() {
        return telecommunicationsCollection;
    }

    public void setTelecommunicationsCollection(Collection<Telecommunications> telecommunicationsCollection) {
        this.telecommunicationsCollection = telecommunicationsCollection;
    }

    @XmlTransient
    public Collection<Perifericos> getPerifericosCollection() {
        return perifericosCollection;
    }

    public void setPerifericosCollection(Collection<Perifericos> perifericosCollection) {
        this.perifericosCollection = perifericosCollection;
    }

    @XmlTransient
    public Collection<Laptops> getLaptopsCollection() {
        return laptopsCollection;
    }

    public void setLaptopsCollection(Collection<Laptops> laptopsCollection) {
        this.laptopsCollection = laptopsCollection;
    }

    @XmlTransient
    public Collection<Workstation> getWorkstationCollection() {
        return workstationCollection;
    }

    public void setWorkstationCollection(Collection<Workstation> workstationCollection) {
        this.workstationCollection = workstationCollection;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (itItemPK != null ? itItemPK.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof ItItem)) {
            return false;
        }
        ItItem other = (ItItem) object;
        if ((this.itItemPK == null && other.itItemPK != null) || (this.itItemPK != null && !this.itItemPK.equals(other.itItemPK))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "JPA.Entidades.ItItem[ itItemPK=" + itItemPK + " ]";
    }
    
}
