# Obsidian

<!--suppress HtmlDeprecatedAttribute -->
<p align="center">
    <img src="https://raw.githubusercontent.com/Adrigamer2950/Obsidian/refs/heads/master/logo_256x.png" alt="Logo" />
</p>

An API aimed to simplify the development of Paper plugins

[![Available in Modrinth](https://raw.githubusercontent.com/intergrav/devins-badges/refs/heads/v3/assets/cozy/available/modrinth_vector.svg)](https://modrinth.com/plugin/obsidiancore)
[![Available in HangarMC](https://raw.githubusercontent.com/intergrav/devins-badges/refs/heads/v3/assets/cozy/available/hangar_vector.svg)](https://hangar.papermc.io/devadri/Obsidian)

# Info

This API is aimed to simplify the development of Paper plugins,
providing a lot of utilities that will make your life (and mine) easier

Even though a standalone plugin version is available, it's recommended to shade this API into your
plugins, as this API changes frequently and depending on the standalone version may cause
some issues if server owners install some plugins that depend on different versions of this API

> [!CAUTION]
> You need Java 17+ to use this API/Plugin. If you are using a lower version consider upgrading

> [!WARNING]
> You need to set your plugin to use Spigot mappings in order to preserve compatibility with 
> Paper forks that are still using Spigot mappings.
> More information in the [PaperMC documentation](https://docs.papermc.io/paper/dev/project-setup/#plugin-remapping)

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
    implementation("me.devadri:obsidian:{VERSION}") // Replace {VERSION} with the desired version
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
        <groupId>me.devadri</groupId>
        <artifactId>obsidian</artifactId>
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