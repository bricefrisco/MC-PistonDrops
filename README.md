# PistonDrops
### Change the items that drop when a block is destroyed by a piston.

## Config
```yaml
# Please don't change the version manually.
version: 1

# https://hub.spigotmc.org/javadocs/bukkit/org/bukkit/Material.html

items-to-change:
  PUMPKIN: # The block, when destroyed by a piston, that will have its drops replaced.
    new-drop: PUMPKIN_PIE # What you would like to drop instead
    quantity: 1 # How much of it you would like to drop
  MELON:
    new-drop: NONE # Use NONE to disable drops. Do not use 'AIR' or other materials that cannot be dropped.
    quantity: 0
```