# Build and run

## Запустить Postgresql
```bash
docker-compose --file docker-compose/docker-compose.yaml up
```

## Собрать jar
```bash
./mvnw clean verify -DskipTests
```

## Запустить jar
```bash
java -jar target/currency_exchange-0.0.1-SNAPSHOT.jar
```



# Usage

## Получить текущий курс.
* source_currency - для какой валюты
* date - на какую дату (необязательный параметр по умолчанию используется текущая дата) 

```bash
curl -X GET --location "http://localhost:8080/api/v1/open/currency?source_currency=USD&date=2024-08-05"
```

## Конвертировать из одной валюты в другую
* source_currency - из какой валюты
* target_currency - в какую валюту
* source_currency_value - сумма для конвертации
* date - на какую дату сделать конвертацию (необязательный параметр по умолчанию используется текущая дата)

```bash
curl -X GET --location "http://localhost:8080/api/v1/open/currency/convert?source_currency=USD&target_currency=KZT&source_currency_value=1.5"
```

```bash
curl -X GET --location "http://localhost:8080/api/v1/open/currency/convert?source_currency=USD&target_currency=KZZ&source_currency_value=1.5"
```