package br.com.library.controllers;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.persistence.EntityManager;
import javax.servlet.http.HttpSession;

import org.apache.commons.codec.digest.DigestUtils;

import br.com.library.models.TypeUser;
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
	private List<User> allUsers = new ArrayList<User>();
	private String username = "";
	
	public void init() {
		if (this.user == null) {
			this.user = new User();
		}
	}

	public String login() {
	
		allUsers = userRepository.searchLogin(user.getUsername(), DigestUtils.md5Hex(user.getPassword()), user.getTypeuser());
		
		if (allUsers.isEmpty()) {
			FacesContext.getCurrentInstance().addMessage(null,
					new FacesMessage(FacesMessage.SEVERITY_ERROR, "Nome de usuário ou senha incorretos.", "Erro"));
			return null;
		} else {
			HttpSession session = (HttpSession) FacesContext.getCurrentInstance().getExternalContext()
					.getSession(false);

			if (session != null) {
				session.setAttribute("username", allUsers);
			}
			
			return "/Home.faces?faces-redirect=true";
		}
	}
	
	public boolean isLogged() {
		if (this.user.getTypeuser().equals("ADMINISTRADOR")) {
			return true;
		} else
			return false;
	}

	public String logout() {
		FacesContext.getCurrentInstance().getExternalContext().invalidateSession();
		return "/Login?faces-redirect=true";
	}

	public User getUser() {
		if(user == null) {
			user = new User();
		}
		
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

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public TypeUser[] gettypeUser() {
		return TypeUser.values();
	}

}
