package fr.pantheonsorbonne.ufr27.miage.service;


import fr.pantheonsorbonne.ufr27.miage.dto.Booking;
import fr.pantheonsorbonne.ufr27.miage.exception.UnsuficientQuotaForVenueException;

public interface BookingService {

    Booking book(Booking booking) throws UnsuficientQuotaForVenueException;
}
