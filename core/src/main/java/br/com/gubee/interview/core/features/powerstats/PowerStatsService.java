package br.com.gubee.interview.core.features.powerstats;

import br.com.gubee.interview.model.PowerStats;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;
import org.springframework.web.client.HttpClientErrorException;



@Service
public class PowerStatsService {
    @Autowired private PowerStatsRepository powerStatsRepository;

    public PowerStats save(PowerStats powerStats) {
        validatePowerStats(powerStats);
        return powerStatsRepository.save(powerStats);
    }
    public void validatePowerStats(PowerStats ps){

        if(ObjectUtils.isEmpty(ps.getStrength())){
            throw new HttpClientErrorException(HttpStatus.BAD_REQUEST, "PowerStats.Strength is required");
        }
        if(ObjectUtils.isEmpty(ps.getAgility())){
            throw new HttpClientErrorException(HttpStatus.BAD_REQUEST, "PowerStats.Agility is required");
        }
        if(ObjectUtils.isEmpty(ps.getDexterity())){
            throw new HttpClientErrorException(HttpStatus.BAD_REQUEST, "PowerStats.Iexterity is required");
        }
        if(ObjectUtils.isEmpty(ps.getIntelligence())){
            throw new HttpClientErrorException(HttpStatus.BAD_REQUEST, "PowerStats.Intelligence is required");
        }
    }


}
