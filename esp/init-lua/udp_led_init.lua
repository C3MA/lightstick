print("Booting... Lightstick v0.51")
wifi.setmode(wifi.STATION)
wifi.sta.config("sticknet","stickpw1")

function startUDPServer()
  s=net.createServer(net.UDP)
  s:on("receive",function(s,c) 
    -- remove header (the first four bytes)
    d=string.sub(c, 5)
    ws2812.writergb(4, d)
  end)
  s:listen(2342)
  print ("UDP Server started")
end

function startTelnetServer(dummy)
	s=net.createServer(net.TCP, 180)
	s:listen(2323,function(c)
	global_c=c
	function s_output(str)
	  if(global_c~=nil)
	     then global_c:send(str)
	  end
	end
	node.output(s_output, 0)
	c:on("receive",function(c,l)
	  node.input(l)
	end) 
	c:on("disconnection",function(c)
	  node.output(nil)
	  global_c=nil
	end)
	print("Welcome to the lightstick. Let the force be with you")
	end)
	print("WiFi up and running")
end

counter=0
tmr.alarm(1, 200, 1, function() 
   if wifi.sta.getip()=="0.0.0.0" or wifi.sta.getip() == nil then
      print("Connect AP, Waiting...") 
      if counter == 0 then
          ws2812.writergb(4, string.char(0, 60, 0):rep(60))
	  counter=1
      else
          ws2812.writergb(4, string.char(0, 0, 0):rep(60))
          counter=0
      end
	
   else
      print("Connected")
      print( wifi.sta.getip() )
      -- Display IP on the stick (red = hundreds, green = tenth, blue = ones)
      ws2812.writergb(4, string.char(0, 0, 0):rep(60))
      ip=wifi.sta.getip()
      for k in string.gmatch(ip, "(%d+)") do
	lastIP=k
      end
      hundred=math.floor(lastIP / 100)
      lastIP=lastIP-hundred*100
      tenth=math.floor(lastIP / 10)
      lastIP=lastIP-tenth*10
      ones=lastIP
      -- Set some orange points, soo the IP address is not coded in the foot, afterwards the IP is displayed
      ws2812.writergb(4, string.char(128, 82, 0):rep(10) .. string.char(30, 0, 0):rep(hundred) .. string.char(0,30,0):rep(tenth) .. string.char(0,0,30):rep(ones) )

      startUDPServer()
      startTelnetServer()
      tmr.stop(1)
   end	
end)
