# Inevitable
An essential-like plugin that focuses on being modular and modern

---

## 🧪 Alpha Version
this plugin is still under development

---

## 📦 Features
- Custom Join/Quit messages & actions
- **NOTE:** all commands are fully customizable and modular
- Commands for opening utility blocks _(e.g. Crafting Table, Loom...)_
- and many more commands...

---

## 🧰 Requirements
- **Minecraft Version:** 1.21.X
- **Server Type:** Paper and above
- **Java Version:** 21+

---

## ⚙️ Installation
There is 2 ways to do use the plugin right now.

**DISCLAIMER:** Do not use this plugin on public servers, as it is still in ALPHA development.

<details>
<summary><b>Way 1. Download the plugin</b></summary>

This won't always be the latest version or even available
1. Download the latest release from [Releases](https://github.com/Ontey6/Inevitable/releases).
2. Place the `.jar` file in your server's `/plugins/` folder.
3. Restart or reload your server.
4. Check `/plugins` to confirm successful load.
</details>

<details>
<summary><b>Way 2. Compile it yourself</b></summary>

This will usually contain small bugs without hotfixes, but has the latest features
1. Download the Code from [GitHub](https://github.com/Ontey6/Inevitable/archive/refs/heads/master.zip)
2. Unzip and open it in your IDE _(preferred IntelliJ)_
3. **IMPORTANT:** Wait for all processes to be finished _(loading bar on the right bottom)_
4. Go to the Gradle Tab on the right _(the elefant)_
5. In that window, double click `Inevitable.Tasks.paperweight.reobfJar` _(with a gearwheel icon)_
   If that doesn't work, use `Inevitable.Tasks.build.build`, but that jar is illegal to distribute (only personal use)!
6. In the files on the left, open `Inevitable/build/libs/` and copy the `.jar` file in there _(CTRL + C)_
7. Paste the `.jar` file in your server's `/plugins/` folder _(CTRL + V)_
8. Restart or reload your server.
9. Check `/plugins` to confirm successful load.
</details>

---

## 🔧 Configuration

<details>
<summary>⚙️<b> config.yml</b></summary>

Default `config.yml`:
```yaml
format:
  prefix: '[Inevitable]'
  placeholder-format: '<%ph>'

# The notification to all players when a player joins
# comment out/remove to remove it
# Listable; can be String or List
join-message: '&e<player> joined the game'

# The notification to all players when a player leaves the game
# comment out/remove to remove it
# Listable; can be String or List
quit-message: '&e<player> left the game'

# An action section which is executed when a player joins
# Executor: player who joined
on-join:
  message: 'Welcome to the server!'
  commands: 'title <player> actionbar §aWelcome'

# An action section which is executed when a player leaves the game
# Executor: player who left the game
on-quit: {}
```

</details>

<details>
<summary><b>📟 commands.yml</b></summary>

The commands.yml file is generated on runtime.

A generated command should look like this:

```yaml
enderchest:
    name: enderchest
    description: Opens your enderchest
    usage: /<command> [player]
    enabled: true
    permission: inevitable.command.enderchest
    aliases:
    - ec
    options: {}
```

</details>

---

## 🕹️ Commands
All commands can be customized in commands.yml

They all contain description and usage by default, so look in commands.yml,
or if you want to reset the command to its defaults, delete it and restart / reload the server.

---

## 🪪 Permissions
| Permission                           | Default | Description                                                                                             |
|--------------------------------------|---------|---------------------------------------------------------------------------------------------------------|
| inevitable.command.\<command>        | op      | Grants access to that command (customizable in commands.yml)                                            |
| inevitable.command.\<command>.target | op      | makes the player able to target other players with that command (needs to be a TargetableConfigCommand) |

---

## 🐞 Issues / Support
Open an [issue](https://github.com/Ontey6/Inevitable/issues) for bugs or feature requests.

---

## 🧑‍💻 Developers
- **Author:** Ontey
- **Contributors:** You are always welcome to contribute

---

## 📜 License
Custom License based on MIT

<details>
<summary><b>LICENSE</b></summary>

Custom License (based on MIT)

Copyright (c) 2025, Ontey

Permission is given, free of charge, to anyone who gets a copy of this
software and related files (the "Software"), to use, copy, change,
merge, publish, or share the Software. You may also make and use your
own versions of it, as long as you follow these rules:

1. Rules for sharing this Software
   - Do not use it for anything illegal.
   - You are not allowed to sell the Software itself.
   - You are not allowed to use it on Minecraft servers with sexual content.
   - You must always give credit to the original author (Ontey).
   - You may not rename or claim the Software as your own.
     (Modifications and new features are allowed, but authorship must stay clear.)

2. Rules for using this Software
   - Do not use it on cracked (non-official) Minecraft servers.
   - Do not use it in ways that Mojang says are not allowed.
   - You are allowed to use the Software commercially (for example on paid servers or servers that accept donations).

This permission notice and the copyright notice above must be included in
all copies or large parts of the Software.

THE SOFTWARE IS PROVIDED "AS IS", WITHOUT ANY WARRANTY. THIS INCLUDES
NO PROMISE OF BEING ERROR-FREE OR FIT FOR A PARTICULAR USE. THE AUTHORS
ARE NOT RESPONSIBLE FOR ANY PROBLEMS, DAMAGES, OR LOSSES THAT MAY HAPPEN
FROM USING THE SOFTWARE.

</details>