/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package JPA.Entidades;

import java.io.Serializable;
import java.util.Collection;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
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
@Table(name = "lenguaje")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Lenguaje.findAll", query = "SELECT l FROM Lenguaje l")})
public class Lenguaje implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 5)
    @Column(name = "len_lenguaje")
    private String lenLenguaje;
    @Size(max = 45)
    @Column(name = "len_nombre")
    private String lenNombre;
    @ManyToMany(mappedBy = "lenguajeCollection")
    private Collection<Empleado> empleadoCollection;

    public Lenguaje() {
    }

    public Lenguaje(String lenLenguaje) {
        this.lenLenguaje = lenLenguaje;
    }

    public String getLenLenguaje() {
        return lenLenguaje;
    }

    public void setLenLenguaje(String lenLenguaje) {
        this.lenLenguaje = lenLenguaje;
    }

    public String getLenNombre() {
        return lenNombre;
    }

    public void setLenNombre(String lenNombre) {
        this.lenNombre = lenNombre;
    }

    @XmlTransient
    public Collection<Empleado> getEmpleadoCollection() {
        return empleadoCollection;
    }

    public void setEmpleadoCollection(Collection<Empleado> empleadoCollection) {
        this.empleadoCollection = empleadoCollection;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (lenLenguaje != null ? lenLenguaje.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Lenguaje)) {
            return false;
        }
        Lenguaje other = (Lenguaje) object;
        if ((this.lenLenguaje == null && other.lenLenguaje != null) || (this.lenLenguaje != null && !this.lenLenguaje.equals(other.lenLenguaje))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "JPA.Entidades.Lenguaje[ lenLenguaje=" + lenLenguaje + " ]";
    }
    
}
