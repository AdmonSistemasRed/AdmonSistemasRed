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
import javax.persistence.JoinColumns;
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
@Table(name = "software")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Software.findAll", query = "SELECT s FROM Software s")})
public class Software implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 45)
    @Column(name = "idSoftware")
    private String idSoftware;
    @Size(max = 45)
    @Column(name = "nombreSoftware")
    private String nombreSoftware;
    @Size(max = 45)
    @Column(name = "licencia")
    private String licencia;
    @Size(max = 45)
    @Column(name = "cantidadUsuarios")
    private String cantidadUsuarios;
    @Size(max = 45)
    @Column(name = "version")
    private String version;
    @Size(max = 100)
    @Column(name = "descripcion")
    private String descripcion;
    @Size(max = 45)
    @Column(name = "arquitectura")
    private String arquitectura;
    @JoinColumns({
        @JoinColumn(name = "IT_item_it_serie", referencedColumnName = "it_serie"),
        @JoinColumn(name = "IT_item_it_marca", referencedColumnName = "it_marca")})
    @ManyToOne(optional = false)
    private ItItem itItem;

    public Software() {
    }

    public Software(String idSoftware) {
        this.idSoftware = idSoftware;
    }

    public String getIdSoftware() {
        return idSoftware;
    }

    public void setIdSoftware(String idSoftware) {
        this.idSoftware = idSoftware;
    }

    public String getNombreSoftware() {
        return nombreSoftware;
    }

    public void setNombreSoftware(String nombreSoftware) {
        this.nombreSoftware = nombreSoftware;
    }

    public String getLicencia() {
        return licencia;
    }

    public void setLicencia(String licencia) {
        this.licencia = licencia;
    }

    public String getCantidadUsuarios() {
        return cantidadUsuarios;
    }

    public void setCantidadUsuarios(String cantidadUsuarios) {
        this.cantidadUsuarios = cantidadUsuarios;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getArquitectura() {
        return arquitectura;
    }

    public void setArquitectura(String arquitectura) {
        this.arquitectura = arquitectura;
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
        hash += (idSoftware != null ? idSoftware.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Software)) {
            return false;
        }
        Software other = (Software) object;
        if ((this.idSoftware == null && other.idSoftware != null) || (this.idSoftware != null && !this.idSoftware.equals(other.idSoftware))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "JPA.Entidades.Software[ idSoftware=" + idSoftware + " ]";
    }
    
}
