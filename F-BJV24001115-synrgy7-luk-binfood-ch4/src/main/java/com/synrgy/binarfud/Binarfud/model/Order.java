package com.synrgy.binarfud.Binarfud.model;

import jakarta.persistence.*;
import lombok.*;

import java.util.Date;
import java.util.List;
import java.util.UUID;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "\"order\"")
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    private Date orderTime;

    private String destinationAddress;

    @ManyToOne(targetEntity = Users.class)
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    private Users user;

    private boolean completed;

    @OneToMany(mappedBy = "order", fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    private List<OrderDetail> orderDetailList;
}
