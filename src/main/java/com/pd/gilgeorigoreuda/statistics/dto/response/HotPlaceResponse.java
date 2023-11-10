package com.pd.gilgeorigoreuda.statistics.dto.response;

import com.pd.gilgeorigoreuda.statistics.domain.HotPlace;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class HotPlaceResponse {

    private Long rank;
    private String name;

    private HotPlaceResponse(final Long id, final String name) {
        this.rank = id;
        this.name = name;
    }

    public static HotPlaceResponse of(final HotPlace hotPlace) {
        return new HotPlaceResponse(
                hotPlace.getId(),
                hotPlace.getPlace()
        );
    }

}
