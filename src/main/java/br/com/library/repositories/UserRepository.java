package br.com.library.repositories;

import java.io.Serializable;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;

import br.com.library.models.User;
import br.com.library.utils.DataConfiguration;

public class UserRepository implements Serializable {

	private static final long serialVersionUID = 1L;
	
	private EntityManager data = DataConfiguration.getEntityManager();
	
	public UserRepository (EntityManager data) {
		this.data = data;
	}
	
	//search for a user
	public User index(Long id) {
		return data.find(User.class, id);
	}
	
	//search for all users
	public List<User> all(){
		TypedQuery<User> query = data.createQuery("from user u order by u.id", User.class);
		return query.getResultList();
	}
	
	//add user 
	public void create(User user) {
		this.data.persist(user);
	}
	
	//updates the user if it already exists
	public void update(User user) {
		this.data.merge(user);
	}
	
	//remove user
	public void delete(User user) {
		this.data.remove(user);
	}
	
	

}
