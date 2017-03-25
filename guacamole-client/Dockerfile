FROM dordoka/rpi-tomcat:8.0.20
MAINTAINER Michael Jumper <mike.jumper@guac-dev.org>

# Environment variables
ENV \
    BUILD_DIR=/tmp/guacamole-docker-BUILD \
    BUILD_DEPENDENCIES="                  \
        maven"

# Add configuration scripts
COPY guacamole-docker/bin /opt/guacamole/bin/

# Copy source to container for sake of build
COPY . "$BUILD_DIR"

RUN  apt-get update                                                    && \
    apt-get install -y curl

# Build latest guacamole-client and authentication
RUN apt-get update                                                    && \
    apt-get install -y --no-install-recommends $BUILD_DEPENDENCIES    && \
    /opt/guacamole/bin/build-guacamole.sh "$BUILD_DIR" /opt/guacamole && \
    rm -Rf "$BUILD_DIR"                                               && \
    rm -Rf /var/lib/apt/lists/*                                       && \
    apt-get purge -y --auto-remove $BUILD_DEPENDENCIES

# Start Guacamole under Tomcat, listening on 0.0.0.0:8080
EXPOSE 8080
CMD ["/opt/guacamole/bin/start.sh" ]

