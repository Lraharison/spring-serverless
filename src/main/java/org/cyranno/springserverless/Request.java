package org.cyranno.springserverless;

import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

import java.util.Map;

@Builder
@Getter
@ToString
public class Request<I> {
    private final I body;
    private final Map<String, String> headers;
    private final Map<String, String> pathParameters;
    private final Map<String, String> queryStringParameters;
}
