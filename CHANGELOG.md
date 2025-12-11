switched bukkit command api to brigadier
- rewrote the command api for brigadier:
  - brigadier api now in package `com.ontey.api.brigadier`
  - added static factory class `Arg` for arguments instead of having multiple classes for args
  - renamed `ConfigCommand.Defaults` to `CommandConfiguration`
    - removed `usage` because brigadier makes its own
    - collapsed `name` and `aliases` into `names`
  - renamed `ConfigCommand.Settings` to `CommandOptions`
    - made messages disablable by setting them to `false`
  - Put the records `CommandConfiguration` and `CommandOptions` each into their own class
- con: can't reload commands via `/inevitable reload` anymore

made join and quit message modular

added modular chat-format file and module

added `messages.incapable-executor` message to the options of targetable commands

bumped paperweight userdev version to 2.0.0-beta.19

removed option to specify the prefix in config.yml

made messages togglable by setting `message: false`