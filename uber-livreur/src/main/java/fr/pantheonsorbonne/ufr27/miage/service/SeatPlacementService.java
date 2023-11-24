package fr.pantheonsorbonne.ufr27.miage.service;

import fr.pantheonsorbonne.ufr27.miage.exception.CustomerNotFoundException;

public interface SeatPlacementService {
    String bookSeat(int venueId) throws CustomerNotFoundException.NoSeatAvailableException;
}
