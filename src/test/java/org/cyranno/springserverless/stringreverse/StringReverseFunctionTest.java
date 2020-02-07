package org.cyranno.springserverless.stringreverse;

import com.amazonaws.services.lambda.runtime.events.APIGatewayProxyRequestEvent;
import com.amazonaws.services.lambda.runtime.events.APIGatewayProxyResponseEvent;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Before;
import org.junit.Test;
import org.springframework.test.util.ReflectionTestUtils;
import wiremock.com.google.common.collect.ImmutableMap;

import static java.util.Collections.emptyMap;
import static org.junit.Assert.assertEquals;
import static org.springframework.http.HttpStatus.BAD_REQUEST;
import static org.springframework.http.HttpStatus.OK;

public class StringReverseFunctionTest {
    private StringReverseFunction stringReverseFunction;

    @Before
    public void setUp() {
        final ObjectMapper mapper = new ObjectMapper();
        this.stringReverseFunction = new StringReverseFunction();
        ReflectionTestUtils.setField(this.stringReverseFunction, "mapper", mapper);
    }

    @Test
    public void shouldReturnReverseString() {
        final String expectedBody = "\"cba\"";
        final APIGatewayProxyRequestEvent request = new APIGatewayProxyRequestEvent()
                .withHeaders(emptyMap())
                .withQueryStringParamters(emptyMap())
                .withPathParamters(ImmutableMap.of("str", "abc"))
                .withBody(null);

        final APIGatewayProxyResponseEvent response = this.stringReverseFunction.apply(request);

        assertEquals(expectedBody, response.getBody());
        assertEquals(OK.value(), response.getStatusCode().intValue());
    }

    @Test
    public void shouldReturnErrorIfParameterIsNotProvided() {
        final APIGatewayProxyRequestEvent request = new APIGatewayProxyRequestEvent()
                .withHeaders(emptyMap())
                .withQueryStringParamters(emptyMap())
                .withPathParamters(emptyMap())
                .withBody(null);

        final APIGatewayProxyResponseEvent response = this.stringReverseFunction.apply(request);

        assertEquals(BAD_REQUEST.value(), response.getStatusCode().intValue());
    }
}
