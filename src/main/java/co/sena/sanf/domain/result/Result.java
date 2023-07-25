package co.sena.sanf.domain.result;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.bson.codecs.pojo.annotations.BsonProperty;
import org.eclipse.microprofile.openapi.annotations.media.Schema;
import org.hibernate.validator.constraints.Length;

import java.io.Serializable;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Result implements Serializable {

    @BsonProperty("competencia")
    @Schema(example = "Desarrollar procesos de comunicación")
    @NotBlank(message = "El campo competence (competencia) no puede ser nulo o vacio")
    @Length(min = 10, max = 240, message = "El campo competence (competencia) debe contener entre 3 y 240 caracteres")
    private String competence;

    @BsonProperty("resultados")
    @NotEmpty(message = "La lista de resultados no puede ser vacia")
    @NotNull(message = "El campo results (resultados) no puede ser nulo o vacio")
    @Size(min = 1, message = "La lista de resultados debe tener al menos 1 registro")
    @Schema(example = "[\"Verificar características\",\"Aplicar herramientas\",\"Obtener modelo 3d\"]")
    private List<String> results;
}
