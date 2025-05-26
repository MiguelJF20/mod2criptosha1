/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dto;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author LENOVO
 */
@Entity
@Table(name = "productos")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Productos.findAll", query = "SELECT p FROM Productos p"),
    @NamedQuery(name = "Productos.findByCodiProd", query = "SELECT p FROM Productos p WHERE p.codiProd = :codiProd"),
    @NamedQuery(name = "Productos.findByNombProd", query = "SELECT p FROM Productos p WHERE p.nombProd = :nombProd"),
    @NamedQuery(name = "Productos.findByPrecProd", query = "SELECT p FROM Productos p WHERE p.precProd = :precProd"),
    @NamedQuery(name = "Productos.findByStockProd", query = "SELECT p FROM Productos p WHERE p.stockProd = :stockProd"),
    @NamedQuery(name = "Productos.findByFechProd", query = "SELECT p FROM Productos p WHERE p.fechProd = :fechProd")})
public class Productos implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "codiProd")
    private Integer codiProd;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 100)
    @Column(name = "nombProd")
    private String nombProd;
    @Lob
    @Size(max = 65535)
    @Column(name = "descProd")
    private String descProd;
    @Basic(optional = false)
    @NotNull
    @Column(name = "precProd")
    private double precProd;
    @Basic(optional = false)
    @NotNull
    @Column(name = "stockProd")
    private int stockProd;
    @Column(name = "fechProd")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fechProd;

    public Productos() {
    }

    public Productos(Integer codiProd) {
        this.codiProd = codiProd;
    }

    public Productos(Integer codiProd, String nombProd, double precProd, int stockProd) {
        this.codiProd = codiProd;
        this.nombProd = nombProd;
        this.precProd = precProd;
        this.stockProd = stockProd;
    }

    public Integer getCodiProd() {
        return codiProd;
    }

    public void setCodiProd(Integer codiProd) {
        this.codiProd = codiProd;
    }

    public String getNombProd() {
        return nombProd;
    }

    public void setNombProd(String nombProd) {
        this.nombProd = nombProd;
    }

    public String getDescProd() {
        return descProd;
    }

    public void setDescProd(String descProd) {
        this.descProd = descProd;
    }

    public double getPrecProd() {
        return precProd;
    }

    public void setPrecProd(double precProd) {
        this.precProd = precProd;
    }

    public int getStockProd() {
        return stockProd;
    }

    public void setStockProd(int stockProd) {
        this.stockProd = stockProd;
    }

    public Date getFechProd() {
        return fechProd;
    }

    public void setFechProd(Date fechProd) {
        this.fechProd = fechProd;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (codiProd != null ? codiProd.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Productos)) {
            return false;
        }
        Productos other = (Productos) object;
        if ((this.codiProd == null && other.codiProd != null) || (this.codiProd != null && !this.codiProd.equals(other.codiProd))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "dto.Productos[ codiProd=" + codiProd + " ]";
    }
    
}
