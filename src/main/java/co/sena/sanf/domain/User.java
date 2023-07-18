package co.sena.sanf.domain;

import io.quarkus.mongodb.panache.PanacheMongoEntityBase;
import lombok.*;
import org.bson.codecs.pojo.annotations.BsonProperty;

import java.io.Serializable;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = false)
public class User extends PanacheMongoEntityBase implements Serializable {

    @BsonProperty("nombre")
    private String name;

    @BsonProperty("apellido")
    private String lastName;

    @BsonProperty("tipoDocumento")
    private DocumentTypes documentType;

    @BsonProperty("numDocumento")
    private String documentNumber;

    @BsonProperty("correo")
    private String email;

    private String password;

    private RolTypes rol;

    @BsonProperty("activo")
    private Boolean status;
}
