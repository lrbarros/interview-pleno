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

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ActiveProfiles;


import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * <p>Teste gerado a partir do chatGpt utilizei a ferramenta para ganho de performace chequei a estrutura<p/>
 * <p> alterei os pontos que podeira ser ajustados </p>
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
    public void deveriaEmcontrarPeloFiltro() {
        // Configuração do cenário de teste
        Filter filter = new Filter("man");
        Hero hero = herois().get(0);

        // Definição do comportamento esperado do mock do HeroService
        Mockito.when(heroService.findByFilter(filter)).thenReturn(hero);

        // Chamada do método sendo testado
        ResponseEntity<?> response = heroController.findByFilter(filter);

        // Verificação dos resultados
        Assertions.assertEquals(HttpStatus.OK, response.getStatusCode());
        Assertions.assertEquals(hero, response.getBody());
    }

    @Test
    public void DeveriaEncontrarPeloId() {
        // Configuração do cenário de teste
        Filter filter = new Filter("man");
        Hero hero = herois().get(0);
       //id no banco
        hero.setId(UUID.randomUUID());
        //id enviado
        UUID id = hero.getId();

        // Definição do comportamento esperado do mock do HeroService
        Mockito.when(heroService.findById(id)).thenReturn(hero);

        // Chamada do método sendo testado
        ResponseEntity<?> response = heroController.getById(id);

        // Verificação dos resultados
        Assertions.assertEquals(HttpStatus.OK, response.getStatusCode());
        Assertions.assertEquals(hero, response.getBody());
    }
    @Test
    public void DeveriaDarErroPeloId() {
        // Configuração do cenário de teste
        Filter filter = new Filter("man");
        Hero hero = herois().get(0);
        //id no banco
        hero.setId(UUID.randomUUID());
        //id enviado
        UUID id = UUID.randomUUID();

        // Definição do comportamento esperado do mock do HeroService
        Mockito.when(heroService.findById(id)).thenReturn(null);

        // Chamada do método sendo testado
        ResponseEntity<?> response = heroController.getById(id);

        // Verificação dos resultados
        Assertions.assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());

    }


    @Test
    public void deveriaCriarHeroi()  {
        // Configuração do cenário de teste
        Hero hero = herois().get(0);
        hero.setId(null);
        Hero heroDB = herois().get(0);
        heroDB.setId(UUID.randomUUID());

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
    public void deveriaAtualizarHeroi() {
        // Configuração do cenário de teste
        Hero hero = herois().get(0);
        Hero heroUpdated = herois().get(0);
        heroUpdated.setRace(RaceEnum.ALIEN);

        // Definição do comportamento esperado do mock do HeroService
        Mockito.when(heroService.update(hero)).thenReturn(heroUpdated);
        // Chamada do método sendo testado
        ResponseEntity<?> response = heroController.updateHero(hero);

        // Verificação dos resultados
        Assertions.assertEquals(HttpStatus.OK, response.getStatusCode());
        Assertions.assertEquals(heroUpdated, response.getBody());
    }

    @Test
    public void deveriaDeletar() {
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
    public void deveriaRetornar404AoDeletar() {
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
    private List<Hero> herois(){
        List<Hero> herois = new ArrayList<>();
        Hero hero  = new Hero();
        hero.setId(UUID.randomUUID());
        hero.setName("SuperMan");
        hero.setRace(RaceEnum.HUMAN);

        PowerStats powerStats = new PowerStats();
        powerStats.setStrength((short) 100);
        powerStats.setAgility((short) 95);
        powerStats.setDexterity((short) 90);
        powerStats.setIntelligence((short)70);

        hero.setPowerStats(powerStats);

        herois.add(hero);

        hero  = new Hero();
        hero.setName("Spiderman");
        hero.setRace(RaceEnum.HUMAN);

        powerStats = new PowerStats();
        powerStats.setStrength((short) 70);
        powerStats.setAgility((short) 80);
        powerStats.setDexterity((short) 90);
        powerStats.setIntelligence((short)90);

        hero.setPowerStats(powerStats);
        herois.add(hero);

        return herois;
    }

}
