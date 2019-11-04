# Invoke an AWS Lambda function from another Lambda Function

This is a simple Java program which will invoke a Lambda function in Amazon Web Services (AWS) 
from another Lambda function. I have taken this code snippet from one of the production
services that I recently wrote at work. The problem which lead to this was, AWS has a limitation
in which  we can't configure multiple Lambda functions to S3 bucket events, 
<i>with the same prefix and suffix</i>. 

So in this particular case, we wanted to process all text files uploaded to a particular 
S3 bucket in two different ways. So obviously, we wrote two different Lambda functions. 
But I later discovered that I can't trigger more than one Lambda for the same suffix in
an S3 bucket. So the solution was to invoke the new Lambda from within the exiting Lambda. 
And that's how this POC came into existence.     

# How To Run?

Make sure you change the values of the constants in the 
```com.contactsunny.poc.InvokeLambdaFromAnotherLambdaPOC.config.CustomConstants```
class, according to your own setup. Then, build the project using the following command:

```shell script
mvn package shade:shade
```

This will create a ```.jar``` file in the ```target/``` directory. Upload that directory 
to S3 or directly in a Lambda function. That's all!