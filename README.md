# API for currency exchange
# supported currencies
EUR, RUB, USD, GBP, JPY, CHF, CNY, TRY

# Database setup:
docker run --name mysql-exchange -e MYSQL_ROOT_PASSWORD=password -e TZ=UTC -e MYSQL_DATABASE=exchange -d -p 3308:3306 mysql

# Fixer API access key is necessary
get free access key for Fixer API https://fixer.io/product

# usage examples:
Exchange request: 
curl "http://HOSTNAME:8081/exchange?amount=200&source=EUR&target=USD&user_id=1"
amount - amount of source currency
source - source currency
target - target currency
user_id - ID of user

response: { 
id: 12
result: 225,66
}
id - request ID
result - amount in target currency

