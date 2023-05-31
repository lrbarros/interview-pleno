package br.com.gubee.interview.model;


import br.com.gubee.interview.enums.RaceEnum;
import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.util.UUID;
import java.time.LocalDateTime;

@Data
@Entity
@Table(name = "hero")
public class Hero {
    @Id
    @GeneratedValue
    @Column(columnDefinition = "uuid", updatable = false)
    private UUID id;

    @Column(nullable = false, unique = true)
    private String name;


    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private RaceEnum race;

    @ManyToOne(fetch = FetchType.EAGER,cascade = CascadeType.PERSIST)
    @JoinColumn(name = "power_stats_id",nullable = false)
    @JsonProperty(value = "power_stats")
    private PowerStats powerStats;

    @Column(nullable = false)
    private boolean enabled = true;

    @CreationTimestamp
    @Column(nullable = false)
    private LocalDateTime createdAt;

    @UpdateTimestamp
    @Column(nullable = false)
    private LocalDateTime updatedAt;



}