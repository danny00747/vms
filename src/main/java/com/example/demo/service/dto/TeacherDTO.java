package com.example.demo.service.dto;
import com.example.demo.service.mapper.DTO;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;

/**
 * A DTO for the {@link com.example.demo.domain.Teacher} entity.
 */
public class TeacherDTO implements Serializable {


    private int teacherId;

    @NotNull(message = "teacherName is a required field.")
    @Size(min=5, max=30, message = "teacherName must be between 5 and 30.")
    private String teacherName;

    @NotNull
    private String teacherEmail;

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
                "}";
    }
}
