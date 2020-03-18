package br.com.library.utils;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

public class DataConfiguration {
	public static void main(String[] args) {
		Persistence.createEntityManagerFactory("libraryPU");
	}

	private static EntityManagerFactory factory ;
	
	static {
		factory = Persistence.createEntityManagerFactory("libraryPU");
	}

	public static EntityManager getEntityManager() {
		return factory.createEntityManager();
	}

}
