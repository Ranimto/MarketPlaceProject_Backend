package com.example.Market.Model;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "Partenaire")
public class Partenaire {
    @Id
    @GeneratedValue
    private Long id;

    @Column(name = "Name",unique = true)
    @Enumerated(EnumType.STRING)
    private PartenerName Name;
    @Column(name = "manager")
    private String manager;
    @Column(name = "address")
    private String address ;


}
