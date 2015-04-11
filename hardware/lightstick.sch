<?xml version="1.0" encoding="utf-8"?>
<!DOCTYPE eagle SYSTEM "eagle.dtd">
<eagle version="6.6.0">
<drawing>
<settings>
<setting alwaysvectorfont="no"/>
<setting verticaltext="up"/>
</settings>
<grid distance="0.1" unitdist="inch" unit="mm" style="lines" multiple="1" display="yes" altdistance="0.01" altunitdist="inch" altunit="mm"/>
<layers>
<layer number="1" name="Top" color="4" fill="1" visible="no" active="no"/>
<layer number="2" name="Route2" color="1" fill="3" visible="no" active="no"/>
<layer number="3" name="Route3" color="4" fill="3" visible="no" active="no"/>
<layer number="4" name="Route4" color="1" fill="4" visible="no" active="no"/>
<layer number="5" name="Route5" color="4" fill="4" visible="no" active="no"/>
<layer number="6" name="Route6" color="1" fill="8" visible="no" active="no"/>
<layer number="7" name="Route7" color="4" fill="8" visible="no" active="no"/>
<layer number="8" name="Route8" color="1" fill="2" visible="no" active="no"/>
<layer number="9" name="Route9" color="4" fill="2" visible="no" active="no"/>
<layer number="10" name="Route10" color="1" fill="7" visible="no" active="no"/>
<layer number="11" name="Route11" color="4" fill="7" visible="no" active="no"/>
<layer number="12" name="Route12" color="1" fill="5" visible="no" active="no"/>
<layer number="13" name="Route13" color="4" fill="5" visible="no" active="no"/>
<layer number="14" name="Route14" color="1" fill="6" visible="no" active="no"/>
<layer number="15" name="Route15" color="4" fill="6" visible="no" active="no"/>
<layer number="16" name="Bottom" color="1" fill="1" visible="no" active="no"/>
<layer number="17" name="Pads" color="2" fill="1" visible="no" active="no"/>
<layer number="18" name="Vias" color="2" fill="1" visible="no" active="no"/>
<layer number="19" name="Unrouted" color="6" fill="1" visible="no" active="no"/>
<layer number="20" name="Dimension" color="15" fill="1" visible="no" active="no"/>
<layer number="21" name="tPlace" color="7" fill="1" visible="no" active="no"/>
<layer number="22" name="bPlace" color="7" fill="1" visible="no" active="no"/>
<layer number="23" name="tOrigins" color="15" fill="1" visible="no" active="no"/>
<layer number="24" name="bOrigins" color="15" fill="1" visible="no" active="no"/>
<layer number="25" name="tNames" color="7" fill="1" visible="no" active="no"/>
<layer number="26" name="bNames" color="7" fill="1" visible="no" active="no"/>
<layer number="27" name="tValues" color="7" fill="1" visible="no" active="no"/>
<layer number="28" name="bValues" color="7" fill="1" visible="no" active="no"/>
<layer number="29" name="tStop" color="7" fill="3" visible="no" active="no"/>
<layer number="30" name="bStop" color="7" fill="6" visible="no" active="no"/>
<layer number="31" name="tCream" color="7" fill="4" visible="no" active="no"/>
<layer number="32" name="bCream" color="7" fill="5" visible="no" active="no"/>
<layer number="33" name="tFinish" color="6" fill="3" visible="no" active="no"/>
<layer number="34" name="bFinish" color="6" fill="6" visible="no" active="no"/>
<layer number="35" name="tGlue" color="7" fill="4" visible="no" active="no"/>
<layer number="36" name="bGlue" color="7" fill="5" visible="no" active="no"/>
<layer number="37" name="tTest" color="7" fill="1" visible="no" active="no"/>
<layer number="38" name="bTest" color="7" fill="1" visible="no" active="no"/>
<layer number="39" name="tKeepout" color="4" fill="11" visible="no" active="no"/>
<layer number="40" name="bKeepout" color="1" fill="11" visible="no" active="no"/>
<layer number="41" name="tRestrict" color="4" fill="10" visible="no" active="no"/>
<layer number="42" name="bRestrict" color="1" fill="10" visible="no" active="no"/>
<layer number="43" name="vRestrict" color="2" fill="10" visible="no" active="no"/>
<layer number="44" name="Drills" color="7" fill="1" visible="no" active="no"/>
<layer number="45" name="Holes" color="7" fill="1" visible="no" active="no"/>
<layer number="46" name="Milling" color="3" fill="1" visible="no" active="no"/>
<layer number="47" name="Measures" color="7" fill="1" visible="no" active="no"/>
<layer number="48" name="Document" color="7" fill="1" visible="no" active="no"/>
<layer number="49" name="Reference" color="7" fill="1" visible="no" active="no"/>
<layer number="51" name="tDocu" color="7" fill="1" visible="no" active="no"/>
<layer number="52" name="bDocu" color="7" fill="1" visible="no" active="no"/>
<layer number="91" name="Nets" color="2" fill="1" visible="yes" active="yes"/>
<layer number="92" name="Busses" color="1" fill="1" visible="yes" active="yes"/>
<layer number="93" name="Pins" color="2" fill="1" visible="no" active="yes"/>
<layer number="94" name="Symbols" color="4" fill="1" visible="yes" active="yes"/>
<layer number="95" name="Names" color="7" fill="1" visible="yes" active="yes"/>
<layer number="96" name="Values" color="7" fill="1" visible="yes" active="yes"/>
<layer number="97" name="Info" color="7" fill="1" visible="yes" active="yes"/>
<layer number="98" name="Guide" color="6" fill="1" visible="yes" active="yes"/>
</layers>
<schematic xreflabel="%F%N/%S.%C%R" xrefpart="/%S.%C%R">
<libraries>
<library name="frames">
<description>&lt;b&gt;Frames for Sheet and Layout&lt;/b&gt;</description>
<packages>
</packages>
<symbols>
<symbol name="A4L-LOC">
<wire x1="256.54" y1="3.81" x2="256.54" y2="8.89" width="0.1016" layer="94"/>
<wire x1="256.54" y1="8.89" x2="256.54" y2="13.97" width="0.1016" layer="94"/>
<wire x1="256.54" y1="13.97" x2="256.54" y2="19.05" width="0.1016" layer="94"/>
<wire x1="256.54" y1="19.05" x2="256.54" y2="24.13" width="0.1016" layer="94"/>
<wire x1="161.29" y1="3.81" x2="161.29" y2="24.13" width="0.1016" layer="94"/>
<wire x1="161.29" y1="24.13" x2="215.265" y2="24.13" width="0.1016" layer="94"/>
<wire x1="215.265" y1="24.13" x2="256.54" y2="24.13" width="0.1016" layer="94"/>
<wire x1="246.38" y1="3.81" x2="246.38" y2="8.89" width="0.1016" layer="94"/>
<wire x1="246.38" y1="8.89" x2="256.54" y2="8.89" width="0.1016" layer="94"/>
<wire x1="246.38" y1="8.89" x2="215.265" y2="8.89" width="0.1016" layer="94"/>
<wire x1="215.265" y1="8.89" x2="215.265" y2="3.81" width="0.1016" layer="94"/>
<wire x1="215.265" y1="8.89" x2="215.265" y2="13.97" width="0.1016" layer="94"/>
<wire x1="215.265" y1="13.97" x2="256.54" y2="13.97" width="0.1016" layer="94"/>
<wire x1="215.265" y1="13.97" x2="215.265" y2="19.05" width="0.1016" layer="94"/>
<wire x1="215.265" y1="19.05" x2="256.54" y2="19.05" width="0.1016" layer="94"/>
<wire x1="215.265" y1="19.05" x2="215.265" y2="24.13" width="0.1016" layer="94"/>
<text x="217.17" y="15.24" size="2.54" layer="94">&gt;DRAWING_NAME</text>
<text x="217.17" y="10.16" size="2.286" layer="94">&gt;LAST_DATE_TIME</text>
<text x="230.505" y="5.08" size="2.54" layer="94">&gt;SHEET</text>
<text x="216.916" y="4.953" size="2.54" layer="94">Sheet:</text>
<frame x1="0" y1="0" x2="260.35" y2="179.07" columns="6" rows="4" layer="94"/>
</symbol>
</symbols>
<devicesets>
<deviceset name="A4L-LOC" prefix="FRAME" uservalue="yes">
<description>&lt;b&gt;FRAME&lt;/b&gt;&lt;p&gt;
DIN A4, landscape with location and doc. field</description>
<gates>
<gate name="G$1" symbol="A4L-LOC" x="0" y="0"/>
</gates>
<devices>
<device name="">
<technologies>
<technology name=""/>
</technologies>
</device>
</devices>
</deviceset>
</devicesets>
</library>
<library name="con-ria182">
<description>&lt;h2&gt;RIA Connectors&lt;/h2&gt;


