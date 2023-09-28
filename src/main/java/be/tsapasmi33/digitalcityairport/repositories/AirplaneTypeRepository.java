package be.tsapasmi33.digitalcityairport.repositories;

import be.tsapasmi33.digitalcityairport.models.entities.AirplaneType;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AirplaneTypeRepository extends JpaRepository<AirplaneType, Long> {
}
