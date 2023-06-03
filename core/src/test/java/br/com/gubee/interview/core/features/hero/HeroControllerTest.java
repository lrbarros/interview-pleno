package br.com.gubee.interview.core.features.hero;

import br.com.gubee.interview.enums.RaceEnum;
import br.com.gubee.interview.model.Hero;
import br.com.gubee.interview.model.PowerStats;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.client.HttpClientErrorException;

import java.util.UUID;

/**
 * <p>Teste gerado a partir do chatGpt</p>
 */
public class HeroControllerTest {

    @InjectMocks
    private HeroController heroController;

    @Mock
    private HeroService heroService;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void testFindByFilter() {
        // Configuração do cenário de teste
        Filter filter = new Filter();
        Hero hero = new Hero();

        // Definição do comportamento esperado do mock do HeroService
        Mockito.when(heroService.findByFilter(filter)).thenReturn(hero);

        // Chamada do método sendo testado
        ResponseEntity<?> response = heroController.findByFilter(filter);

        // Verificação dos resultados
        Assertions.assertEquals(HttpStatus.OK, response.getStatusCode());
        Assertions.assertEquals(hero, response.getBody());
    }

    @Test
    public void testGetById() {
        // Configuração do cenário de teste
        UUID id = UUID.randomUUID();
        Hero hero = new Hero();

        // Definição do comportamento esperado do mock do HeroService
        Mockito.when(heroService.findById(id)).thenReturn(hero);

        // Chamada do método sendo testado
        ResponseEntity<?> response = heroController.getById(id);

        // Verificação dos resultados
        Assertions.assertEquals(HttpStatus.OK, response.getStatusCode());
        Assertions.assertEquals(hero, response.getBody());
    }

    @Test
    public void testCreate()  {
        // Configuração do cenário de teste
        Hero hero = new Hero();
        Hero heroDB = new Hero();

        // Definição do comportamento esperado do mock do HeroService
        try {
            Mockito.when(heroService.create(hero)).thenReturn(heroDB);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        // Chamada do método sendo testado
        ResponseEntity<?> response = null;
        try {
            response = heroController.create(hero);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        // Verificação dos resultados
        Assertions.assertEquals(HttpStatus.CREATED, response.getStatusCode());
        Assertions.assertEquals(heroDB, response.getBody());
    }

    @Test
    public void testUpdateHero() {
        // Configuração do cenário de teste
        Hero hero = new Hero();
        Hero heroUpdated = new Hero();

        // Definição do comportamento esperado do mock do HeroService
        Mockito.when(heroService.update(hero)).thenReturn(heroUpdated);
        // Chamada do método sendo testado
        ResponseEntity<?> response = heroController.updateHero(hero);

        // Verificação dos resultados
        Assertions.assertEquals(HttpStatus.OK, response.getStatusCode());
        Assertions.assertEquals(heroUpdated, response.getBody());
    }

    @Test
    public void testDelete() {
        // Configuração do cenário de teste
        UUID id = UUID.randomUUID();
        Boolean deleted = true;

        // Definição do comportamento esperado do mock do HeroService
        Mockito.when(heroService.delete(id)).thenReturn(deleted);

        // Chamada do método sendo testado
        ResponseEntity<?> response = heroController.delete(id);

        // Verificação dos resultados
        Assertions.assertEquals(HttpStatus.OK, response.getStatusCode());
    }

    @Test
    public void testDelete_NotFound() {
        // Configuração do cenário de teste
        UUID id = UUID.randomUUID();
        Boolean deleted = false;

        // Definição do comportamento esperado do mock do HeroService
        Mockito.when(heroService.delete(id)).thenReturn(deleted);

        // Chamada do método sendo testado
        ResponseEntity<?> response = heroController.delete(id);

        // Verificação dos resultados
        Assertions.assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }

}
