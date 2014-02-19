/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package JPA.Entidades;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 *
 * @author madman
 */
@Embeddable
public class ItItemPK implements Serializable {
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 30)
    @Column(name = "it_serie")
    private String itSerie;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 45)
    @Column(name = "it_marca")
    private String itMarca;

    public ItItemPK() {
    }

    public ItItemPK(String itSerie, String itMarca) {
        this.itSerie = itSerie;
        this.itMarca = itMarca;
    }

    public String getItSerie() {
        return itSerie;
    }

    public void setItSerie(String itSerie) {
        this.itSerie = itSerie;
    }

    public String getItMarca() {
        return itMarca;
    }

    public void setItMarca(String itMarca) {
        this.itMarca = itMarca;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (itSerie != null ? itSerie.hashCode() : 0);
        hash += (itMarca != null ? itMarca.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof ItItemPK)) {
            return false;
        }
        ItItemPK other = (ItItemPK) object;
        if ((this.itSerie == null && other.itSerie != null) || (this.itSerie != null && !this.itSerie.equals(other.itSerie))) {
            return false;
        }
        if ((this.itMarca == null && other.itMarca != null) || (this.itMarca != null && !this.itMarca.equals(other.itMarca))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "JPA.Entidades.ItItemPK[ itSerie=" + itSerie + ", itMarca=" + itMarca + " ]";
    }
    
}
