# SkinTotem — Minecraft Fabric Mod (1.20.1)

**SkinTotem** is an experimental Minecraft Fabric mod that dynamically changes the texture of the Totem of Undying based on the player’s username. Custom textures are intended to be loaded from the external folder `config/skintotem`.

---

## Features

- Dynamic Totem of Undying texture per player username  
- Planned support for loading textures from external config folder

---

## Current Status

⚠️ **Warning:** This mod is still in !non!active development and **not ready for use!**

- Custom textures from `config/skintotem` are **not yet automatically loaded**  
- Basic model and model loading framework implemented  
- Texture loading and integration is incomplete and requires further work

---

## Requirements

- Minecraft 1.20.1  
- Fabric Loader and Fabric API for 1.20.1  
- Java 17+

---

## Installation

1. Clone this repository  
2. Build the mod using Gradle (`./gradlew build`)  
3. Place the generated `.jar` file into your Minecraft `mods` folder  
4. Create a folder `config/skintotem` for your custom texture PNG files

---

## Usage

- Give a name on anvil to totems

---

## Roadmap

- Implement dynamic loading and registration of textures from `config/skintotem`  
- Support runtime texture updates  
- Improve API for custom texture and model manipulation

---

## Contributing

Feel free to open issues or submit pull requests if you want to help with development or have ideas.

---

Thanks for checking out SkinTotem!
