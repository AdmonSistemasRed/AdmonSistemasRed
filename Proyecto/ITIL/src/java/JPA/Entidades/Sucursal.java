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
@Table(name = "sucursal")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Sucursal.findAll", query = "SELECT s FROM Sucursal s"),
    @NamedQuery(name = "Sucursal.findBySucursalidSucursal", query = "SELECT s FROM Sucursal s WHERE s.sucursalidSucursal = :sucursalidSucursal"),
    @NamedQuery(name = "Sucursal.findBySucursalNombre", query = "SELECT s FROM Sucursal s WHERE s.sucursalNombre = :sucursalNombre"),
    @NamedQuery(name = "Sucursal.findBySucursalUbicacion", query = "SELECT s FROM Sucursal s WHERE s.sucursalUbicacion = :sucursalUbicacion"),
    @NamedQuery(name = "Sucursal.findBySucursalObservacion", query = "SELECT s FROM Sucursal s WHERE s.sucursalObservacion = :sucursalObservacion"),
    @NamedQuery(name = "Sucursal.findBySucursalcol", query = "SELECT s FROM Sucursal s WHERE s.sucursalcol = :sucursalcol")})
public class Sucursal implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "Sucursal_idSucursal")
    private Integer sucursalidSucursal;
    @Size(max = 90)
    @Column(name = "Sucursal_Nombre")
    private String sucursalNombre;
    @Size(max = 50)
    @Column(name = "Sucursal_Ubicacion")
    private String sucursalUbicacion;
    @Size(max = 100)
    @Column(name = "Sucursal_Observacion")
    private String sucursalObservacion;
    @Size(max = 45)
    @Column(name = "Sucursalcol")
    private String sucursalcol;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "sucursalSucursalidSucursal")
    private Collection<Empleado> empleadoCollection;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "sucursalSucursalidSucursal")
    private Collection<Area> areaCollection;

    public Sucursal() {
    }

    public Sucursal(Integer sucursalidSucursal) {
        this.sucursalidSucursal = sucursalidSucursal;
    }

    public Integer getSucursalidSucursal() {
        return sucursalidSucursal;
    }

    public void setSucursalidSucursal(Integer sucursalidSucursal) {
        this.sucursalidSucursal = sucursalidSucursal;
    }

    public String getSucursalNombre() {
        return sucursalNombre;
    }

    public void setSucursalNombre(String sucursalNombre) {
        this.sucursalNombre = sucursalNombre;
    }

    public String getSucursalUbicacion() {
        return sucursalUbicacion;
    }

    public void setSucursalUbicacion(String sucursalUbicacion) {
        this.sucursalUbicacion = sucursalUbicacion;
    }

    public String getSucursalObservacion() {
        return sucursalObservacion;
    }

    public void setSucursalObservacion(String sucursalObservacion) {
        this.sucursalObservacion = sucursalObservacion;
    }

    public String getSucursalcol() {
        return sucursalcol;
    }

    public void setSucursalcol(String sucursalcol) {
        this.sucursalcol = sucursalcol;
    }

    @XmlTransient
    public Collection<Empleado> getEmpleadoCollection() {
        return empleadoCollection;
    }

    public void setEmpleadoCollection(Collection<Empleado> empleadoCollection) {
        this.empleadoCollection = empleadoCollection;
    }

    @XmlTransient
    public Collection<Area> getAreaCollection() {
        return areaCollection;
    }

    public void setAreaCollection(Collection<Area> areaCollection) {
        this.areaCollection = areaCollection;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (sucursalidSucursal != null ? sucursalidSucursal.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Sucursal)) {
            return false;
        }
        Sucursal other = (Sucursal) object;
        if ((this.sucursalidSucursal == null && other.sucursalidSucursal != null) || (this.sucursalidSucursal != null && !this.sucursalidSucursal.equals(other.sucursalidSucursal))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Entidades.Sucursal[ sucursalidSucursal=" + sucursalidSucursal + " ]";
    }
    
}
