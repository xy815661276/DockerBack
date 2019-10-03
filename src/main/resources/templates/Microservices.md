## Microservices and Testing - Lessons Learned

------------

*Forward from [https://microservices-on-my-mind.blogspot.com/2018/08/microservices-and-testing-lessons.html?mkt_tok=eyJpIjoiWVRBMVl6VmtaV0pqTW1JNCIsInQiOiI1QTlGblpBaU0vMWQ5ODNVVUJwVjloM21IMy9GNFFIUFEzZ1lVQnA4UWEvQmt6dHBFZUIwYXZIRTFjem94c1ErWTU2U2g3U3lvZENHRllIN2JBak9aS3YzMDNyeHBBc2pSck5xSERwOGE5K2VDcTZScDgvL01zbjBYN1RMSFFOcyJ9)*

------------

In my previous blog (Microservices and Testing pyramid), I talked about the variation in the testing pyramid which is more visible in the microservice stack. In this blog I want to provide some more context as to exactly what is contributing to this variation and share some of the lessons learned in evolving our testing strategy. 

A traditional testing pyramid has Unit Tests at the bottom followed by component tests and finally Integration and UI tests. There are more pictures with further breakdown available over the internet, but this is the crux of it - heavy on Unit Tests then Component Test and finally Integration and UI tests. 
![](https://cdn.img.wenhairu.com/images/2019/10/03/8tQAI.png)

Since Unit Tests carry the heavy weightage in the testing pyramid, any significant variation to it has to touch the Unit-Tests and anything that touches Unit-Tests has to have the same good characteristics that we love about them:

1.	They are Fast and Stable (here stable means they break only if there is a real failure and not some flakiness in the env setup) 
2.	Easy to write and maintain (especially if you do it during the development time) 
3.	Trigger on every check-in 
4.	Easy Setup (every language has a natural support for Unit tests) 
5.	Nice code-coverage reports 


To better understand it in the context of microservices, let’s take an example of a common use case. The most common use case would be a service that receives an incoming request. In order to process this request, it will require some data from its dependent APIs. It will make the call to get the data and use it to further process the request and finally store the result into its own storage and server the result back to its caller.
![](https://cdn.img.wenhairu.com/images/2019/10/03/8tyB6.png)

What are the challenges? When we write any test, we are essentially doing AAA which is Arrange, Act and Assert. Arrange is essentially the mocking. We need to make sure all the dependencies are satisfied before we can call the piece of code that we want to test. Act are the parameters that we want to use in order to test the functionality and Assert is the validation we need to do on the result. Arguably Arrange is the most difficult of all. Mocking is difficult!

Now let’s see some new techniques in terms of Arrange/Mocking that are becoming more popular these days. For any microservice, there are only two things we care about -1) Its immediate consumers (which are the incoming requests) and 2) its immediate dependencies. We don’t have to worry about the entire dependency graph but only its immediate dependencies! To accomplish this, teams are getting into practice of creating consistent snapshots. There are many ways to do it e.g tools like crawler (which can crawl to immediate dependencies and download data in JSON format - more suitable for get style operations) or recorder (which can record an API call and replay it when needed - more suitable for post/put) are becoming more popular. These snapshots are then checked-in into the source control with the service. So, when you download the service code, you are not only getting the code but also a snapshot of all its dependencies that was taken at some point in time. These snapshots are then further used for mocking. The key here is to be able to take consistent snapshots meaning- let’s say a service has a dependency on an API and it consumes two fields. If tomorrow it exposes one more field which the service needs to consume, then it will take a new snapshot and this new snapshot ideally should not override the values of two earlier fields. Otherwise assertions would start to fail!

In a more abstract term, what we are doing is essentially creating a well-known dataset in terms of snapshots of the immediate dependencies. Your team should have full control over it and no one else should touch it. These datasets are usually small in nature due to the fact that we only care about the immediate dependence and not the entire environment! Once we have these snapshots/datasets, then it becomes very easy to create a Component-Test suite which looks something like this: 
![](https://cdn.img.wenhairu.com/images/2019/10/03/8tR1u.png)

The service under test is the middle container and comes with its own data container as a part of the same stack. The only difference b/w service running in production versus in test mode is that in test mode - it is talking to a mock container instead of a real environment. The mock container serves the snapshots we downloaded in the earlier step as mock. And finally, there is a test-client container which is responsible for calling the service API with different sets of parameters and can further validates the response produced by the service.

Here is a screenshot of the test-run result of an upcoming microservice stack I am currently working on. I am a big fan of BDD and the test-client container is written in cucumber-js framework. From the screenshot, it ran 195 test cases (with 962 steps) in about 19 seconds. It spits out this nice code coverage report when all the tests are passed! Yes, it’s possible to get code coverage from a running service - just needs bit of different configuration/hack depending on the language. 
![](https://cdn.img.wenhairu.com/images/2019/10/03/8tk4h.png)

So now let’s compare this Component-Test setup with the Unit Tests:
1.	They are Fast and Stable - Well not as fast as unit tests but since it’s a microservice already, the number of test cases in its own pipeline would be much smaller compared to monolithic. Even if there are large number of test case, lets say 5000, it can still run under few mins. So comparatively fast enough! Stable - because there is no external dependency! These are all local containers talking to each other. 
2.	Easy to write and maintain - When we first started adopting this idea of consistent snapshots, I wasn’t sure how long we can run with it. But it has been few years now and in practice it has turned out to be easy to adopt and maintain. Makes mocking very fun! 
3.	Trigger on every check-in - All our Jenkins pipelines are setup in such a way that it kicks off Component-test suite on every check-in and fails the build if any of the test case is failed. 
4.	Easy Setup - Anyone who knows containers - knows how easy it is to create a setup like above and make these container talk to each other. 
5.	Nice code-coverage reports - As shown in the screenshot, it is possible to get code coverage reports from a running service. 
So finally, the variation in the testing pyramid looks something like this:

![](https://cdn.img.wenhairu.com/images/2019/10/03/8tohP.png)
In a way, it’s really these powerful set of tools and techniques coming together to create Component-Test suite which has sort of the same good characteristics as that of the Unit tests. In terms of adoption, It has been practically exponential within our organization. 

#### Lessons Learned

##### Variation in the testing pyramid
As explained above - we are seeing more and more stacks coming-up that are very heavy on Component-Tests as the primary means of testing followed by some Unit Tests and rest of the pyramid follows.
##### Component Test Suite should be easy to spin up and tear down locally
Two things which can boost developer’s productivity to the next level - 1) Very simple local dev setup (checkout blog Microservices - Developer Experience blog) and 2) a very simple way to test your service in isolation w/o worrying about the entire environment. Snapshot style mocks helps in creating fast and reliable tests. It helps in cross team development also meaning - if I need to create a small pr for other team’s service, reviewer and I, both would feel much more confident that it wouldn't break anything else if all their existing use case pass in a fast and reliable way.
##### Reliable and Fast Components tests payoff well in terms of Ease of Refactoring and Portability
In microservice architecture - when evolving a new version of the API, it’s very common thing to refactor an entire service code or rewrite it from scratch in a different language. I have personally done it so many times! In such fast paced development, these Component-Tests become very important because they become portable! You can completely rewrite a service in a different language but still reuse the existing tests by just swapping out the old service container with the new one! In traditional stacks that are heavy on Unit-Tests only, this level of refactoring becomes very difficult since Unit-Tests are non-portable in nature.
##### Consumer driven tests are becoming more important than before
Everytime we are building a new version of the API, it very important to make sure the existing consumers continue to work as expected. For this, we allow consumers to submit their actual use-case into our pipelines in terms of Component-Tests. This helps in two ways - 1) We know what part of our API are actually used and it helps with the refactoring (if you have a GraphQL api - it becomes even simpler to track) and 2) On every checkin - we can make sure we never break their original use-case. 












