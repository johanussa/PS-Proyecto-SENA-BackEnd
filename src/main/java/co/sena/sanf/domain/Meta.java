package co.sena.sanf.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.bson.codecs.pojo.annotations.BsonProperty;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Meta implements Serializable {

    @BsonProperty("fechaCreacion")
    private LocalDateTime creationDate;

    @BsonProperty("fechaActualizacion")
    private List<LocalDateTime> updateDate;

    private String source;

    private String ipAddress;

    private String tokenRaw;

    private String type;
}
