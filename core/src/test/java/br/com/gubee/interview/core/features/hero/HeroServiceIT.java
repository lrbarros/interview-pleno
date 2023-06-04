package br.com.gubee.interview.core.features.hero;

import br.com.gubee.interview.core.features.hero.dto.HeroCompareDTO;
import br.com.gubee.interview.core.features.hero.dto.ResultCompareDTO;
import br.com.gubee.interview.enums.RaceEnum;
import br.com.gubee.interview.model.Hero;
import br.com.gubee.interview.model.PowerStats;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;


import java.util.List;
import java.util.UUID;

@ActiveProfiles("it")
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@SpringBootTest
public class HeroServiceIT {

    @Autowired
    private HeroService heroService;
    private Hero hero;


    @Test
    @Order(1)
    public void deveriaCadastrarHeroiComSucesso(){
        try {
            hero  = new Hero();
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

            hero  = new Hero();
            hero.setName("SpiderMan");
            hero.setRace(RaceEnum.HUMAN);

            powerStats = new PowerStats();
            powerStats.setStrength((short) 100);
            powerStats.setAgility((short) 95);
            powerStats.setDexterity((short) 90);
            powerStats.setIntelligence((short)70);

            hero.setPowerStats(powerStats);
            hero = heroService.create(hero);
            Assertions.assertNotNull(hero.getId() );
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
    @Test
    @Order(2)
    public void deveriaDispararExceptionAoTentarCadastrarHeroiSemPowerStats(){
        Hero hero = new Hero();
        hero.setName("Robin");
        hero.setRace(RaceEnum.HUMAN);
        try{
            heroService.create(hero);
            Assertions.fail();
        }catch (Exception e){
            Assertions.assertEquals(e.getMessage(),"400 PowerStats is required");

        }
    }
    @Test
    @Order(3)
    public void deveriaBuscarHeroiComSucessoPorNome(){
        Filter filter = new Filter();
        filter.setName("man");
        Hero hero = heroService.findByFilter(filter);

        Assertions.assertNotNull(hero);
    }
    @Test
    @Order(4)
    public void deveriaRetornarNullBuscandoHeroiInesistentePorNome(){
        Filter filter = new Filter();
        filter.setName(UUID.randomUUID().toString());//valor qq
        Hero hero = heroService.findByFilter(filter);

        Assertions.assertNull(hero);
    }
    @Test
    @Order(5)
    public void deveriaBuscarHeroiComSucssoPorId(){

        Hero heroByName = heroService.findByFilter(new Filter("man"));
        Hero heroById = heroService.findById(heroByName.getId());

        Assertions.assertEquals(heroByName,heroById);
    }
    @Test
    @Order(6)
    public void deveriaRetornarNullBuscandoHeroiInesistentePorId(){

        hero = heroService.findById(UUID.randomUUID());
        Assertions.assertNull(hero);
    }
    @Test
    @Order(7)
    public void deveriaCompararDoisHerois(){

        HeroCompareDTO heroCompare = new HeroCompareDTO();
        Hero spiderMan = heroService.findByFilter(new Filter("SuperMan"));
        Hero superMan = heroService.findByFilter(new Filter("Spiderman"));

        heroCompare.setHero1Id(superMan.getId());
        heroCompare.setHero2Id(spiderMan.getId());

        List<ResultCompareDTO> resultCompare = heroService.compare(heroCompare);
        Assertions.assertTrue(resultCompare.size()==2);


    }
    @Test
    @Order(8)
    public void deveriaBuscarHeroiEDeletarPeloIdComSucesso(){
        boolean deleted =false;
        hero = heroService.findByFilter(new Filter("Spiderman"));
        deleted =heroService.delete(hero.getId());
        Assertions.assertTrue(deleted);

        deleted=false;
        hero = heroService.findByFilter(new Filter("Superman"));
        deleted =heroService.delete(hero.getId());
        Assertions.assertTrue(deleted);
    }
    @Test
    @Order(9)
    public void deveriaRetornarFalseAoNaoDeletarHeroi(){
        boolean deleted =heroService.delete(UUID.randomUUID());
        Assertions.assertFalse(deleted);
    }


}