&lt;b&gt;RIA 182&lt;br&gt;
RIA 169&lt;/b&gt;&lt;br&gt;
&lt;br&gt;
www.HarryGr.de</description>
<packages>
<package name="RIA182-02">
<wire x1="-1.3" y1="0.1" x2="-0.9" y2="0.1" width="0.127" layer="21"/>
<wire x1="-0.9" y1="0.1" x2="-0.5" y2="0.1" width="0.127" layer="21"/>
<wire x1="-0.5" y1="0.1" x2="0.5" y2="0.1" width="0.127" layer="21"/>
<wire x1="0.5" y1="0.1" x2="0.9" y2="0.1" width="0.127" layer="21"/>
<wire x1="0.9" y1="0.1" x2="1.3" y2="0.1" width="0.127" layer="21"/>
<wire x1="1.3" y1="0.1" x2="1.4" y2="0.1" width="0.127" layer="21"/>
<wire x1="1.3" y1="0.1" x2="1.3" y2="-2.1" width="0.127" layer="21"/>
<wire x1="-1.3" y1="-2.1" x2="-0.9" y2="-2.1" width="0.127" layer="21"/>
<wire x1="-0.9" y1="-2.1" x2="-0.5" y2="-2.1" width="0.127" layer="21"/>
<wire x1="-0.5" y1="-2.1" x2="0.5" y2="-2.1" width="0.127" layer="21"/>
<wire x1="0.5" y1="-2.1" x2="0.9" y2="-2.1" width="0.127" layer="21"/>
<wire x1="0.9" y1="-2.1" x2="1.3" y2="-2.1" width="0.127" layer="21"/>
<wire x1="-1.3" y1="0.1" x2="-1.3" y2="-2.1" width="0.127" layer="21"/>
<wire x1="1.4" y1="0.1" x2="1.4" y2="1" width="0.127" layer="21"/>
<wire x1="1.4" y1="1" x2="1.1" y2="1.3" width="0.127" layer="21" curve="90"/>
<wire x1="-0.5" y1="0.8" x2="-0.5" y2="1" width="0.127" layer="21"/>
<wire x1="-0.5" y1="0.8" x2="0.5" y2="0.8" width="0.127" layer="21"/>
<wire x1="0.5" y1="0.8" x2="0.5" y2="1" width="0.127" layer="21"/>
<wire x1="-0.5" y1="1" x2="-0.8" y2="1.3" width="0.127" layer="21" curve="90"/>
<wire x1="0.5" y1="1" x2="0.8" y2="1.3" width="0.127" layer="21" curve="-90"/>
<wire x1="0.8" y1="1.3" x2="1.1" y2="1.3" width="0.127" layer="21"/>
<wire x1="-1.4" y1="0.1" x2="-1.4" y2="1" width="0.127" layer="21"/>
<wire x1="-1.4" y1="1" x2="-1.1" y2="1.3" width="0.127" layer="21" curve="-90"/>
<wire x1="-1.1" y1="1.3" x2="-0.8" y2="1.3" width="0.127" layer="21"/>
<wire x1="-1.3" y1="0.1" x2="-1.4" y2="0.1" width="0.127" layer="21"/>
<wire x1="-0.9" y1="0.1" x2="-0.9" y2="-2.1" width="0.127" layer="21"/>
<wire x1="0.9" y1="0.1" x2="0.9" y2="-2.1" width="0.127" layer="21"/>
<wire x1="-0.5" y1="0.1" x2="-0.5" y2="-2.1" width="0.127" layer="21"/>
<wire x1="0.5" y1="0.1" x2="0.5" y2="-2.1" width="0.127" layer="21"/>
<wire x1="2.2" y1="0.1" x2="2.6" y2="0.1" width="0.127" layer="21"/>
<wire x1="2.6" y1="0.1" x2="3" y2="0.1" width="0.127" layer="21"/>
<wire x1="3" y1="0.1" x2="4" y2="0.1" width="0.127" layer="21"/>
<wire x1="4" y1="0.1" x2="4.4" y2="0.1" width="0.127" layer="21"/>
<wire x1="4.4" y1="0.1" x2="4.8" y2="0.1" width="0.127" layer="21"/>
<wire x1="4.8" y1="0.1" x2="4.9" y2="0.1" width="0.127" layer="21"/>
<wire x1="4.8" y1="0.1" x2="4.8" y2="-2.1" width="0.127" layer="21"/>
<wire x1="2.2" y1="-2.1" x2="2.6" y2="-2.1" width="0.127" layer="21"/>
<wire x1="2.6" y1="-2.1" x2="3" y2="-2.1" width="0.127" layer="21"/>
<wire x1="3" y1="-2.1" x2="4" y2="-2.1" width="0.127" layer="21"/>
<wire x1="4" y1="-2.1" x2="4.4" y2="-2.1" width="0.127" layer="21"/>
<wire x1="4.4" y1="-2.1" x2="4.8" y2="-2.1" width="0.127" layer="21"/>
<wire x1="2.2" y1="0.1" x2="2.2" y2="-2.1" width="0.127" layer="21"/>
<wire x1="4.9" y1="0.1" x2="4.9" y2="1" width="0.127" layer="21"/>
<wire x1="4.9" y1="1" x2="4.6" y2="1.3" width="0.127" layer="21" curve="90"/>
<wire x1="3" y1="0.8" x2="3" y2="1" width="0.127" layer="21"/>
<wire x1="3" y1="0.8" x2="4" y2="0.8" width="0.127" layer="21"/>
<wire x1="4" y1="0.8" x2="4" y2="1" width="0.127" layer="21"/>
<wire x1="3" y1="1" x2="2.7" y2="1.3" width="0.127" layer="21" curve="90"/>
<wire x1="4" y1="1" x2="4.3" y2="1.3" width="0.127" layer="21" curve="-90"/>
<wire x1="4.3" y1="1.3" x2="4.6" y2="1.3" width="0.127" layer="21"/>
<wire x1="2.1" y1="0.1" x2="2.1" y2="1" width="0.127" layer="21"/>
<wire x1="2.1" y1="1" x2="2.4" y2="1.3" width="0.127" layer="21" curve="-90"/>
<wire x1="2.4" y1="1.3" x2="2.7" y2="1.3" width="0.127" layer="21"/>
<wire x1="2.2" y1="0.1" x2="2.1" y2="0.1" width="0.127" layer="21"/>
<wire x1="2.6" y1="0.1" x2="2.6" y2="-2.1" width="0.127" layer="21"/>
<wire x1="4.4" y1="0.1" x2="4.4" y2="-2.1" width="0.127" layer="21"/>
<wire x1="3" y1="0.1" x2="3" y2="-2.1" width="0.127" layer="21"/>
<wire x1="4" y1="0.1" x2="4" y2="-2.1" width="0.127" layer="21"/>
<wire x1="1.4" y1="0.1" x2="2.1" y2="0.1" width="0.127" layer="21"/>
<wire x1="-1.4" y1="0.1" x2="-2.5" y2="0.1" width="0.127" layer="21"/>
<wire x1="4.9" y1="0.1" x2="6" y2="0.1" width="0.127" layer="21"/>
<wire x1="-2.5" y1="0.1" x2="-2.5" y2="-9.2" width="0.127" layer="21"/>
<wire x1="-2.5" y1="-9.2" x2="-2.5" y2="-9.9" width="0.09" layer="21"/>
<wire x1="6" y1="0.1" x2="6" y2="-9.2" width="0.127" layer="21"/>
<wire x1="6" y1="-9.2" x2="6" y2="-9.9" width="0.09" layer="21"/>
<wire x1="-2.5" y1="-9.9" x2="-1.3" y2="-9.9" width="0.09" layer="21"/>
<wire x1="-1.3" y1="-9.9" x2="1.3" y2="-9.9" width="0.09" layer="21"/>
<wire x1="4.8" y1="-9.9" x2="5.7" y2="-9.9" width="0.09" layer="21"/>
<wire x1="4.8" y1="-9.9" x2="1.3" y2="-9.9" width="0.09" layer="21"/>
<wire x1="1.3" y1="-9.9" x2="2.2" y2="-9.9" width="0.09" layer="21"/>
<wire x1="2.2" y1="-9.9" x2="4.8" y2="-9.9" width="0.09" layer="21"/>
<wire x1="4.8" y1="-9.9" x2="6" y2="-9.9" width="0.09" layer="21"/>
<wire x1="-2.5" y1="-9.2" x2="6" y2="-9.2" width="0.127" layer="21"/>
<wire x1="-1.3" y1="-2.1" x2="-1.3" y2="-9.9" width="0.09" layer="21"/>
<wire x1="1.3" y1="-2.1" x2="1.3" y2="-9.9" width="0.09" layer="21"/>
<wire x1="2.2" y1="-2.1" x2="2.2" y2="-9.9" width="0.09" layer="21"/>
<wire x1="4.8" y1="-2.1" x2="4.8" y2="-9.9" width="0.09" layer="21"/>
<pad name="1" x="0" y="0" drill="1.5" shape="long" rot="R90"/>
<pad name="2" x="3.5" y="0" drill="1.5" shape="long" rot="R90"/>
<text x="-2.54" y="-12.192" size="1.27" layer="25">&gt;NAME</text>
<text x="8.24" y="-10.16" size="1.27" layer="27" rot="R90">&gt;VALUE</text>
</package>
<package name="RIA182-169-02">
<description>mit Stecker RIA 169</description>
<wire x1="-1.3" y1="0.1" x2="-0.9" y2="0.1" width="0.127" layer="21"/>
<wire x1="-0.9" y1="0.1" x2="-0.5" y2="0.1" width="0.127" layer="21"/>
<wire x1="-0.5" y1="0.1" x2="0.5" y2="0.1" width="0.127" layer="21"/>
<wire x1="0.5" y1="0.1" x2="0.9" y2="0.1" width="0.127" layer="21"/>
<wire x1="0.9" y1="0.1" x2="1.3" y2="0.1" width="0.127" layer="21"/>
<wire x1="1.3" y1="0.1" x2="1.4" y2="0.1" width="0.127" layer="21"/>
<wire x1="1.3" y1="0.1" x2="1.3" y2="-2.1" width="0.127" layer="21"/>
<wire x1="-1.3" y1="-2.1" x2="-0.9" y2="-2.1" width="0.127" layer="21"/>
<wire x1="-0.9" y1="-2.1" x2="-0.5" y2="-2.1" width="0.127" layer="21"/>
<wire x1="-0.5" y1="-2.1" x2="0.5" y2="-2.1" width="0.127" layer="21"/>
<wire x1="0.5" y1="-2.1" x2="0.9" y2="-2.1" width="0.127" layer="21"/>
<wire x1="0.9" y1="-2.1" x2="1.3" y2="-2.1" width="0.127" layer="21"/>
<wire x1="-1.3" y1="0.1" x2="-1.3" y2="-2.1" width="0.127" layer="21"/>
<wire x1="1.4" y1="0.1" x2="1.4" y2="1" width="0.127" layer="21"/>
<wire x1="1.4" y1="1" x2="1.1" y2="1.3" width="0.127" layer="21" curve="90"/>
<wire x1="-0.5" y1="0.8" x2="-0.5" y2="1" width="0.127" layer="21"/>
<wire x1="-0.5" y1="0.8" x2="0.5" y2="0.8" width="0.127" layer="21"/>
<wire x1="0.5" y1="0.8" x2="0.5" y2="1" width="0.127" layer="21"/>
<wire x1="-0.5" y1="1" x2="-0.8" y2="1.3" width="0.127" layer="21" curve="90"/>
<wire x1="0.5" y1="1" x2="0.8" y2="1.3" width="0.127" layer="21" curve="-90"/>
<wire x1="0.8" y1="1.3" x2="1.1" y2="1.3" width="0.127" layer="21"/>
<wire x1="-1.4" y1="0.1" x2="-1.4" y2="1" width="0.127" layer="21"/>
<wire x1="-1.4" y1="1" x2="-1.1" y2="1.3" width="0.127" layer="21" curve="-90"/>
<wire x1="-1.1" y1="1.3" x2="-0.8" y2="1.3" width="0.127" layer="21"/>
<wire x1="-1.3" y1="0.1" x2="-1.4" y2="0.1" width="0.127" layer="21"/>
<wire x1="-0.9" y1="0.1" x2="-0.9" y2="-2.1" width="0.127" layer="21"/>
<wire x1="0.9" y1="0.1" x2="0.9" y2="-2.1" width="0.127" layer="21"/>
<wire x1="-0.5" y1="0.1" x2="-0.5" y2="-2.1" width="0.127" layer="21"/>
<wire x1="0.5" y1="0.1" x2="0.5" y2="-2.1" width="0.127" layer="21"/>
<wire x1="2.2" y1="0.1" x2="2.6" y2="0.1" width="0.127" layer="21"/>
<wire x1="2.6" y1="0.1" x2="3" y2="0.1" width="0.127" layer="21"/>
<wire x1="3" y1="0.1" x2="4" y2="0.1" width="0.127" layer="21"/>
<wire x1="4" y1="0.1" x2="4.4" y2="0.1" width="0.127" layer="21"/>
<wire x1="4.4" y1="0.1" x2="4.8" y2="0.1" width="0.127" layer="21"/>
<wire x1="4.8" y1="0.1" x2="4.9" y2="0.1" width="0.127" layer="21"/>
<wire x1="4.8" y1="0.1" x2="4.8" y2="-2.1" width="0.127" layer="21"/>
<wire x1="2.2" y1="-2.1" x2="2.6" y2="-2.1" width="0.127" layer="21"/>
<wire x1="2.6" y1="-2.1" x2="3" y2="-2.1" width="0.127" layer="21"/>
<wire x1="3" y1="-2.1" x2="4" y2="-2.1" width="0.127" layer="21"/>
<wire x1="4" y1="-2.1" x2="4.4" y2="-2.1" width="0.127" layer="21"/>
<wire x1="4.4" y1="-2.1" x2="4.8" y2="-2.1" width="0.127" layer="21"/>
<wire x1="2.2" y1="0.1" x2="2.2" y2="-2.1" width="0.127" layer="21"/>
<wire x1="4.9" y1="0.1" x2="4.9" y2="1" width="0.127" layer="21"/>
<wire x1="4.9" y1="1" x2="4.6" y2="1.3" width="0.127" layer="21" curve="90"/>
<wire x1="3" y1="0.8" x2="3" y2="1" width="0.127" layer="21"/>
<wire x1="3" y1="0.8" x2="4" y2="0.8" width="0.127" layer="21"/>
<wire x1="4" y1="0.8" x2="4" y2="1" width="0.127" layer="21"/>
<wire x1="3" y1="1" x2="2.7" y2="1.3" width="0.127" layer="21" curve="90"/>
<wire x1="4" y1="1" x2="4.3" y2="1.3" width="0.127" layer="21" curve="-90"/>
<wire x1="4.3" y1="1.3" x2="4.6" y2="1.3" width="0.127" layer="21"/>
<wire x1="2.1" y1="0.1" x2="2.1" y2="1" width="0.127" layer="21"/>
<wire x1="2.1" y1="1" x2="2.4" y2="1.3" width="0.127" layer="21" curve="-90"/>
<wire x1="2.4" y1="1.3" x2="2.7" y2="1.3" width="0.127" layer="21"/>
<wire x1="2.2" y1="0.1" x2="2.1" y2="0.1" width="0.127" layer="21"/>
<wire x1="2.6" y1="0.1" x2="2.6" y2="-2.1" width="0.127" layer="21"/>
<wire x1="4.4" y1="0.1" x2="4.4" y2="-2.1" width="0.127" layer="21"/>
<wire x1="3" y1="0.1" x2="3" y2="-2.1" width="0.127" layer="21"/>
<wire x1="4" y1="0.1" x2="4" y2="-2.1" width="0.127" layer="21"/>
<wire x1="1.4" y1="0.1" x2="2.1" y2="0.1" width="0.127" layer="21"/>
<wire x1="-1.4" y1="0.1" x2="-2.5" y2="0.1" width="0.127" layer="21"/>
<wire x1="4.9" y1="0.1" x2="6" y2="0.1" width="0.127" layer="21"/>
<wire x1="-2.5" y1="0.1" x2="-2.5" y2="-9.2" width="0.127" layer="21"/>
<wire x1="6" y1="0.1" x2="6" y2="-9.2" width="0.127" layer="21"/>
<wire x1="-2.5" y1="-9.9" x2="-1.9" y2="-9.9" width="0.127" layer="21"/>
<wire x1="-1.9" y1="-9.9" x2="-1.3" y2="-9.9" width="0.127" layer="21"/>
<wire x1="-1.3" y1="-9.9" x2="-1" y2="-9.9" width="0.127" layer="21"/>
<wire x1="-1" y1="-9.9" x2="1" y2="-9.9" width="0.127" layer="21"/>
<wire x1="1" y1="-9.9" x2="1.3" y2="-9.9" width="0.127" layer="21"/>
<wire x1="1.3" y1="-9.9" x2="2.2" y2="-9.9" width="0.127" layer="21"/>
<wire x1="2.2" y1="-9.9" x2="2.5" y2="-9.9" width="0.127" layer="21"/>
<wire x1="2.5" y1="-9.9" x2="4.5" y2="-9.9" width="0.127" layer="21"/>
<wire x1="4.5" y1="-9.9" x2="4.8" y2="-9.9" width="0.127" layer="21"/>
<wire x1="4.8" y1="-9.9" x2="5.4" y2="-9.9" width="0.127" layer="21"/>
<wire x1="5.4" y1="-9.9" x2="6" y2="-9.9" width="0.127" layer="21"/>
<wire x1="-2.5" y1="-9.2" x2="6" y2="-9.2" width="0.09" layer="21"/>
<wire x1="-1.3" y1="-2.1" x2="-1.3" y2="-9.9" width="0.09" layer="21"/>
<wire x1="1.3" y1="-2.1" x2="1.3" y2="-9.9" width="0.09" layer="21"/>
<wire x1="4.8" y1="-2.1" x2="4.8" y2="-9.9" width="0.09" layer="21"/>
<wire x1="6" y1="-9.2" x2="6" y2="-9.9" width="0.127" layer="21"/>
<wire x1="5.4" y1="-15.8" x2="-1.9" y2="-15.8" width="0.127" layer="21"/>
<wire x1="-2.5" y1="-9.9" x2="-2.5" y2="-9.2" width="0.127" layer="21"/>
<wire x1="-1.9" y1="-10.6" x2="-1" y2="-10.6" width="0.127" layer="21"/>
<wire x1="-1" y1="-10.6" x2="1" y2="-10.6" width="0.127" layer="21"/>
<wire x1="1" y1="-10.6" x2="2.5" y2="-10.6" width="0.127" layer="21"/>
<wire x1="2.5" y1="-10.6" x2="4.5" y2="-10.6" width="0.127" layer="21"/>
<wire x1="4.5" y1="-10.6" x2="5.4" y2="-10.6" width="0.127" layer="21"/>
<wire x1="-1.9" y1="-14.9" x2="5.4" y2="-14.9" width="0.127" layer="21"/>
<wire x1="1.2019" y1="-12.1223" x2="-0.7779" y2="-14.1019" width="0.127" layer="21"/>
<wire x1="0.7776" y1="-11.6982" x2="-1.202" y2="-13.6778" width="0.127" layer="21"/>
<wire x1="4.7019" y1="-12.1223" x2="2.7221" y2="-14.1019" width="0.127" layer="21"/>
<wire x1="4.2776" y1="-11.6982" x2="2.298" y2="-13.6778" width="0.127" layer="21"/>
<wire x1="1" y1="-9.9" x2="1" y2="-10.6" width="0.127" layer="21"/>
<wire x1="2.5" y1="-9.9" x2="2.5" y2="-10.6" width="0.127" layer="21"/>
<wire x1="-1" y1="-9.9" x2="-1" y2="-10.6" width="0.127" layer="21"/>
<wire x1="4.5" y1="-9.9" x2="4.5" y2="-10.6" width="0.127" layer="21"/>
<wire x1="-1.9" y1="-9.9" x2="-1.9" y2="-10.6" width="0.127" layer="21"/>
<wire x1="-1.9" y1="-10.6" x2="-1.9" y2="-14.9" width="0.127" layer="21"/>
<wire x1="-1.9" y1="-14.9" x2="-1.9" y2="-15.8" width="0.127" layer="21"/>
<wire x1="5.4" y1="-9.9" x2="5.4" y2="-10.6" width="0.127" layer="21"/>
<wire x1="5.4" y1="-10.6" x2="5.4" y2="-14.9" width="0.127" layer="21"/>
<wire x1="5.4" y1="-14.9" x2="5.4" y2="-15.8" width="0.127" layer="21"/>
<wire x1="4.8" y1="-2.1" x2="4.8" y2="-9.9" width="0.09" layer="21"/>
<wire x1="2.2" y1="-2.1" x2="2.2" y2="-9.9" width="0.09" layer="21"/>
<circle x="0" y="-12.9" radius="1.5" width="0.127" layer="21"/>
<circle x="3.5" y="-12.9" radius="1.5" width="0.127" layer="21"/>
<pad name="1" x="0" y="0" drill="1.5" shape="long" rot="R90"/>
<pad name="2" x="3.5" y="0" drill="1.5" shape="long" rot="R90"/>
<text x="-1.54" y="-17.942" size="1.27" layer="25">&gt;NAME</text>
<text x="8.24" y="-15.41" size="1.27" layer="27" rot="R90">&gt;VALUE</text>
</package>
</packages>
<symbols>
<symbol name="SK">
<wire x1="-3.81" y1="0" x2="-1.27" y2="0" width="0.254" layer="94"/>
<wire x1="0" y1="-1.27" x2="0" y2="1.27" width="0.254" layer="94" curve="-180" cap="flat"/>
<wire x1="0" y1="0" x2="2.54" y2="0" width="0.6096" layer="94"/>
<circle x="-5.08" y="0" radius="1.27" width="0.254" layer="94"/>
<text x="-6.35" y="0.889" size="1.778" layer="95" rot="R180">&gt;NAME</text>
<pin name="SK" x="5.08" y="0" visible="off" length="short" direction="pas" rot="R180"/>
</symbol>
</symbols>
<devicesets>
<deviceset name="RIA182-02" prefix="X">
<description>Gewinkelter Wannenstecker&lt;br&gt;
2-polig&lt;br&gt;
Rastermaß: 3,5mm&lt;br&gt;
Lötstift / Steckerstift: 1x1mm&lt;br&gt;
Bohrung: 1,5mm&lt;br&gt;
Bemessungsstrom: 5A&lt;br&gt;
Nennquerschnitt Stecker: 1,5qmm eindrähtig / 1qmm feinstdrähtig</description>
<gates>
<gate name="-1" symbol="SK" x="0" y="0"/>
<gate name="-2" symbol="SK" x="0" y="-5.08"/>
</gates>
<devices>
<device name="" package="RIA182-02">
<connects>
<connect gate="-1" pin="SK" pad="1"/>
<connect gate="-2" pin="SK" pad="2"/>
</connects>
<technologies>
<technology name=""/>
</technologies>
</device>
<device name="-169" package="RIA182-169-02">
<connects>
<connect gate="-1" pin="SK" pad="1"/>
<connect gate="-2" pin="SK" pad="2"/>
</connects>
<technologies>
<technology name=""/>
</technologies>
</device>
</devices>
</deviceset>
</devicesets>
</library>
<library name="supply1">
<description>&lt;b&gt;Supply Symbols&lt;/b&gt;&lt;p&gt;
 GND, VCC, 0V, +5V, -5V, etc.&lt;p&gt;
 Please keep in mind, that these devices are necessary for the
 automatic wiring of the supply signals.&lt;p&gt;
 The pin name defined in the symbol is identical to the net which is to be wired automatically.&lt;p&gt;
 In this library the device names are the same as the pin names of the symbols, therefore the correct signal names appear next to the supply symbols in the schematic.&lt;p&gt;
 &lt;author&gt;Created by librarian@cadsoft.de&lt;/author&gt;</description>
