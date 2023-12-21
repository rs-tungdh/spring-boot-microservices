package org.example.studentservices.repository;

import org.example.studentservices.model.Point;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PointRepository extends JpaRepository<Point, Integer> {
}
