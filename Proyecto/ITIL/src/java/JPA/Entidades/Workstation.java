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
@Table(name = "workstation")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Workstation.findAll", query = "SELECT w FROM Workstation w"),
    @NamedQuery(name = "Workstation.findByIdWorkstation", query = "SELECT w FROM Workstation w WHERE w.idWorkstation = :idWorkstation"),
    @NamedQuery(name = "Workstation.findByProcesador", query = "SELECT w FROM Workstation w WHERE w.procesador = :procesador"),
    @NamedQuery(name = "Workstation.findByMemoria", query = "SELECT w FROM Workstation w WHERE w.memoria = :memoria"),
    @NamedQuery(name = "Workstation.findByDiscoDuro", query = "SELECT w FROM Workstation w WHERE w.discoDuro = :discoDuro"),
    @NamedQuery(name = "Workstation.findByTarjetaVideo", query = "SELECT w FROM Workstation w WHERE w.tarjetaVideo = :tarjetaVideo"),
    @NamedQuery(name = "Workstation.findByPuertos", query = "SELECT w FROM Workstation w WHERE w.puertos = :puertos"),
    @NamedQuery(name = "Workstation.findByResolucionMonitor", query = "SELECT w FROM Workstation w WHERE w.resolucionMonitor = :resolucionMonitor"),
    @NamedQuery(name = "Workstation.findByUnidadOptica", query = "SELECT w FROM Workstation w WHERE w.unidadOptica = :unidadOptica"),
    @NamedQuery(name = "Workstation.findByDescripcion", query = "SELECT w FROM Workstation w WHERE w.descripcion = :descripcion"),
    @NamedQuery(name = "Workstation.findByDireccionIP", query = "SELECT w FROM Workstation w WHERE w.direccionIP = :direccionIP"),
    @NamedQuery(name = "Workstation.findByArquitectura", query = "SELECT w FROM Workstation w WHERE w.arquitectura = :arquitectura"),
    @NamedQuery(name = "Workstation.findByCostoTotal", query = "SELECT w FROM Workstation w WHERE w.costoTotal = :costoTotal"),
    @NamedQuery(name = "Workstation.findByDepreciacion", query = "SELECT w FROM Workstation w WHERE w.depreciacion = :depreciacion")})
public class Workstation implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 45)
    @Column(name = "idWorkstation")
    private String idWorkstation;
    @Size(max = 45)
    @Column(name = "procesador")
    private String procesador;
    @Size(max = 45)
    @Column(name = "memoria")
    private String memoria;
    @Size(max = 45)
    @Column(name = "discoDuro")
    private String discoDuro;
    @Size(max = 45)
    @Column(name = "tarjetaVideo")
    private String tarjetaVideo;
    @Size(max = 45)
    @Column(name = "puertos")
    private String puertos;
    @Size(max = 45)
    @Column(name = "resolucionMonitor")
    private String resolucionMonitor;
    @Size(max = 45)
    @Column(name = "unidadOptica")
    private String unidadOptica;
    @Size(max = 45)
    @Column(name = "descripcion")
    private String descripcion;
    @Size(max = 45)
    @Column(name = "direccionIP")
    private String direccionIP;
    @Size(max = 45)
    @Column(name = "arquitectura")
    private String arquitectura;
    @Size(max = 45)
    @Column(name = "CostoTotal")
    private String costoTotal;
    @Size(max = 45)
    @Column(name = "depreciacion")
    private String depreciacion;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "workstationidWorkstation")
    private Collection<Computadora> computadoraCollection;

    public Workstation() {
    }

    public Workstation(String idWorkstation) {
        this.idWorkstation = idWorkstation;
    }

    public String getIdWorkstation() {
        return idWorkstation;
    }

    public void setIdWorkstation(String idWorkstation) {
        this.idWorkstation = idWorkstation;
    }

    public String getProcesador() {
        return procesador;
    }

    public void setProcesador(String procesador) {
        this.procesador = procesador;
    }

    public String getMemoria() {
        return memoria;
    }

    public void setMemoria(String memoria) {
        this.memoria = memoria;
    }

    public String getDiscoDuro() {
        return discoDuro;
    }

    public void setDiscoDuro(String discoDuro) {
        this.discoDuro = discoDuro;
    }

    public String getTarjetaVideo() {
        return tarjetaVideo;
    }

    public void setTarjetaVideo(String tarjetaVideo) {
        this.tarjetaVideo = tarjetaVideo;
    }

    public String getPuertos() {
        return puertos;
    }

    public void setPuertos(String puertos) {
        this.puertos = puertos;
    }

    public String getResolucionMonitor() {
        return resolucionMonitor;
    }

    public void setResolucionMonitor(String resolucionMonitor) {
        this.resolucionMonitor = resolucionMonitor;
    }

    public String getUnidadOptica() {
        return unidadOptica;
    }

    public void setUnidadOptica(String unidadOptica) {
        this.unidadOptica = unidadOptica;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getDireccionIP() {
        return direccionIP;
    }

    public void setDireccionIP(String direccionIP) {
        this.direccionIP = direccionIP;
    }

    public String getArquitectura() {
        return arquitectura;
    }

    public void setArquitectura(String arquitectura) {
        this.arquitectura = arquitectura;
    }

    public String getCostoTotal() {
        return costoTotal;
    }

    public void setCostoTotal(String costoTotal) {
        this.costoTotal = costoTotal;
    }

    public String getDepreciacion() {
        return depreciacion;
    }

    public void setDepreciacion(String depreciacion) {
        this.depreciacion = depreciacion;
    }

    @XmlTransient
    public Collection<Computadora> getComputadoraCollection() {
        return computadoraCollection;
    }

    public void setComputadoraCollection(Collection<Computadora> computadoraCollection) {
        this.computadoraCollection = computadoraCollection;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idWorkstation != null ? idWorkstation.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Workstation)) {
            return false;
        }
        Workstation other = (Workstation) object;
        if ((this.idWorkstation == null && other.idWorkstation != null) || (this.idWorkstation != null && !this.idWorkstation.equals(other.idWorkstation))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Entidades.Workstation[ idWorkstation=" + idWorkstation + " ]";
    }
    
}
