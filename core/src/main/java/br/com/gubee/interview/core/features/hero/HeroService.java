package br.com.gubee.interview.core.features.hero;

import br.com.gubee.interview.core.features.powerstats.PowerStatsService;
import br.com.gubee.interview.model.Hero;
import br.com.gubee.interview.model.PowerStats;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;
import org.springframework.web.client.HttpClientErrorException;

import javax.persistence.Column;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
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

    private Hero update(Hero hero) {

        return hero;
    }

    public Boolean delete(UUID id){
        Hero hero = findById(id);
        if(hero==null){
            return false;
        }
        heroRepository.delete(hero);
        return true;
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
}