<packages>
</packages>
<symbols>
<symbol name="+12V">
<wire x1="1.27" y1="-1.905" x2="0" y2="0" width="0.254" layer="94"/>
<wire x1="0" y1="0" x2="-1.27" y2="-1.905" width="0.254" layer="94"/>
<wire x1="1.27" y1="-0.635" x2="0" y2="1.27" width="0.254" layer="94"/>
<wire x1="0" y1="1.27" x2="-1.27" y2="-0.635" width="0.254" layer="94"/>
<text x="-2.54" y="-5.08" size="1.778" layer="96" rot="R90">&gt;VALUE</text>
<pin name="+12V" x="0" y="-2.54" visible="off" length="short" direction="sup" rot="R90"/>
</symbol>
<symbol name="GND">
<wire x1="-1.905" y1="0" x2="1.905" y2="0" width="0.254" layer="94"/>
<text x="-2.54" y="-2.54" size="1.778" layer="96">&gt;VALUE</text>
<pin name="GND" x="0" y="2.54" visible="off" length="short" direction="sup" rot="R270"/>
</symbol>
<symbol name="+5V">
<wire x1="1.27" y1="-1.905" x2="0" y2="0" width="0.254" layer="94"/>
<wire x1="0" y1="0" x2="-1.27" y2="-1.905" width="0.254" layer="94"/>
<text x="-2.54" y="-5.08" size="1.778" layer="96" rot="R90">&gt;VALUE</text>
<pin name="+5V" x="0" y="-2.54" visible="off" length="short" direction="sup" rot="R90"/>
</symbol>
</symbols>
<devicesets>
<deviceset name="+12V" prefix="P+">
<description>&lt;b&gt;SUPPLY SYMBOL&lt;/b&gt;</description>
<gates>
<gate name="1" symbol="+12V" x="0" y="0"/>
</gates>
<devices>
<device name="">
<technologies>
<technology name=""/>
</technologies>
</device>
</devices>
</deviceset>
<deviceset name="GND" prefix="GND">
<description>&lt;b&gt;SUPPLY SYMBOL&lt;/b&gt;</description>
<gates>
<gate name="1" symbol="GND" x="0" y="0"/>
</gates>
<devices>
<device name="">
<technologies>
<technology name=""/>
</technologies>
</device>
</devices>
</deviceset>
<deviceset name="+5V" prefix="P+">
<description>&lt;b&gt;SUPPLY SYMBOL&lt;/b&gt;</description>
<gates>
<gate name="1" symbol="+5V" x="0" y="0"/>
</gates>
<devices>
<device name="">
<technologies>
<technology name=""/>
</technologies>
</device>
</devices>
</deviceset>
</devicesets>
</library>
<library name="pinhead">
<description>&lt;b&gt;Pin Header Connectors&lt;/b&gt;&lt;p&gt;
&lt;author&gt;Created by librarian@cadsoft.de&lt;/author&gt;</description>
<packages>
<package name="1X03">
<description>&lt;b&gt;PIN HEADER&lt;/b&gt;</description>
<wire x1="-3.175" y1="1.27" x2="-1.905" y2="1.27" width="0.1524" layer="21"/>
<wire x1="-1.905" y1="1.27" x2="-1.27" y2="0.635" width="0.1524" layer="21"/>
<wire x1="-1.27" y1="0.635" x2="-1.27" y2="-0.635" width="0.1524" layer="21"/>
<wire x1="-1.27" y1="-0.635" x2="-1.905" y2="-1.27" width="0.1524" layer="21"/>
<wire x1="-1.27" y1="0.635" x2="-0.635" y2="1.27" width="0.1524" layer="21"/>
<wire x1="-0.635" y1="1.27" x2="0.635" y2="1.27" width="0.1524" layer="21"/>
<wire x1="0.635" y1="1.27" x2="1.27" y2="0.635" width="0.1524" layer="21"/>
<wire x1="1.27" y1="0.635" x2="1.27" y2="-0.635" width="0.1524" layer="21"/>
<wire x1="1.27" y1="-0.635" x2="0.635" y2="-1.27" width="0.1524" layer="21"/>
<wire x1="0.635" y1="-1.27" x2="-0.635" y2="-1.27" width="0.1524" layer="21"/>
<wire x1="-0.635" y1="-1.27" x2="-1.27" y2="-0.635" width="0.1524" layer="21"/>
<wire x1="-3.81" y1="0.635" x2="-3.81" y2="-0.635" width="0.1524" layer="21"/>
<wire x1="-3.175" y1="1.27" x2="-3.81" y2="0.635" width="0.1524" layer="21"/>
<wire x1="-3.81" y1="-0.635" x2="-3.175" y2="-1.27" width="0.1524" layer="21"/>
<wire x1="-1.905" y1="-1.27" x2="-3.175" y2="-1.27" width="0.1524" layer="21"/>
<wire x1="1.27" y1="0.635" x2="1.905" y2="1.27" width="0.1524" layer="21"/>
<wire x1="1.905" y1="1.27" x2="3.175" y2="1.27" width="0.1524" layer="21"/>
<wire x1="3.175" y1="1.27" x2="3.81" y2="0.635" width="0.1524" layer="21"/>
<wire x1="3.81" y1="0.635" x2="3.81" y2="-0.635" width="0.1524" layer="21"/>
<wire x1="3.81" y1="-0.635" x2="3.175" y2="-1.27" width="0.1524" layer="21"/>
<wire x1="3.175" y1="-1.27" x2="1.905" y2="-1.27" width="0.1524" layer="21"/>
<wire x1="1.905" y1="-1.27" x2="1.27" y2="-0.635" width="0.1524" layer="21"/>
<pad name="1" x="-2.54" y="0" drill="1.016" shape="long" rot="R90"/>
<pad name="2" x="0" y="0" drill="1.016" shape="long" rot="R90"/>
<pad name="3" x="2.54" y="0" drill="1.016" shape="long" rot="R90"/>
<text x="-3.8862" y="1.8288" size="1.27" layer="25" ratio="10">&gt;NAME</text>
<text x="-3.81" y="-3.175" size="1.27" layer="27">&gt;VALUE</text>
<rectangle x1="-0.254" y1="-0.254" x2="0.254" y2="0.254" layer="51"/>
<rectangle x1="-2.794" y1="-0.254" x2="-2.286" y2="0.254" layer="51"/>
<rectangle x1="2.286" y1="-0.254" x2="2.794" y2="0.254" layer="51"/>
</package>
<package name="1X03/90">
<description>&lt;b&gt;PIN HEADER&lt;/b&gt;</description>
<wire x1="-3.81" y1="-1.905" x2="-1.27" y2="-1.905" width="0.1524" layer="21"/>
<wire x1="-1.27" y1="-1.905" x2="-1.27" y2="0.635" width="0.1524" layer="21"/>
<wire x1="-1.27" y1="0.635" x2="-3.81" y2="0.635" width="0.1524" layer="21"/>
<wire x1="-3.81" y1="0.635" x2="-3.81" y2="-1.905" width="0.1524" layer="21"/>
<wire x1="-2.54" y1="6.985" x2="-2.54" y2="1.27" width="0.762" layer="21"/>
<wire x1="-1.27" y1="-1.905" x2="1.27" y2="-1.905" width="0.1524" layer="21"/>
<wire x1="1.27" y1="-1.905" x2="1.27" y2="0.635" width="0.1524" layer="21"/>
<wire x1="1.27" y1="0.635" x2="-1.27" y2="0.635" width="0.1524" layer="21"/>
<wire x1="0" y1="6.985" x2="0" y2="1.27" width="0.762" layer="21"/>
<wire x1="1.27" y1="-1.905" x2="3.81" y2="-1.905" width="0.1524" layer="21"/>
<wire x1="3.81" y1="-1.905" x2="3.81" y2="0.635" width="0.1524" layer="21"/>
<wire x1="3.81" y1="0.635" x2="1.27" y2="0.635" width="0.1524" layer="21"/>
<wire x1="2.54" y1="6.985" x2="2.54" y2="1.27" width="0.762" layer="21"/>
<pad name="1" x="-2.54" y="-3.81" drill="1.016" shape="long" rot="R90"/>
<pad name="2" x="0" y="-3.81" drill="1.016" shape="long" rot="R90"/>
<pad name="3" x="2.54" y="-3.81" drill="1.016" shape="long" rot="R90"/>
<text x="-4.445" y="-3.81" size="1.27" layer="25" ratio="10" rot="R90">&gt;NAME</text>
<text x="5.715" y="-3.81" size="1.27" layer="27" rot="R90">&gt;VALUE</text>
<rectangle x1="-2.921" y1="0.635" x2="-2.159" y2="1.143" layer="21"/>
<rectangle x1="-0.381" y1="0.635" x2="0.381" y2="1.143" layer="21"/>
<rectangle x1="2.159" y1="0.635" x2="2.921" y2="1.143" layer="21"/>
<rectangle x1="-2.921" y1="-2.921" x2="-2.159" y2="-1.905" layer="21"/>
<rectangle x1="-0.381" y1="-2.921" x2="0.381" y2="-1.905" layer="21"/>
<rectangle x1="2.159" y1="-2.921" x2="2.921" y2="-1.905" layer="21"/>
</package>
</packages>
<symbols>
<symbol name="PINHD3">
<wire x1="-6.35" y1="-5.08" x2="1.27" y2="-5.08" width="0.4064" layer="94"/>
<wire x1="1.27" y1="-5.08" x2="1.27" y2="5.08" width="0.4064" layer="94"/>
<wire x1="1.27" y1="5.08" x2="-6.35" y2="5.08" width="0.4064" layer="94"/>
<wire x1="-6.35" y1="5.08" x2="-6.35" y2="-5.08" width="0.4064" layer="94"/>
<text x="-6.35" y="5.715" size="1.778" layer="95">&gt;NAME</text>
<text x="-6.35" y="-7.62" size="1.778" layer="96">&gt;VALUE</text>
<pin name="1" x="-2.54" y="2.54" visible="pad" length="short" direction="pas" function="dot"/>
<pin name="2" x="-2.54" y="0" visible="pad" length="short" direction="pas" function="dot"/>
<pin name="3" x="-2.54" y="-2.54" visible="pad" length="short" direction="pas" function="dot"/>
</symbol>
</symbols>
<devicesets>
<deviceset name="PINHD-1X3" prefix="JP" uservalue="yes">
<description>&lt;b&gt;PIN HEADER&lt;/b&gt;</description>
<gates>
<gate name="A" symbol="PINHD3" x="0" y="0"/>
</gates>
<devices>
<device name="" package="1X03">
<connects>
<connect gate="A" pin="1" pad="1"/>
<connect gate="A" pin="2" pad="2"/>
<connect gate="A" pin="3" pad="3"/>
</connects>
<technologies>
<technology name=""/>
</technologies>
</device>
<device name="/90" package="1X03/90">
<connects>
<connect gate="A" pin="1" pad="1"/>
<connect gate="A" pin="2" pad="2"/>
<connect gate="A" pin="3" pad="3"/>
</connects>
<technologies>
<technology name=""/>
</technologies>
</device>
</devices>
</deviceset>
</devicesets>
</library>
</libraries>
<attributes>
</attributes>
<variantdefs>
</variantdefs>
<classes>
<class number="0" name="default" width="0" drill="0">
</class>
</classes>
<parts>
<part name="FRAME1" library="frames" deviceset="A4L-LOC" device=""/>
<part name="X1" library="con-ria182" deviceset="RIA182-02" device=""/>
<part name="X2" library="con-ria182" deviceset="RIA182-02" device=""/>
<part name="P+1" library="supply1" deviceset="+12V" device=""/>
<part name="P+2" library="supply1" deviceset="+12V" device=""/>
<part name="GND1" library="supply1" deviceset="GND" device=""/>
<part name="GND2" library="supply1" deviceset="GND" device=""/>
<part name="JP1" library="pinhead" deviceset="PINHD-1X3" device=""/>
<part name="P+3" library="supply1" deviceset="+5V" device=""/>
<part name="GND3" library="supply1" deviceset="GND" device=""/>
</parts>
<sheets>
<sheet>
<plain>
<text x="10.16" y="43.18" size="1.778" layer="91">power supply</text>
<text x="106.68" y="35.56" size="1.778" layer="91">to LED stripe</text>
</plain>
<instances>
<instance part="FRAME1" gate="G$1" x="0" y="0"/>
<instance part="X1" gate="-1" x="22.86" y="27.94"/>
<instance part="X1" gate="-2" x="22.86" y="22.86"/>
<instance part="X2" gate="-1" x="48.26" y="27.94"/>
<instance part="X2" gate="-2" x="48.26" y="22.86"/>
<instance part="P+1" gate="1" x="30.48" y="35.56"/>
<instance part="P+2" gate="1" x="55.88" y="35.56"/>
<instance part="GND1" gate="1" x="30.48" y="15.24"/>
<instance part="GND2" gate="1" x="55.88" y="15.24"/>
<instance part="JP1" gate="A" x="124.46" y="25.4"/>
<instance part="P+3" gate="1" x="111.76" y="33.02"/>
<instance part="GND3" gate="1" x="111.76" y="17.78"/>
</instances>
<busses>
</busses>
<nets>
<net name="GND" class="0">
<segment>
<pinref part="X1" gate="-2" pin="SK"/>
<pinref part="GND1" gate="1" pin="GND"/>
<wire x1="27.94" y1="22.86" x2="30.48" y2="22.86" width="0.1524" layer="91"/>
<wire x1="30.48" y1="22.86" x2="30.48" y2="17.78" width="0.1524" layer="91"/>
</segment>
<segment>
<pinref part="X2" gate="-2" pin="SK"/>
<pinref part="GND2" gate="1" pin="GND"/>
<wire x1="53.34" y1="22.86" x2="55.88" y2="22.86" width="0.1524" layer="91"/>
<wire x1="55.88" y1="22.86" x2="55.88" y2="17.78" width="0.1524" layer="91"/>
</segment>
<segment>
<pinref part="JP1" gate="A" pin="3"/>
<pinref part="GND3" gate="1" pin="GND"/>
<wire x1="121.92" y1="22.86" x2="111.76" y2="22.86" width="0.1524" layer="91"/>
<wire x1="111.76" y1="22.86" x2="111.76" y2="20.32" width="0.1524" layer="91"/>
</segment>
</net>
<net name="+12V" class="0">
<segment>
<pinref part="X1" gate="-1" pin="SK"/>
<pinref part="P+1" gate="1" pin="+12V"/>
<wire x1="27.94" y1="27.94" x2="30.48" y2="27.94" width="0.1524" layer="91"/>
<wire x1="30.48" y1="27.94" x2="30.48" y2="33.02" width="0.1524" layer="91"/>
</segment>
<segment>
<pinref part="X2" gate="-1" pin="SK"/>
<pinref part="P+2" gate="1" pin="+12V"/>
<wire x1="53.34" y1="27.94" x2="55.88" y2="27.94" width="0.1524" layer="91"/>
<wire x1="55.88" y1="27.94" x2="55.88" y2="33.02" width="0.1524" layer="91"/>
</segment>
</net>
<net name="+5V" class="0">
<segment>
<pinref part="JP1" gate="A" pin="1"/>
<pinref part="P+3" gate="1" pin="+5V"/>
<wire x1="121.92" y1="27.94" x2="111.76" y2="27.94" width="0.1524" layer="91"/>
<wire x1="111.76" y1="27.94" x2="111.76" y2="30.48" width="0.1524" layer="91"/>
</segment>
</net>
<net name="DATA" class="0">
<segment>
<pinref part="JP1" gate="A" pin="2"/>
<wire x1="121.92" y1="25.4" x2="111.76" y2="25.4" width="0.1524" layer="91"/>
<label x="111.76" y="25.4" size="1.778" layer="95" rot="R180" xref="yes"/>
</segment>
</net>
</nets>
</sheet>
</sheets>
</schematic>
</drawing>
</eagle>
