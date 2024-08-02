* Usage

curl -X GET --location "http://localhost:8080/api/v1/open/currency?source_currency=USD&date=2024-08-02"

curl -X GET --location "http://localhost:8080/api/v1/open/currency/convert?source_currency=USD&target_currency=KZT&source_currency_value=1.5"

curl -X GET --location "http://localhost:8080/api/v1/open/currency/convert?source_currency=USD&target_currency=KZZ&source_currency_value=1.5"