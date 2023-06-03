package br.com.gubee.interview.core.features.hero;

import br.com.gubee.interview.core.features.powerstats.PowerStatsService;
import br.com.gubee.interview.enums.RaceEnum;
import br.com.gubee.interview.model.Hero;
import br.com.gubee.interview.model.PowerStats;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;


public class HeroServiceTest {

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
        Hero h = hero();
        try{
            heroService.create(h);
        }catch (Exception e){
            Assertions.fail();
        }

    }
    @Test
    public void deveriaLancarErroHeroiSemPowerStats(){
        Hero h = hero();
        h.setPowerStats(null);
        try {
         heroService.create(h);
         Assertions.fail();
        }catch (Exception e){}
    }


    private Hero hero(){
        Hero hero  = new Hero();
        hero.setName("SuperMan");
        hero.setRace(RaceEnum.HUMAN);

        PowerStats powerStats = new PowerStats();
        powerStats.setStrength((short) 100);
        powerStats.setAgility((short) 95);
        powerStats.setDexterity((short) 90);
        powerStats.setIntelligence((short)70);

        hero.setPowerStats(powerStats);
        return hero;
    }

}
