/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package JPA.Entidades;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author madman
 */
@Entity
@Table(name = "depto")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Depto.findAll", query = "SELECT d FROM Depto d"),
    @NamedQuery(name = "Depto.findByDepDepartamento", query = "SELECT d FROM Depto d WHERE d.depDepartamento = :depDepartamento"),
    @NamedQuery(name = "Depto.findByDepNombre", query = "SELECT d FROM Depto d WHERE d.depNombre = :depNombre"),
    @NamedQuery(name = "Depto.findByDepDescripcion", query = "SELECT d FROM Depto d WHERE d.depDescripcion = :depDescripcion")})
public class Depto implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 30)
    @Column(name = "dep_departamento")
    private String depDepartamento;
    @Size(max = 45)
    @Column(name = "dep_nombre")
    private String depNombre;
    @Size(max = 100)
    @Column(name = "dep_descripcion")
    private String depDescripcion;
    @JoinColumn(name = "area_are_idArea", referencedColumnName = "are_idArea")
    @ManyToOne(optional = false)
    private Area areaareidArea;

    public Depto() {
    }

    public Depto(String depDepartamento) {
        this.depDepartamento = depDepartamento;
    }

    public String getDepDepartamento() {
        return depDepartamento;
    }

    public void setDepDepartamento(String depDepartamento) {
        this.depDepartamento = depDepartamento;
    }

    public String getDepNombre() {
        return depNombre;
    }

    public void setDepNombre(String depNombre) {
        this.depNombre = depNombre;
    }

    public String getDepDescripcion() {
        return depDescripcion;
    }

    public void setDepDescripcion(String depDescripcion) {
        this.depDescripcion = depDescripcion;
    }

    public Area getAreaareidArea() {
        return areaareidArea;
    }

    public void setAreaareidArea(Area areaareidArea) {
        this.areaareidArea = areaareidArea;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (depDepartamento != null ? depDepartamento.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Depto)) {
            return false;
        }
        Depto other = (Depto) object;
        if ((this.depDepartamento == null && other.depDepartamento != null) || (this.depDepartamento != null && !this.depDepartamento.equals(other.depDepartamento))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Entidades.Depto[ depDepartamento=" + depDepartamento + " ]";
    }
    
}
