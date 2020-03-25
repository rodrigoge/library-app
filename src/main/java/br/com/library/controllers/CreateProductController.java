package br.com.library.controllers;

import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.context.FacesContext;
import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;

import br.com.library.business.ProductBusiness;
import br.com.library.business.exception.BusinessException;
import br.com.library.models.Product;
import br.com.library.models.Provider;
import br.com.library.models.TypeProduct;
import br.com.library.repositories.ProductRepository;
import br.com.library.repositories.ProviderRepository;
import br.com.library.utils.DataConfiguration;

@ManagedBean
@RequestScoped
public class CreateProductController implements Serializable {

	private static final long serialVersionUID = 1L;
	
	private Product product = new Product();
	private Provider provider = new Provider();
	private List<Provider> providers = new ArrayList<Provider>();
	private List<Product> products;
	EntityManager data = DataConfiguration.getEntityManager();
	private ProductRepository productRepository = new ProductRepository(data);
	
	public void init() {
		if(this.product == null) {
			this.product = new Product();
		}
	}
	
	public void save() throws IOException{
		EntityManager data = DataConfiguration.getEntityManager();
		EntityTransaction transaction = data.getTransaction();
		FacesContext context = FacesContext.getCurrentInstance();
		
		try {
			transaction.begin();
			ProductBusiness productBusiness = new ProductBusiness(new ProductRepository(data));
			productBusiness.save(product);
			this.product = new Product();
			context.addMessage(null, new FacesMessage("Produto salvo com sucesso."));
			transaction.commit();
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
	public List<Product> getProducts() {
		return products;
	}
	public void setProducts(List<Product> products) {
		this.products = products;
	}
	public ProductRepository getProductRepository() {
		return productRepository;
	}
	public void setProductRepository(ProductRepository productRepository) {
		this.productRepository = productRepository;
	}
	
	public TypeProduct[] gettypeProduct() {
		return TypeProduct.values();
	}
	
	public List<Provider> getProviders() {
		return providers;
	}

	public void setProviders(List<Provider> providers) {
		this.providers = providers;
	}
	
	public Provider getProvider() {
		return provider;
	}

	public void setProvider(Provider provider) {
		this.provider = provider;
	}

	public void searchProviders() {
		EntityManager data = DataConfiguration.getEntityManager();
		ProviderRepository providers = new ProviderRepository(data);
		this.providers = providers.all();
		data.close();
	}
	
	
	
}
