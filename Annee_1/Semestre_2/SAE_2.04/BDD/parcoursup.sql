DROP TABLE IF EXISTS import, Departement, Formation, Etablissement, EffectifCandidat, EffectifAdmission;
DROP SEQUENCE IF EXISTS cno, ano;

\! echo "Téléchargement des données Parcoursup ..."

\! wget -O fr-esr-parcoursup.csv https://data.enseignementsup-recherche.gouv.fr/api/explore/v2.1/catalog/datasets/fr-esr-parcoursup/exports/csv?lang=fr&timezone=Europe%2FBerlin&use_labels=true&delimiter=%3B & wait

\! echo "Création de la table import" 

CREATE TABLE import (
n1 int, n2 VARCHAR(40), n3 CHAR(8), n4 VARCHAR(200), n5 VARCHAR(3), n6 VARCHAR(30), n7 VARCHAR(40), n8 VARCHAR(30),
n9 VARCHAR(40), n10 VARCHAR(400),n11 VARCHAR(30), n12 VARCHAR(20), n13 VARCHAR(400), n14 VARCHAR(200), n15 VARCHAR(200), 
n16 VARCHAR(300), n17 VARCHAR(40), n18 int, n19 int, n20 int, n21 int,
n22 int, n23 int, n24 int, n25 int, n26 int, n27 int, n28 int,
n29 int, n30 int, n31 int, n32 int, n33 int, n34 int, n35 int,
n36 int, n37 int, n38 int, n39 int, n40 int, n41 int, n42 int,
n43 int, n44 int, n45 int, n46 int, n47 int, n48 int, n49 int,
n50 int, n51 FLOAT, n52 FLOAT, n53 FLOAT, n54 int, n55 int, n56 int,
n57 int, n58 int, n59 int, n60 int, n61 int, n62 int, n63 int,
n64 int, n65 int, n66 FLOAT, n67 int, n68 int, n69 int, n70 int,
n71 int, n72 int, n73 int, n74 FLOAT, n75 FLOAT, n76 FLOAT, n77 FLOAT,
n78 FLOAT, n79 FLOAT, n80 FLOAT, n81 FLOAT, n82 FLOAT, n83 FLOAT, n84 FLOAT,
n85 FLOAT, n86 FLOAT, n87 FLOAT, n88 FLOAT, n89 FLOAT, n90 FLOAT, n91 FLOAT,
n92 FLOAT, n93 FLOAT, n94 FLOAT, n95 FLOAT, n96 FLOAT, n97 FLOAT, n98 FLOAT,
n99 FLOAT, n100 FLOAT, n101 FLOAT, n102 VARCHAR(50), n103 FLOAT, n104 VARCHAR(50), n105 FLOAT,
n106 VARCHAR(50), n107 FLOAT, n108 VARCHAR(60), n109 VARCHAR(30), n110 int, n111 VARCHAR(100), n112 VARCHAR(200), 
n113 FLOAT, n114 FLOAT, n115 FLOAT, n116 FLOAT, n117 VARCHAR(6), n118 VARCHAR(5));

\! sleep 3

\! echo "Importation des données"

\copy import FROM fr-esr-parcoursup.csv DELIMITER ';' csv header

CREATE SEQUENCE cno START 1;
CREATE SEQUENCE ano START 1;

\! sleep 3 

\! echo "Debut de la ventilation des tables"


CREATE TABLE Departement(num_departement, nom_departement, nom_region)
AS SELECT DISTINCT n5, n6, n7 FROM import;

CREATE TABLE Etablissement(code_etablissement, num_departement, nom_etablissement, statut, academie)
AS SELECT DISTINCT n3, n5, n4, n2, n8 FROM import;

CREATE TABLE Formation(code_formation, code_etablissement, commune, filere_formation, selectivite, capacite , lien_platforme , coordonnees_gps)
AS SELECT n110, n3, n9, n13, n11, n18, n112, n17 FROM import;

CREATE TABLE EffectifCandidat(cno, code_formation, c_total, c_dont_candidates, c_phase_pricipale, c_neo_generaux, c_neo_generaux_boursiers, c_neo_techno, c_neo_techno_boursiers, c_neo_pro, c_neo_pro_boursiers, c_autres, c_phase_complementaire)
AS SELECT nextval('cno'), n110, n19, n20, n21, n23, n24, n25, n26, n27, n28, n29, n30 FROM import;

CREATE TABLE EffectifAdmission(ano, code_formation, a_total, a_dont_admises, a_neo_dont_boursiers, a_phase_principale, a_avant_fin, a_phase_complementaire, a_total_neo, a_neo_generaux , a_neo_techno, a_neo_pro, a_autres)
AS SELECT nextval('ano'), n110, n47, n48, n55, n49, n53, n50, n56, n57, n58, n59, n60 FROM import;

ALTER TABLE Departement ADD PRIMARY KEY(num_departement);
ALTER TABLE Etablissement ADD PRIMARY KEY(code_etablissement);
ALTER TABLE Etablissement ADD FOREIGN KEY(num_departement) REFERENCES Departement(num_departement) ON UPDATE CASCADE ON DELETE CASCADE;
ALTER TABLE Formation ADD PRIMARY KEY(code_formation);
ALTER TABLE Formation ADD FOREIGN KEY(code_etablissement) REFERENCES Etablissement(code_etablissement) ON UPDATE CASCADE ON DELETE CASCADE;
ALTER TABLE EffectifCandidat ADD PRIMARY KEY(cno);
ALTER TABLE EffectifAdmission ADD PRIMARY KEY(ano);
ALTER TABLE EffectifCandidat ADD FOREIGN KEY (code_formation) REFERENCES Formation(code_formation) ON UPDATE CASCADE ON DELETE CASCADE;
ALTER TABLE EffectifAdmission ADD FOREIGN KEY (code_formation) REFERENCES Formation(code_formation) ON UPDATE CASCADE ON DELETE CASCADE;

\! echo "Fin de la ventilation"

\! sleep 5

\i requetes.sql