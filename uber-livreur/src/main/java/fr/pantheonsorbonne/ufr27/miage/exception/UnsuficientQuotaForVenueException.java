package fr.pantheonsorbonne.ufr27.miage.exception;

public class UnsuficientQuotaForVenueException extends Throwable {
    public UnsuficientQuotaForVenueException(int venueId) {
        super("not enough quota for venue " + venueId);
    }
}
