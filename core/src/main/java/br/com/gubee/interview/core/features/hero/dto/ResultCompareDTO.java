package br.com.gubee.interview.core.features.hero.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
public class ResultCompareDTO {
    private UUID heroId;
    private int strengthDifference;
    private int agilityDifference;
    private int dexterityDifference;
    private int intelligenceDifference;
}
