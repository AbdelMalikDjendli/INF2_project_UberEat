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

import java.util.concurrent.CompletableFuture;

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

        // Créez un CompletableFuture pour simuler le comportement asynchrone
        CompletableFuture<Void> confirmationFuture = new CompletableFuture<>();

        // Définissez le comportement simulé du mock ConfirmationCodeService
        when(confirmationCodeService.isGoodCode()).thenReturn("true");

        // Définissez le comportement du mock CompletableFuture
        doAnswer(invocation -> {
            confirmationFuture.complete(null);
            return null;
        }).when(confirmationFuture).thenRun(any());

        // Testez la méthode confirmDelivery
        Response response = confirmationCodeResource.confirmDelivery(codeDTO);

        // Vérifiez que la méthode sendConfirmationCode du mock OrderGateway a été appelée
        verify(orderGateway).sendConfirmationCode("1234");

        // Attendez l'achèvement du traitement asynchrone
        confirmationFuture.get();

        // Vérifiez que la méthode setDeliveryManIsAvaible du mock DeliveryManService a été appelée
        verify(deliveryManService).setDeliveryManIsAvaible(any(), eq(true));

        // Vérifiez que le statut de la réponse est accepté (202)
        assertEquals(202, response.getStatus());

    }

}
