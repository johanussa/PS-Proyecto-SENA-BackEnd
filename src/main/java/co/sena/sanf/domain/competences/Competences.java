package co.sena.sanf.domain.competences;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.bson.codecs.pojo.annotations.BsonProperty;
import org.bson.types.ObjectId;
import org.eclipse.microprofile.openapi.annotations.media.Schema;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor

public class Competences {

    @BsonProperty("resultados")
    @Schema(example = "64bcae48f877082e4a53c1c6")
    @NotNull(message = "El campo resultsId (id colección resultados) no puede ser nulo o vacio")
    private ObjectId results;

    @BsonProperty("competencias")
    @NotEmpty(message = "La lista de competencias no puede ser vacia")
    @NotNull(message = "El campo competences (competencias) no puede ser nulo o vacio")
    @Schema(example = "[\"Modelar base de datos\",\"Desarrollar procesos de comunicación\",\"Implementar red física\"]")
    private List<String> competences;
}
