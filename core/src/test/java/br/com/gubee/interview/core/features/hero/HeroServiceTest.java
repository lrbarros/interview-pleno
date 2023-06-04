package br.com.gubee.interview.core.features.hero;

import br.com.gubee.interview.core.features.powerstats.PowerStatsService;
import br.com.gubee.interview.enums.RaceEnum;
import br.com.gubee.interview.model.Hero;
import br.com.gubee.interview.model.PowerStats;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.function.Executable;
import org.mockito.*;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;


public class HeroServiceTest {

    @Mock
    private HeroService heroService;
    @Mock
    private HeroRepository heroRepository;
    @Captor
    private ArgumentCaptor<Hero> captor;
    @BeforeEach
    public void beforeEach(){
        MockitoAnnotations.initMocks(this);
        this.heroService = new HeroService(heroRepository);
    }

    @Test
    public void deveriaCriarHeroi(){
        Hero h = herois().get(0);
        try{
            Mockito.when(heroService.findByFilter(new Filter(h.getName()))).thenReturn(null);

            heroService.create(h);

            Mockito.verify(heroRepository).save(captor.capture());
            Hero heroSaved = captor.getValue();

            Assertions.assertDoesNotThrow(() -> Exception.class);
        }catch (Exception e){
            Assertions.fail();
        }

    }
    @Test
    public void deveriaLancarErroHeroiSemPowerStats(){
        Hero h = herois().get(0);
        h.setPowerStats(null);
        try {
         heroService.create(h);
         Assertions.fail();
        }catch (Exception e){}
    }

    @Test
    public void deveriaLancarErroCadastrarHero(){


        try{
            Assertions.assertThrows(Exception.class, (Executable) heroService.create(Mockito.any()));
            Assertions.fail();

        }catch (Exception e){

        }

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
