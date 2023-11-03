package com.pd.gilgeorigoreuda.statistics.domain;

import jakarta.persistence.*;
import lombok.*;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
@Getter
@Table(name = "HotPlaces")
public class HotPlace {
    @Id
    private Long id;

    @Column(name = "hot_place", nullable = false, length = 50)
    private String hotPlace;
}