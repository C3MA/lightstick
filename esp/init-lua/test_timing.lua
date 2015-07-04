tmr.alarm(3, 100, 1, function() 
      time=((math.floor(tmr.now() / 100000)) % 1000)
      hundred=math.floor(time/ 100)
      time=time-hundred*100
      tenth=math.floor(time / 10)
      time=time-tenth*10
      ones=time
      ws2812.writergb(4, string.char(30, 0, 0):rep(hundred) .. string.char(0,30,0):rep(tenth) .. string.char(0,0,30):rep(ones) .. string.char(0,0,0):rep(10))
end)
