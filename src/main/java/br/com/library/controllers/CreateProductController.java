package br.com.library.controllers;

import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.context.FacesContext;
import javax.faces.model.SelectItem;
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
	private List<Product> products;
	EntityManager data = DataConfiguration.getEntityManager();
	private ProductRepository productRepository = new ProductRepository(data);
	
	private Provider providerSelect = new Provider();
	private List<SelectItem> providersSelect;
	private ProviderRepository providerRepository = new ProviderRepository(data);

	
	
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
			product.setProvider(providerSelect.getProvidername());
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

	public Provider getProviderSelect() {
		return providerSelect;
	}

	public void setProviderSelect(Provider providerSelect) {
		this.providerSelect = providerSelect;
	}

	public TypeProduct[] gettypeProduct() {
		return TypeProduct.values();
	}

	public List<SelectItem> getProvidersSelect() {
		if(providersSelect == null) {
			System.out.println("Aqui primeiro");
			providersSelect = new ArrayList<SelectItem>();
			
			providerRepository = new ProviderRepository(data);
			
			List<Provider> listProviders = providerRepository.all();
			
			if(listProviders != null && !listProviders.isEmpty()) {
				SelectItem item;
				
				for (Provider provider : listProviders) {
					item = new SelectItem(provider, provider.getProvidername());
					
					providersSelect.add(item);
					System.out.println("Aqui");
				}
			}
		}
		
		return providersSelect;
		
	}
	
}
