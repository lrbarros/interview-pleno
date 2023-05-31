package br.com.gubee.interview.core.features.hero;

import br.com.gubee.interview.model.Hero;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.UUID;

@RestController
@RequestMapping("/v1/hero")
public class HeroController {

    @Autowired
    private HeroService heroService;
    @GetMapping
    public ResponseEntity<?>findByFilter(Filter filter){
        Hero hero = heroService.findByFilter(filter);
        if(hero == null) {
            return ResponseEntity.ok().build();
        }
        return  ResponseEntity.ok(hero);
    }
    @GetMapping("/{id}")
    public ResponseEntity<?>getById(@PathVariable UUID id){
        Hero hero = heroService.findById(id);
        if(hero == null) {
            return ResponseEntity.notFound().build();
        }
        return  ResponseEntity.ok(hero);
    }

    @PostMapping
    public ResponseEntity<?>create( @RequestBody Hero hero){
        Hero heroDB =  heroService.create(hero);
       if(heroDB == null){
           return ResponseEntity.status( HttpStatus.NO_CONTENT).build();
       }
        return  ResponseEntity.status(HttpStatus.CREATED).body(heroDB);
    }
    @PutMapping
    public ResponseEntity<?>updateHero( @RequestBody Hero hero){
        Hero heroUpdated = heroService.update(hero);
        if(ObjectUtils.isEmpty(heroUpdated)){
            return ResponseEntity.notFound().build();
        }
        return  ResponseEntity.ok(heroUpdated);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?>delete(@PathVariable UUID id){
        Boolean deleted = heroService.delete(id);
        if(!deleted){
            return  ResponseEntity.notFound().build();
        }
        return  ResponseEntity.ok().build();
    }
}
