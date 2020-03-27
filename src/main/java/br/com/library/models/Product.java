package br.com.library.models;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Table;

@Entity
@Table(name = "product")
public class Product implements Serializable {

	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue
	private Long id;
	
	@Column(length = 70, nullable = false)
	private String productname;
	
	@Column(length = 20, nullable = false)
	private String typeproduct;
	
	@Column(nullable = false)
	@JoinColumn(name="providername")
	private String provider;
	
	@Column(length = 10, nullable = false)
	private Double quantity;
	
	@Column(length = 10, nullable = false)
	private Double profitmargin;
	
	@Column(length = 10, nullable = false)
	private Double unitprice;
	
	@Column(length = 10, nullable = false)
	private Double saleprice;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getProductname() {
		return productname;
	}

	public void setProductname(String productname) {
		this.productname = productname;
	}

	public String getTypeproduct() {
		return typeproduct;
	}

	public void setTypeproduct(String typeproduct) {
		this.typeproduct = typeproduct;
	}

	public String getProvider() {
		return provider;
	}

	public void setProvider(String string) {
		this.provider = string;
	}

	public Double getQuantity() {
		return quantity;
	}

	public void setQuantity(Double quantity) {
		this.quantity = quantity;
	}

	public Double getProfitmargin() {
		return profitmargin;
	}

	public void setProfitmargin(Double profitmargin) {
		this.profitmargin = profitmargin;
	}

	public Double getUnitprice() {
		return unitprice;
	}

	public void setUnitprice(Double unitprice) {
		this.unitprice = unitprice;
	}

	public Double getSaleprice() {
		return saleprice;
	}

	public void setSaleprice(Double saleprice) {
		this.saleprice = saleprice;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Product other = (Product) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}
	
}
