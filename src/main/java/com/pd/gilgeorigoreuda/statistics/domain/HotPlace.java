package com.pd.gilgeorigoreuda.statistics.domain;

import com.pd.gilgeorigoreuda.common.entity.BaseTimeEntity;
import jakarta.persistence.*;
import lombok.*;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
@Getter
@Table(name = "hot_places")
public class HotPlace extends BaseTimeEntity {

    @Id
    private Long id;

    @Column(name = "place", nullable = false, length = 100)
    private String place;

}