package com.es2.reserva_salas.domain.usagePolicy;

import com.es2.reserva_salas.domain.timeSlot.TimeSlot;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;
import java.util.UUID;

@Table(name = "usage_policy")
@Entity
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class UsagePolicy {
    @Id
    @GeneratedValue
    private UUID id;

    private int minReservationDuration;
    private int maxReservationDuration;

    @ElementCollection
    @CollectionTable(name = "usage_policy_timeslots",
            joinColumns = @JoinColumn(name = "usage_policy_id"))
    private List<TimeSlot> availableTimeSlots;

}
