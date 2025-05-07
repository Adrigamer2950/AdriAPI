# AdriAPI

<!--suppress HtmlDeprecatedAttribute -->
<p align="center">
    <img src="https://avatars.githubusercontent.com/u/58531641?v=4" height="200" alt="Logo" />
</p>

An API aimed to simplify the development of Paper plugins

![Build](https://github.com/Adrigamer2950/AdriAPI/actions/workflows/build.yml/badge.svg)

# Info

This API is aimed to simplify the development of Paper plugins,
providing a lot of utilities that will make your life (and mine) easier

> [!CAUTION]
> You need Java 17+ to use this API/Plugin. If you are using a lower version consider upgrading

> [!WARNING]
> Only Minecraft 1.17 or superior is officially supported. Versions below 1.17 will not receive any support

### It's mostly used in my plugins, but you can use it on your own plugins if you want, just make sure to give proper credit!

# How to import

## Gradle

````kotlin
repositories {
    maven {
        name = "devadri"
        url = uri("https://repo.devadri.es/repository/releases") // Use dev repository for beta builds
    }
}

dependencies {
    implementation("me.adrigamer2950:AdriAPI:{VERSION}") // Replace {VERSION} with the desired version
}
````

## Maven

```xml
<repositories>
    <repository>
        <id>devadri</id>
        <url>https://repo.devadri.es/repository/releases</url> <!-- Use dev repository for beta builds -->
    </repository>
</repositories>

<dependencies>
    <dependency>
        <groupId>me.adrigamer2950</groupId>
        <artifactId>AdriAPI</artifactId>
        <version>{VERSION}</version> <!-- Replace {VERSION} with the desired version -->
    </dependency>
</dependencies>
```

# Credits
- Thanks to [Byteflux](https://github.com/Byteflux) and [AlessioDP](https://github.com/AlessioDP)
  for creating the [Libby](https://github.com/AlessioDP/libby/tree/gradle) library, which is used to manage
  dependencies at runtime.
- Thanks to [CryptoMorin](https://github.com/CryptoMorin) for creating the [XSeries](https://github.com/CryptoMorin/XSeries) library,
  which is used to preserve compatibility with older and newer versions of Minecraft, as IDs and APIs can vary
  between versions.