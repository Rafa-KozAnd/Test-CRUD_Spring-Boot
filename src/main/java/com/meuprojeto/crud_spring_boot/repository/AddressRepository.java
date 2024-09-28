package com.meuprojeto.crud_spring_boot.repository;

import com.meuprojeto.crud_spring_boot.model.Address;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AddressRepository extends JpaRepository<Address, Long> {
}
