package br.com.library.converters;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;
import javax.persistence.EntityManager;

import br.com.library.models.Provider;
import br.com.library.repositories.ProviderRepository;
import br.com.library.utils.DataConfiguration;

@FacesConverter(forClass = Provider.class)
public class ProviderConverter implements Converter{
	
	@SuppressWarnings("deprecation")
	@Override
	public Object getAsObject(FacesContext context, UIComponent component, String value) {
		Provider provider = null;
		EntityManager data = DataConfiguration.getEntityManager();
		
		try {
			if(value != null && !"".equals(value)) {
				provider = new ProviderRepository(data).index(new Long(value));
			}
			
			return provider;
		}finally {
			data.close();
		}
	}

	@Override
	public String getAsString(FacesContext context, UIComponent component, Object value) {
		if(value != null) {
			Provider provider = ((Provider) value);
			return provider.getId() == null ? null : provider.getId().toString();
		}
		
		return null;
	}

}
