FROM oraclelinux:8 as builder

RUN set -eux; \
    dnf install -y tar git wget unzip

ENV JAVA_URL=https://download.oracle.com/java/21/latest \
    JAVA_HOME=/usr/java/jdk-21

SHELL ["/bin/bash", "-o", "pipefail", "-c"]
RUN set -eux; \
    ARCH="$(uname -m)" && \
    if [ "$ARCH" = "x86_64" ]; then ARCH="x64"; fi && \
    JAVA_PKG="$JAVA_URL/jdk-21_linux-${ARCH}_bin.tar.gz"; \
    JAVA_SHA256=$(curl -sSL "$JAVA_PKG.sha256") && \
    curl -o /tmp/jdk.tgz -sSL "$JAVA_PKG" && \
    echo "$JAVA_SHA256  /tmp/jdk.tgz" | sha256sum -c - && \
    mkdir -p "$JAVA_HOME" && \
    tar --extract --file /tmp/jdk.tgz --directory "$JAVA_HOME" --strip-components 1

ENV PATH=$JAVA_HOME/bin:$PATH

RUN wget https://services.gradle.org/distributions/gradle-8.8-bin.zip -P /tmp && \
    unzip -d /opt/gradle /tmp/gradle-8.8-bin.zip && \
    ln -s /opt/gradle/gradle-8.8/bin/gradle /usr/bin/gradle

WORKDIR /app

COPY . .

RUN gradle clean build -x test

FROM oraclelinux:8

ENV LANG en_US.UTF-8
ENV JAVA_HOME=/usr/java/jdk-21
ENV PATH=$JAVA_HOME/bin:$PATH

COPY --from=builder $JAVA_HOME $JAVA_HOME

COPY --from=builder /app/build/libs/*.jar /app/app.jar

RUN set -eux; \
    dnf -y update; \
    dnf install -y \
        freetype fontconfig; \
    rm -rf /var/cache/dnf; \
    ln -sfT "$JAVA_HOME" /usr/java/default; \
    ln -sfT "$JAVA_HOME" /usr/java/latest; \
    for bin in "$JAVA_HOME/bin/"*; do \
        base="$(basename "$bin")"; \
        [ ! -e "/usr/bin/$base" ]; \
        alternatives --install "/usr/bin/$base" "$base" "$bin" 20000; \
    done

EXPOSE 8080

ENTRYPOINT ["java", "-jar", "/app/app.jar"]
