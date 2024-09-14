# try_jqwik

## 環境構築

- [Maven in 5 Minutes](https://maven.apache.org/guides/getting-started/maven-in-five-minutes.html)

```
$ mvn -v
Apache Maven 3.6.3
Maven home: /usr/share/maven
Java version: 17.0.12, vendor: Ubuntu, runtime: /usr/lib/jvm/java-17-openjdk-arm64
Default locale: en, platform encoding: UTF-8
OS name: "linux", version: "5.15.153.1-microsoft-standard-wsl2", arch: "aarch64", family: "unix"

$ mvn archetype:generate -DgroupId=com.github.gbz3.try_jqwik -DartifactId=my-app -DarchetypeArtifactId=maven-archetype-quickstart -DarchetypeVersion=1.5 -DinteractiveMode=false
$ cd my-app

## build
$ mvn package

## run
$ java -cp target/my-app-1.0-SNAPSHOT.jar com.github.gbz3.try_jqwik.App
Hello World!
$
```
