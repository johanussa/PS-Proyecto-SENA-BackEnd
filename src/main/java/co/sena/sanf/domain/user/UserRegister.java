package co.sena.sanf.domain.user;

import co.sena.sanf.domain.Meta;
import io.quarkus.mongodb.panache.common.MongoEntity;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.bson.types.ObjectId;
import org.eclipse.microprofile.openapi.annotations.media.Schema;

import java.io.Serializable;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@MongoEntity(collection = "SENA_USERS")
public class UserRegister implements Serializable {

    @Schema(hidden = true)
    private ObjectId id;

    @Valid
    private User data;

    @Schema(hidden = true)
    private Meta meta;
}
