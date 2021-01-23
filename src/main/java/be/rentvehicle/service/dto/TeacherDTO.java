package be.rentvehicle.service.dto;

import be.rentvehicle.domain.Teacher;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import java.util.List;

/**
 * A DTO for the {@link Teacher} entity.
 */

public class TeacherDTO {

    private int teacherId;

    @NotNull(message = "teacherName is a required field.")
    @Size(min=5, max=30, message = "teacherName must be between 5 and 30.")
    private String teacherName;

    @NotNull(message = "teacherEmail is a required field.")
    @Pattern(regexp = "^\\w+([-+.']\\w+)*@\\w+([-.]\\w+)*\\.\\w+([-.]\\w+)*$", message = "Please provide a valid email")
    private String teacherEmail;

    private List<Integer> listNumbers = List.of(1, 2, 3, 4, 5);

    public TeacherDTO(int teacherId, String teacherName, String teacherEmail) {
        this.teacherId = teacherId;
        this.teacherName = teacherName;
        this.teacherEmail = teacherEmail;
    }

    public void setTeacherId(int teacherId) {
        this.teacherId = teacherId;
    }

    public void setTeacherName(String teacherName) {
        this.teacherName = teacherName;
    }

    public void setTeacherEmail(String teacherEmail) {
        this.teacherEmail = teacherEmail;
    }

    public int getTeacherId() {
        return teacherId;
    }

    public String getTeacherName() {
        return teacherName;
    }

    public String getTeacherEmail() {
        return teacherEmail;
    }

    public List<Integer> getListNumbers() {
        return listNumbers;
    }

    public void setListNumbers(List<Integer> listNumbers) {
        this.listNumbers = listNumbers;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof TeacherDTO)) {
            return false;
        }

        return teacherId == ((TeacherDTO) o).teacherId;
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "TeacherDTO{" +
                "id=" + getTeacherId() +
                ", names='" + getTeacherName() + "'" +
                ", email='" + getTeacherEmail() + "'" +
                ", numbers='" + listNumbers + "'" +
                "}";
    }
}
