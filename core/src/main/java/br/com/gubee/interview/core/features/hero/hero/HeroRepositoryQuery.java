package br.com.gubee.interview.core.features.hero.hero;

import br.com.gubee.interview.core.features.hero.Filter;
import br.com.gubee.interview.model.Hero;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface HeroRepositoryQuery {


    public Hero findFirstByNameLikeIgnoreCase(Filter filter);
}
