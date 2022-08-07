![Fabrigradle](assets/fabrigradle_logo_with_text.png)

# An intelligent Gradle plugin used to develop mods for [fabric](https://fabricmc.net).

[![License: GPL v3](https://img.shields.io/badge/License-GPLv3-blue.svg?style=for-the-badge)](https://www.gnu.org/licenses/gpl-3.0)
[![Gradle Plugin](https://img.shields.io/maven-metadata/v?label=Gradle&logo=Gradle&metadataUrl=https://plugins.gradle.org/m2/one/taya/fabrigradle/one.taya.fabrigradle.gradle.plugin/maven-metadata.xml&style=for-the-badge&labelColor=02303a)](https://plugins.gradle.org/plugin/one.taya.fabrigradle)
[![Ko-Fi](https://img.shields.io/badge/Ko--fi-F16061?style=for-the-badge&logo=ko-fi&logoColor=white)](https://ko-fi.com/tayacrystal)

## Purpose
This project provides a centralized configuration with helpful defaults to help you get your fabric mod projects started faster and to keep them more maintainable and reduce mistakes over their development lifecycle.

### Main features
- Description file generation: `fabric.mod.json` and `mixins.json`
- Debug tasks
- Shortcuts for repositories and dependencies

## Getting started creating mods with Fabrigradle
Create a file named `build.gradle` with the following content
```groovy
plugins {
  id 'one.taya.fabrigadle' version '3.0.0'
}

group 'one.taya' // This should a domain you own in reverse, or something reasonably unique
version '0.0.1-SNAPSHOT' // Your project version

// Configuration for Fabrigradle
fabrigradle {

  versions { // look up newest versions on https://fabricmc.net/develop
    minecraft '1.19.2'
    mappings '1.19.2+build.1'
    loader '0.14.9'
    fabricApi '0.58.6+1.19.2'
  }

  entrypoints {
    main 'one.taya.example.ExampleMod' // The main class of your mod
  }
}
```
This is the minimal setup to get your mod to build and run, for adding further configuration refer to the following sections explaining the different configuration options.

## Configuration Options

### Versions
- TODO: Document

### Id
- Has to be all lowercase with only alphanumeric characters
- Defaults to your projects name
```groovy
fabrigradle {
  id 'mod-id'
}
```

### Version
- Should follow [SemVer2.0.0](https://semver.org/) spec
- Defaults to your projects version
```groovy
fabrigradle {
  version '1.2.3'
}
```

### Environment
- The Environment your mod is made for: `server` or `client`
- Defaults to both if omitted
```groovy
fabrigradle {
  environment 'client'
}
```

### Entrypoints
- TODO: Document
### Jars
- TODO: Document
### LanguageAdapters
- TODO: Document
### Mixins
- TODO: Document
### Accesswidener
- TODO: Document
### Depends
- TODO: Document
### Recommends
- TODO: Document
### Suggests
- TODO: Document
### Conflicts
- TODO: Document
### Breaks
- TODO: Document
### Name
- TODO: Document
### Description
- TODO: Document
### Authors
- TODO: Document
### Contributors
- TODO: Document
### Contact
- TODO: Document
### License
- TODO: Document
### Icon
- TODO: Document
### Eula
- TODO: Document