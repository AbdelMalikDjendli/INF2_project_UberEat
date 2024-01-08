package fr.pantheonsorbonne.ufr27.miage.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

class ConfirmationCodeServiceImplTest {

    private ConfirmationCodeServiceImpl confirmationCodeService;

    @BeforeEach
    void setUp() {
        confirmationCodeService = new ConfirmationCodeServiceImpl();
    }

    @Test
    void testInitialIsGoodCodeIsNull() {
        assertNull(confirmationCodeService.isGoodCode());
    }

    @Test
    void testSetAndGetGoodCode() {
        confirmationCodeService.setGoodCode("123456");
        assertEquals("123456", confirmationCodeService.isGoodCode());
    }

    @Test
    void testSetAndGetAnotherGoodCode() {
        confirmationCodeService.setGoodCode("789012");
        assertEquals("789012", confirmationCodeService.isGoodCode());
    }

    @Test
    void testSetGoodCodeToNull() {
        confirmationCodeService.setGoodCode(null);
        assertNull(confirmationCodeService.isGoodCode());
    }
}
