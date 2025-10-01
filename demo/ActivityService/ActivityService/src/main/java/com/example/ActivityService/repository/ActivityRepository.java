package com.example.ActivityService.repository;

import com.example.ActivityService.model.Activity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ActivityRepository extends JpaRepository<Activity, String> {
}
