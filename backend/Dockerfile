ARG JDK_REVISION=eclipse-temurin
ARG JDK_VERSION_DEV=23-jdk-noble
ARG JDK_VERSION_PROD=23-jdk-noble

FROM ${JDK_REVISION}:${JDK_VERSION_DEV} as base
ARG SBT_VERSION=1.10.7
WORKDIR /work
COPY . /work
RUN set -x \
    # install sbt
    && apt update \
    && apt install curl bash unzip -y \
    && SBT_VER=${SBT_VERSION} \
    # piccolo is dead https://github.com/sbt/sbt/issues/5860
    && SBT_URL="https://github.com/sbt/sbt/releases/download/v${SBT_VER}/sbt-${SBT_VER}.zip" \
    && curl -Ls ${SBT_URL} > /tmp/sbt-${SBT_VER}.zip \
    && mkdir /opt/sbt \
    && unzip /tmp/sbt-${SBT_VER}.zip -d /opt/sbt \
    && sed -i -r 's#run \"\$\@\"#unset JAVA_TOOL_OPTIONS\nrun \"\$\@\"#g' /opt/sbt/sbt/bin/sbt \
    && rm -rf /tmp/sbt-${SBT_VER}.tgz /var/cache/apt/*
RUN /opt/sbt/sbt/bin/sbt assembly

FROM ${JDK_REVISION}:${JDK_VERSION_PROD}
ARG SCALA_VERSION=3.6.0
ARG JAR_NAME=calc-hash-site-backend.jar
COPY --from=base /work/target/scala-${SCALA_VERSION}/${JAR_NAME} /opt
ARG USERNAME=scala
ARG GROUPNAME=scala
ARG GROUPID=2000
RUN groupadd -g ${GROUPID} ${GROUPNAME} && \
    useradd -m -s /bin/bash -u ${GROUPID} -g ${GROUPNAME} ${USERNAME}
USER $USERNAME
ENV JAR_NAME=${JAR_NAME}
ENTRYPOINT ["/opt/java/openjdk/bin/java", "-jar", "/opt/calc-hash-site-backend.jar"]
