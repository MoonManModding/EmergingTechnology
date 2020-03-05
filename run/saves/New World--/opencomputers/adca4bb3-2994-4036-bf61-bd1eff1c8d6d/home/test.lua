local component = require "component"
c = component.etech_piezoelectric_tile
local computer = require "computer"

while true do
  if c.isSteppedOn() then
    computer.beep()
  end
end