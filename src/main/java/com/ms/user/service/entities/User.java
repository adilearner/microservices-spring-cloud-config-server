package com.ms.user.service.entities;

import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "User")
public class User {
    @Id
    @Column(name = "ID")
    private String userId;

    @Column(name = "NAME" ,length = 20)
    private String name;

    @Column(name = "EMAIL")
    private String email;

    @Column(name = "ABOUT")
    private String about;

    //If you don't want to save ratings in database,mark it as transient
    @Transient
    private List<Rating> ratings = new ArrayList<>();

}
