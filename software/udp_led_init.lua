s=net.createServer(net.UDP)
s:on("receive",function(s,c) 
--	print(c) 
	ws2812.writergb(4, c)
end)
s:listen(2342)

-- Boot code to say hello
counter=0
tmr.alarm(1, 1000, 1, function() 
if counter == 0 then
	print ("red")
	ws2812.writergb(4, string.char(255, 0, 0):rep(60))
else
if counter == 1 then
	print ("green")
	ws2812.writergb(4, string.char(0, 255, 0):rep(60))
end
end
end)
