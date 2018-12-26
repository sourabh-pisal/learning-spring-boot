package learning.springdatajpa.repository;

import learning.springdatajpa.domain.Person;
import learning.springdatajpa.domain.Ticket;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;
import java.util.Set;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.containsInAnyOrder;

@RunWith(SpringRunner.class)
@DataJpaTest
public class PersonRepositoryITest {
    @Autowired
    private TestEntityManager entityManager;
    @Autowired
    private PersonRepository personRepository;

    private Person[] people;
    private Ticket ticket;

    @Before
    public void setUp() {
        //Given
        ticket = saveTicket();
        people = savePeople();
        entityManager.flush();
    }

    @After
    public void tearDown() {
        //Delete Inserted Data
        deleteTicket();
        deletePerson();
        entityManager.flush();
    }

    @Test
    public void givenPeopleListPresentWhenFindAllThenShouldReturnPeople() {
        //When
        List<Person> actualPeople = personRepository.findAll();

        //Then
        assertThat(actualPeople, is(notNullValue()));
        assertThat(actualPeople, containsInAnyOrder(people));
    }

    private Ticket saveTicket() {
        return entityManager.persistAndFlush(new Ticket());
    }

    private Person[] savePeople() {
        Person personOne = new Person();
        personOne.setFirstName("FN1");
        personOne.setLastName("LN1");
        personOne.setTickets(Set.of(ticket));
        personOne = entityManager.persistAndFlush(personOne);

        Person personTwo = new Person();
        personTwo.setFirstName("FN2");
        personTwo.setLastName("LN2");
        personTwo.setTickets(Set.of(ticket));
        entityManager.persistAndFlush(personTwo);
        personTwo = entityManager.persistAndFlush(personTwo);

        return new Person[]{personOne, personTwo};
    }

    private void deletePerson() {
        for (Person person : people) {
            entityManager.remove(person);
        }
    }

    private void deleteTicket() {
        entityManager.remove(ticket);
    }

}
