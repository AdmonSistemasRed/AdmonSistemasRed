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
@Table(name = "telecommunications")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Telecommunications.findAll", query = "SELECT t FROM Telecommunications t"),
    @NamedQuery(name = "Telecommunications.findByIdTelecom", query = "SELECT t FROM Telecommunications t WHERE t.idTelecom = :idTelecom"),
    @NamedQuery(name = "Telecommunications.findByNumeroPuertosSalida", query = "SELECT t FROM Telecommunications t WHERE t.numeroPuertosSalida = :numeroPuertosSalida"),
    @NamedQuery(name = "Telecommunications.findByNumeroPuertosEntrada", query = "SELECT t FROM Telecommunications t WHERE t.numeroPuertosEntrada = :numeroPuertosEntrada"),
    @NamedQuery(name = "Telecommunications.findByTipoInterfaz", query = "SELECT t FROM Telecommunications t WHERE t.tipoInterfaz = :tipoInterfaz"),
    @NamedQuery(name = "Telecommunications.findByDireccionFisica", query = "SELECT t FROM Telecommunications t WHERE t.direccionFisica = :direccionFisica"),
    @NamedQuery(name = "Telecommunications.findByModelo", query = "SELECT t FROM Telecommunications t WHERE t.modelo = :modelo"),
    @NamedQuery(name = "Telecommunications.findByMarca", query = "SELECT t FROM Telecommunications t WHERE t.marca = :marca"),
    @NamedQuery(name = "Telecommunications.findByObservaciones", query = "SELECT t FROM Telecommunications t WHERE t.observaciones = :observaciones"),
    @NamedQuery(name = "Telecommunications.findByDireccionIP", query = "SELECT t FROM Telecommunications t WHERE t.direccionIP = :direccionIP")})
public class Telecommunications implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 45)
    @Column(name = "idTelecom")
    private String idTelecom;
    @Size(max = 45)
    @Column(name = "numeroPuertosSalida")
    private String numeroPuertosSalida;
    @Size(max = 45)
    @Column(name = "numeroPuertosEntrada")
    private String numeroPuertosEntrada;
    @Size(max = 45)
    @Column(name = "tipoInterfaz")
    private String tipoInterfaz;
    @Size(max = 45)
    @Column(name = "direccionFisica")
    private String direccionFisica;
    @Size(max = 45)
    @Column(name = "modelo")
    private String modelo;
    @Size(max = 45)
    @Column(name = "marca")
    private String marca;
    @Size(max = 100)
    @Column(name = "observaciones")
    private String observaciones;
    @Size(max = 45)
    @Column(name = "direccionIP")
    private String direccionIP;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "telecommunicationsidTelecom")
    private Collection<ItItem> itItemCollection;

    public Telecommunications() {
    }

    public Telecommunications(String idTelecom) {
        this.idTelecom = idTelecom;
    }

    public String getIdTelecom() {
        return idTelecom;
    }

    public void setIdTelecom(String idTelecom) {
        this.idTelecom = idTelecom;
    }

    public String getNumeroPuertosSalida() {
        return numeroPuertosSalida;
    }

    public void setNumeroPuertosSalida(String numeroPuertosSalida) {
        this.numeroPuertosSalida = numeroPuertosSalida;
    }

    public String getNumeroPuertosEntrada() {
        return numeroPuertosEntrada;
    }

    public void setNumeroPuertosEntrada(String numeroPuertosEntrada) {
        this.numeroPuertosEntrada = numeroPuertosEntrada;
    }

    public String getTipoInterfaz() {
        return tipoInterfaz;
    }

    public void setTipoInterfaz(String tipoInterfaz) {
        this.tipoInterfaz = tipoInterfaz;
    }

    public String getDireccionFisica() {
        return direccionFisica;
    }

    public void setDireccionFisica(String direccionFisica) {
        this.direccionFisica = direccionFisica;
    }

    public String getModelo() {
        return modelo;
    }

    public void setModelo(String modelo) {
        this.modelo = modelo;
    }

    public String getMarca() {
        return marca;
    }

    public void setMarca(String marca) {
        this.marca = marca;
    }

    public String getObservaciones() {
        return observaciones;
    }

    public void setObservaciones(String observaciones) {
        this.observaciones = observaciones;
    }

    public String getDireccionIP() {
        return direccionIP;
    }

    public void setDireccionIP(String direccionIP) {
        this.direccionIP = direccionIP;
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
        hash += (idTelecom != null ? idTelecom.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Telecommunications)) {
            return false;
        }
        Telecommunications other = (Telecommunications) object;
        if ((this.idTelecom == null && other.idTelecom != null) || (this.idTelecom != null && !this.idTelecom.equals(other.idTelecom))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Entidades.Telecommunications[ idTelecom=" + idTelecom + " ]";
    }
    
}
