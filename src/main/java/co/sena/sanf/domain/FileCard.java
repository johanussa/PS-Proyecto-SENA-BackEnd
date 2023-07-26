package co.sena.sanf.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class FileCard implements Serializable {

    private String numberFileCard;
    private String routeNumber;
    private String trimester;
    private String code;
    private String color;
    private String program;
    private String numberStudents;
    private String classRoom;
    private List<String> competences;
    private List<String> results;
    private String description;

}
