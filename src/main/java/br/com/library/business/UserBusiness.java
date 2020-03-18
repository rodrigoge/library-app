package br.com.library.business;

import java.io.Serializable;

import br.com.library.business.exception.BusinessException;
import br.com.library.models.User;
import br.com.library.repositories.UserRepository;

public class UserBusiness implements Serializable {

	private static final long serialVersionUID = 1L;
	
	private UserRepository users;
	
	public UserBusiness(UserRepository users) {
		this.users = users;
	}
	
	public void save(User user) throws BusinessException{
		this.users.update(user);
	}
	
	public void remove(User user) throws BusinessException{
		user = this.users.index(user.getId());
		this.users.delete(user);
	}

}
