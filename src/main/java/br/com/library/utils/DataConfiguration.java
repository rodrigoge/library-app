package br.com.library.utils;

import javax.persistence.Persistence;

public class DataConfiguration {
	public static void main(String[] args) {
        Persistence.createEntityManagerFactory("libraryPU");
    }
	
	
}
