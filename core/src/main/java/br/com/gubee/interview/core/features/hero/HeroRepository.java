package br.com.gubee.interview.core.features.hero;


import br.com.gubee.interview.model.Hero;
import org.springframework.data.jpa.repository.JpaRepository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;


import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface HeroRepository extends JpaRepository<Hero,UUID>  {

    @Query("SELECT h FROM Hero h WHERE LOWER(h.name) LIKE LOWER(CONCAT('%', :name, '%'))")
    public List<Hero> findFirstByNameLikeIgnoreCase(@Param("name") String name);

}
