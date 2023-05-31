package br.com.gubee.interview.core.features.hero;

import br.com.gubee.interview.core.features.powerstats.PowerStatsService;
import br.com.gubee.interview.model.Hero;
import br.com.gubee.interview.model.PowerStats;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.BeanWrapper;
import org.springframework.beans.BeanWrapperImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;
import org.springframework.web.client.HttpClientErrorException;

import javax.persistence.Column;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import java.util.Arrays;
import java.util.Objects;
import java.util.Optional;
import java.util.UUID;

@Service
public class HeroService {

    @Autowired private PowerStatsService powerStatsService;
    @Autowired private HeroRepository heroRepository;
    public Hero findByFilter(Filter filter) {
        return heroRepository.findFirstByNameLikeIgnoreCase(filter.getName()).stream().findFirst().orElse(null);
    }

    public Hero findById(UUID id) {
        return heroRepository.findById(id).orElse(null);
    }

    public Hero create(Hero hero) throws HttpClientErrorException {

        Hero heroDB = findByFilter(new Filter(hero.getName()));

        if(ObjectUtils.isEmpty(heroDB)){
            validateHero(hero);
            return heroRepository.save(hero);
        }else{
           return update(hero);
        }
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
}
