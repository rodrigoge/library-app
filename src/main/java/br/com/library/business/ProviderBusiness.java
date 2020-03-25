package br.com.library.business;

import java.io.Serializable;

import br.com.library.business.exception.BusinessException;
import br.com.library.models.Provider;
import br.com.library.repositories.ProviderRepository;

public class ProviderBusiness implements Serializable{

	private static final long serialVersionUID = 1L;
	
	private ProviderRepository providers;
	
	public ProviderBusiness (ProviderRepository providers) {
		this.providers = providers;
	}
	
	public void save(Provider provider) throws BusinessException{
		this.providers.update(provider);
	}
	
	public void remove(Provider provider) throws BusinessException{
		provider = this.providers.index(provider.getId());
		this.providers.delete(provider);
	}

}
