FROM debian:jessie

RUN echo oracle-java8-installer shared/accepted-oracle-license-v1-1 select true | /usr/bin/debconf-set-selections

RUN apt-key adv --keyserver hkp://keyserver.ubuntu.com:80 --recv-keys EEA14886

RUN echo "deb http://ppa.launchpad.net/webupd8team/java/ubuntu trusty main" | tee /etc/apt/sources.list.d/webupd8team-java.list
RUN echo "deb-src http://ppa.launchpad.net/webupd8team/java/ubuntu trusty main" | tee -a /etc/apt/sources.list.d/webupd8team-java.list

RUN apt-get update

# install oracle jdk
RUN apt-get install oracle-java8-installer -y

COPY ./buildData/roadConceptDB.sql /var/
COPY ./buildData/init.sh /var/
COPY ./buildData/road-concept.jar /var/

RUN chmod +x /var/init.sh
RUN chmod +x /var/road-concept.jar

EXPOSE 8080

# uncomment when Dockerfile is ready
# RUN apt-get clean

CMD /var/init.sh
