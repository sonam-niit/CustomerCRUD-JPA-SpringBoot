# implementing Exception Handling

- Create Exception Package in your project
- Create 2 Exceptions as per your project Requirement like
- CustomerNotFoundException
- InvalidParamException

## throwing Exception from Controllers for invalid Parameter Exception
```java
@PostMapping("/")
    public ResponseEntity<Customer> save(@RequestBody Customer customer) {
        //Updated Code Here
        if (customer == null || customer.getName() == null || customer.getName().isBlank() || customer.getEmail()==null || customer.getEmail().isBlank()) {
            throw new InvalidParamsException("Customer name and Email must be provided");
        }
        Customer savedCustomer=service.save(customer);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedCustomer);
    }
```
- Similar like this update for Put and FindByEmail as well
- Try to check From PostMan Method is throwing Exception But Not Handled 
- To handle Create GloablExceptionhandler Class
- Here, 3 Handlers added 2 for Specific Exception Created and One for General Exception

- For, FindByEmail If you pass Blank Email in postman then it shows InvalidParamExecption

## For Customer Not Found Exception update Service Layer

```java
public Customer getCustomerById(Integer id){
        return  repo.findById(id).orElseThrow(()-> new CustomerNotFoundException("Customer Not Found with Id: "+id));
    }

public Customer getCustomerByEmail(String email) {
    return repo
            .findByEmail(email)
            .orElseThrow(() -> new CustomerNotFoundException("Customer not found with email: " + email));
}
```
- Check From PostMan and try to find by id or email which is not available

## implementing Swagger UI
- Add below dependencies in your pom.xml
```xml
<!-- Source: https://mvnrepository.com/artifact/org.springdoc/springdoc-openapi-starter-webmvc-ui -->
<dependency>
    <groupId>org.springdoc</groupId>
    <artifactId>springdoc-openapi-starter-webmvc-ui</artifactId>
    <version>3.0.1</version>
    <scope>compile</scope>
</dependency>
```
- After Adding Dependency Run your Application
- Open Browser and hit below URL
- http://localhost:8082/swagger-ui.html

## Implementing Profiles

- created multiple profiles
  - application-dev.properties
  - application-test.properties
  - application.properties (here you can mention which profile is activated)

- In test data Used H2 database for testing purpose
- To directly seed test data
- create data.sql under resources folder and It will seed data directly in H2 Database

## To Test Repository Directly

- Create Folder named Repo under your existing test package
- Create Test for Repository
- use @DataJpaTest for Testing Repository Layer
- Write TestCases and Run