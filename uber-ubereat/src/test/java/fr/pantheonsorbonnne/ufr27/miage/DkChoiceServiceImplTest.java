package fr.pantheonsorbonnne.ufr27.miage;

import fr.pantheonsorbonne.ufr27.miage.service.DkChoiceServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class DkChoiceServiceImplTest {

    private DkChoiceServiceImpl dkChoiceService;

    @BeforeEach
    void setUp() {
        dkChoiceService = new DkChoiceServiceImpl();
    }

    @Test
    void testSetAndGetMinEstimation() {
        int testEstimation = 50;
        dkChoiceService.setMinEstimation(testEstimation);
        assertEquals(testEstimation, dkChoiceService.getMinEstimation(), "La valeur de l'estimation minimale doit correspondre à celle définie.");
    }

    @Test
    void testIncrementNumberOfEstimation() {
        dkChoiceService.setNumberOfEstimation();
        dkChoiceService.setNumberOfEstimation();
        assertEquals(2, dkChoiceService.getNumberOfEstimation(), "Le nombre d'estimations doit être incrémenté correctement.");
    }

    @Test
    void testSetAndGetDkName() {
        String testDkName = "TestDK";
        dkChoiceService.setDkName(testDkName);
        assertEquals(testDkName, dkChoiceService.getDkName(), "Le nom du Dark Kitchen doit correspondre à celui défini.");
    }

    @Test
    void testResetEstimations() {
        dkChoiceService.setMinEstimation(30);
        dkChoiceService.setNumberOfEstimation();
        dkChoiceService.resetEstimations();

        assertEquals(100, dkChoiceService.getMinEstimation(), "L'estimation minimale doit être réinitialisée à 100.");
        assertEquals(0, dkChoiceService.getNumberOfEstimation(), "Le nombre d'estimations doit être réinitialisé à 0.");
    }
}
