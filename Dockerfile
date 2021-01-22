FROM hseeberger/scala-sbt:8u222_1.3.5_2.13.1

RUN apt-get update && \
    apt-get install -y sbt libxrender1 libxtst6 libxi6

WORKDIR /SE_GWENT
ADD . /SE_GWENT
CMD sbt test
CMD sbt run

#docker  build -t  gwent:v1 .
#docker run -ti  --rm -e DISPLAY=192.168.2.177:0.0 gwent:v1