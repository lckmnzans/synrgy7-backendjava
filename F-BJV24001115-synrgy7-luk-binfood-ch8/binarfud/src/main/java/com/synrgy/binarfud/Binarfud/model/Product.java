package com.synrgy.binarfud.Binarfud.model;

import jakarta.persistence.*;
import lombok.*;

import java.util.UUID;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "product")
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    private String productName;

    private Double price;

    @ManyToOne(targetEntity = Merchant.class)
    @JoinColumn(name = "merchant_id", referencedColumnName = "id")
    private Merchant merchant;
}
