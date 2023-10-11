//package be.tsapasmi33.digitalcityairport;
//
//import be.tsapasmi33.digitalcityairport.models.entities.*;
//import be.tsapasmi33.digitalcityairport.models.entities.enums.FidelityStatus;
//import be.tsapasmi33.digitalcityairport.repositories.*;
//import jakarta.annotation.PostConstruct;
//import jakarta.persistence.EntityManager;
//import jakarta.persistence.PersistenceContext;
//import jakarta.persistence.Query;
//import lombok.AllArgsConstructor;
//import org.springframework.stereotype.Component;
//
//import java.time.LocalDate;
//import java.time.LocalDateTime;
//import java.util.ArrayList;
//
//@AllArgsConstructor
//@Component
//public class DataInitializer {
//
//    @PersistenceContext
//    private EntityManager entityManager;
//
//    @PostConstruct
//    public void init() {
//        Query query = entityManager.createQuery("""
//        INSERT INTO airplane_type (make, model, capacity)
//        VALUES
//            ('Boeing', '737 Max 300', 200),
//            ('Airbus', 'A320', 180),
//            ('Embraer', 'E175', 80),
//            ('Bombardier', 'CRJ900', 76),
//            ('Boeing', '747', 416),
//            ('Airbus', 'A380', 853),
//            ('Boeing', '777', 400),
//            ('Boeing', '787', 330),
//            ('Airbus', 'A350', 440),
//            ('Embraer', 'E190', 110),
//            ('Airbus', 'A330', 250),
//            ('Bombardier', 'Q400', 90),
//            ('Boeing', '737 Max 8', 178),
//            ('Boeing', '737 Max 9', 193),
//            ('Airbus', 'A321', 240),
//            ('Embraer', 'E145', 50),
//            ('Boeing', '767', 245),
//            ('Bombardier', 'CRJ700', 70),
//            ('Airbus', 'A319', 140),
//            ('Boeing', '737-800', 160),
//            ('Embraer', 'E135', 37),
//            ('Airbus', 'A330neo', 260),
//            ('Boeing', '757', 200),
//            ('Airbus', 'A300', 266),
//            ('Bombardier', 'Q300', 56),
//            ('Boeing', '737-700', 149),
//            ('Embraer', 'E120', 30),
//            ('Airbus', 'A340', 375),
//            ('Boeing', '727', 149)
//""");
//        query.executeUpdate();
//    }
//}
