package br.com.gubee.interview.core.features.hero;

import br.com.gubee.interview.core.features.hero.dto.HeroCompareDTO;
import br.com.gubee.interview.core.features.hero.dto.ResultCompareDTO;
import br.com.gubee.interview.core.features.powerstats.PowerStatsService;
import br.com.gubee.interview.model.Hero;
import br.com.gubee.interview.model.PowerStats;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.BeanWrapper;
import org.springframework.beans.BeanWrapperImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;
import org.springframework.web.client.HttpClientErrorException;


import java.util.Arrays;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class HeroService {

    private final HeroRepository heroRepository;

    public Hero findByFilter(Filter filter) {
        return heroRepository.findFirstByNameLikeIgnoreCase(filter);
    }

    public Hero findById(UUID id) {
        return heroRepository.findById(id).orElse(null);
    }

    public Hero create(Hero hero)  {

        Hero heroDB = findByFilter(new Filter(hero.getName()));

        if(ObjectUtils.isEmpty(heroDB)){
            validateHero(hero);
            return heroRepository.save(hero);
        }
        throw new DataIntegrityViolationException("One hero found with this name with id: "+hero.getId());
    }

    public Boolean delete(UUID id){
        Hero hero = findById(id);
        if(hero==null){
            return false;
        }
        heroRepository.delete(hero);
        return true;
    }
    public Hero update(Hero hero) {
        Hero heroDb = heroRepository.findById(hero.getId()).orElse(null);
        if(!ObjectUtils.isEmpty(heroDb)){
            BeanUtils.copyProperties(hero,heroDb, getNullPropertyNames(hero));
            heroDb = heroRepository.save(heroDb);
        }
        return heroDb;
    }
    public void validateHero(Hero hero) {

        if(ObjectUtils.isEmpty(hero.getName())){
            throw new HttpClientErrorException(HttpStatus.BAD_REQUEST, "Name is required");
        }
        if(ObjectUtils.isEmpty(hero.getRace())){
            throw new HttpClientErrorException(HttpStatus.BAD_REQUEST, "Race is required");
        }
        if(ObjectUtils.isEmpty(hero.getPowerStats())){
            throw new HttpClientErrorException(HttpStatus.BAD_REQUEST, "PowerStats is required");
        }

    }

    private  String[] getNullPropertyNames(Object source) {
        final BeanWrapper src = new BeanWrapperImpl(source);
        java.beans.PropertyDescriptor[] pds = src.getPropertyDescriptors();

        return Arrays.stream(pds)
                .map(java.beans.PropertyDescriptor::getName)
                .filter(name -> src.getPropertyValue(name) == null)
                .toArray(String[]::new);
    }

    public List<ResultCompareDTO> compare(HeroCompareDTO heroCompare) {
        Hero hero1 = heroRepository.findById(heroCompare.getHero1Id()).orElse(null);
        Hero hero2 = heroRepository.findById(heroCompare.getHero2Id()).orElse(null);
        if(hero1==null || hero2==null){
            return null;
        }
        ResultCompareDTO resultH1 = calculePowerDiff(hero1, hero2);
        ResultCompareDTO resultH2 = calculePowerDiff(hero2, hero1);

        return Arrays.asList(resultH1,resultH2);
    }

    private ResultCompareDTO calculePowerDiff(Hero hero1, Hero hero2) {
        ResultCompareDTO resultH1 = new ResultCompareDTO();
        resultH1.setHeroId(hero1.getId());
        resultH1.setAgilityDifference(hero1.getPowerStats().getAgility() - hero2.getPowerStats().getAgility());
        resultH1.setDexterityDifference(hero1.getPowerStats().getDexterity() - hero2.getPowerStats().getDexterity());
        resultH1.setStrengthDifference(hero1.getPowerStats().getStrength() - hero2.getPowerStats().getStrength());
        resultH1.setIntelligenceDifference(hero1.getPowerStats().getIntelligence() - hero2.getPowerStats().getIntelligence());

        return resultH1;
    }
}
