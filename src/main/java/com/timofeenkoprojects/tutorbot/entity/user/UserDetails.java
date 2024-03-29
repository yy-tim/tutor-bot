package com.timofeenkoprojects.tutorbot.entity.user;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.LocalDateTime;
import java.util.UUID;

@Getter
@Setter
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "user_details")
@FieldDefaults(level = AccessLevel.PRIVATE)
public class UserDetails {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.AUTO)
    UUID id;

    @Column(name = "username")
    String username;

    @Column(name = "first_name")
    String firstName;

    @Column(name = "last_name")
    String lastName;

    @Column(name = "registered_at")
    LocalDateTime registeredAt;

    @Column(name = "now_updating_timetable")
    String timetableId;

}
