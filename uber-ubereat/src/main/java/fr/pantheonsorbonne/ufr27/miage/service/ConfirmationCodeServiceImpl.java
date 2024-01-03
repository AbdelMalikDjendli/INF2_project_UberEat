package fr.pantheonsorbonne.ufr27.miage.service;

import jakarta.enterprise.context.ApplicationScoped;

import java.util.Random;

@ApplicationScoped
public class ConfirmationCodeServiceImpl implements ConfirmationCodeService{
    String currentCode;

    public String getCurrentCode() {
        return currentCode;
    }

    private void setCurrentCode(String currentCode) {
        this.currentCode = currentCode;
    }

    @Override
    public String generateCode() {
        StringBuilder code = new StringBuilder();
        for(int i = 0;i<4;i++){
            Random random = new Random();
            int randomNumber = random.nextInt(10);
            code.append(Integer.toString(randomNumber));
        }
        setCurrentCode(code.toString());
        return code.toString();
    }
}
