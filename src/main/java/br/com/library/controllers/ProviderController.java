package br.com.library.controllers;

import java.io.IOException;
import java.io.Serializable;
import java.util.List;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.context.FacesContext;
import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;

import br.com.library.business.ProviderBusiness;
import br.com.library.business.exception.BusinessException;
import br.com.library.models.Provider;
import br.com.library.models.TypeUser;
import br.com.library.models.User;
import br.com.library.repositories.ProviderRepository;
import br.com.library.utils.DataConfiguration;

@ManagedBean
@RequestScoped
public class ProviderController implements Serializable {

	private static final long serialVersionUID = 1L;

	private Provider provider = new Provider();
	private Provider providerSelected = new Provider();
	private List<Provider> providers;
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
	
	public void search() {
		EntityManager data = DataConfiguration.getEntityManager();
		ProviderRepository providers = new ProviderRepository(data);
		this.providers = providers.all();
		data.close();
	}
	
	public void update() {
		EntityManager data = DataConfiguration.getEntityManager();
		EntityTransaction transaction = data.getTransaction();
		FacesContext faces = FacesContext.getCurrentInstance();
		
		try {
			transaction.begin();
			ProviderBusiness providerBusiness = new ProviderBusiness(new ProviderRepository(data));
			providerBusiness.save(provider);
			this.provider = new Provider();
			faces.addMessage(null, new FacesMessage("Atualizado com sucesso."));
			transaction.commit();
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
		
		ProviderBusiness providerBusiness = new ProviderBusiness(new ProviderRepository(data));
		
		try {
			transaction.begin();
			providerBusiness.remove(this.providerSelected);
			context.addMessage(null, new FacesMessage("Fornecedor excluído."));
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

	public List<Provider> getProviders() {
		return providers;
	}

	public void setProviders(List<Provider> providers) {
		this.providers = providers;
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

}
