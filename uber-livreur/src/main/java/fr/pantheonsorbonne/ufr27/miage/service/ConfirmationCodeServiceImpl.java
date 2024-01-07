package fr.pantheonsorbonne.ufr27.miage.service;

import jakarta.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class ConfirmationCodeServiceImpl implements ConfirmationCodeService {
    String isGoodCode;

    @Override
    public String isGoodCode() {
        return isGoodCode;
    }

    @Override
    public void setGoodCode(String goodCode) {
        isGoodCode = goodCode;
    }


}
