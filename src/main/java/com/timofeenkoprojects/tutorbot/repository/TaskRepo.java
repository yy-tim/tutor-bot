package com.timofeenkoprojects.tutorbot.repository;

import com.timofeenkoprojects.tutorbot.entity.task.Task;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface TaskRepo extends JpaRepository<Task, UUID> {
}
