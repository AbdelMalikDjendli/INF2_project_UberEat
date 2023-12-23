package fr.pantheonsorbonne.ufr27.miage.service;

import jakarta.enterprise.context.ApplicationScoped;

import java.util.List;

@ApplicationScoped
public class DkChoiceServiceImpl implements DkChoiceService {

    private int currentEstimation = 100;
    private String dkQueue;

    @Override
    public void setMinEstimation(int estimation) {
        if(estimation<currentEstimation){
            this.currentEstimation = estimation;
        }
    }

    @Override
    public String chooseFastestDarkKitchen(List<String> estimations) {

        if (estimations.size() == 1) {
            return estimations.get(0).split(":")[0]; // Retourne l'ID directement si une seule DK
        }

        String fastestDK = null;
        int fastestTime = Integer.MAX_VALUE;

        for (String est : estimations) {
            String[] parts = est.split(":");
            if (parts.length == 2) {
                String dkId = parts[0];
                int time = Integer.parseInt(parts[1]);

                if (time < fastestTime) {
                    fastestTime = time;
                    fastestDK = dkId;
                }
            }
        }


        return fastestDK; // Retourne l'ID de la Dark Kitchen la plus rapide
    }

    @Override
    public void setDkQueue(String name) {
        this.dkQueue = name;
    }

    @Override
    public int getMinEstimation(int estimation) {
        return this.currentEstimation;
    }

    @Override
    public String getDkQueue() {
        return this.dkQueue;
    }
}
