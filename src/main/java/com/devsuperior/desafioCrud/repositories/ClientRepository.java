package com.devsuperior.desafioCrud.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.devsuperior.desafioCrud.entities.Client;

public interface ClientRepository extends JpaRepository<Client, Long> {

}
