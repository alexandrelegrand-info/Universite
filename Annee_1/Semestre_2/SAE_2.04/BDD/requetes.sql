\! echo "Début du requêtage"

\! sleep 3

\! echo "\n\n\n\n\n"

\! echo "Question 1 :\n"

SELECT n56 AS nb_base, n57 + n58 + n59 AS calcul FROM import;
\! echo "SELECT n56 AS nb_base, n57 + n58 + n59 AS calcul FROM import;"

\! sleep 3 

\! echo "\n\n\n\n\n"

\! echo "Question 2 :\n"

SELECT n56 AS nb_base, n57 + n58 + n59 AS calcul, n56 - (n57 + n58 + n59) AS difference FROM import WHERE (n56 - (n57 + n58 + n59)) <> 0;
\! echo "SELECT n56 AS nb_base, n57 + n58 + n59 AS calcul, n56 - (n57 + n58 + n59) AS difference FROM import WHERE (n56 - (n57 + n58 + n59)) <> 0;\n"

\! echo "Il n'y a aucune ligne d'affiché, ça signifie que les colonnes nb_base et calcul ont toutes la même valeur\n\n"

\! sleep 3

\! echo "\n\n\n\n\n"

\! echo "Question 3 :\n"

SELECT n74 AS nb_base, ROUND(n51*100/n47) AS calcul FROM import WHERE n47 <> 0;
\! echo "SELECT n74 AS nb_base, ROUND(n51*100/n47) AS calcul FROM import WHERE n47 <> 0;"

\! sleep 3 

\! echo "\n\n\n\n\n"

\! echo "Question 4 :\n"

SELECT n74 AS nb_base, ROUND(n51*100/n47) AS calcul, n74 - ROUND(n51*100/n47) AS difference FROM import WHERE n74 - ROUND(n51*100/n47) <> 0 AND n47 <> 0;
\! echo "SELECT n74 AS nb_base, ROUND(n51*100/n47) AS calcul, n74 - ROUND(n51*100/n47) AS difference FROM import WHERE n74 - ROUND(n51*100/n47) <> 0 AND n47 <> 0;\n"

\! echo "Il n'y a aucune ligne d'affiché, ça signifie que les colonnes nb_base et calcul ont toutes la même valeur\n\n"

\! sleep 3

\! echo "\n\n\n\n\n"

\! echo "Question 5 :\n"

SELECT n76 AS nb_base, ROUND(n53*100/n47) AS calcul FROM import WHERE n47 <> 0;
\! echo "SELECT n76 AS nb_base, ROUND(n53*100/n47) AS calcul FROM import WHERE n47 <> 0;"

\! sleep 3

\! echo "\n\n\n\n\n"

\! echo "Question 6 :\n"

SELECT i.n76 AS nb_base, ROUND(e.a_avant_fin*100/e.a_total) AS calcul FROM import i, EffectifAdmission e WHERE e.a_total <> 0 AND i.n110 = e.code_formation;
\! echo "SELECT i.n76 AS nb_base, ROUND(e.a_avant_fin*100/e.a_total) AS calcul FROM import i, EffectifAdmission e WHERE e.a_total <> 0 AND i.n110 = e.code_formation;"

\! sleep 3

\! echo "\n\n\n\n\n"

\! echo "Question 7 :\n"

SELECT n81 AS nb_base, ROUND((ROUND(n55)/ROUND(n56))*100) AS calcul FROM import WHERE n56 <> 0;
\! echo "SELECT n81 AS nb_base, ROUND((ROUND(n55)/ROUND(n56))*100) AS calcul FROM import WHERE n56 <> 0;"

\! sleep 3

\! echo "\n\n\n\n\n"

\! echo "Question 8 :\n"

SELECT i.n81 AS nb_base, ROUND((ROUND(e.a_neo_dont_boursiers)/ROUND(e.a_total_neo))*100) AS calcul FROM import i, EffectifAdmission e WHERE e.a_total_neo <> 0 AND i.n110 = e.code_formation;

\! echo "SELECT i.n81 AS nb_base, ROUND((ROUND(e.a_neo_dont_boursiers)/ROUND(e.a_total_neo))*100) AS calcul FROM import i, EffectifAdmission e WHERE e.a_total_neo <> 0 AND i.n110 = e.code_formation;"