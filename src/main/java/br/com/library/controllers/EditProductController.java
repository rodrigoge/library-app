package br.com.library.controllers;

import java.io.Serializable;
import java.util.List;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;

import br.com.library.business.ProductBusiness;
import br.com.library.business.exception.BusinessException;
import br.com.library.models.Product;
import br.com.library.repositories.ProductRepository;
import br.com.library.utils.DataConfiguration;

@ManagedBean
@ViewScoped
public class EditProductController implements Serializable {

	private static final long serialVersionUID = 1L;
	
	private Product product = new Product();
	private Product productSelected = new Product();
	private List<Product> products;
	EntityManager data = DataConfiguration.getEntityManager();
	private ProductRepository productRepository = new ProductRepository(data);
	
	public void init() {
		if(this.product == null) {
			this.product = new Product();
		}
	}
	
	public void search() {
		EntityManager data = DataConfiguration.getEntityManager();
		ProductRepository products = new ProductRepository(data);
		this.products = products.all();
		data.close();
	}
	
	public void update() {
		EntityManager data = DataConfiguration.getEntityManager();
		EntityTransaction transaction = data.getTransaction();
		FacesContext faces = FacesContext.getCurrentInstance();

		try {
			transaction.begin();
			ProductBusiness productBusiness = new ProductBusiness(new ProductRepository(data));
			productBusiness.save(product);
			this.product = new Product();
			faces.addMessage(null, new FacesMessage("Atualizado com sucesso."));
			transaction.commit();
			this.search();
		} catch (BusinessException e) {
			transaction.rollback();
			FacesMessage message = new FacesMessage(e.getMessage());
			message.setSeverity(FacesMessage.SEVERITY_ERROR);
			faces.addMessage(null, message);
		} finally {
			data.close();
		}
	}
	
	public void delete() {
		FacesContext context = FacesContext.getCurrentInstance();
		EntityManager data = DataConfiguration.getEntityManager();
		EntityTransaction transaction = data.getTransaction();
		
		ProductBusiness productBusiness = new ProductBusiness(new ProductRepository(data));
		
		try {
			transaction.commit();
			productBusiness.remove(this.productSelected);
			context.addMessage(null, new FacesMessage("Produto removido."));
			transaction.commit();
			this.search();
		} catch (BusinessException e) {
			transaction.rollback();
			FacesMessage message = new FacesMessage(e.getMessage());
			message.setSeverity(FacesMessage.SEVERITY_ERROR);
			context.addMessage(null, message);
		} finally {
			data.close();
		}
	}
	
	
	public Product getProduct() {
		return product;
	}
	public void setProduct(Product product) {
		this.product = product;
	}
	public Product getProductSelected() {
		return productSelected;
	}
	public void setProductSelected(Product productSelected) {
		this.productSelected = productSelected;
	}
	public List<Product> getProducts() {
		return products;
	}
	public ProductRepository getProductRepository() {
		return productRepository;
	}
	public void setProductRepository(ProductRepository productRepository) {
		this.productRepository = productRepository;
	}

}
