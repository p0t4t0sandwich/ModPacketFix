# ModPacketFix

[![License](https://img.shields.io/github/license/p0t4t0sandwich/ModPacketFix?color=blue)](https://img.shields.io/github/downloads/p0t4t0sandwich/ModPacketFix/LICENSE)
[![Github](https://img.shields.io/github/stars/p0t4t0sandwich/ModPacketFix)](https://github.com/p0t4t0sandwich/ModPacketFix)
[![Github Issues](https://img.shields.io/github/issues/p0t4t0sandwich/ModPacketFix?label=Issues)](https://github.com/p0t4t0sandwich/ModPacketFix/issues)
[![Discord](https://img.shields.io/discord/1067482396246683708?color=7289da&logo=discord&logoColor=white)](https://discord.neuralnexus.dev)
[![wakatime](https://wakatime.com/badge/github/p0t4t0sandwich/ModPacketFix.svg)](https://wakatime.com/badge/github/p0t4t0sandwich/ModPacketFix)

A plugin that fixes some of the too big/too small packet errors that occur when connecting to a Vanilla server with a Forge client.

[Link to my development Discord](https://discord.neuralnexus.dev)

When reporting an issue, please include the following:

- Client version and type (Forge, Vanilla, etc.)
- Server version and type (Spigot, Paper, etc.)
- Velocity version and Ambassador version
- Mod list
- Install [Spit it Out](https://www.curseforge.com/minecraft/mc-mods/spit-it-out), recreate the error, then include the Client log file
- Server log file
- Velocity log file

## Download

[![Github Releases](https://img.shields.io/github/downloads/p0t4t0sandwich/ModPacketFix/total?label=Github&logo=github&color=181717)](https://github.com/p0t4t0sandwich/ModPacketFix/releases)
[![Maven Repo](https://img.shields.io/maven-metadata/v?label=Release&metadataUrl=https%3A%2F%2Fmaven.neuralnexus.dev%2Freleases%2Fdev%2Fneuralnexus%2FModPacketFix%2Fmaven-metadata.xml)](https://maven.neuralnexus.dev/#/releases/dev/neuralnexus/ModPacketFix)
[![Maven Repo](https://img.shields.io/maven-metadata/v?label=Snapshot&metadataUrl=https%3A%2F%2Fmaven.neuralnexus.dev%2Fsnapshots%2Fdev%2Fneuralnexus%2FModPacketFix%2Fmaven-metadata.xml)](https://maven.neuralnexus.dev/#/snapshots/dev/neuralnexus/ModPacketFix)

[![Spigot](https://img.shields.io/spiget/downloads/112937?label=Spigot&logo=spigotmc&color=ED8106)](https://www.spigotmc.org/resources/modpacketfix.112937/)
[![Hangar](https://img.shields.io/badge/Hangar-download-blue)](https://hangar.papermc.io/p0t4t0sandwich/ModPacketFix)
[![Modrinth](https://img.shields.io/modrinth/dt/modpacketfix?label=Modrinth&logo=modrinth&color=00AF5C)](https://modrinth.com/mod/modpacketfix)

[//]: # ([![CurseForge]&#40;https://img.shields.io/curseforge/dt/900128?label=CurseForge&logo=curseforge&color=F16436&#41;]&#40;https://www.curseforge.com/minecraft/mc-mods/modpacketfix&#41;)

## Dependencies

### Required Dependencies

- [ProtocolLib](https://github.com/dmulloy2/ProtocolLib/releases) - For Bukkit/Spigot/Paper servers

## Fixes Implemented

- Server sending a recipe book packet that is too big
