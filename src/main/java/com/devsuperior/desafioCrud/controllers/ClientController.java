package com.devsuperior.desafioCrud.controllers;

import java.net.URI;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.devsuperior.desafioCrud.dto.ClientDto;
import com.devsuperior.desafioCrud.entities.Client;
import com.devsuperior.desafioCrud.services.ClientService;

@RestController
@RequestMapping(value = "/clients")
public class ClientController {
	
	@Autowired
	private ClientService clientService;

	@Autowired
	private ModelMapper modelMapper;

	@GetMapping(value = "/{id}")
	@ResponseBody
	public ResponseEntity<ClientDto> findById(@PathVariable("id") Long id) {
		return ResponseEntity.ok(convertToDto(clientService.findById(id)));
	}

	@GetMapping
	@ResponseBody
	public ResponseEntity<Page<ClientDto>> findAll(Pageable pageable) {
		Page<Client> clients = clientService.findAll(pageable);
		return ResponseEntity.ok(clients.map(this::convertToDto));
	}
	
	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	@ResponseBody
    public ResponseEntity<ClientDto> insert(@RequestBody ClientDto clientDto) {
		Client client = convertToEntity(clientDto);
		Client clientCreated = clientService.insert(client);
		URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
				.buildAndExpand(convertToDto(clientCreated).getId()).toUri();
		return ResponseEntity.created(uri).body(convertToDto(clientCreated));
	}
	
	@PutMapping(value = "/{id}")
    @ResponseStatus(HttpStatus.OK)
	public ResponseEntity<ClientDto> update(@PathVariable("id") Long id, @RequestBody ClientDto clientDto) {
		Client client = convertToEntity(clientDto);
		return ResponseEntity.ok(convertToDto(clientService.update(id, client)));
	}
	
	@DeleteMapping(value = "/{id}")
    @ResponseStatus(HttpStatus.OK)
	public ResponseEntity<Void> delete(@PathVariable("id") Long id) {
		clientService.delete(id);
		return ResponseEntity.noContent().build();
	}
	
	private ClientDto convertToDto(Client client) {
		return modelMapper.map(client, ClientDto.class);
	}
	
	private Client convertToEntity(ClientDto clientDto) {
		return modelMapper.map(clientDto, Client.class);
	}
}
