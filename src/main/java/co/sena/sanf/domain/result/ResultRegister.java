package co.sena.sanf.domain.result;

import co.sena.sanf.domain.Meta;
import io.quarkus.mongodb.panache.common.MongoEntity;
import jakarta.validation.Valid;
import lombok.*;
import org.bson.types.ObjectId;
import org.eclipse.microprofile.openapi.annotations.media.Schema;

import java.io.Serializable;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@MongoEntity(collection = "SENA_PROG-RESULTS")
public class ResultRegister implements Serializable {

    @Schema(hidden = true)
    private ObjectId id;

    @Valid
    private List<Result> data;

    @Schema(hidden = true)
    private Meta meta;
}
