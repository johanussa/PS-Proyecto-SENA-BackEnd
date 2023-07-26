package co.sena.sanf.domain.schedule;

import co.sena.sanf.domain.FileCard;
import co.sena.sanf.domain.Hours;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Schedule implements Serializable {

    private LocalDate startDate;
    private LocalDate endDate;
    private List<FileCard> fileCards;
    private List<String> complementary;
    private boolean isPlantContract;
    private List<Hours> hours;

}
