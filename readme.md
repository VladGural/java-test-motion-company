# How run this test

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