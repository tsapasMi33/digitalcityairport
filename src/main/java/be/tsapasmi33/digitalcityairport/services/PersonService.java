package be.tsapasmi33.digitalcityairport.services;

import be.tsapasmi33.digitalcityairport.models.entities.Person;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

@Service
public class PersonService {
    private List<Person> people = new ArrayList<>();

    private int getIndexById(String id) {
        for (int i = 0; i < people.size(); i++) {
            if (people.get(i).getId().equals(id)) {
                return i;
            }
        }
        return -1;
    }

    public Person addPerson(Person person) {
        people.add(person);
        return person;
    }

    public Person getPerson(String id) {
        int index = getIndexById(id);
        if (index != -1) {
            return people.get(index);
        }
        return null;
    }

    public List<Person> getPersonIfNameLike(String ex) {
        List<Person> result = new ArrayList<>();
        for (Person p : people) {
            if (p.getFirstname().matches("("+ex+")+") || p.getLastname().matches("("+ex+")+")) {
                result.add(p);
            }
        }
        return result;
    }

    public List<Person> getPeople() {
        return people;
    }

    public Person updatePerson(String id, Person person) {
        int index = getIndexById(id);
        if (index != -1) {
            people.set(index, person);
            return person;
        }
        return null;
    }

    public void deletePerson(String id) {
        int index = getIndexById(id);
        if (index != -1) {
            people.remove(index);
        }
    }

}
