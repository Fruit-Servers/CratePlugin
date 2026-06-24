package com.hazebyte.crate.cratereloaded.component.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Builder
@AllArgsConstructor
@Data
public class CrateOpenResponse {

    // False when the open was rejected so the caller skips consuming the key
    @Builder.Default
    private boolean opened = true;
}
