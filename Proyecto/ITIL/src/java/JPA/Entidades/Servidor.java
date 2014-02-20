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
@Table(name = "servidor")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Servidor.findAll", query = "SELECT s FROM Servidor s"),
    @NamedQuery(name = "Servidor.findByIdServidor", query = "SELECT s FROM Servidor s WHERE s.idServidor = :idServidor"),
    @NamedQuery(name = "Servidor.findByProcesardor", query = "SELECT s FROM Servidor s WHERE s.procesardor = :procesardor"),
    @NamedQuery(name = "Servidor.findByMemoria", query = "SELECT s FROM Servidor s WHERE s.memoria = :memoria"),
    @NamedQuery(name = "Servidor.findByDiscoDuro", query = "SELECT s FROM Servidor s WHERE s.discoDuro = :discoDuro"),
    @NamedQuery(name = "Servidor.findByUnidadOptica", query = "SELECT s FROM Servidor s WHERE s.unidadOptica = :unidadOptica"),
    @NamedQuery(name = "Servidor.findByBateriaReserva", query = "SELECT s FROM Servidor s WHERE s.bateriaReserva = :bateriaReserva"),
    @NamedQuery(name = "Servidor.findByDescripcion", query = "SELECT s FROM Servidor s WHERE s.descripcion = :descripcion"),
    @NamedQuery(name = "Servidor.findByDireccionIP", query = "SELECT s FROM Servidor s WHERE s.direccionIP = :direccionIP"),
    @NamedQuery(name = "Servidor.findByArquitectura", query = "SELECT s FROM Servidor s WHERE s.arquitectura = :arquitectura"),
    @NamedQuery(name = "Servidor.findByCostoTotal", query = "SELECT s FROM Servidor s WHERE s.costoTotal = :costoTotal"),
    @NamedQuery(name = "Servidor.findByDepreciacion", query = "SELECT s FROM Servidor s WHERE s.depreciacion = :depreciacion")})
public class Servidor implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 45)
    @Column(name = "idServidor")
    private String idServidor;
    @Size(max = 45)
    @Column(name = "procesardor")
    private String procesardor;
    @Size(max = 45)
    @Column(name = "memoria")
    private String memoria;
    @Size(max = 45)
    @Column(name = "discoDuro")
    private String discoDuro;
    @Size(max = 45)
    @Column(name = "unidadOptica")
    private String unidadOptica;
    @Size(max = 45)
    @Column(name = "bateriaReserva")
    private String bateriaReserva;
    @Size(max = 100)
    @Column(name = "descripcion")
    private String descripcion;
    @Size(max = 45)
    @Column(name = "direccionIP")
    private String direccionIP;
    @Size(max = 45)
    @Column(name = "arquitectura")
    private String arquitectura;
    @Size(max = 45)
    @Column(name = "costoTotal")
    private String costoTotal;
    @Size(max = 45)
    @Column(name = "depreciacion")
    private String depreciacion;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "servidoridServidor")
    private Collection<Computadora> computadoraCollection;

    public Servidor() {
    }

    public Servidor(String idServidor) {
        this.idServidor = idServidor;
    }

    public String getIdServidor() {
        return idServidor;
    }

    public void setIdServidor(String idServidor) {
        this.idServidor = idServidor;
    }

    public String getProcesardor() {
        return procesardor;
    }

    public void setProcesardor(String procesardor) {
        this.procesardor = procesardor;
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

    public String getUnidadOptica() {
        return unidadOptica;
    }

    public void setUnidadOptica(String unidadOptica) {
        this.unidadOptica = unidadOptica;
    }

    public String getBateriaReserva() {
        return bateriaReserva;
    }

    public void setBateriaReserva(String bateriaReserva) {
        this.bateriaReserva = bateriaReserva;
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
        hash += (idServidor != null ? idServidor.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Servidor)) {
            return false;
        }
        Servidor other = (Servidor) object;
        if ((this.idServidor == null && other.idServidor != null) || (this.idServidor != null && !this.idServidor.equals(other.idServidor))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Entidades.Servidor[ idServidor=" + idServidor + " ]";
    }
    
}
