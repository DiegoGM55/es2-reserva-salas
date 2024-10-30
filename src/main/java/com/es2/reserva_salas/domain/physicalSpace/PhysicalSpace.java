package com.es2.reserva_salas.domain.physicalSpace;

import com.es2.reserva_salas.domain.usagePolicy.UsagePolicy;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.UUID;

@Table(name = "physical_space")
@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class PhysicalSpace {
    @Id
    @GeneratedValue
    private UUID id;

    private String name;
    private String capacity;
    private String carateristics;
    private String location;

    @OneToOne
    @JoinColumn(name = "usage_policy_id")
    private UsagePolicy usagePolicy;


}
