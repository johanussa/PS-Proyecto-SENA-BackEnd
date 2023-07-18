package co.sena.sanf.repository;

import co.sena.sanf.domain.UserRegistration;
import io.quarkus.mongodb.panache.PanacheMongoRepository;
import jakarta.enterprise.context.ApplicationScoped;

import java.util.Optional;

@ApplicationScoped
public class UserRepository implements PanacheMongoRepository<UserRegistration> {

    public Optional<UserRegistration> searchUserRegistration(String idUser) {
        return find("data.numDocumento = ?1", idUser).firstResultOptional();
    }
    public void deleteUserRegistration(String idUser) {
        delete("data.numDocumento = ?1", idUser);
    }
}
