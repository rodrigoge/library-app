package br.com.library.repositories;

import java.io.Serializable;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;

import br.com.library.models.Product;
import br.com.library.utils.DataConfiguration;

public class ProductRepository implements Serializable {

	private static final long serialVersionUID = 1L;

	private EntityManager data = DataConfiguration.getEntityManager();
	
	public ProductRepository(EntityManager data) {
		this.data = data;
	}
	
	public Product index(Long id) {
		return data.find(Product.class, id);
	}
	
	public List<Product> all(){
		TypedQuery<Product> query = data.createQuery("from Product p order by p.id", Product.class);
		return query.getResultList();
	}
	
	public void create(Product product) {
		this.data.persist(product);
	}

	// updates the user if it already exists
	public void update(Product product) {
		this.data.merge(product);
	}

	// remove user
	public void delete(Product product) {
		this.data.remove(product);
	}
	
}
