package com.contactsunny.poc.InvokeLambdaFromAnotherLambdaPOC;

import com.amazonaws.services.lambda.AWSLambda;
import com.amazonaws.services.lambda.AWSLambdaAsyncClient;
import com.amazonaws.services.lambda.model.InvokeRequest;
import com.amazonaws.services.lambda.model.InvokeResult;
import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import com.amazonaws.services.lambda.runtime.events.S3Event;
import com.amazonaws.services.s3.event.S3EventNotification;
import com.contactsunny.poc.InvokeLambdaFromAnotherLambdaPOC.utils.Logger;
import org.codehaus.jettison.json.JSONException;
import org.codehaus.jettison.json.JSONObject;

import static com.contactsunny.poc.InvokeLambdaFromAnotherLambdaPOC.config.CustomConstants.DEFAULT_LAMBDA_REGION;
import static com.contactsunny.poc.InvokeLambdaFromAnotherLambdaPOC.config.CustomConstants.LAMBDA_FUNCTION_NAME;

public class App implements RequestHandler<S3Event, String> {

    private static final Logger logger = new Logger(App.class);

    @Override
    public String handleRequest(S3Event s3Event, Context context) {

        S3EventNotification.S3EventNotificationRecord record = s3Event.getRecords().get(0);

        String bucketName = record.getS3().getBucket().getName();
        String keyName = record.getS3().getObject().getKey();

        logger.info("Bucket Name is " + bucketName);
        logger.info("File Path is " + keyName);

        try {
            invokeLambda(keyName, bucketName);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return null;
    }

    private void invokeLambda(String keyName, String bucketName) throws JSONException {

        JSONObject payloadObject = new JSONObject();
        payloadObject.put("keyName", keyName);
        payloadObject.put("bucketName", bucketName);

        String payload = payloadObject.toString();

        logger.info("Payload: " + payload);
        logger.info("Invoking Lambda: " + LAMBDA_FUNCTION_NAME);

        AWSLambda client = AWSLambdaAsyncClient.builder().withRegion(DEFAULT_LAMBDA_REGION).build();

        InvokeRequest request = new InvokeRequest();
        request.withFunctionName(LAMBDA_FUNCTION_NAME).withPayload(payload);
        InvokeResult invoke = client.invoke(request);
        logger.info("Result invoking " + LAMBDA_FUNCTION_NAME + ": " + invoke);
    }
}
