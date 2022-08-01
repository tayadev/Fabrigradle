# Fabrigradle

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