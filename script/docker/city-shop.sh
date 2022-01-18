docker stop shop-api
docker rm shop-api
docker rmi shop-api:0.1
docker build -t shop-api:0.1 .
docker run -d --name shop-api -p 9527:9527 shop-api:0.1