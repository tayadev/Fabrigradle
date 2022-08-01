# Fabrigradle
[![License: GPL v3](https://img.shields.io/badge/License-GPLv3-blue.svg?style=for-the-badge)](https://www.gnu.org/licenses/gpl-3.0)
[![Gradle](https://img.shields.io/badge/Gradle-02303A.svg?style=for-the-badge&logo=Gradle&logoColor=white)](https://gradle.org/)
[![Ko-Fi](https://img.shields.io/badge/Ko--fi-F16061?style=for-the-badge&logo=ko-fi&logoColor=white)](https://ko-fi.com/tayacrystal)


![Fabrigradle](assets/fabrigradle_logo_with_text.png)

An intelligent Gradle plugin used to develop mods for [fabric](https://fabricmc.net).

## Example Usage

`build.gradle`
```groovy
plugins {
  id 'one.taya.fabrigradle' version '3.0.0'
}
 
version '1.0.0'

fabrigradle {
  id 'example'
  name 'Cool Name'
  authors {
    name 'Me!' email 'email@example.org'
  }
  entrypoints {
    main 'one.taya.mod.Mod'
  }
}
```