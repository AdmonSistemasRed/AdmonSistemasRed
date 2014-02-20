/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package JPA.Entidades;

import java.io.Serializable;
import java.util.Collection;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
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
 * @author madman
 */
@Entity
@Table(name = "it_item")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "ItItem.findAll", query = "SELECT i FROM ItItem i"),
    @NamedQuery(name = "ItItem.findByItSerie", query = "SELECT i FROM ItItem i WHERE i.itSerie = :itSerie")})
public class ItItem implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 30)
    @Column(name = "it_serie")
    private String itSerie;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "itItem")
    private Collection<ItItemHasEmpleado> itItemHasEmpleadoCollection;
    @JoinColumn(name = "Computadora_idComputadora", referencedColumnName = "idComputadora")
    @ManyToOne(optional = false)
    private Computadora computadoraidComputadora;
    @JoinColumn(name = "Telecommunications_idTelecom", referencedColumnName = "idTelecom")
    @ManyToOne(optional = false)
    private Telecommunications telecommunicationsidTelecom;
    @JoinColumn(name = "Perifericos_idPeriferico", referencedColumnName = "idPeriferico")
    @ManyToOne(optional = false)
    private Perifericos perifericosidPeriferico;

    public ItItem() {
    }

    public ItItem(String itSerie) {
        this.itSerie = itSerie;
    }

    public String getItSerie() {
        return itSerie;
    }

    public void setItSerie(String itSerie) {
        this.itSerie = itSerie;
    }

    @XmlTransient
    public Collection<ItItemHasEmpleado> getItItemHasEmpleadoCollection() {
        return itItemHasEmpleadoCollection;
    }

    public void setItItemHasEmpleadoCollection(Collection<ItItemHasEmpleado> itItemHasEmpleadoCollection) {
        this.itItemHasEmpleadoCollection = itItemHasEmpleadoCollection;
    }

    public Computadora getComputadoraidComputadora() {
        return computadoraidComputadora;
    }

    public void setComputadoraidComputadora(Computadora computadoraidComputadora) {
        this.computadoraidComputadora = computadoraidComputadora;
    }

    public Telecommunications getTelecommunicationsidTelecom() {
        return telecommunicationsidTelecom;
    }

    public void setTelecommunicationsidTelecom(Telecommunications telecommunicationsidTelecom) {
        this.telecommunicationsidTelecom = telecommunicationsidTelecom;
    }

    public Perifericos getPerifericosidPeriferico() {
        return perifericosidPeriferico;
    }

    public void setPerifericosidPeriferico(Perifericos perifericosidPeriferico) {
        this.perifericosidPeriferico = perifericosidPeriferico;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (itSerie != null ? itSerie.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof ItItem)) {
            return false;
        }
        ItItem other = (ItItem) object;
        if ((this.itSerie == null && other.itSerie != null) || (this.itSerie != null && !this.itSerie.equals(other.itSerie))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Entidades.ItItem[ itSerie=" + itSerie + " ]";
    }
    
}
