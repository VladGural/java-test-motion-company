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
mkdir java_test
cd java_test
```



docker network ls
docker network create motion-test
docker network ls

docker run -d --name=kafka --network motion-test -p 9092:9092 apache/kafka

docker run \
--name postgres-company \
--network motion-test \
-p 55432:5432 \
-e POSTGRES_USER=admin \
-e POSTGRES_PASSWORD=secret123 \
-e POSTGRES_DB=company_db \
-d postgres:16

docker run \
--name postgres-analytic \
--network motion-test \
-p 54432:5432 \
-e POSTGRES_USER=admin \
-e POSTGRES_PASSWORD=secret123 \
-e POSTGRES_DB=analytic_db \
-d postgres:16

cd ./company
mvn clean install
docker build -t company-service .

docker run \
--name company-service \
--network motion-test \
-p 8080:80 \
-d company-service

cd ./analytic
mvn clean install
docker build -t analytic-service .

docker run \
--name analytic-service \
--network motion-test \
-p 8081:80 \
-d analytic-service


docker compose up --detach

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
            "companyAddress": [
                "HEADQUARTER",
                "DISTRIBUTION_CENTER"
            ]
        },
        {
            "country": "Ukraine",
            "city": "Lviv",
            "street": "Velika Gora",
            "zip": "02011",
            "companyAddress": [
                "HEADQUARTER"
            ]
        }
    ]
}