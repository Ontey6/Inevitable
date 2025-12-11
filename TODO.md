add more options, especially messages

make brigadier commands reloadable with /in rel - sometime else

make selectors togglable - sometime else

make config command options reloadable

fix commands not be able to have different names than original - finished

make name and aliases into one names field - finished

migrate enderchest command to brigadier and deprecate Command API (for removal) - finished

make gamemode command's suggestions dynamic - finished

use FileLog everywhere (rather than just printStackTrace)

add more debug messages

more reloadability

# New Command API:
- add name - (now identifier) - finished
- use setRoot rather than reflection in constructor - finished
- merge name and aliases (and enabled) into names - finished

# Ideas

## Commands
- msg
- gamemode/gm
- manage (with menu api)
- better xp command
- worldguard and region command with brigadier

## Features
- variables (also accessible via papi)