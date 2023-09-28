//package be.tsapasmi33.digitalcityairport.repositories;
//
//import be.tsapasmi33.digitalcityairport.models.entities.enums.FidelityStatus;
//import be.tsapasmi33.digitalcityairport.models.entities.Person;
//import jakarta.transaction.Transactional;
//import org.springframework.data.jpa.repository.JpaRepository;
//import org.springframework.data.jpa.repository.Modifying;
//import org.springframework.data.jpa.repository.Query;
//
//import java.util.List;
//import java.util.Optional;
//
//public interface PersonRepository extends JpaRepository<Person, Long> {
//    //    List<Person> findAllByFirstnameContainingOrLastnameContaining(String search, String search2);
//    @Query("""
//                SELECT p
//                FROM Person p
//                WHERE
//                    p.firstname LIKE %?1% OR
//                    p.lastname LIKE %?1%
//            """)
//    List<Person> findAllByNameContaining(String containing);
//
//    @Transactional
//    @Modifying
//    @Query("""
//             UPDATE Person p
//             SET p.status = :fidelity
//             WHERE p.id = :id
//            """)
//    void updateFidelity(long id, FidelityStatus fidelity);
//}
