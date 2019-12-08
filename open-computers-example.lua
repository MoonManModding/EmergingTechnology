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

    print("- Hydroponic Grow Bed -")
    print("Water: " .. water)
    print("Medium: " .. medium)
    print("Multiplier: " .. multiplier)
    print("Light Level: " .. light)

  if (water < 100) then
    computer.beep()
    print("Warning - Low water!")
  else

until event.pull(1) == "interrupted"