package br.com.gubee.interview.model;

import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import javax.persistence.*;
import java.util.UUID;
import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "power_stats")
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class PowerStats {
    @Id
    @GeneratedValue
    @Column(columnDefinition = "uuid", updatable = false)
    @EqualsAndHashCode.Include
    private UUID id;

    @Column(nullable = false)
    private short strength;

    @Column(nullable = false)
    private short agility;

    @Column(nullable = false)
    private short dexterity;

    @Column(nullable = false)
    private short intelligence;

    @CreationTimestamp
    @Column(nullable = false)
    private LocalDateTime createdAt;

    @UpdateTimestamp
    @Column(nullable = false)
    private LocalDateTime updatedAt;
}

