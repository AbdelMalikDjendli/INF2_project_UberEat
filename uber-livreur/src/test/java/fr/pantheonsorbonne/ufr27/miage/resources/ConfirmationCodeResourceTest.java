package fr.pantheonsorbonne.ufr27.miage.resources;

import fr.pantheonsorbonne.ufr27.miage.camel.OrderGateway;
import fr.pantheonsorbonne.ufr27.miage.dto.CodeDTO;
import fr.pantheonsorbonne.ufr27.miage.ressource.ConfirmationCodeRessource;
import fr.pantheonsorbonne.ufr27.miage.service.ConfirmationCodeService;
import fr.pantheonsorbonne.ufr27.miage.service.DeliveryManService;
import jakarta.ws.rs.core.Response;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

public class ConfirmationCodeResourceTest {

    @Mock
    private OrderGateway orderGateway;

    @Mock
    private ConfirmationCodeService confirmationCodeService;

    @Mock
    private DeliveryManService deliveryManService;

    @InjectMocks
    private ConfirmationCodeRessource confirmationCodeResource;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testConfirmDeliveryWithGoodCode() throws Exception {
        CodeDTO codeDTO = new CodeDTO("1234");

        // Défini le comportement simulé du mock ConfirmationCodeService
        when(confirmationCodeService.isGoodCode()).thenReturn("true");

        // Teste la méthode confirmDelivery
        Response response = confirmationCodeResource.confirmDelivery(codeDTO);

        // Vérifie que la méthode sendConfirmationCode du mock OrderGateway a été appelée
        verify(orderGateway).sendConfirmationCode("1234");

        // Vérifie que la méthode setDeliveryManIsAvaible du mock DeliveryManService a été appelée
        verify(deliveryManService).setDeliveryManIsAvaible(anyLong(), eq(true));

        // Vérifie que le statut de la réponse est accepté (202)
        assertEquals(Response.Status.ACCEPTED.getStatusCode(), response.getStatus());
    }
}