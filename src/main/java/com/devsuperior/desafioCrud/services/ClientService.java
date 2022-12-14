package com.devsuperior.desafioCrud.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.devsuperior.desafioCrud.entities.Client;
import com.devsuperior.desafioCrud.repositories.ClientRepository;

@Service
public class ClientService {
	
	@Autowired
	private ClientRepository repository;
	
	@Transactional(readOnly = true)
	public Client findById(Long id) {
		return repository.findById(id).get();
	}
	
	@Transactional(readOnly = true)
	public List<Client> findAll() {
		return repository.findAll();
	}
	
}
