package co.sena.sanf.repository;

import co.sena.sanf.domain.result.ResultRegister;
import io.quarkus.mongodb.panache.PanacheMongoRepository;
import jakarta.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class ResultsRepository implements PanacheMongoRepository<ResultRegister> {
}
