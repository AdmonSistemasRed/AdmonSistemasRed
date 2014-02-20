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
@Table(name = "perifericos")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Perifericos.findAll", query = "SELECT p FROM Perifericos p"),
    @NamedQuery(name = "Perifericos.findByIdPeriferico", query = "SELECT p FROM Perifericos p WHERE p.idPeriferico = :idPeriferico"),
    @NamedQuery(name = "Perifericos.findByCategoria", query = "SELECT p FROM Perifericos p WHERE p.categoria = :categoria"),
    @NamedQuery(name = "Perifericos.findByMarca", query = "SELECT p FROM Perifericos p WHERE p.marca = :marca"),
    @NamedQuery(name = "Perifericos.findByModelo", query = "SELECT p FROM Perifericos p WHERE p.modelo = :modelo"),
    @NamedQuery(name = "Perifericos.findByDescripcion", query = "SELECT p FROM Perifericos p WHERE p.descripcion = :descripcion")})
public class Perifericos implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 45)
    @Column(name = "idPeriferico")
    private String idPeriferico;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 45)
    @Column(name = "categoria")
    private String categoria;
    @Size(max = 45)
    @Column(name = "marca")
    private String marca;
    @Size(max = 45)
    @Column(name = "modelo")
    private String modelo;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 100)
    @Column(name = "descripcion")
    private String descripcion;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "perifericosidPeriferico")
    private Collection<ItItem> itItemCollection;

    public Perifericos() {
    }

    public Perifericos(String idPeriferico) {
        this.idPeriferico = idPeriferico;
    }

    public Perifericos(String idPeriferico, String categoria, String descripcion) {
        this.idPeriferico = idPeriferico;
        this.categoria = categoria;
        this.descripcion = descripcion;
    }

    public String getIdPeriferico() {
        return idPeriferico;
    }

    public void setIdPeriferico(String idPeriferico) {
        this.idPeriferico = idPeriferico;
    }

    public String getCategoria() {
        return categoria;
    }

    public void setCategoria(String categoria) {
        this.categoria = categoria;
    }

    public String getMarca() {
        return marca;
    }

    public void setMarca(String marca) {
        this.marca = marca;
    }

    public String getModelo() {
        return modelo;
    }

    public void setModelo(String modelo) {
        this.modelo = modelo;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
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
        hash += (idPeriferico != null ? idPeriferico.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Perifericos)) {
            return false;
        }
        Perifericos other = (Perifericos) object;
        if ((this.idPeriferico == null && other.idPeriferico != null) || (this.idPeriferico != null && !this.idPeriferico.equals(other.idPeriferico))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Entidades.Perifericos[ idPeriferico=" + idPeriferico + " ]";
    }
    
}
