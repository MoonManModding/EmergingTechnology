local component = require "component"
local event = require "event"
local term = require "term"
local computer = require "computer"
growbed = component.etech_grow_bed

computer.beep()

print("Connecting to grow bed...")

repeat
    term.clear()

    local water = growbed.getWaterLevel() 
    local medium = growbed.getMediumName() 
    local multiplier = growbed.getMediumGrowthMultiplier()
    local light = growbed.getLightLevel()
    local growth = growbed.getPlantGrowth()
    local name = growbed.getPlantName()

    print("- Hydroponic Grow Bed -")
    print("Current crop: " .. name)
    print("Crop growth: " .. growth)
    print("Water: " .. water)
    print("Medium: " .. medium)
    print("Multiplier: " .. multiplier)
    print("Light Level: " .. light)

  if (growth == 7) then
    computer.beep()
    print("Crop is ready to be harvested!")
  end

  if (water < 100) then
    computer.beep()
    print("Warning - Low water!")
  end

until event.pull(1) == "interrupted"