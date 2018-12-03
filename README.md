# Quote Simulator - AWS Serverless

### Table of Contents  
 - Context
 - Problem description (the actual requirement for the interview)
 - Technologies (my choice of technologies + explanation)  

### Context

This is an interview project, it does not have any utility otherwise.

### Problem description 

#### The brief

Integrating with third-party systems is a big part of what we do here, and (probably) of any successful software company these days. 

In this task, we want you to create a backend system which interfaces with an external payments api, transforms and persists data in a local database. 


#### Requirements (in order of priority):
- Creation of in-memory database 
    -  User table (see below for structure and pre-populated data)
    -  Payment log table
- Integration with third-party api to create payment
- Creation of an API endpoint which can be called with payment amount
- Saving results to payment log
- Unit test coverage

#### Bonus
- Error handling
- Mapping of third-party response to only save relevant data to database (Only keep the following fields from the response: `id`, `source`, `target`, `sourceAmount`, `targetAmount`, `rate`, `fee`)
  
#### End-to-end walkthrough
1) API endpoint receives request, with parameters of `userId` and `paymentAmount`
2) System goes and gets user from database by `userId` (if exists)
3) System calls third-party api to create quote with the user's `payoutCurrency` and the request's `paymentAmount`
4) If success, saves result to the payment log table 

#### Technical notes

API documentation:
https://api-docs.transferwise.com/#quotes

Sandbox API key: `***`

Business profile id: `1`

TransferWise quote api to use:  `https://api.sandbox.transferwise.tech/v1/quotes`

Source currency will always be: `GBP`

#### Model info

For simplicity, you can use the following information for pre-populating the user infomation table:
```
| id          | firstName | lastName  | payoutCurrency |
|-------------|-----------|-----------|----------------|
| {generated} | John      | Doe       | GBP            |
| {generated} | Ron       | Peterson  | USD            |
```

### Technologies

#### Cloud vs On-premise:
- Cloud, AWS: most acquainted to, but main reason is scalability and ease of management

#### Language
 - Java: my most productive language to get the task done, but would recommend node.js for better cold start and easier json parsing

#### In-Memory DB:
 - I would of chosen a NoSQL which is persistent, but I followed the requirements
 - Redis: most popular in memory db/caching system
 - app.redislabs.com: deployed the redis cluster here. Did not use AWS ElastiCache because of additional VPC settings.
    They would of taken me much more time and they slow lambdas down.

#### Microservices:
 - AWS Lambda: a higher level of microservices, FaaS, easier to manage.
 - Alternative: Microservices with Jetty, Spring Boot, Spring Core, Spring MVC and Docker
 - Alternative 2: Node.js server, Express, Docker
 - Decision: ease of scaling, code and necessity of doing the task fast (I needed to be productive)

#### API:
 - AWS API Gateway configured/managed by Serverless framework (https://serverless.com/)

#### HTTP Client:
 - OkHTTP: did want to try something new with this opportunity, otherwise Jersey would do just fine

#### Test:
 - Automation tests: HTTP requests to the lambda function and testing the response (code and body)
    - Do API calls and monitor the response. Use GET /payment/{paymentId} to see if payment was create, check for invalid requests ...