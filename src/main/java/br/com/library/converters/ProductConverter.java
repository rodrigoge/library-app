package br.com.library.converters;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;
import javax.persistence.EntityManager;

import br.com.library.models.Product;
import br.com.library.models.User;
import br.com.library.repositories.ProductRepository;
import br.com.library.repositories.UserRepository;
import br.com.library.utils.DataConfiguration;

@FacesConverter(forClass = Product.class)
public class ProductConverter implements Converter{
	
	@SuppressWarnings("deprecation")
	@Override
	public Object getAsObject(FacesContext context, UIComponent component, String value) {
		Product product = null;
		EntityManager data = DataConfiguration.getEntityManager();
		
		//check the id of user for the insert
		try {
			if(value != null && !"".equals(value)) {
				product = new ProductRepository(data).index(new Long(value));
			}
			
			return product;
			
		}finally {
			data.close();
		}
	}

	@Override
	public String getAsString(FacesContext context, UIComponent component, Object value) {
		if(value != null) {
			Product product = ((Product) value);
			return product.getId() == null ? null : product.getId().toString();
		}
		
		return null;
	}

}
