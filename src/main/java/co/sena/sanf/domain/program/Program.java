package co.sena.sanf.domain.program;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.bson.codecs.pojo.annotations.BsonProperty;
import org.bson.types.ObjectId;
import org.eclipse.microprofile.openapi.annotations.media.Schema;
import org.hibernate.validator.constraints.Length;

import java.io.Serializable;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Program implements Serializable {

    @BsonProperty("programa")
    @Schema(example = "Programación de software")
    @NotBlank(message = "El campo program (programa) no puede ser nulo o vacio")
    @Length(min = 8, max = 35, message = "El campo program (programa) debe contener minimo 8 y maximo 35 caracteres")
    private String program;

    @BsonProperty("competencias")
    @Schema(example = "64bcae48f877082e4a53c1c6")
    @NotNull(message = "El campo competences (competencias) no puede ser nulo o vacio")
    private ObjectId competences;
}
