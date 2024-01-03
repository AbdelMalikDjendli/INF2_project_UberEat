package fr.pantheonsorbonne.ufr27.miage.service;

public interface ConfirmationCodeService {
    String isGoodCode();
    void setGoodCode(String goodCode);
}
