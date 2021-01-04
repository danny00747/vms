package com.example.demo.config;

import com.example.demo.service.mapper.DTO;

import javax.validation.constraints.*;

public enum TeacherDto {
    ;

    public sealed interface TeacherId permits Response.Public {
        @Positive Long teacherId();
    }

    public sealed interface TeacherName permits Request.Create, Response.Public {
        @NotNull @Size(min = 5, max = 30, message = "teacherName must be between 5 and 30.")
        String teacherName();
    }

    public sealed interface TeacherEmail permits Request.Create, Response.Public {
        @NotNull String teacherEmail();
    }

    public enum Request {
        ;

        public record Create(String teacherName, String teacherEmail) implements TeacherEmail, TeacherName, DTO {
        }
    }

    public enum Response {
        ;

        public record Public(Long teacherId, String teacherName,
                             String teacherEmail) implements TeacherId, TeacherName, TeacherEmail {
        }
    }

    /*
       public static <DTO extends TeacherName & TeacherEmail> String getMarkup(DTO dto) {
        return dto.teacherName() + " : " + dto.teacherEmail();
    }
     */

}
