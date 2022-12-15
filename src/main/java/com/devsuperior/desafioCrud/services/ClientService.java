package com.devsuperior.desafioCrud.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import com.devsuperior.desafioCrud.entities.Client;
import com.devsuperior.desafioCrud.repositories.ClientRepository;
import com.devsuperior.desafioCrud.services.exceptions.DatabaseException;
import com.devsuperior.desafioCrud.services.exceptions.ResourceNotFoundException;

import jakarta.persistence.EntityNotFoundException;

@Service
public class ClientService {
	
	@Autowired
	private ClientRepository repository;
	
	@Transactional(readOnly = true)
	public Client findById(Long id) {
		return repository.findById(id).orElseThrow(
				() -> new ResourceNotFoundException("Cliente não encontrado"));
	}
	
	@Transactional(readOnly = true)
	public Page<Client> findAll(Pageable pageable) {
		return repository.findAll(pageable);
	}
	
	@Transactional
	public Client insert(Client client) {
		return repository.save(client);
	}
	
	@Transactional
	public Client update(Long id, Client client) {
		try {
			Client updateClient = repository.getReferenceById(id);
			updateRegister(updateClient, client);
			return repository.save(updateClient);
		}
		catch (EntityNotFoundException e) {
			throw new ResourceNotFoundException("Cliente não encontrado");
		}
	}
	
	@Transactional(propagation = Propagation.SUPPORTS)
	public void delete(Long id) {
		try {
			repository.deleteById(id);
		}
		catch (EmptyResultDataAccessException e) {
			throw new ResourceNotFoundException("Cliente não encontrado");
		}
		catch (DataIntegrityViolationException e) {
			throw new DatabaseException("Falha na integridade referencial");
		}
		
		
	}
	
	private void updateRegister(Client updateClient, Client client) {
		updateClient.setName(client.getName());
		updateClient.setCpf(client.getCpf());
		updateClient.setIncome(client.getIncome());
		updateClient.setBirthDate(client.getBirthDate());
		updateClient.setChildren(client.getChildren());
	}
	
}
