package br.com.library.controllers;

import java.io.Serializable;
import java.util.List;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.persistence.EntityManager;
import javax.servlet.http.HttpSession;

import br.com.library.models.User;
import br.com.library.repositories.UserRepository;
import br.com.library.utils.DataConfiguration;

@ManagedBean
@SessionScoped
public class LoginController implements Serializable {

	private static final long serialVersionUID = 1L;
	
	private User user = new User();
	EntityManager data = DataConfiguration.getEntityManager();
	private UserRepository userRepository = new UserRepository(data);
	private List<User> allUsers;
	
	
	public void init() {
		if(this.user == null) {
			this.user = new User();
		}
	}
	
	public String login() {

		allUsers = userRepository.all();
		
		if(allUsers.isEmpty()) {
			FacesContext.getCurrentInstance().addMessage(null, 
					new FacesMessage(FacesMessage.SEVERITY_ERROR, 
							"Nome de usuário ou senha incorretos.", "Erro"));
			return null;
		} else {
			HttpSession session = (HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(false);
			
			if(session != null) {
				session.setAttribute("user", allUsers);
			}
			
			return "Home.xhtml";
		}
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public UserRepository getUserRepository() {
		return userRepository;
	}

	public void setUserRepository(UserRepository userRepository) {
		this.userRepository = userRepository;
	}

	public List<User> getAllUsers() {
		return allUsers;
	}
	
}
