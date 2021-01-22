FROM hseeberger/scala-sbt:8u222_1.3.5_2.13.3
WORKDIR /Gwent-SE
ADD . /Gwent-SE
CMD sbt test
CMD sbt run

#docker  build -ti Gwent-SE:v1
#docker run -ti Gwent-SE:v1