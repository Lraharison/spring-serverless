# Spring Cloud Function + serverless

Project template for using spring cloud function framework and serverless framework

## Building and deploying to AWS
```
mvn clean install
sls deploy
```

## Endpoints
```
 POST - https://${url_api_gateway}/dev/sum
 GET - https://${url_api_gateway}/dev/reverse/{str}
```

Example :
```
 curl -X POST https://${url_api_gateway}/dev/sum -d '{"a":8, "b":10}'
 curl  https://${url_api_gateway}/dev/reverse/abc
```
