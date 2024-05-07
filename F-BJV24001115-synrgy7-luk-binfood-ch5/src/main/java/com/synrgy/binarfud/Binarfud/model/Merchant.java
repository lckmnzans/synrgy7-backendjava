package com.synrgy.binarfud.Binarfud.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
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
@Table(name = "merchant")
public class Merchant {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    private String merchantName;

    private String merchantLocation;

    private boolean open = Boolean.FALSE;

    @JsonIgnore
    @OneToMany(mappedBy = "merchant", fetch = FetchType.EAGER)
    private List<Product> productList;
}
