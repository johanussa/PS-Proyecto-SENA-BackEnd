package co.sena.sanf.repository;

import co.sena.sanf.domain.UserRegister;
import io.quarkus.mongodb.panache.PanacheMongoRepository;
import jakarta.enterprise.context.ApplicationScoped;

import java.util.Optional;

@ApplicationScoped
public class UserRepository implements PanacheMongoRepository<UserRegister> {

    public Optional<UserRegister> searchUserRegistration(String idUser) {
        return find("data.numDocumento = ?1", idUser).firstResultOptional();
    }
}
