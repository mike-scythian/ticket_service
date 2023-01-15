package test.tickets.service.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.stereotype.Repository;
import test.tickets.service.model.Ticket;

import java.util.List;

@RepositoryRestResource
@Repository
public interface TicketRepository extends CrudRepository<Ticket, Long> {
    List<Ticket> findAll();

}