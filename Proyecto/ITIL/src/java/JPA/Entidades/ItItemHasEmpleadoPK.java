/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package JPA.Entidades;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 *
 * @author madman
 */
@Embeddable
public class ItItemHasEmpleadoPK implements Serializable {
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 30)
    @Column(name = "IT_item_it_serie")
    private String iTitemitserie;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 45)
    @Column(name = "IT_item_it_marca")
    private String iTitemitmarca;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 20)
    @Column(name = "empleado_emp_NoEmpleado")
    private String empleadoempNoEmpleado;

    public ItItemHasEmpleadoPK() {
    }

    public ItItemHasEmpleadoPK(String iTitemitserie, String iTitemitmarca, String empleadoempNoEmpleado) {
        this.iTitemitserie = iTitemitserie;
        this.iTitemitmarca = iTitemitmarca;
        this.empleadoempNoEmpleado = empleadoempNoEmpleado;
    }

    public String getITitemitserie() {
        return iTitemitserie;
    }

    public void setITitemitserie(String iTitemitserie) {
        this.iTitemitserie = iTitemitserie;
    }

    public String getITitemitmarca() {
        return iTitemitmarca;
    }

    public void setITitemitmarca(String iTitemitmarca) {
        this.iTitemitmarca = iTitemitmarca;
    }

    public String getEmpleadoempNoEmpleado() {
        return empleadoempNoEmpleado;
    }

    public void setEmpleadoempNoEmpleado(String empleadoempNoEmpleado) {
        this.empleadoempNoEmpleado = empleadoempNoEmpleado;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (iTitemitserie != null ? iTitemitserie.hashCode() : 0);
        hash += (iTitemitmarca != null ? iTitemitmarca.hashCode() : 0);
        hash += (empleadoempNoEmpleado != null ? empleadoempNoEmpleado.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof ItItemHasEmpleadoPK)) {
            return false;
        }
        ItItemHasEmpleadoPK other = (ItItemHasEmpleadoPK) object;
        if ((this.iTitemitserie == null && other.iTitemitserie != null) || (this.iTitemitserie != null && !this.iTitemitserie.equals(other.iTitemitserie))) {
            return false;
        }
        if ((this.iTitemitmarca == null && other.iTitemitmarca != null) || (this.iTitemitmarca != null && !this.iTitemitmarca.equals(other.iTitemitmarca))) {
            return false;
        }
        if ((this.empleadoempNoEmpleado == null && other.empleadoempNoEmpleado != null) || (this.empleadoempNoEmpleado != null && !this.empleadoempNoEmpleado.equals(other.empleadoempNoEmpleado))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Entidades.ItItemHasEmpleadoPK[ iTitemitserie=" + iTitemitserie + ", iTitemitmarca=" + iTitemitmarca + ", empleadoempNoEmpleado=" + empleadoempNoEmpleado + " ]";
    }
    
}
