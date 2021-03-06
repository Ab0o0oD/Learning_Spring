package com.fredrikpedersen.springmvcrest.controllers.v1;

import com.fredrikpedersen.springmvcrest.api.v1.model.customer.CustomerDTO;
import com.fredrikpedersen.springmvcrest.api.v1.model.customer.CustomerListDTO;
import com.fredrikpedersen.springmvcrest.services.customer.CustomerService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

/**
 * @author Fredrik Pedersen
 * @version 1.0
 * @since 16/02/2020 at 17:58
 */

@Controller
@RequestMapping(CustomerController.BASE_URL)
public class CustomerController {

    public static final String BASE_URL = "/api/v1/customers/";
    private final CustomerService customerService;

    public CustomerController(final CustomerService customerService) {
        this.customerService = customerService;
    }

    @GetMapping
    public ResponseEntity<CustomerListDTO> getAllCustomers() {
        return new ResponseEntity<>(new CustomerListDTO(customerService.getAllCustomers()), HttpStatus.OK);
    }

    @GetMapping("{id}")
    public ResponseEntity<CustomerDTO> getCustomerByName(@PathVariable final Long id) {
        return new ResponseEntity<>(
                customerService.getCustomerById(id), HttpStatus.OK
        );
    }

    @PostMapping
    public ResponseEntity<CustomerDTO> createNewCustomer(@RequestBody final CustomerDTO customerDTO) {
        return new ResponseEntity<>(customerService.createNewCustomer(customerDTO),
                HttpStatus.CREATED);
    }

    @PutMapping("{id}")
    public ResponseEntity<CustomerDTO> updateCustomer(@PathVariable final Long id, @RequestBody final CustomerDTO customerDTO){
        return new ResponseEntity<>(customerService.saveCustomerByDTO(id, customerDTO),
                HttpStatus.OK);
    }

    @PatchMapping("{id}")
    public ResponseEntity<CustomerDTO> patchCustomer(@PathVariable final Long id, @RequestBody final CustomerDTO customerDTO) {
        return new ResponseEntity<>(customerService.patchCustomer(id, customerDTO),
                HttpStatus.OK);
    }

    @DeleteMapping("{id}")
    public ResponseEntity<Void> deleteCustomer(@PathVariable final Long id) {
        customerService.deleteCustomerById(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
