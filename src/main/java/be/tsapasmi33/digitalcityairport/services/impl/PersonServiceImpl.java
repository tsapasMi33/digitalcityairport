package be.tsapasmi33.digitalcityairport.services.impl;

import be.tsapasmi33.digitalcityairport.models.entities.FidelityStatus;
import be.tsapasmi33.digitalcityairport.models.entities.Person;
import be.tsapasmi33.digitalcityairport.repositories.PersonRepository;
import be.tsapasmi33.digitalcityairport.services.PersonService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@AllArgsConstructor
@Service
public class PersonServiceImpl implements PersonService {
    private PersonRepository personRepository;


    @Override
    public List<Person> getAll() {
        return personRepository.findAll();
    }

    @Override
    public Person getOne(Long id) {
        return personRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("no resource with this ID"));
    }

    @Override
    public void insert(Person entity) {
        entity.setId(0L);
        personRepository.save(entity);
    }

    @Override
    public void delete(Long id) {
        personRepository.deleteById(id);
    }

    @Override
    public Person update(Long id, Person entity) {
        if( !personRepository.existsById(id) )
            throw new IllegalArgumentException("no resource with this ID");

        entity.setId(id);
        return personRepository.save(entity);
    }

    @Override
    public List<Person> getWithNameContaining(String search) {
        return personRepository.findAllByNameContaining(search);
    }

    @Override
    public void updateFidelity(long id, FidelityStatus fidelity) {
        personRepository.updateFidelity(id, fidelity);
    }
}
