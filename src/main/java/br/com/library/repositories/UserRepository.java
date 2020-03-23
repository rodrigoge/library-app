package br.com.library.repositories;

import java.io.Serializable;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.TypedQuery;

import br.com.library.models.User;
import br.com.library.utils.DataConfiguration;

public class UserRepository implements Serializable {

	private static final long serialVersionUID = 1L;

	private EntityManager data = DataConfiguration.getEntityManager();

	public UserRepository(EntityManager data) {
		this.data = data;
	}

	// search for a user
	public User index(Long id) {
		return data.find(User.class, id);
	}

	// search for all users
	public List<User> all() {
		TypedQuery<User> query = data.createQuery("from User u order by u.id", User.class);
		return query.getResultList();
	}

	// add user
	public void create(User user) {
		this.data.persist(user);
	}

	// updates the user if it already exists
	public void update(User user) {
		this.data.merge(user);
	}

	// remove user
	public void delete(User user) {
		this.data.remove(user);
	}

	// search for users from login
	public List<User> searchLogin(String username, String password, String typeuser) {
		TypedQuery<User> query = data.createQuery(
				"select u from User u where u.username = :username and u.password = :password and u.typeuser = :typeuser",
				User.class).setParameter("username", username).setParameter("password", password)
				.setParameter("typeuser", typeuser);
		return query.getResultList();
	}

	// search for username from insert
	public List<User> searchUsername(String username) {
		TypedQuery<User> query = data.createQuery("select u from User u where u.username = :username", User.class)
				.setParameter("username", username);
		return query.getResultList();
	}

	// search for user from email
	public List<User> searchEmail(String email) {
		TypedQuery<User> query = data.createQuery("select u from User u where u.email = :email", User.class)
				.setParameter("email", email);
		return query.getResultList();
	}

	//search for a user
	public User indexName(String username) {
		TypedQuery<User> query = data.createQuery("select u from User u where u.username = :username", User.class)
				.setParameter("username", username);
		try {
			return query.getSingleResult();
		} catch (NoResultException e) {
			e.printStackTrace();
			return null;
		}
	}
	

}
