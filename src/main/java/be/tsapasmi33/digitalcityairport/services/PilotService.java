package be.tsapasmi33.digitalcityairport.services;

import be.tsapasmi33.digitalcityairport.models.entities.Pilot;

public interface PilotService extends CrudService<Pilot, Long> {
    void addLicence(long pilotId, long airplaneTypeId);
}
