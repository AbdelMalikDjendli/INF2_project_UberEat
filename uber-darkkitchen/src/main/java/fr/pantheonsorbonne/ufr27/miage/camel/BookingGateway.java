package fr.pantheonsorbonne.ufr27.miage.camel;


import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;

@ApplicationScoped
public class BookingGateway {

    @Inject
    BookingService bookingService;
/*
    public Booking book(Booking bookingRequest) throws UnsuficientQuotaForVenueException {

        return bookingService.book(bookingRequest);
    }

 */


}

