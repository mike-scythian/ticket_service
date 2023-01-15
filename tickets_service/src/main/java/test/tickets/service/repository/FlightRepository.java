package test.tickets.service.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.stereotype.Repository;
import test.tickets.service.model.Flight;

import java.util.Optional;

@RepositoryRestResource
@Repository
public interface FlightRepository extends CrudRepository<Flight, Long> {
}