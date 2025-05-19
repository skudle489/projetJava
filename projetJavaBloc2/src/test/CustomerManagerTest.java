package test;

import business.CustomerManager;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class CustomerManagerTest {
    private CustomerManager customerManager;
    @BeforeEach
    void setUp() {
        customerManager = new CustomerManager();
    }

    @Test
    void customerExists() {
        assertTrue(customerManager.customerExists("jean.dupont@email.fr"));
    }
}