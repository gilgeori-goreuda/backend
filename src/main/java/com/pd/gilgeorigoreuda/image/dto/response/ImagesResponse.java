package com.pd.gilgeorigoreuda.image.dto.response;
;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@NoArgsConstructor
public class ImagesResponse {

    private List<String> imageNames;

    public ImagesResponse(final List<String> imageNames) {
        this.imageNames = imageNames;
    }

}
