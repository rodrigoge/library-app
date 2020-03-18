package br.com.library.converters;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;
import javax.persistence.EntityManager;

import br.com.library.models.User;
import br.com.library.repositories.UserRepository;
import br.com.library.utils.DataConfiguration;

@FacesConverter(forClass = User.class)
public class UserCoverter implements Converter{

	@SuppressWarnings("deprecation")
	@Override
	public Object getAsObject(FacesContext context, UIComponent component, String value) {
		User user = null;
		EntityManager data = DataConfiguration.getEntityManager();
		
		//check the id of user for the insert
		try {
			if(value != null && !"".equals(value)) {
				user = new UserRepository(data).index(new Long(value));
			}
			
			return user;
		}finally {
			data.close();
		}
	}

	@Override
	public String getAsString(FacesContext context, UIComponent component, Object value) {
		//converting user id for type String
		if(value != null) {
			User user = ((User) value);
			return user.getId() == null ? null : user.getId().toString();
		}
		
		return null;
	}
	
}
