package fr.pantheonsorbonne.ufr27.miage.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class ConfirmationCodeServiceImplTest {

    private ConfirmationCodeServiceImpl confirmationCodeService;

    @BeforeEach
    void setUp() {
        confirmationCodeService = new ConfirmationCodeServiceImpl();
    }

    @Test
    void testGenerateCode() {
        String code = confirmationCodeService.generateCode();

        assertNotNull(code, "Le code généré ne doit pas être null.");
        assertEquals(4, code.length(), "Le code généré doit avoir une longueur de 4.");
        assertTrue(code.matches("\\d{4}"), "Le code généré doit être composé de 4 chiffres.");
    }

    @Test
    void testGetCurrentCode() {
        String generatedCode = confirmationCodeService.generateCode();
        String currentCode = confirmationCodeService.getCurrentCode();

        assertNotNull(currentCode, "Le code courant ne doit pas être null.");
        assertEquals(generatedCode, currentCode, "Le code courant doit correspondre au dernier code généré.");
    }
}
