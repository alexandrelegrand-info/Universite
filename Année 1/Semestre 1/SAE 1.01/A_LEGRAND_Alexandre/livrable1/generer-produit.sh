#!/bin/sh
java -cp lib/program.jar:. GenerateurProduit data/$1.txt | tee output/$1.html