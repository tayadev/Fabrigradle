![Fabrigradle](assets/fabrigradle_logo_with_text.png)

# An intelligent Gradle plugin used to develop mods for [fabric](https://fabricmc.net).

<!-- <div align="center"> -->
[![License: GPL v3](https://img.shields.io/badge/License-GPLv3-blue.svg?style=for-the-badge)](https://www.gnu.org/licenses/gpl-3.0)
[![Gradle](https://img.shields.io/badge/Gradle-02303A.svg?style=for-the-badge&logo=Gradle&logoColor=white)](https://gradle.org/)
[![Ko-Fi](https://img.shields.io/badge/Ko--fi-F16061?style=for-the-badge&logo=ko-fi&logoColor=white)](https://ko-fi.com/tayacrystal)
<!-- </div> -->

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
  entrypoints {
    main 'one.taya.example.ExampleMod' // The main class of your mod
  }
}
```
This is the minimal setup to get your mod to build and run, for adding further configuration refer to the following sections explaining the different configuration options.

## Configuration Options

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
- To be documented
### Jars
- To be documented
### LanguageAdapters
- To be documented
### Mixins
- To be documented
### Accesswidener
- To be documented
### Depends
- To be documented
### Recommends
- To be documented
### Suggests
- To be documented
### Conflicts
- To be documented
### Breaks
- To be documented
### Name
- To be documented
### Description
- To be documented
### Authors
- To be documented
### Contributors
- To be documented
### Contact
- To be documented
### License
- To be documented
### Icon
- To be documented
### Eula
- To be documented
### ApiVersion
- WIP