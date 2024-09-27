package com.meuprojeto.controller;

import com.meuprojeto.model.Address;
import com.meuprojeto.repository.AddressRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/addresses")
public class AddressController {

    @Autowired
    private AddressRepository addressRepository;

    @GetMapping
    public ResponseEntity<List<Address>> getAllAddresses() {
        List<Address> addresses = addressRepository.findAll();
        return ResponseEntity.ok(addresses);
    }

    @PostMapping
    public ResponseEntity<Address> createAddress(@RequestBody Address address) {
        Address savedAddress = addressRepository.save(address);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedAddress);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Address> updateAddress(@PathVariable Long id, @RequestBody Address addressDetails) {
        Address address = addressRepository.findById(id).orElse(null);

        if (address == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }

        address.setStreet(addressDetails.getStreet());
        address.setCity(addressDetails.getCity());
        address.setCountry(addressDetails.getCountry());

        Address updatedAddress = addressRepository.save(address);
        return ResponseEntity.ok(updatedAddress);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteAddress(@PathVariable Long id) {
        Address address = addressRepository.findById(id).orElse(null);

        if (address == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }

        addressRepository.deleteById(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
}
