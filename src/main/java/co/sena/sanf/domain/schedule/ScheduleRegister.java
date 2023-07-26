package co.sena.sanf.domain.schedule;

import co.sena.sanf.domain.Meta;
import io.quarkus.mongodb.panache.common.MongoEntity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.bson.types.ObjectId;

import java.io.Serializable;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@MongoEntity(collection = "SENA_SCHEDULES")
public class ScheduleRegister implements Serializable {

    private ObjectId id;
    private ObjectId instructorId;
    private List<ScheduleData> data;
    private Meta meta;
}
