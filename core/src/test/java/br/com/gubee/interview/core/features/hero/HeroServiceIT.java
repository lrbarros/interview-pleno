package br.com.gubee.interview.core.features.hero;

import br.com.gubee.interview.enums.RaceEnum;
import br.com.gubee.interview.model.Hero;
import br.com.gubee.interview.model.PowerStats;
import net.bytebuddy.implementation.bind.MethodDelegationBinder;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.web.client.HttpClientErrorException;

import java.util.UUID;

//@ActiveProfiles("it")
@SpringBootTest
public class HeroServiceIT {

    @Autowired
    private HeroService heroService;

    @Test
    public void cadastrarHeroiComSucesso(){

        Hero hero  = new Hero();
        hero.setName("SuperMan");
        hero.setRace(RaceEnum.HUMAN);

        PowerStats powerStats = new PowerStats();
        powerStats.setStrength((short) 100);
        powerStats.setAgility((short) 95);
        powerStats.setDexterity((short) 90);
        powerStats.setIntelligence((short)70);

        hero.setPowerStats(powerStats);
        hero = heroService.create(hero);

        Assertions.assertNotNull(hero.getId() );
    }
    @Test
    public void deveriaDispararExceptionCadastrarHeroiSemPowerStats(){
        Hero hero = new Hero();
        hero.setName("Robin");
        hero.setRace(RaceEnum.HUMAN);
        try{
            heroService.create(hero);
            Assertions.fail();
        }catch (HttpClientErrorException e){
            Assertions.assertEquals(e.getMessage(),"400 PowerStats is required");

        }
    }
    @Test
    public void deveriaBuscarHeroiComSucessoPorNome(){
        Filter filter = new Filter();
        filter.setName("man");
        Hero hero = heroService.findByFilter(filter);

        Assertions.assertNotNull(hero);
    }
    @Test
    public void deveriaRetornarNullBuscandoHeroiInesistente(){
        Filter filter = new Filter();
        filter.setName(UUID.randomUUID().toString());
        Hero hero = heroService.findByFilter(filter);

        Assertions.assertNull(hero);
    }
}

