package org.cyranno.springserverless.stringreverse;

import lombok.extern.slf4j.Slf4j;
import org.cyranno.springserverless.AbstractAwsApiGatewayLambdaFunction;
import org.cyranno.springserverless.Request;
import org.cyranno.springserverless.Response;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import static org.springframework.http.HttpStatus.OK;

@Component("stringReverse")
@Slf4j
public class StringReverseFunction extends AbstractAwsApiGatewayLambdaFunction<Object, String> {

    public StringReverseFunction() {
        super(Object.class);
    }

    @Override
    public Response<String> apply(final Request<Object> request) {
        final String str = request.getPathParameters().get("str");
        log.info("string to reverse {}", str);
        if (StringUtils.isEmpty(str)) {
            log.error("String is not provided");
            throw new StringReverseException("String is not provided");
        }
        return new Response<>(new StringBuilder(str).reverse().toString(), OK.value());
    }
}
