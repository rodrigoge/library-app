package br.com.library.controllers;

import java.io.IOException;
import java.io.Serializable;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.context.FacesContext;
import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;

import br.com.library.business.ProviderBusiness;
import br.com.library.business.exception.BusinessException;
import br.com.library.models.Provider;
import br.com.library.repositories.ProviderRepository;
import br.com.library.utils.DataConfiguration;

@ManagedBean
@RequestScoped
public class CreateProviderController implements Serializable {

	private static final long serialVersionUID = 1L;

	private Provider provider = new Provider();
	private Provider providerSelected = new Provider();
	EntityManager data = DataConfiguration.getEntityManager();
	private ProviderRepository providerRepository = new ProviderRepository(data);
	
	public void init() {
		if(this.provider == null) {
			this.provider = new Provider();
		}
	}
	
	public void save() throws IOException {
		EntityManager data = DataConfiguration.getEntityManager();
		EntityTransaction transaction = data.getTransaction();
		FacesContext context = FacesContext.getCurrentInstance();
		
		try {
			transaction.begin();
			ProviderBusiness providerBusiness = new ProviderBusiness(new ProviderRepository(data));
			providerBusiness.save(provider);
			this.provider = new Provider();
			context.addMessage(null, new FacesMessage("Fornecedor salvo com sucesso."));
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

	public Provider getProvider() {
		return provider;
	}

	public void setProvider(Provider provider) {
		this.provider = provider;
	}

	public EntityManager getData() {
		return data;
	}

	public void setData(EntityManager data) {
		this.data = data;
	}

	public ProviderRepository getProviderRepository() {
		return providerRepository;
	}

	public void setProviderRepository(ProviderRepository providerRepository) {
		this.providerRepository = providerRepository;
	}

	public Provider getProviderSelected() {
		return providerSelected;
	}

	public void setProviderSelected(Provider providerSelected) {
		this.providerSelected = providerSelected;
	}
	
	

}
