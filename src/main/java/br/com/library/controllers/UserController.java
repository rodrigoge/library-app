package br.com.library.controllers;

import java.io.IOException;
import java.io.Serializable;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.context.FacesContext;
import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;

import org.apache.commons.codec.digest.DigestUtils;

import br.com.library.business.UserBusiness;
import br.com.library.business.exception.BusinessException;
import br.com.library.models.TypeUser;
import br.com.library.models.User;
import br.com.library.repositories.UserRepository;
import br.com.library.utils.DataConfiguration;

@ManagedBean
@RequestScoped
public class UserController implements Serializable {

	private static final long serialVersionUID = 1L;
	
	private User user = new User();
	
	public void init() {
		if(this.user == null) {
			this.user = new User();
		}
	}
	
	public void save() throws IOException{
		EntityManager data = DataConfiguration.getEntityManager();
		EntityTransaction transaction = data.getTransaction();
		FacesContext context = FacesContext.getCurrentInstance();
		
		try {
			transaction.begin();
			UserBusiness userBusiness = new UserBusiness(new UserRepository(data));
			user.setPassword(DigestUtils.md5Hex(user.getPassword()));
			userBusiness.save(user);
			this.user = new User();
			context.addMessage(null, new FacesMessage("Salvo com sucesso!"));
			transaction.commit();
		} catch (BusinessException e) {
			transaction.rollback();
			FacesMessage message = new FacesMessage(e.getMessage());
			message.setSeverity(FacesMessage.SEVERITY_ERROR);
			context.addMessage(null, message);
		}finally {
			data.close();
		}
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}
	
	public TypeUser[] gettypeUser() {
		return TypeUser.values();
	}

}
