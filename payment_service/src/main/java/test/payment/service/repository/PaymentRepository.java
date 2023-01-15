package test.payment.service.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.stereotype.Repository;
import test.payment.service.model.Payment;

import java.util.List;

@RepositoryRestResource
@Repository
public interface PaymentRepository extends CrudRepository<Payment, Long> {
}