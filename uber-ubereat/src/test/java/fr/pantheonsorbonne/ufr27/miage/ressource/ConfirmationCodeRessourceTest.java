package fr.pantheonsorbonne.ufr27.miage.ressource;

import fr.pantheonsorbonne.ufr27.miage.resources.ConfirmationCodeRessource;
import fr.pantheonsorbonne.ufr27.miage.service.ConfirmationCodeService;
import jakarta.ws.rs.core.Response;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

class ConfirmationCodeRessourceTest {

    @Mock
    private ConfirmationCodeService confirmationCodeService;

    @InjectMocks
    private ConfirmationCodeRessource confirmationCodeRessource;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testGetNewCode() {
        String generatedCode = "1234";
        when(confirmationCodeService.generateCode()).thenReturn(generatedCode);

        Response response = confirmationCodeRessource.getNewCode();

        assertNotNull(response, "La réponse ne doit pas être null.");
        assertEquals(Response.Status.OK.getStatusCode(), response.getStatus(), "Le statut de la réponse doit être 200 OK.");
        assertEquals(generatedCode, response.getEntity(), "L'entité de la réponse doit être le code généré.");
    }
}
