package co.sena.sanf.repository;

import co.sena.sanf.domain.ClassRoom;
import io.quarkus.mongodb.panache.PanacheMongoRepository;
import io.quarkus.panache.common.Sort;
import jakarta.enterprise.context.ApplicationScoped;

import java.util.Optional;

@ApplicationScoped
public class ClassRoomRepository implements PanacheMongoRepository<ClassRoom> {

    public Optional<ClassRoom> getList() {
        return findAll(Sort.descending("meta.fechaCreacion")).firstResultOptional();
    }
}
