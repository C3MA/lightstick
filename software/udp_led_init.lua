print("Booting... Lightstick v0.10")
wifi.setmode(wifi.STATION)
wifi.sta.config("SSID","password")

function startUDPServer()
  s=net.createServer(net.UDP)
  s:on("receive",function(s,c) 
    ws2813.writergb(4, c)
  end)
  s:listen(2342)
  ws2812.writergb(4, string.char(0, 0, 0):rep(60))
  print ("UDP Server started")
end

counter=0
tmr.alarm(1, 200, 1, function() 
   if wifi.sta.getip()=="0.0.0.0" or wifi.sta.getip() == nil then
      print("Connect AP, Waiting...") 
      if counter == 0 then
          ws2812.writergb(4, string.char(0, 255, 0):rep(60))
	  counter=1
      else
          ws2812.writergb(4, string.char(0, 0, 0):rep(60))
          counter=0
      end
	
   else
      print("Connected")
      print( wifi.sta.getip() )
      startUDPServer()
      tmr.stop(1)
   end	
end)
