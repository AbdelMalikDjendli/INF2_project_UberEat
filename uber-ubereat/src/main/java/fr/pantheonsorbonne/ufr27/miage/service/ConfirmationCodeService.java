package fr.pantheonsorbonne.ufr27.miage.service;

public interface ConfirmationCodeService {
    String generateCode();

    String getCurrentCode();
}
