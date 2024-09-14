# try_jqwik

## 環境構築

- [Maven in 5 Minutes](https://maven.apache.org/guides/getting-started/maven-in-five-minutes.html)
- [Apache Maven 入門](https://zenn.dev/caunus/books/apache-maven-introduction)

```bash
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

## jqwik 有効化

- [User Guide](https://jqwik.net/docs/current/user-guide.html)
- [Kotlin と jqwik で Property Based Testing](https://zenn.dev/msksgm/articles/20221007-kotlin-property-based-testing-with-jqwik)

```bash
## pom.xml に追加
<dependencies>
    ...
    <dependency>
        <groupId>net.jqwik</groupId>
        <artifactId>jqwik</artifactId>
        <version>1.9.0</version>
        <scope>test</scope>
    </dependency>
</dependencies>
```

```bash
$ mvn test
...
[INFO] ------------------------------------------------------------------------
[INFO] BUILD FAILURE
[INFO] ------------------------------------------------------------------------
...
$ 
```
