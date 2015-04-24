
-- Boot code to say hello
counter=0
tmr.alarm(1, 1000, 1, function() 
	print ("red")
	ws2812.writergb(4, string.char(0, 255, 0):rep(60))
end)
