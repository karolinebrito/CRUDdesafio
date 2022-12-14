package com.devsuperior.desafioCrud.controllers;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
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
	public List<ClientDto> findAll() {
		List<Client> clients = clientService.findAll();
		return clients.stream().map(this::convertToDto).collect(Collectors.toList());
	}

	private ClientDto convertToDto(Client client) {
		return modelMapper.map(client, ClientDto.class);
	}
}
