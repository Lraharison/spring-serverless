package org.cyranno.springserverless.sum;

import lombok.Data;

@Data
public class SumResponse {
    private final SumRequest request;
    private final int result;
}
