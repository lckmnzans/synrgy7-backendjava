package com.synrgy.binarfud.Binarfud.model;

import jakarta.persistence.*;
import lombok.*;

import java.util.List;
import java.util.UUID;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "users")
public class Users {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    private String username;

    private String emailAddress;

    private String password;

    @OneToMany(mappedBy = "user")
    private List<Order> orderList;
}
