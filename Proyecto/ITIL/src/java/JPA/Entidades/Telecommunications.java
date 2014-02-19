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
@Table(name = "telecommunications")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Telecommunications.findAll", query = "SELECT t FROM Telecommunications t")})
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
    @Size(max = 100)
    @Column(name = "observaciones")
    private String observaciones;
    @Size(max = 45)
    @Column(name = "direccionIP")
    private String direccionIP;
    @JoinColumns({
        @JoinColumn(name = "IT_item_it_serie", referencedColumnName = "it_serie"),
        @JoinColumn(name = "IT_item_it_marca", referencedColumnName = "it_marca")})
    @ManyToOne(optional = false)
    private ItItem itItem;

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

    public ItItem getItItem() {
        return itItem;
    }

    public void setItItem(ItItem itItem) {
        this.itItem = itItem;
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
        return "JPA.Entidades.Telecommunications[ idTelecom=" + idTelecom + " ]";
    }
    
}
