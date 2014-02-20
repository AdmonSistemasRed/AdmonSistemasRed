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
 * @author madman
 */
@Entity
@Table(name = "computadora")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Computadora.findAll", query = "SELECT c FROM Computadora c"),
    @NamedQuery(name = "Computadora.findByIdComputadora", query = "SELECT c FROM Computadora c WHERE c.idComputadora = :idComputadora"),
    @NamedQuery(name = "Computadora.findByModelo", query = "SELECT c FROM Computadora c WHERE c.modelo = :modelo"),
    @NamedQuery(name = "Computadora.findByFechaAdquisicion", query = "SELECT c FROM Computadora c WHERE c.fechaAdquisicion = :fechaAdquisicion"),
    @NamedQuery(name = "Computadora.findByDescripcion", query = "SELECT c FROM Computadora c WHERE c.descripcion = :descripcion")})
public class Computadora implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 45)
    @Column(name = "idComputadora")
    private String idComputadora;
    @Size(max = 45)
    @Column(name = "modelo")
    private String modelo;
    @Size(max = 45)
    @Column(name = "fechaAdquisicion")
    private String fechaAdquisicion;
    @Size(max = 45)
    @Column(name = "descripcion")
    private String descripcion;
    @ManyToMany(mappedBy = "computadoraCollection")
    private Collection<Software> softwareCollection;
    @JoinColumn(name = "servidor_idServidor", referencedColumnName = "idServidor")
    @ManyToOne(optional = false)
    private Servidor servidoridServidor;
    @JoinColumn(name = "Laptops_idLaptop", referencedColumnName = "idLaptop")
    @ManyToOne(optional = false)
    private Laptops laptopsidLaptop;
    @JoinColumn(name = "Workstation_idWorkstation", referencedColumnName = "idWorkstation")
    @ManyToOne(optional = false)
    private Workstation workstationidWorkstation;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "computadoraidComputadora")
    private Collection<ItItem> itItemCollection;

    public Computadora() {
    }

    public Computadora(String idComputadora) {
        this.idComputadora = idComputadora;
    }

    public String getIdComputadora() {
        return idComputadora;
    }

    public void setIdComputadora(String idComputadora) {
        this.idComputadora = idComputadora;
    }

    public String getModelo() {
        return modelo;
    }

    public void setModelo(String modelo) {
        this.modelo = modelo;
    }

    public String getFechaAdquisicion() {
        return fechaAdquisicion;
    }

    public void setFechaAdquisicion(String fechaAdquisicion) {
        this.fechaAdquisicion = fechaAdquisicion;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    @XmlTransient
    public Collection<Software> getSoftwareCollection() {
        return softwareCollection;
    }

    public void setSoftwareCollection(Collection<Software> softwareCollection) {
        this.softwareCollection = softwareCollection;
    }

    public Servidor getServidoridServidor() {
        return servidoridServidor;
    }

    public void setServidoridServidor(Servidor servidoridServidor) {
        this.servidoridServidor = servidoridServidor;
    }

    public Laptops getLaptopsidLaptop() {
        return laptopsidLaptop;
    }

    public void setLaptopsidLaptop(Laptops laptopsidLaptop) {
        this.laptopsidLaptop = laptopsidLaptop;
    }

    public Workstation getWorkstationidWorkstation() {
        return workstationidWorkstation;
    }

    public void setWorkstationidWorkstation(Workstation workstationidWorkstation) {
        this.workstationidWorkstation = workstationidWorkstation;
    }

    @XmlTransient
    public Collection<ItItem> getItItemCollection() {
        return itItemCollection;
    }

    public void setItItemCollection(Collection<ItItem> itItemCollection) {
        this.itItemCollection = itItemCollection;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idComputadora != null ? idComputadora.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Computadora)) {
            return false;
        }
        Computadora other = (Computadora) object;
        if ((this.idComputadora == null && other.idComputadora != null) || (this.idComputadora != null && !this.idComputadora.equals(other.idComputadora))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Entidades.Computadora[ idComputadora=" + idComputadora + " ]";
    }
    
}
