print("Booting... Lightstick v0.10")
wifi.setmode(wifi.STATION)
wifi.sta.config("sticknet","stickpw1")

function startUDPServer()
  s=net.createServer(net.UDP)
  s:on("receive",function(s,c) 
    ws2812.writergb(4, c)
  end)
  s:listen(2342)
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
      ws2812.writergb(4, string.char(0, 0, 0):rep(60))
      ip=wifi.sta.getip()
      for k in string.gmatch(ip, "(%d+)") do
	lastIP=k
      end

      hundred=math.floor(lastIP / 100)
      lastIP=lastIP-hundred*100
      tenth=math.floor(lastIP / 10)
      lastIP=lastIP-tenth*10
      oneth=lastIP

       ws2812.writergb(4, string.char(255, 0, 0):rep(hundred) .. string.char(0,255,0):rep(tenth) .. string.char(0,0,255):rep(oneth) )

      startUDPServer()
      tmr.stop(1)
   end	
end)
