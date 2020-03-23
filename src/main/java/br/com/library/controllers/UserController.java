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
import javax.persistence.NoResultException;

import org.apache.commons.codec.digest.DigestUtils;

import br.com.library.business.UserBusiness;
import br.com.library.business.exception.BusinessException;
import br.com.library.models.TypeUser;
import br.com.library.models.User;
import br.com.library.repositories.UserRepository;
import br.com.library.utils.DataConfiguration;
import br.com.library.utils.JavaMail;

@ManagedBean
@RequestScoped
public class UserController implements Serializable {

	private static final long serialVersionUID = 1L;

	private User user = new User();
	private List<User> logged;
	private User username = new User();
	EntityManager data = DataConfiguration.getEntityManager();
	private UserRepository userRepository = new UserRepository(data);
	private JavaMail javaMail = new JavaMail();

	public void init() {
		if (this.user == null) {
			this.user = new User();
		}
	}

	public void save() throws IOException {
		EntityManager data = DataConfiguration.getEntityManager();
		EntityTransaction transaction = data.getTransaction();
		FacesContext context = FacesContext.getCurrentInstance();

		logged = userRepository.searchUsername(user.getUsername());

		if (logged.isEmpty()) {
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
			} finally {
				data.close();
			}
		} else {
			FacesContext.getCurrentInstance().addMessage(null,
					new FacesMessage(FacesMessage.SEVERITY_ERROR, "Nome de usuário já existe", "Erro ao cadastrar!"));
		}
	}

	public String recoveryPassword() {
		logged = userRepository.searchEmail(user.getEmail());

		FacesContext context = FacesContext.getCurrentInstance();

		if (logged.isEmpty()) {
			context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Email não existe", "Erro"));
			return null;

		} else {
			context.addMessage(null, new FacesMessage("Um email foi enviado para o " + user.getEmail()));
			javaMail.sendEmail(user.getEmail());

			return "";
		}
	}
	
	public void updatePassword() throws IOException, BusinessException {
		EntityManager data = DataConfiguration.getEntityManager();
		EntityTransaction transaction = data.getTransaction();
		FacesContext context = FacesContext.getCurrentInstance();

		username = userRepository.indexName(user.getUsername());
		
		if(username != null && username.getUsername().equals(user.getUsername())) {
			try {
				transaction.begin();
				UserBusiness userBusiness = new UserBusiness(new UserRepository(data));
				username.setPassword(DigestUtils.md5Hex(user.getPassword()));
				userBusiness.save(username);
				context.addMessage(null, new FacesMessage("Salvo com sucesso!"));
				transaction.commit();

			} catch (NoResultException e) {
				transaction.rollback();
				FacesMessage message = new FacesMessage(e.getMessage());
				message.setSeverity(FacesMessage.SEVERITY_ERROR);
				context.addMessage(null, new FacesMessage("Usuário não existe."));
			
			}
		} else {
			FacesContext.getCurrentInstance().addMessage(null,
					new FacesMessage(FacesMessage.SEVERITY_ERROR, "Nome de usuário já existe", "Erro ao cadastrar!"));
		}
		
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public User getUsername() {
		return username;
	}

	public void setUsername(User username) {
		this.username = username;
	}

	public TypeUser[] gettypeUser() {
		return TypeUser.values();
	}

}
