package br.com.gubee.interview.core.features.hero.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
@JsonIgnoreProperties(ignoreUnknown = true)
public class HeroCompareDTO {
    private UUID hero1Id;
    private UUID hero2Id;

}
