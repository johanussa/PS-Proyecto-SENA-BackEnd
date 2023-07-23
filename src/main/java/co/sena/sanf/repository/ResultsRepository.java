package co.sena.sanf.repository;

import co.sena.sanf.domain.ResultRegister;
import io.quarkus.mongodb.panache.PanacheMongoRepository;
import jakarta.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class ResultsRepository implements PanacheMongoRepository<ResultRegister> {
}
