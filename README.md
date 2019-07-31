# Waes Passport - Assignment
This Assignment is my passport to get a job in the Netherlands at Waes!

## The Assignment

* Provide 2 http endpoints that accepts JSON base64 encoded binary data on both endpoints
    * host/v1/diff/{ID}/left and host/v1/diff/{ID}/right
* The provided data needs to be diff-ed and the results shall be available on a third end point
    * host/v1/diff/{ID}
* The results shall provide the following info in JSON format
    * If equal return that
    * If not of equal size just return that
    * If of same size provide insight in where the diffs are, actual diffs are not needed.
    * So mainly offsets + length in the data
* Make assumptions in the implementation explicit, choices are good but need to be
communicated


## Stack
- Java 11
- Spring boot 2.x
- Hibernate
- H2 Database
- Docker
- Jersey
- JUnit 5
- Gradle
- Log4j

## How to run
For the usability purposes you can find the built waes-passport.jar in the /run-package directory

```bash
java -jar ./run-package/waes-passport.jar
```

Or you can use the docker image: [tonsaito/waes-passport](https://hub.docker.com/r/tonsaito/waes-passport)

```bash
docker run -p 8090:8090 --name waes tonsaito/waes-passport
```

## Future improvements
```
- API authentication/authorization: OAuth 2.0
- Externalize configuration (12Factor)
- JMeter Test (To validate concurrency and stress test)
- Use Lombok to generate getters, setters and another beans methods
- Use sonarcloud for static code analysis
```

## API documentation

Generated Swagger url: [http://localhost:8090/swagger-ui.html](http://localhost:8090/swagger-ui.html)

## Usage

You can import postman collection using the <b>run-package/waes-passport.postman_collection.json</b> file,
use the swagger interface or just call the follow endpoints

#### PUT http://localhost:8090/v1/diff/{id}/left 
#### PUT http://localhost:8090/v1/diff/{id}/right

**Body**
```
{
	"encoded_data" : "V2UgYXJlIHdhZXMh"
}
```
**Possible Responses**

```
[400] Required field encoded_data
[500] Invalid base64 entry
[201] Request received successfully
```
___

#### GET http://localhost:8090/v1/diff/{id}

**Possible Responses**

```
[400] Invalid ID. Please, try again (for non numeric ids)
[200] ID not found. Please, try again
[200] Equal content
[200] Not equal size content
[200] Not equal content with the following example body:
{
    "result": "Not Equal content",
    "diffList": [
        {
            "offset": 1,
            "length": 1
        },
        {
            "offset": 2,
            "length": 1
        }
    ]
}
```
