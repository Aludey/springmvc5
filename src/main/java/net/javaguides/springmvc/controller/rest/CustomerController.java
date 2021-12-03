package net.javaguides.springmvc.controller.rest;

import net.javaguides.springmvc.entity.Customer;
import net.javaguides.springmvc.exception.ResourceNotFoundException;
import net.javaguides.springmvc.service.CustomerService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController("rest")
@RequestMapping("/api/v1")
public class CustomerController {

    private static final Logger LOG = LoggerFactory.getLogger(CustomerController.class);

    @Autowired
    private CustomerService customerService;

    @GetMapping("/customers")
    @ResponseBody
    public List<Customer> listCustomers() {
        LOG.debug("inside show customer-rest handler method");
        return customerService.getCustomers();
    }

    // Save
    // returns 201 instead of 200
    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping("/customers")
    @ResponseBody
    public Customer newCustomer(@RequestBody Customer theCustomer) {
        return customerService.saveCustomer(theCustomer);
    }

    // Find
    @ResponseStatus(HttpStatus.FOUND)
    @GetMapping("/customers/{id}")
    public Customer findOne(@PathVariable Integer id) throws ResourceNotFoundException {
        return customerService.getCustomer(id);
    }

    // Update
    @ResponseStatus(HttpStatus.OK)
    @PutMapping("/customers/{id}")
    public Customer updateOne(@PathVariable Integer id, @RequestBody Customer updatedCustomer) throws ResourceNotFoundException {
        Customer oldCustomer = customerService.getCustomer(id);
        oldCustomer.setEmail(updatedCustomer.getEmail());
        oldCustomer.setFirstName(updatedCustomer.getFirstName());
        oldCustomer.setLastName(updatedCustomer.getLastName());

        return customerService.saveCustomer(oldCustomer);
    }

    // Remove
    @ResponseStatus(HttpStatus.OK)
    @DeleteMapping("/customers/{id}")
    public void deleteOne(@PathVariable Integer id) throws ResourceNotFoundException {
        customerService.deleteCustomer(id);
    }
}