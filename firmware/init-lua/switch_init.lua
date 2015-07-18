print("Booting... Button v0.10")
wifi.setmode(wifi.STATION)
wifi.sta.config("SSID","password")

global_connected=false
global_c=nil

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
    print("Welcome to NodeMcu world.")
    end)
    print("WiFi up and running")
end

m = mqtt.Client("button", 120, "", "")
function startMqttService(c)
        m:connect("10.23.x.x", 1883, 0, nil)
        tmr.alarm(2, 2000, 0, function() 
        m:subscribe("/room/beamer/#",0)
    end)
end
function configureMqttService(c)
    m:on("connect", function(con) 
        netPrint ("MQTT connected") 
        m:publish("/room/button/ip",wifi.sta.getip(),0,0, function(conn) netPrint("Sent IP") end)
        global_connected=true
    end)
    m:on("offline", function(con) 
        netPrint ("MQTT offline")
        global_connected=false
        startMqttService()
    end)

    m:on("message", function(conn, topic, data)
      if data ~= nil then
        netPrint(topic .. ":" .. data)
      end
    end)
    startMqttService()
end


tmr.alarm(1, 1000, 1, function() 
   if wifi.sta.getip()=="0.0.0.0" or wifi.sta.getip() == nil then
      print("Connect AP, Waiting...") 
   else
      --print("Connected")
      --print( wifi.sta.getip() )
      startTelnetServer()
      configureMqttService()
      tmr.stop(1)
   end
end)


du=0
last=0

gpio.mode(4,gpio.INT) -- GPIO5
gpio.write(4,gpio.HIGH)
function pin4cb(level)
    if tmr.now()-last > 100000 then
        du = tmr.now()-last
        last=tmr.now()
        print("Pressed")
        if global_connected==true then
            m:publish("/room/button/pressed", "ON",0,0, nil)
        end
    end
end
gpio.trig(4,"down", pin4cb)
