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

Entities which falls neither in the weapon / equipment class nor in the player / enemies class. Usually generated in the levels randomly, and typically can be broken with a few exceptions. These usually are to be picked up by the player or opened to gain equipment or other items, which serves to be thrown to hit other enemies. Examples include pots, skulls, bombs, etc.

Activity


##### ***Equipment:***

#### ***Map Generation Class:***

##### ***Map/Subsection/Block Class:***

##### ***Kali Alter Class:***

##### ***Shop Class:***

##### Other Entities:
#### Money Class:
#### Music Class:
#### HUD Class:

### Support:
For support, we can try our best in helping to the best we can. We worked hard on our project and take pride in it, but we could always further polish up our own code or help others be better with theirs.

### Roadmap:
We believe that we have done what we could utilizing Java to create the game of Spelunky. Nearly all of the base game's system was imported and created in our own way, and the only way we could continue working on the game is to maybe fix some minor bugs or to implement entirely new concepts of our own. Maybe some day if we have time we'll come back and polish the project up. 

### Contributing:

Though we hold no credit to creating the Spelunky game idea, if anyone is interested in modifying our code for their own purposes then by all means feel free to do so.

### Acknowledgement:

The assets and music utilized in this project were all produced by the talented minds at Mossmouth, LLC. Without their hard work and inspiration, our game could never have been dreamnt up and created, nor would we hold a special place in our hearts of lazy afternoons relaxing while playing their game. Special thanks to Mr. David in being the best teacher we could've had, and as a guide in assisting us in our code. 
