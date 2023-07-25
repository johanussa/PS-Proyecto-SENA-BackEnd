package co.sena.sanf.domain.competences;

import co.sena.sanf.domain.Meta;
import io.quarkus.mongodb.panache.common.MongoEntity;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.bson.types.ObjectId;
import org.eclipse.microprofile.openapi.annotations.media.Schema;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@MongoEntity(collection = "SENA_PROG-COMPETENCES")
public class CompetencesRegister {

    @Schema(hidden = true)
    private ObjectId id;

    @Valid
    private Competences data;

    @Schema(hidden = true)
    private Meta meta;
}
