package br.com.gubee.interview.core.features.hero.hero;

import br.com.gubee.interview.core.features.hero.Filter;
import br.com.gubee.interview.model.Hero;
import org.springframework.data.jpa.repository.Query;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import java.util.List;

public class HeroRepositoryImpl implements HeroRepositoryQuery{

    @PersistenceContext
    private EntityManager entityManager;
    @Override
    public Hero findFirstByNameLikeIgnoreCase(Filter filter) {

        TypedQuery<Hero>  q = entityManager.createQuery("SELECT h FROM Hero h WHERE LOWER(h.name) LIKE LOWER(CONCAT('%', :name, '%'))",Hero.class);
        q.setParameter("name",filter.getName());
        q.setMaxResults(1);

        return  q.getResultList().stream().findFirst().orElse(null);
    }

}
