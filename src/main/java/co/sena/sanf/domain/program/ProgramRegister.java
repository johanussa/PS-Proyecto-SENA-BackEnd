package co.sena.sanf.domain.program;

import co.sena.sanf.domain.Meta;
import io.quarkus.mongodb.panache.common.MongoEntity;
import jakarta.validation.Valid;
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
@MongoEntity(collection = "SENA_PROGRAMS")
public class ProgramRegister implements Serializable {

    @Schema(hidden = true)
    private ObjectId id;

    @Valid
    @NotEmpty(message = "La lista de programas no puede ser vacia")
    @NotNull(message = "El campo data (programas) no puede ser nulo o vacio")
    @Size(min = 2, message = "La lista de programas debe tener al menos 2 registros")
    private List<Program> data;

    @Schema(hidden = true)
    private Meta meta;
}
