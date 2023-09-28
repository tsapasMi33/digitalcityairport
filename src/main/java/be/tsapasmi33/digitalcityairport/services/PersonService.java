package be.tsapasmi33.digitalcityairport.services;

import be.tsapasmi33.digitalcityairport.models.entities.FidelityStatus;
import be.tsapasmi33.digitalcityairport.models.entities.Person;

import java.util.List;

public interface PersonService extends CrudService<Person, Long> {
    List<Person> getWithNameContaining(String search);
    void updateFidelity(long id, FidelityStatus fidelity);
}
