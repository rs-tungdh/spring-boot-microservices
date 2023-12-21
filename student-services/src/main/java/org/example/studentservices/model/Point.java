package org.example.studentservices.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;

@Entity
public class Point {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @JsonProperty("average_point")
    private double averagePoint;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "student_id")
    @JsonIgnore
    private Student student;

    public Point(int id, double averagePoint) {
        this.id = id;
        this.averagePoint = averagePoint;
    }

    public Point() {

    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public double getAveragePoint() {
        return averagePoint;
    }

    public void setAveragePoint(double averagePoint) {
        this.averagePoint = averagePoint;
    }

    public Student getStudent() {
        return student;
    }

    public void setStudent(Student student) {
        this.student = student;
    }

    @Override
    public String toString() {
        return "Point{" +
                "id=" + id +
                ", averagePoint=" + averagePoint +
                '}';
    }
}
