package be.tsapasmi33.digitalcityairport.services;

import be.tsapasmi33.digitalcityairport.models.entities.AirplaneType;
import be.tsapasmi33.digitalcityairport.models.entities.Pilot;

import java.time.LocalDateTime;

public interface PilotService extends CrudService<Pilot, Long> {
    void addLicence(long pilotId, AirplaneType airplaneType);

    Pilot getIfAvailable(long pilotId, LocalDateTime departure, LocalDateTime arrival);

    boolean checkLicence(long pilotId, AirplaneType airplaneType);
}
