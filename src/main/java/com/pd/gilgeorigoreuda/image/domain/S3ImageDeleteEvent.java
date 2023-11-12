package com.pd.gilgeorigoreuda.image.domain;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class S3ImageDeleteEvent {

    private final String imageName;

}
