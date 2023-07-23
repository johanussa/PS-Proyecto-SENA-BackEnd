package co.sena.sanf.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.bson.codecs.pojo.annotations.BsonProperty;
import org.bson.types.ObjectId;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor

public class Competences {

    @BsonProperty("resultados")
    private ObjectId resultsId;

    @BsonProperty("competencias")
    private List<String> competences;
}
