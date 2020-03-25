package br.com.library.business;

import java.io.Serializable;

import br.com.library.business.exception.BusinessException;
import br.com.library.models.Product;
import br.com.library.repositories.ProductRepository;

public class ProductBusiness implements Serializable {

	private static final long serialVersionUID = 1L;
	
	private ProductRepository products;
	
	public ProductBusiness(ProductRepository products) {
		this.products = products;
	}
	
	public void save(Product product) throws BusinessException{
		this.products.update(product);
	}
	
	public void remove(Product product) throws BusinessException{
		product = this.products.index(product.getId());
		this.products.delete(product);
	}

}
