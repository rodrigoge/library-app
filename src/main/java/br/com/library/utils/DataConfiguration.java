package br.com.library.utils;

import javax.annotation.PostConstruct;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

public class DataConfiguration {
	public static void main(String[] args) {
		Persistence.createEntityManagerFactory("libraryPU");
	}
	
	@Inject
    private PersistenceProperties properties;
	
	@PostConstruct
    public void postConstruct() {
        DataConfiguration.factory = Persistence.createEntityManagerFactory("libraryPU", properties.get());
    }

	private static EntityManagerFactory factory ;
	
	static {
		factory = Persistence.createEntityManagerFactory("libraryPU");
	}

	public static EntityManager getEntityManager() {
		return factory.createEntityManager();
	}

}
