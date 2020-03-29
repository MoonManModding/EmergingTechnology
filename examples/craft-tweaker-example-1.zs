// Thanks to Ren!

#modloaded emergingtechnology 

import mods.emergingtechnology.Shredder;

// allow for additional creations of shredded plants

if (loadedMods.contains("enderio")) {
	Shredder.addRecipe(<emergingtechnology:shreddedplant>, <enderio:item_material:46>);
}

if (loadedMods.contains("better_diving")) {
    Shredder.addRecipe(<emergingtechnology:shreddedplant>, <better_diving:creepvine>);
    Shredder.addRecipe(<emergingtechnology:shreddedplant>, <better_diving:seagrass>);
    Shredder.addRecipe(<emergingtechnology:shreddedplant> * 2, <better_diving:seagrass_tall_bottom>);
}
