package com.crisdevApps.Nebra.model;

import com.crisdevApps.Nebra.model.enums.BusinessCategory;
import com.crisdevApps.Nebra.model.enums.BusinessState;
import jakarta.persistence.*;
import lombok.*;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
@ToString
@Getter
@Setter
@Builder
public class Business implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private UUID id;

    @OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    private List<Image> images;

    private String description;

    private String name;

    private String phoneContact;

    private BusinessCategory category;

    private LocalDateTime dateCreated;

    @Embedded
    private Coordinate location;

    @ManyToOne(fetch = FetchType.EAGER)
    private User userOwner;

    @OneToMany(fetch = FetchType.EAGER, orphanRemoval = true, cascade = CascadeType.ALL)
    private List<Comment> comments;

    @OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    private List<Revision> revisionsList;

    private BusinessState businessState;

    @OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    private List<Schedule> scheduleList;

}
