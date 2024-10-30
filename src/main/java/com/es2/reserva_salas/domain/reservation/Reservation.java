package com.es2.reserva_salas.domain.reservation;

import com.es2.reserva_salas.domain.physicalSpace.PhysicalSpace;
import com.es2.reserva_salas.domain.user.User;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.UUID;

@Table(name = "reservation")
@Entity
@Getter
@Setter
@NoArgsConstructor
public class Reservation {
    @Id
    @GeneratedValue
    private UUID id;
    private LocalDateTime startDateHour;
    private LocalDateTime endDateHour;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne
    @JoinColumn(name = "physical_space_id")
    private PhysicalSpace physicalSpace;

}
