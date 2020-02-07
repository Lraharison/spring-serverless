package org.cyranno.springserverless.sum;


import com.amazonaws.services.lambda.runtime.events.APIGatewayProxyRequestEvent;
import com.amazonaws.services.lambda.runtime.events.APIGatewayProxyResponseEvent;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Before;
import org.junit.Test;
import org.springframework.test.util.ReflectionTestUtils;

import static java.util.Collections.emptyMap;
import static org.junit.Assert.assertEquals;
import static org.springframework.http.HttpStatus.BAD_REQUEST;
import static org.springframework.http.HttpStatus.OK;

public class SumFunctionTest {
    private SumFunction sumFunction;

    @Before
    public void setUp() {
        final ObjectMapper mapper = new ObjectMapper();
        this.sumFunction = new SumFunction();
        ReflectionTestUtils.setField(this.sumFunction, "mapper", mapper);
    }

    @Test
    public void shouldReturnSumResult() {
        final String expectedBody = String.format("{\"request\":{\"a\":5,\"b\":9},\"result\":14}");
        final APIGatewayProxyRequestEvent request = new APIGatewayProxyRequestEvent()
                .withHeaders(emptyMap())
                .withQueryStringParamters(emptyMap())
                .withPathParamters(emptyMap())
                .withBody("{\"a\":5, \"b\":9}");

        final APIGatewayProxyResponseEvent response = this.sumFunction.apply(request);

        assertEquals(expectedBody, response.getBody());
        assertEquals(OK.value(), response.getStatusCode().intValue());
    }

    @Test
    public void shouldReturnSumErrorIfBodyIsEmpty() {
        final APIGatewayProxyRequestEvent request = new APIGatewayProxyRequestEvent()
                .withHeaders(emptyMap())
                .withQueryStringParamters(emptyMap())
                .withPathParamters(emptyMap())
                .withBody("");

        final APIGatewayProxyResponseEvent response = this.sumFunction.apply(request);

        assertEquals(BAD_REQUEST.value(), response.getStatusCode().intValue());
    }
}
