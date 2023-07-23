package co.sena.sanf.domain;


import io.quarkus.mongodb.panache.common.MongoEntity;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.bson.types.ObjectId;
import org.eclipse.microprofile.openapi.annotations.media.Schema;

import java.io.Serializable;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@MongoEntity(collection = "SENA_CLASS-ROOMS")
public class ClassRoom implements Serializable {

    private ObjectId id;

    @NotEmpty(message = "La lista de ambientes no puede ser vacia")
    @NotNull(message = "El campo classRooms (ambientes) no puede ser nulo o vacio")
    @Size(min = 10, message = "La lista de ambientes debe tener al menos 10 registros")
    @Schema(example = "[\"QMA Carpintería mecánica\",\"105 Mecanizado\",\"107 Soladura\",\"112 Transversales\"]")
    private List<String> data;

    private Meta meta;
}
