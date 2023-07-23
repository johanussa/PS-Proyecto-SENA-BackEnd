package co.sena.sanf.domain;

import io.quarkus.mongodb.panache.PanacheMongoEntity;
import io.quarkus.mongodb.panache.common.MongoEntity;
import lombok.*;
import org.bson.types.ObjectId;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
@MongoEntity(collection = "SENA_PROG-RESULTS")
public class ResultRegister extends PanacheMongoEntity {

    private ObjectId id;
    private List<Result> data;
    private Meta meta;
}
