package org.cyranno.springserverless;

import com.amazonaws.services.lambda.runtime.events.APIGatewayProxyRequestEvent;
import com.amazonaws.services.lambda.runtime.events.APIGatewayProxyResponseEvent;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;

import java.io.IOException;
import java.util.function.Function;

@Slf4j
public abstract class AbstractAwsApiGatewayLambdaFunction<I, O> implements Function<APIGatewayProxyRequestEvent, APIGatewayProxyResponseEvent> {
    private final Class<I> inputType;
    @Autowired
    private ObjectMapper mapper;

    public AbstractAwsApiGatewayLambdaFunction(final Class<I> inputType) {
        this.inputType = inputType;
    }

    @Override
    public APIGatewayProxyResponseEvent apply(final APIGatewayProxyRequestEvent requestEvent) {
        try {
            log.info("event: {}", requestEvent.toString());
            return this.handle(requestEvent);
        } catch (final SpringServerLessException e) {
            log.error("event error : {}", e.getMessage());
            return this.handle(e);
        }
    }

    public abstract Response<O> apply(Request<I> request);

    private APIGatewayProxyResponseEvent handle(final APIGatewayProxyRequestEvent event) {
        log.info("api gateway proxy request event received {}", event);
        final Request<I> request = this.createRequest(event);
        log.info("processing request {}", request);
        final Response<O> response = this.apply(request);
        log.info("response returned {}", response);
        final APIGatewayProxyResponseEvent responseEvent = this.createEvent(response);
        log.info("api gateway proxy response event returned {}", responseEvent);
        return responseEvent;
    }

    private APIGatewayProxyResponseEvent createEvent(final Response<O> response) {
        final String json;
        try {
            json = this.mapper.writeValueAsString(response.getBody());
        } catch (final JsonProcessingException e) {
            log.error("unable to generate json", e);
            throw new UnexpectedErrorException("unable to generate json", e);
        }
        return new APIGatewayProxyResponseEvent()
                .withBody(json)
                .withStatusCode(response.getStatusCode());
    }

    private Request<I> createRequest(final APIGatewayProxyRequestEvent event) {
        I body = null;
        if (!StringUtils.isEmpty(event.getBody())) {
            try {
                body = this.mapper.readValue(event.getBody(), this.inputType);
            } catch (final IOException e) {
                log.error("invalid json", e);
                throw new UnexpectedErrorException("invalid json", e);
            }
        }
        return Request.<I>builder()
                .headers(event.getHeaders())
                .pathParameters(event.getPathParameters())
                .queryStringParameters(event.getQueryStringParameters())
                .body(body)
                .build();
    }

    private APIGatewayProxyResponseEvent handle(final SpringServerLessException e) {
        return this.createEvent(e);
    }

    private APIGatewayProxyResponseEvent createEvent(final SpringServerLessException e) {
        final ErrorResponse errorResponse = new ErrorResponse(e);
        try {
            final String jsonError = this.mapper.writeValueAsString(errorResponse);
            return new APIGatewayProxyResponseEvent()
                    .withBody(jsonError)
                    .withStatusCode(e.getStatus());
        } catch (final JsonProcessingException ex) {
            log.error("json process error", e);
            throw new UnexpectedErrorException("json process error", ex);
        }
    }
}
