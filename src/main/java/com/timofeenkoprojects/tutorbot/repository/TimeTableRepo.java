package com.timofeenkoprojects.tutorbot.repository;

import com.timofeenkoprojects.tutorbot.entity.timetable.TimeTable;
import com.timofeenkoprojects.tutorbot.entity.timetable.WeekDay;
import com.timofeenkoprojects.tutorbot.entity.user.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface TimeTableRepo extends JpaRepository<TimeTable, UUID> {
    List<TimeTable> findAllByUsersContainingAndWeekDay(User user, WeekDay weekDay);
    TimeTable findTimeTableById(UUID id);
}
