package co.sena.sanf.repository;

import co.sena.sanf.domain.competences.CompetencesRegister;
import io.quarkus.mongodb.panache.PanacheMongoRepository;
import jakarta.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class CompetencesRepository implements PanacheMongoRepository<CompetencesRegister> {
}
