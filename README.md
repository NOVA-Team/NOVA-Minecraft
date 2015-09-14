[![Build Status](https://img.shields.io/travis/NOVA-Team/NOVA-Minecraft.svg?style=flat-square)](https://travis-ci.org/NOVA-Team/NOVA-Minecraft)
[![Coverage](https://img.shields.io/codecov/c/github/NOVA-Team/NOVA-Minecraft.svg?style=flat-square)](https://codecov.io/github/NOVA-Team/NOVA-Minecraft)

# Minecraft-Plugin
A collection of NOVA features that are Minecraft-specific.

## Workspace Setup
Type the following command in the root directory of the repository.
```
gradlew setupDecompWorkspace idea
```

### Dependency
* NOVA-Core

## Redstone API
Add the Redstone component to your block and it will be able to handle Redstone signals.

Simply add this to your block and it will be able to handle Redstone events:

```java
add(Game.components().make(Redstone.class, this))
```
