package co.sena.sanf.domain.schedule;

import co.sena.sanf.domain.Meta;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.bson.codecs.pojo.annotations.BsonProperty;

import java.io.Serializable;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ScheduleData implements Serializable {

    @BsonProperty("horario")
    private Schedule schedule;

    private Meta meta;
}
