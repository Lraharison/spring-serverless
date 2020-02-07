package org.cyranno.springserverless.sum;

import lombok.extern.slf4j.Slf4j;
import org.cyranno.springserverless.AbstractAwsApiGatewayLambdaFunction;
import org.cyranno.springserverless.Request;
import org.cyranno.springserverless.Response;
import org.springframework.stereotype.Component;

import static org.springframework.http.HttpStatus.OK;

@Component("sum")
@Slf4j
public class SumFunction extends AbstractAwsApiGatewayLambdaFunction<SumRequest, SumResponse> {
    public SumFunction() {
        super(SumRequest.class);
    }

    @Override
    public Response<SumResponse> apply(final Request<SumRequest> request) {
        if (request.getBody() == null) {
            log.error("data request is not provided");
            throw new SumException("data request is not provided");
        }
        final int result = request.getBody().getA() + request.getBody().getB();
        final SumResponse bodyResponse = new SumResponse(request.getBody(), result);
        return new Response<>(bodyResponse, OK.value());
    }
}
