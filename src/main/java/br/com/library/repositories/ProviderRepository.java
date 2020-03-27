package br.com.library.repositories;

import java.io.Serializable;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;

import br.com.library.models.Provider;
import br.com.library.utils.DataConfiguration;

public class ProviderRepository implements Serializable {

	private static final long serialVersionUID = 1L;

	private EntityManager data = DataConfiguration.getEntityManager();
	
	public ProviderRepository(EntityManager data) {
		this.data = data;
	}
	
	public Provider index(Long id) {
		return data.find(Provider.class, id);
	}
	
	public List<Provider> all(){
		TypedQuery<Provider> query = data.createQuery("from Provider p order by p.id", Provider.class);
		return query.getResultList();
	}
	
	public void create(Provider provider) {
		this.data.persist(provider);
	}
	
	public void update(Provider provider) {
		this.data.merge(provider);
	}
	
	public void delete(Provider provider) {
		this.data.remove(provider);
	}
	
	public List<Provider> searchProvider(String providername) {
		TypedQuery<Provider> query = data.createQuery("select p from Provider p where p.providername = :providername", Provider.class)
				.setParameter("providername", providername);
		return query.getResultList();
	}
	
	
	
}
