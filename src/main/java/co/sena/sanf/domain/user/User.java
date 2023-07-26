package co.sena.sanf.domain.user;

import co.sena.sanf.domain.enums.DocumentTypes;
import co.sena.sanf.domain.enums.RolTypes;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.*;
import org.bson.codecs.pojo.annotations.BsonProperty;
import org.eclipse.microprofile.openapi.annotations.media.Schema;
import org.hibernate.validator.constraints.Length;

import java.io.Serializable;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = false)
public class User implements Serializable {

    private static final String REXNAME = "^[a-zA-ZÁ-ÿ\\s]*$";
    private static final String REXDOC = "^[a-zA-Z0-9]*$";
    private static final String REXEMAIL = "^\\w+@(misena|soy\\.sena|sena)\\.edu\\.co$";

    @BsonProperty("nombre")
    @Schema(example = "Jhon Jairo")
    @NotBlank(message = "El campo name (nombre) no puede ser nulo o vacio")
    @Length(min = 3, max = 35, message = "El campo name (nombre) debe contener minimo 3 y maximo 30 caracteres")
    @Pattern(regexp = REXNAME, message = "El campo name (nombre) no puede contener números o caracteres especiales")
    private String name;

    @BsonProperty("apellido")
    @Schema(example = "Rodriguez Sanchez")
    @NotBlank(message = "El campo lastName (apellido) no puede ser nulo o vacio")
    @Length(min = 3, max = 35, message = "El campo lastName (apellido) debe contener minimo 3 y maximo 30 caracteres")
    @Pattern(regexp = REXNAME, message = "El campo apellido no puede contener números o caracteres especiales")
    private String lastName;

    @BsonProperty("tipoDocumento")
    @Enumerated(EnumType.STRING)
    @NotNull(message = "El campo documentType (tipo documento) no puede ser nulo o vacio")
    private DocumentTypes documentType;

    @BsonProperty("numDocumento")
    @Schema(example = "12345678ABC")
    @NotBlank(message = "El campo documentNumber (número documento) no puede ser nulo o vacio")
    @Length(min = 7, max = 16, message = "El campo número documento debe contener minimo 7 y maximo 16 caracteres")
    @Pattern(regexp = REXDOC, message = "El campo número documento no puede contener espacios o caracteres especiales")
    private String documentNumber;

    @BsonProperty("correo")
    @Schema(example = "correo@misena.edu.co")
    @NotBlank(message = "El campo email (correo) no puede ser nulo o vacio")
    @Pattern(regexp = REXEMAIL, message = "El campo email (correo) debe ser de dominio misena, soy.sena o sena")
    private String email;

    @Schema(example = "9H7z8guBeASj46Y")
    @NotBlank(message = "El campo password (contraseña) no puede ser nulo o vacio")
    @Length(min = 8, max = 20, message = "El campo password (contraseña) debe contener minimo 8 y maximo 20 caracteres")
    private String password;

    @Enumerated(EnumType.STRING)
    @NotNull(message = "El campo rol no puede ser nulo o vacio")
    private RolTypes rol;

    @Schema(hidden = true)
    @BsonProperty("activo")
    private Boolean status;
}
