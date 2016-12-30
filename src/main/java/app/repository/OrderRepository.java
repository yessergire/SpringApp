package app.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import app.model.Customer;
import app.model.Order;

public interface OrderRepository extends JpaRepository<Order, Long> {
    public List<Order> findByCustomer(Customer customer);
}
