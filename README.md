# API for currency exchange

# database setup:
docker run --name mysql-exchange -e MYSQL_ROOT_PASSWORD=password -e TZ=UTC -e MYSQL_DATABASE=exchange -d -p 3308:3306 mysql

#Fixer API access key
get free access key for Fixer API https://fixer.io/product


# usage examples:
curl "http://HOSTNAME:8081/exchange?amount=200&source=EUR&target=USD&user_id=1"

# supported currencies
EUR, RUB, USD, GBP, JPY, CHF, CNY, TRY