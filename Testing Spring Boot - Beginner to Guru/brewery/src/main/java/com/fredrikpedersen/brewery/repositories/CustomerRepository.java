package com.fredrikpedersen.brewery.repositories;

import com.fredrikpedersen.brewery.domain.Customer;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface CustomerRepository extends JpaRepository<Customer, UUID> {
}