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
@Table(name = "laptops")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Laptops.findAll", query = "SELECT l FROM Laptops l")})
public class Laptops implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 45)
    @Column(name = "idLaptop")
    private String idLaptop;
    @Size(max = 45)
    @Column(name = "procesador")
    private String procesador;
    @Size(max = 45)
    @Column(name = "memoria")
    private String memoria;
    @Size(max = 45)
    @Column(name = "duracionBateria")
    private String duracionBateria;
    @Size(max = 45)
    @Column(name = "discoDuro")
    private String discoDuro;
    @Size(max = 45)
    @Column(name = "resolucionPantalla")
    private String resolucionPantalla;
    @Size(max = 45)
    @Column(name = "tarjetaVideo")
    private String tarjetaVideo;
    @Size(max = 45)
    @Column(name = "puerto")
    private String puerto;
    @Size(max = 100)
    @Column(name = "descripcion")
    private String descripcion;
    @Size(max = 45)
    @Column(name = "direccionIP")
    private String direccionIP;
    @Size(max = 45)
    @Column(name = "arquitectura")
    private String arquitectura;
    @JoinColumns({
        @JoinColumn(name = "IT_item_it_serie", referencedColumnName = "it_serie"),
        @JoinColumn(name = "IT_item_it_marca", referencedColumnName = "it_marca")})
    @ManyToOne(optional = false)
    private ItItem itItem;

    public Laptops() {
    }

    public Laptops(String idLaptop) {
        this.idLaptop = idLaptop;
    }

    public String getIdLaptop() {
        return idLaptop;
    }

    public void setIdLaptop(String idLaptop) {
        this.idLaptop = idLaptop;
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

    public String getDuracionBateria() {
        return duracionBateria;
    }

    public void setDuracionBateria(String duracionBateria) {
        this.duracionBateria = duracionBateria;
    }

    public String getDiscoDuro() {
        return discoDuro;
    }

    public void setDiscoDuro(String discoDuro) {
        this.discoDuro = discoDuro;
    }

    public String getResolucionPantalla() {
        return resolucionPantalla;
    }

    public void setResolucionPantalla(String resolucionPantalla) {
        this.resolucionPantalla = resolucionPantalla;
    }

    public String getTarjetaVideo() {
        return tarjetaVideo;
    }

    public void setTarjetaVideo(String tarjetaVideo) {
        this.tarjetaVideo = tarjetaVideo;
    }

    public String getPuerto() {
        return puerto;
    }

    public void setPuerto(String puerto) {
        this.puerto = puerto;
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

    public ItItem getItItem() {
        return itItem;
    }

    public void setItItem(ItItem itItem) {
        this.itItem = itItem;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idLaptop != null ? idLaptop.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Laptops)) {
            return false;
        }
        Laptops other = (Laptops) object;
        if ((this.idLaptop == null && other.idLaptop != null) || (this.idLaptop != null && !this.idLaptop.equals(other.idLaptop))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "JPA.Entidades.Laptops[ idLaptop=" + idLaptop + " ]";
    }
    
}
