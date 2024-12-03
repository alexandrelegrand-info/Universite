#!/bin/sh
pandoc --toc -N -f markdown -t html5 -s res/meta.yaml Rapport.md res/Questions.md -c res/Rapport.css -o Rapport.html && pandoc --toc -N -V geometry:margin=1in -f markdown -t pdf res/meta.yaml Rapport.md res/Questions.md -o Rapport.pdf
