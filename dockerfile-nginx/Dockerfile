FROM ubuntu:xenial

RUN apt-get update && apt-get install -y nginx

ENV RESOURCES_HOME=/files

EXPOSE 80

COPY nginx.conf /etc/nginx/nginx.conf

COPY sample.yml $RESOURCES_HOME/sample.yml

CMD ["nginx", "-g", "daemon off;"]
