#!/bin/bash
convert -delay 1x8 $(seq -f %02g.png 1 1 15) -coalesce -layers OptimizeTransparency animation.gif
