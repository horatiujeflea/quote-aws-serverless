# quote-aws-serverless

Technologies

Cloud vs On-premise:
- Cloud, AWS: most acquainted to, but main reason is scalability and ease of management

Language
 - Java: my most productive language to get the task done, but would recommend node.js for better cold start and easier json parsing

In-Memory DB:
 - I would of chosen a NoSQL which is persistent, but I followed the requirements
 - Redis: most popular in memory db/caching system
 - app.redislabs.com: deployed the redis cluster here. Did not use AWS ElastiCache because of additional VPC settings.
    They would of taken me much more time and they slow lambdas down.

Microservices:
 - AWS Lambda: a higher level of microservices, FaaS, easier to manage.
 - Alternative: Microservices with Jetty, Spring Boot, Spring Core, Spring MVC and Docker
 - Alternative 2: Node.js server, Express, Docker
 - Decision: ease of scaling, code and necessity of doing the task fast (I needed to be productive)

API:
 - AWS API Gateway configured/managed by Serverless framework (https://serverless.com/)

HTTP Client:
 - OkHTTP: did want to try something new with this opportunity, otherwise Jersey would do just fine

Test:
 - Automation tests: HTTP requests to the lambda function and testing the response (code and body)
    - Do API calls and monitor the response. Use GET /payment/{paymentId} to see if payment was create, check for invalid requests ...