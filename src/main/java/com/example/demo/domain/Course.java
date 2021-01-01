package com.example.demo.domain;

import javax.persistence.*;
import java.io.Serializable;

@Entity
public class Course implements Serializable {

    @Id
    private int id;
    private String name;
    private int workload;
    private short rate;

    @ManyToOne
    @JoinColumn(foreignKey = @ForeignKey(name = "fk_course_teacher"))
    private Teacher teacher;

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public int getWorkload() {
        return workload;
    }

    public short getRate() {
        return rate;
    }

    @Override
    public int hashCode() {
        return 1;
    }

    @Override
    public boolean equals(Object obj) {
        return this == obj;
    }

}
