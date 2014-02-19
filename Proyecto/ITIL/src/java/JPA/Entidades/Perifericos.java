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
@Table(name = "perifericos")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Perifericos.findAll", query = "SELECT p FROM Perifericos p")})
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
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 100)
    @Column(name = "descripcion")
    private String descripcion;
    @JoinColumns({
        @JoinColumn(name = "IT_item_it_serie", referencedColumnName = "it_serie"),
        @JoinColumn(name = "IT_item_it_marca", referencedColumnName = "it_marca")})
    @ManyToOne(optional = false)
    private ItItem itItem;

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

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
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
        return "JPA.Entidades.Perifericos[ idPeriferico=" + idPeriferico + " ]";
    }
    
}
