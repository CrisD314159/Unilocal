package com.crisdevApps.Nebra.model;

import jakarta.persistence.Embeddable;
import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
@ToString
@Getter
@Setter
@Embeddable
@Builder
public class Coordinate {
    private String latitude;
    private String longitude;

}
