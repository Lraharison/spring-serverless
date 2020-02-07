package org.cyranno.springserverless;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

@Getter
@Builder
@ToString
@AllArgsConstructor
public class Response<O> {
    private final O body;
    private final int statusCode;
}
