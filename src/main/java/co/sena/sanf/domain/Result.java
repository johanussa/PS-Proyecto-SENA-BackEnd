package co.sena.sanf.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.bson.codecs.pojo.annotations.BsonProperty;

import java.io.Serializable;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Result implements Serializable {

    @BsonProperty("competencia")
    private String competence;

    @BsonProperty("resultados")
    private List<String> results;
}
