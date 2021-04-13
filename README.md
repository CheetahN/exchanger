# API for currency exchange

# database setup:
docker run --name mysql-exchange -e MYSQL_ROOT_PASSWORD=password -e TZ=UTC -e MYSQL_DATABASE=exchange -d -p 3308:3306 mysql

# exchange example:
curl "http://HOSTNAME:8081/exchange?amount=200&original=EUR&target=USD&user_id=1"
