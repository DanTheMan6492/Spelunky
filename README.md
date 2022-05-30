# ***Quarter 4 Final Project: Spelunky***
*Created by Danial Waseem, Ryan Lerdworatawee, & Alan Wu*

### Game Description:

### Classes:

#### ***Physics Class:***
The physics class is a universal constant that applies to all generated entities besides the map itself. Be it the player, the enemy, or even item - the physics class applies to them in one form or another. Typically entities all are vulnerable to gravity and the fall damage associated. Entities also get knocked around with attacks; their corpses being flung across the room and acting as a projectile which could collide and damage enemies. 

#### ***Entity Class:***
A generalized entity class in which the player, enemy, and item class rely heavily upon. This acts as a foundation for each entity's health, size, position on the map, and even some other intricacies such as interacting with level exits for damsels. 

##### ***Player Class:***

![Player Select](https://github.com/DanTheMan6492/Spelunky/blob/89ca23dbf59d1fd8cb6695a2a197fb144b0f2a80/src/imgs/Characters/image.png)
In the game, there are a total of 21 player skins that the player can select at the start of the game. The character is controlled by a controller, and can do a variety of things on their journey further and further deeper. They can walk, jump, attack, and even use items from their inventories or in their hands. The player also have 4 bombs and 4 ropes which they can use to assist in their travels, which they can attain more of along with items and equipment in randomly-generated boxes or purchased from shops. 


##### ***Enemy Class:***
![Doggy](https://github.com/DanTheMan6492/Spelunky/blob/ada204c2fab9a4f0e90c1c6ec8e4f173ea649ab7/src/imgs/Monsters/Damsel/damsel_stun.gif)
![Caveman](https://github.com/DanTheMan6492/Spelunky/blob/ada204c2fab9a4f0e90c1c6ec8e4f173ea649ab7/src/imgs/Monsters/Caveman/cavemanStand.gif)
![Mummy](https://github.com/DanTheMan6492/Spelunky/blob/ada204c2fab9a4f0e90c1c6ec8e4f173ea649ab7/src/imgs/Monsters/Mummy/mummyWalk.gif)
![Olmec](https://github.com/DanTheMan6492/Spelunky/blob/ca4d344b87a6dd55fed91a0757528376e314d88b/src/imgs/Monsters/olmec.png)

Thoughout your journey, you will encounter a variety of enemies which impedes your travels downward. These entities all have different actions and behaviors, which the player should do well in avoiding or analysing. Some enemies, like the mantrap, can kill immediately on contact while other entities such as the damsel dog can heal the player should they find the exit of the level. Enemies typically spawn randomly in their corresponding levels (Though there is one exeption deep, deep in the caves...), so be careful when descending down into the depths below!

##### ***Item Class:***
![Pot](https://github.com/DanTheMan6492/Spelunky/blob/3eff2d5c8b3a34940d3e597a811f817b3e9c83cf/src/imgs/Items/Objects/pot.png)
![Skull](https://github.com/DanTheMan6492/Spelunky/blob/3eff2d5c8b3a34940d3e597a811f817b3e9c83cf/src/imgs/Items/Objects/rock.png)
![Arrow](https://github.com/DanTheMan6492/Spelunky/blob/3eff2d5c8b3a34940d3e597a811f817b3e9c83cf/src/imgs/Items/Objects/arrow.png)

Entities which falls neither in the weapon / equipment class nor in the player / enemies class. Usually generated in the levels randomly, and typically can be broken with a few exceptions. These usually are to be picked up by the player or opened to gain equipment or other items, which serves to be thrown to hit other enemies. Examples include pots, skulls, bombs, and many more.

##### ***Equipment:***
![Kapala Cup](https://github.com/DanTheMan6492/Spelunky/blob/12ee22e59214b739edc947bc626fa3385670974f/src/imgs/Items/HUD/kapala/0.png)
![Spike Shoes](https://github.com/DanTheMan6492/Spelunky/blob/12ee22e59214b739edc947bc626fa3385670974f/src/imgs/Items/HUD/item_3.png)
![Sticky Paste](https://github.com/DanTheMan6492/Spelunky/blob/12ee22e59214b739edc947bc626fa3385670974f/src/imgs/Items/HUD/item_4.png)
![Spring Shoes](https://github.com/DanTheMan6492/Spelunky/blob/12ee22e59214b739edc947bc626fa3385670974f/src/imgs/Items/HUD/item_2.png)

Equipment entities typically spawn in shops or crates, though can also drop from enemies dying. They typically change the character's interactions with the world when picked up, such as gaining the ability to climb vertical walls or to be able to safely bounce on certain enemies which would otherwise kill you for it.

##### ***Map/Subsection/Block Class:***
In summary, a level consists of 16 total subsections arranged in a 4x4 grid. These individual subsections arranged into a randomly generated path from the top to the bottom of the 2d array. Branching paths that lead to dead-ends or shops also exist. Each of these subsections are further broken up into 10 x 8 grids of blocks, which can vary in type, from blocks, water, lava, or nothing at all. These blocks create the template rooms used for the map, and can be broken / mined through using bombs or pickaxes. The blocks, finally, could also have gold or gems in them and can be broken for them by the player.

![Floor 1](https://github.com/DanTheMan6492/Spelunky/blob/12ee22e59214b739edc947bc626fa3385670974f/src/imgs/Tiles/1/1_1.png)
![Floor 2](https://github.com/DanTheMan6492/Spelunky/blob/12ee22e59214b739edc947bc626fa3385670974f/src/imgs/Tiles/2/1_1.png)
![Floor 3](https://github.com/DanTheMan6492/Spelunky/blob/12ee22e59214b739edc947bc626fa3385670974f/src/imgs/Tiles/3/1_1.png)
![Floor 4](https://github.com/DanTheMan6492/Spelunky/blob/12ee22e59214b739edc947bc626fa3385670974f/src/imgs/Tiles/4/1_1.png)

Each of the 4 worlds have a total of 4 levels, each with their own unique block and background sprites.

##### ***Shop Class:***

![Shopkeeper](https://github.com/DanTheMan6492/Spelunky/blob/12ee22e59214b739edc947bc626fa3385670974f/src/imgs/Monsters/Shopkeep/shopkeepStand.gif)

A shop is randomly generated within a branching path in a level. In a shop, people can either spend money to directly buy items, or choose to rob the store by taking the items out of it without paying or attacking the shopkeeper, which causes him to be angry and start shooting his shotgun and for armed shopkeepers to appear in most subsequent levels. Shopkeepers have an anger system based on whether the player kills or steals, and drops by 1 value for every level progressed until the level reaches back to 0, where they would stay neutral until the player once again does something.


##### ***Kali Alter Class:***

![Kali](https://github.com/DanTheMan6492/Spelunky/blob/e774222c1e9d28265da727caaf5fb5ab7955f1f0/src/imgs/Tiles/1/9.png)
![Kali](https://github.com/DanTheMan6492/Spelunky/blob/e774222c1e9d28265da727caaf5fb5ab7955f1f0/src/imgs/Tiles/1/6.png)
![Kali](https://github.com/DanTheMan6492/Spelunky/blob/e774222c1e9d28265da727caaf5fb5ab7955f1f0/src/imgs/Tiles/1/7.png)


Kali alters are special rooms generated like shops, containing a singular alter made by 2 blocks. Players are able to sacrifice stunned living entities or dead entities by placing their bodies on top of the alter, which kills them instantly and gains a favor or two depending on if the entity is alive or not. This favor lasts between levels, and should the player reach 8 favor each additional sacrifice provides a valuable equipment or item. Destroying the alter is also possible, but should be avoided as it results in -16 favor and spawns a hoard of enemies if the final favor value is negative

##### Other Entities:

![gems](https://github.com/DanTheMan6492/Spelunky/blob/cc46d4cb7527a832310a7dd1b6489046591f074c/src/imgs/Items/Objects/big_gem_blue.png)
![gems](https://github.com/DanTheMan6492/Spelunky/blob/cc46d4cb7527a832310a7dd1b6489046591f074c/src/imgs/Items/Objects/big_gem_green.png)
![gems](https://github.com/DanTheMan6492/Spelunky/blob/cc46d4cb7527a832310a7dd1b6489046591f074c/src/imgs/Items/Objects/big_gem_red.png)
![gems](https://github.com/DanTheMan6492/Spelunky/blob/cc46d4cb7527a832310a7dd1b6489046591f074c/src/imgs/Items/Objects/big_gold_nugget.png)

Other entities which appear throughout a level include boxes which spawns items and gold / gems which provide the player differing amounts of money. Gold and Gems are randomly generated throughout the level as entities, in blocks, or in crates / chests. Some entities also drop these entities when dying, such as the shopkeeper. Each gold and gem has different values corresponding to their type. Blood, while mostly decorative, is shot out of living entities taking damage or dying, and quickly disappears. The only use of it is with the Kapala cup (see 1st image of items) which allows the blood to be collected, giving the player an extra health per 8 pellets of blood collected.


#### Money/score Class:

The money system mostly revolves around picking up randomly generated gold and treasure from around the level. This collected gold can then be spent in shops in exchange for items without angering the shopkeeper. The money also acts as a score system for the game, with the higher the money the better the score a person gets.

#### Music Class:

Music which is played in the background of each level. There are a total of 3 music tracks for each world, which is selected randomly at the beginning of each level and looped until the player exits into a level transition stage. The Olmec Boss level has a unique track.

Sound effects vary greatly, and corresponds for an action or reaction by many things be it player, enemy, or item.

#### HUD:

Coded in Frame and stored in Player, the HUD shows off the individual values that the player has, including health, money, time, and the equipment that is in inventory.

### Support:
For support, we can try our best in helping to the best we can. We worked hard on our project and take pride in it, but we could always further polish up our own code or help others be better with theirs.

### Roadmap:
We believe that we have done what we could utilizing Java to create the game of Spelunky. Nearly all of the base game's system was imported and created in our own way, and the only way we could continue working on the game is to maybe fix some minor bugs or to implement entirely new concepts of our own. Maybe some day if we have time we'll come back and polish the project up. 

### Contributing:

Though we hold no credit to creating the Spelunky game idea, if anyone is interested in modifying our code for their own purposes then by all means feel free to do so.

### Acknowledgement:

The assets and music utilized in this project were all produced by the talented minds at Mossmouth, LLC. Without their hard work and inspiration, our game could never have been dreamnt up and created, nor would we hold a special place in our hearts of lazy afternoons relaxing while playing their game. Special thanks to Mr. David in being the best teacher we could've had, and as a guide in assisting us in our code. 
