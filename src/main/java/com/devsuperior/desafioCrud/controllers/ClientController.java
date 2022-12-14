package com.devsuperior.desafioCrud.controllers;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

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
	public ClientDto findById(@PathVariable("id") Long id) {
		return convertToDto(clientService.findById(id));
	}

	@GetMapping
	@ResponseBody
	public Page<ClientDto> findAll(Pageable pageable) {
		Page<Client> clients = clientService.findAll(pageable);
		return clients.map(this::convertToDto);
	}

	private ClientDto convertToDto(Client client) {
		return modelMapper.map(client, ClientDto.class);
	}
}
