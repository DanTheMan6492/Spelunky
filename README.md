# ***Quarter 4 Final Project: Spelunky***
*Created by Danial Waseem, Ryan Lerdworatawee, & Alan Wu*

### ***Game Description:***

Our game seeks to be a recreated copy of the classic game Spelunky in Java. The game seeks a spelunker traveling down a deeper and deeper cave in search of some unnamable treasure. The player jumps, dodges, fights, bombs, and ropes their way deeper and deeper, eventually facing off against harder and harder opponent. On the way, the spelunker can encounter traps, shops, and forgotten alters for a frightfully appearing yet fair deity. Will the spelunker see to their treasure, and be forever remembered?

### ***Classes:***

#### ***Physics:***
The physics class is a universal constant that applies to all generated entities besides the map itself. Be it the player, the enemy, or even item - the physics class applies to them in one form or another. Typically entities all are vulnerable to gravity and the fall damage associated. Entities also get knocked around with attacks; their corpses being flung across the room and acting as a projectile which could collide and damage enemies. 

#### ***Entity Class:***
A generalized entity class in which the player, enemy, and item class rely heavily upon. This acts as a foundation for each entity's health, size, position on the map, and even some other intricacies such as interacting with level exits for damsels. 

##### ***Player Class:***

![Player Select](https://github.com/DanTheMan6492/Spelunky/blob/89ca23dbf59d1fd8cb6695a2a197fb144b0f2a80/src/imgs/Characters/image.png)
In the game, there are a total of 21 player skins that the player can select at the start of the game. The character is controlled by a controller, and can do a variety of things on their journey further and further deeper. They can walk, jump, attack, and even use items from their inventories or in their hands. The player also have 4 bombs which they can use to assist in their travels. 


##### ***Enemy Class:***
![Doggy](https://github.com/DanTheMan6492/Spelunky/blob/ada204c2fab9a4f0e90c1c6ec8e4f173ea649ab7/src/imgs/Monsters/Damsel/damsel_stun.gif)
![Caveman](https://github.com/DanTheMan6492/Spelunky/blob/ada204c2fab9a4f0e90c1c6ec8e4f173ea649ab7/src/imgs/Monsters/Caveman/cavemanStand.gif)
![Mummy](https://github.com/DanTheMan6492/Spelunky/blob/ada204c2fab9a4f0e90c1c6ec8e4f173ea649ab7/src/imgs/Monsters/Mummy/mummyWalk.gif)
![Olmec](https://github.com/DanTheMan6492/Spelunky/blob/ca4d344b87a6dd55fed91a0757528376e314d88b/src/imgs/Monsters/olmec.png)

Thoughout your journey, you will encounter a variety of enemies which impedes your travels downward. These entities all have different actions and behaviors, which the player should do well in avoiding or analysing. Some enemies, like the mantrap, can kill immediately on contact while other entities such as the damsel dog can heal the player should they find the exit of the level. Enemies typically spawn randomly in their corresponding levels, so be careful when descending down into the depths below!

##### ***Map/Subsection/Block Class:***
In summary, a level consists of 16 total subsections arranged in a 4x4 grid. These individual subsections arranged into a randomly generated path from the top to the bottom of the 2d array. Branching paths that lead to dead-ends or shops also exist. Each of these subsections are further broken up into 10 x 8 grids of blocks, which can vary in type, from blocks, water, lava, or nothing at all. These blocks create the template rooms used for the map, and can be broken / mined through using bombs or pickaxes. The blocks, finally, could also have gold or gems in them and can be broken for them by the player.

![Floor 1](https://github.com/DanTheMan6492/Spelunky/blob/12ee22e59214b739edc947bc626fa3385670974f/src/imgs/Tiles/1/1_1.png)
![Floor 2](https://github.com/DanTheMan6492/Spelunky/blob/12ee22e59214b739edc947bc626fa3385670974f/src/imgs/Tiles/2/1_1.png)
![Floor 3](https://github.com/DanTheMan6492/Spelunky/blob/12ee22e59214b739edc947bc626fa3385670974f/src/imgs/Tiles/3/1_1.png)
![Floor 4](https://github.com/DanTheMan6492/Spelunky/blob/12ee22e59214b739edc947bc626fa3385670974f/src/imgs/Tiles/4/1_1.png)

Each of the 4 worlds have a total of 4 levels, each with their own unique block and background sprites.

##### ***Shop Class:***

![Shopkeeper](https://github.com/DanTheMan6492/Spelunky/blob/12ee22e59214b739edc947bc626fa3385670974f/src/imgs/Monsters/Shopkeep/shopkeepStand.gif)

A shop is randomly generated within a branching path in a level. Purely decorative with a shopkeeper inside.

##### ***Kali Alter Class:***

![Kali](https://github.com/DanTheMan6492/Spelunky/blob/e774222c1e9d28265da727caaf5fb5ab7955f1f0/src/imgs/Tiles/1/9.png)
![Kali](https://github.com/DanTheMan6492/Spelunky/blob/e774222c1e9d28265da727caaf5fb5ab7955f1f0/src/imgs/Tiles/1/6.png)
![Kali](https://github.com/DanTheMan6492/Spelunky/blob/e774222c1e9d28265da727caaf5fb5ab7955f1f0/src/imgs/Tiles/1/7.png)


Kali alters are special rooms generated like shops, which also acts as decoraition to the levels.

#### ***Money/score Class:***

The money system mostly revolves around killing enemies. It mainly acts as the score system for the game.

#### ***Music / SFX Class:***

Music which is played in the background of each level. There are a total of 3 music tracks for each world, which is selected randomly at the beginning of each level and looped until the player exits into a level transition stage. The Olmec Boss level has a unique track.

Sound effects vary greatly, and corresponds for an action or reaction by many things be it player, enemy, or item.

#### ***HUD:***

Coded in Frame and stored in Player, the HUD shows off the individual values that the player has, including health, money, time, and the equipment that is in inventory.

### ***Support:***
For support, we can try our best in helping to the best we can. We worked hard on our project and take pride in it, but we could always further polish up our own code or help others be better with theirs.

### ***Roadmap:***
We believe that we have done what we could utilizing Java to create the game of Spelunky. Nearly all of the base game's system was imported and created in our own way, and the only way we could continue working on the game is to maybe fix some minor bugs or to implement entirely new concepts of our own. Maybe some day if we have time we'll come back and polish the project up. 

### ***Contributing:***

Though we hold no credit to creating the Spelunky game idea, if anyone is interested in modifying our code for their own purposes then by all means feel free to do so.

### ***Acknowledgement:***

The assets and music utilized in this project were all produced by the talented minds at Mossmouth, LLC. Without their hard work and inspiration, our game could never have been dreamnt up and created, nor would we hold a special place in our hearts of lazy afternoons relaxing while playing their game. Special thanks to Mr. David in being the best teacher we could've had, and as a guide in assisting us in our code. 
