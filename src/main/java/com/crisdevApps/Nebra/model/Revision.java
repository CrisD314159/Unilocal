package com.crisdevApps.Nebra.model;

import com.crisdevApps.Nebra.model.enums.RevisionState;
import jakarta.persistence.*;
import lombok.*;

import java.util.UUID;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
@ToString
@Getter
@Setter
@Builder
public class Revision {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private UUID id;
    private RevisionState state;
    @ManyToOne(fetch = FetchType.LAZY)
    private User moderator;
    @ManyToOne(fetch = FetchType.LAZY)
    private Business business;
    private String reason;
}
