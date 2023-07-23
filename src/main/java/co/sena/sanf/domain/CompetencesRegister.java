package co.sena.sanf.domain;

import io.quarkus.mongodb.panache.common.MongoEntity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.bson.types.ObjectId;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@MongoEntity(collection = "SENA_PROG-COMPETENCES")
public class CompetencesRegister {

    private ObjectId id;
    private Competences data;
    private Meta meta;
}
