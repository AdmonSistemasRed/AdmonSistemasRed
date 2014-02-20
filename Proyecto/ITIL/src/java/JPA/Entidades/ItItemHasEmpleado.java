/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package JPA.Entidades;

import java.io.Serializable;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author madman
 */
@Entity
@Table(name = "it_item_has_empleado")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "ItItemHasEmpleado.findAll", query = "SELECT i FROM ItItemHasEmpleado i"),
    @NamedQuery(name = "ItItemHasEmpleado.findByITitemitserie", query = "SELECT i FROM ItItemHasEmpleado i WHERE i.itItemHasEmpleadoPK.iTitemitserie = :iTitemitserie"),
    @NamedQuery(name = "ItItemHasEmpleado.findByITitemitmarca", query = "SELECT i FROM ItItemHasEmpleado i WHERE i.itItemHasEmpleadoPK.iTitemitmarca = :iTitemitmarca"),
    @NamedQuery(name = "ItItemHasEmpleado.findByEmpleadoempNoEmpleado", query = "SELECT i FROM ItItemHasEmpleado i WHERE i.itItemHasEmpleadoPK.empleadoempNoEmpleado = :empleadoempNoEmpleado")})
public class ItItemHasEmpleado implements Serializable {
    private static final long serialVersionUID = 1L;
    @EmbeddedId
    protected ItItemHasEmpleadoPK itItemHasEmpleadoPK;
    @JoinColumn(name = "empleado_emp_NoEmpleado", referencedColumnName = "emp_NoEmpleado", insertable = false, updatable = false)
    @ManyToOne(optional = false)
    private Empleado empleado;
    @JoinColumn(name = "IT_item_it_serie", referencedColumnName = "it_serie", insertable = false, updatable = false)
    @ManyToOne(optional = false)
    private ItItem itItem;

    public ItItemHasEmpleado() {
    }

    public ItItemHasEmpleado(ItItemHasEmpleadoPK itItemHasEmpleadoPK) {
        this.itItemHasEmpleadoPK = itItemHasEmpleadoPK;
    }

    public ItItemHasEmpleado(String iTitemitserie, String iTitemitmarca, String empleadoempNoEmpleado) {
        this.itItemHasEmpleadoPK = new ItItemHasEmpleadoPK(iTitemitserie, iTitemitmarca, empleadoempNoEmpleado);
    }

    public ItItemHasEmpleadoPK getItItemHasEmpleadoPK() {
        return itItemHasEmpleadoPK;
    }

    public void setItItemHasEmpleadoPK(ItItemHasEmpleadoPK itItemHasEmpleadoPK) {
        this.itItemHasEmpleadoPK = itItemHasEmpleadoPK;
    }

    public Empleado getEmpleado() {
        return empleado;
    }

    public void setEmpleado(Empleado empleado) {
        this.empleado = empleado;
    }

    public ItItem getItItem() {
        return itItem;
    }

    public void setItItem(ItItem itItem) {
        this.itItem = itItem;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (itItemHasEmpleadoPK != null ? itItemHasEmpleadoPK.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof ItItemHasEmpleado)) {
            return false;
        }
        ItItemHasEmpleado other = (ItItemHasEmpleado) object;
        if ((this.itItemHasEmpleadoPK == null && other.itItemHasEmpleadoPK != null) || (this.itItemHasEmpleadoPK != null && !this.itItemHasEmpleadoPK.equals(other.itItemHasEmpleadoPK))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Entidades.ItItemHasEmpleado[ itItemHasEmpleadoPK=" + itItemHasEmpleadoPK + " ]";
    }
    
}
