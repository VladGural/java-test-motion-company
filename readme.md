# Java Test Assignment (Software Engineer position at Motion)

### High-Level Requirements
Build a simple system for managing company data with analytics capabilities.
The system should consists of two independent services: Company and
Analytics. The communication between services should be asynchronous.

#### Company
This service must provide endpoints to allow users to create, view, update, and
delete companies.

A company should include:
* company name
* status (active, inactive, blocked)
* registration number
* contact information
* industry
* addresses (country, city, street, zip, category)

Each company can have multiple addresses. Each address can belong to
multiple categories, for example headquarters, warehouse, branch office,
distribution center etc.

When a company is created, updated, or deleted, the corresponding events
should be sent to the Analytics Service.

#### Analytics
This service must provide endpoints for viewing simple statistics about a
company, including:
* current name of the company
* any previous names of the company
* aggregated statistics on the number of each address category
associated with the company (e.g., "warehouse - 3," "headquarters - 1").
* current status

The statistics must be updated based on events received from the Company
Service.

### Technical Requirements
* Java 17 or higher
* Spring Boot 3.x
* Postgres 16
* Kafka
* Docker & Docker Compose
* Gradle or Maven
* Spring Data JDBC or Spring Data JPA for persistence

* [Optional] Use WebFlux and R2DBC
* [Optional] Implement CDC
* [Optional] Write basic unit and integration tests for key functionalities
* [Optional] Document any architectural decisions or trade-offs considered
during the implementation

### Submission
* Provide a GitHub repository with the solution
* The solution should contain a readme file with instructions on how to set up
and run the application locally, including any configuration required

 
# How run solution of this test

You should have JDK 21, Maven, Docker, and Git installed on your computer to run this test. 

Create a new directory and move to it
```
mkdir java_test && cd java_test
```

Clone all artifacts from GitHab repository
```
git clone https://github.com/VladGural/java-test-motion-common.git
```

```
git clone https://github.com/VladGural/java-test-motion-exception.git
```

```
git clone https://github.com/VladGural/java-test-motion-analytic.git
```

```
git clone https://github.com/VladGural/java-test-motion-company.git
```

Build and put all necessary libs in local maven repo
```
mvn -f ./java-test-motion-common/pom.xml clean install
```

```
mvn -f ./java-test-motion-exception/pom.xml clean install
```

Build and put all services in the local maven repo. 
Meanwhile, it will start component tests for Company and Analytic services.
```
mvn -f ./java-test-motion-company/pom.xml clean install
```

```
mvn -f ./java-test-motion-analytic/pom.xml clean install
```

Create docker images for Company and Analytic services
```
docker build -t company-service ./java-test-motion-company/.
```

```
docker build -t analytic-service ./java-test-motion-analytic/.
```

And now run services in docker containers
```
docker compose -f ./java-test-motion-company/compose.yaml up --detach
```

For check type, the next
```
docker ps
```

And if everything was done correctly we will see something like this.
```
CONTAINER ID   IMAGE                              COMMAND                  CREATED          STATUS          PORTS                                                               NAMES
3800d7990512   analytic-service                   "/__cacert_entrypoin…"   49 seconds ago   Up 49 seconds   0.0.0.0:8081->80/tcp, [::]:8081->80/tcp                             analytic-service
278099ea88f1   company-service                    "/__cacert_entrypoin…"   49 seconds ago   Up 49 seconds   0.0.0.0:8080->80/tcp, [::]:8080->80/tcp                             company-service
7653491a7d7d   confluentinc/cp-kafka:latest       "/etc/confluent/dock…"   49 seconds ago   Up 49 seconds   0.0.0.0:9092->9092/tcp, :::9092->9092/tcp                           kafka
01599f916f96   confluentinc/cp-zookeeper:latest   "/etc/confluent/dock…"   50 seconds ago   Up 49 seconds   2888/tcp, 3888/tcp, 0.0.0.0:22181->2181/tcp, [::]:22181->2181/tcp   zookeeper
d88dd6174c9c   postgres:16                        "docker-entrypoint.s…"   50 seconds ago   Up 49 seconds   0.0.0.0:55432->5432/tcp, [::]:55432->5432/tcp                       postgres-company
fd8429babf20   postgres:16                        "docker-entrypoint.s…"   50 seconds ago   Up 49 seconds   0.0.0.0:54432->5432/tcp, [::]:54432->5432/tcp                       postgres-analytic
```

Our services are running!!!

For testing we can use postmen
```
CREATE COMPANY
URL: http://localhost:8080/v1/companies
METHOD: POST
REQUEST:
{
    "name": "Alis",
    "status": "ACTIVE",
    "contactInformation": "phone: +380503332211, mail: info@alis.pro",
    "industry": "food",
    "companyAddress": [
        {
            "country": "Ukraine",
            "city": "Kyiv",
            "street": "Vasilya Tutunnika",
            "zip": "03150",
            "addressCategory": [
                "HEADQUARTER",
                "DISTRIBUTION_CENTER"
            ]
        },
        {
            "country": "Ukraine",
            "city": "Lviv",
            "street": "Velika Gora",
            "zip": "02011",
            "addressCategory": [
                "HEADQUARTER"
            ]
        }
    ]
}
```

```
GET CURRENT NAME
URL: http://localhost:8081/v1/companies/{ID_YOUR_COMPANY_CREATED_ABOVE}/current-names
METHOD: GET
RESPONSE:
{
    "currentName": "Alis"
}
```

```
GET PREVIOUS NAMES
URL: http://localhost:8081/v1/companies/{ID_YOUR_COMPANY_CREATED_ABOVE}/names
METHOD: GET
RESPONSE:
{
    "currentName": "Alis",
    "previousNames": []
}
```

```
GET ADDRESS CATEGORY STAT
URL: http://localhost:8081/v1/companies/{ID_YOUR_COMPANY_CREATED_ABOVE}/address-category-stats
METHOD: GET
RESPONSE:
{
    "addressCategoryStat": {
        "DISTRIBUTION_CENTER": 1,
        "HEADQUARTER": 2
    }
}
```

```
GET CURRENT STATUS
URL: http://localhost:8081/v1/companies/{ID_YOUR_COMPANY_CREATED_ABOVE}/current-statuses
METHOD: GET
RESPONSE:
{
    "currentStatus": "ACTIVE"
}
```