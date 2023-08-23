# Mega Randomizer
Minecraft Randomizer Mod

Welcome to Mega Randomizer, the Minecraft randomizer that you can customize!

This mod comes with custom game rules that allow you to individually turn on or off item drop randomization for
Blocks, Entities or Players. (More to come in future versions!)

The random drop rules can be changed from the in-game menu or through commands.


# Basic Rundown of how my Code Works:

## Randomized Drops Customization

### New GameRules
The mod adds three new GameRules to the game: block random drops, entity random drops, and player random drops.
These are created and registered in the MegaGameRules.java class. The player can change them using commands 
or via the custom Mega Random Options gui options screen.

### ModPauseScreen
The mod listens for when the vanilla Minecraft Pause Screen is triggered and instead displays ModPauseScreen, which is identical
to the vanilla PauseScreen with the addition of a button to open the Mega Randomizer Options Screen.


### Mega Randomizer Options Screen
This class includes one button for toggling each of the three randomizer GameRules.
Since this screen only runs on the client, when the screen is set to this menu, a network packet is sent to the server to request an
update of each GameRule. When a rule is changed from this menu, the rule's value in the server is updated
via a network packet, which also sends another packet back to the client containing all three rule values on the server
side to keep both sides in sync.

### Network Packets

There are three new network packets, which are registered in MegaMessages.java. One network packet is for requesting
an update of MegaGameRule values from the server, one for setting the MegaGameRule values in the server, and one
for setting the MegaGameRule values in the client.

### Dedicated Server
When the mod is running in a dedicated server, the client can not modify the MegaGameRules, and neither the
Mega Randomizer Options screen nor the button to open it show up.




## Drop Randomization

### Random Drops for Each World
This mod utilizes the world's seed for randomization so that the drops will be randomized for each new world,
but not, for example, each time the game is loaded, or each time the same type of block is broken!  
Obviously, it would not be ideal to modify the Block and Entity .jar files directly, nor would it make
sense to use custom Json loot tables to define which items to drop.

### Block Drop Randomization
Forge offers LootModifiers to change block loot tables using LootConditions and Json files, which is not ideal in this
case, since that would not make the drops different for each world, and it would be difficult to randomize drops custom
items from other mods. I personally feel that a proper randomizer should also affect drops of any non-vanilla items 
registered by other mods.  
To solve this, I created one BlockDropsModifier class that does not worry about fields returned from LootModifier Json files, but 
instead replaces the vanilla drops given by the class's doApply() method with randomized ones.
This meant I had to create my own LootItemCondition class, BlockDropSourceCondition.

#### BlockDropSourceCondition
The available LootItemCondition classes all checked for conditions that were a bit too specific for my purposes,
so I created BlockDropSourceCondition.java. The test() method of this class simply checks if the drops are coming from
a block.  
However, the access of LootItemConditions.register() is private, so in order to register my LootItemCondition, I had to
setup an access transformer for that method, which requires the SRG name for the method. I got the SRG mapping from
the linkie.shedaniel.dev website.

### Entity Drop Randomization
To randomize entity drops, it turned out to be a lot simpler to register a LivingDropsEvent listener, since the
event gives me the list of items which that entity is going to drop.  
My onEntityDrop() method in the ModEvents class replaces the list of vanilla drops for the entity that triggered the 
event with a list of randomized drops.

### Player Drop Randomization
Since players are a type of entity in Minecraft, the above-mentioned onEntityDrop() method can simply check
if the entity is an instance of Player, to allow player drop randomization to be turned on or off using the player
random drops GameRule.

### Actual Randomization Functionality
As previously mentioned, the mod randomizes drops on a per-world basis. When the server is ready, the shuffleItems() 
method of RandomDrops.java shuffles a list of all available drop items in the game using the world seed as the 
seed parameter for java.util.Random. The list is obtained at runtime from the Forge Items registry so that all
registered Items in the game, including ones registered from other loaded mods, will be included.  
Of course, that list includes items that the player should not normally be able to obtain in Survival mode, such
as Air, Command Blocks, Spawners, etc., so I created a list of "exclude items" that is then removed from the master
list before it is shuffled.  
When items are dropped in the game, whether by blocks or entities, the getRandomizedItem() method of RandomDrops.java
takes the vanilla drop item and gets its index from the master list, then returns whatever item is at that index of 
the shuffled list.


