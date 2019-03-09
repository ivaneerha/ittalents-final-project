package com.example.kinoarena.controllers;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


import com.example.kinoarena.model.Ticket;

@Repository
public interface TicketRepository extends JpaRepository<Ticket,Long>{

}
