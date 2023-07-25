package co.sena.sanf.repository;

import co.sena.sanf.domain.program.ProgramRegister;
import io.quarkus.mongodb.panache.PanacheMongoRepository;
import jakarta.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class ProgramRepository implements PanacheMongoRepository<ProgramRegister> {
}
