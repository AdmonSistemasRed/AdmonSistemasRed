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
@Table(name = "roles")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Roles.findAll", query = "SELECT r FROM Roles r")})
public class Roles implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 45)
    @Column(name = "rol_idRol")
    private String rolidRol;
    @Size(max = 45)
    @Column(name = "rol_nombre")
    private String rolNombre;
    @Size(max = 100)
    @Column(name = "rol_Descripcion")
    private String rolDescripcion;
    @ManyToMany(mappedBy = "rolesCollection")
    private Collection<Empleado> empleadoCollection;

    public Roles() {
    }

    public Roles(String rolidRol) {
        this.rolidRol = rolidRol;
    }

    public String getRolidRol() {
        return rolidRol;
    }

    public void setRolidRol(String rolidRol) {
        this.rolidRol = rolidRol;
    }

    public String getRolNombre() {
        return rolNombre;
    }

    public void setRolNombre(String rolNombre) {
        this.rolNombre = rolNombre;
    }

    public String getRolDescripcion() {
        return rolDescripcion;
    }

    public void setRolDescripcion(String rolDescripcion) {
        this.rolDescripcion = rolDescripcion;
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
        hash += (rolidRol != null ? rolidRol.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Roles)) {
            return false;
        }
        Roles other = (Roles) object;
        if ((this.rolidRol == null && other.rolidRol != null) || (this.rolidRol != null && !this.rolidRol.equals(other.rolidRol))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "JPA.Entidades.Roles[ rolidRol=" + rolidRol + " ]";
    }
    
}
