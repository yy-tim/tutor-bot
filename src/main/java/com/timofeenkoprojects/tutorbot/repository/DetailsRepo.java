package com.timofeenkoprojects.tutorbot.repository;

import com.timofeenkoprojects.tutorbot.entity.user.UserDetails;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface DetailsRepo extends JpaRepository<UserDetails, UUID> {

}
