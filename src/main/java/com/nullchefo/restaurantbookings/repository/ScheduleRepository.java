package com.nullchefo.restaurantbookings.repository;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.nullchefo.restaurantbookings.entity.Schedule;

@Repository
public interface ScheduleRepository extends JpaRepository<Schedule, UUID> {
}
