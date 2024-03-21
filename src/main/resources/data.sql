CREATE SCHEMA IF NOT EXISTS TouristGuideDB;
SET SCHEMA TouristGuideDB;

DROP TABLE if exists ATTRACTIONTAGS;
DROP TABLE if exists ATTRACTIONS;
DROP TABLE if exists CITY;
DROP TABLE if exists TAGS;


CREATE TABLE CITY (
                    CityID INTEGER NOT NULL AUTO_INCREMENT,
                    Name VARCHAR(30),
                    PRIMARY KEY(CityID)
);

CREATE TABLE ATTRACTIONS (
                             AttractionID INTEGER AUTO_INCREMENT ,
                             CityID INTEGER,
                             Name VARCHAR(50),
                             Description VARCHAR(100),
                             PRIMARY KEY(AttractionID),
                             FOREIGN KEY(CityID) REFERENCES CITY(CityID)
);

CREATE TABLE TAGS (
                      TagID INTEGER NOT NULL AUTO_INCREMENT,
                      Name VARCHAR(30),
                      PRIMARY KEY(TagID)
);

CREATE TABLE ATTRACTIONTAGS (
                                AttractionID INTEGER,
                                TagID Integer,
                                PRIMARY KEY(AttractionID,TagID),
                                FOREIGN KEY (AttractionID) REFERENCES ATTRACTIONS(AttractionID),
                                FOREIGN KEY (TagID) REFERENCES TAGS(TagID)
);

INSERT Into city (name) VALUES('Købenahavn');
INSERT Into city (name) VALUES ('Aalborg');
INSERT Into city (name) VALUES ('Esbjerg');
INSERT Into city (name) VALUES ('Århus');

INSERT into tags (name) VALUES('Børnevenlig');
INSERT into tags (name) VALUES('Gratis');
INSERT into tags (name) VALUES('Fedt');
INSERT into tags (name) VALUES('Kunst');



INSERT into attractions (name, CityID, description) VALUES ('Tivoli',1, 'Forlystelsespark i hjertet af KBH');
INSERT into attractions (name,CityID, description) VALUES ('Amalienborg', 1, 'Stedet hvor de kongelige bor');
INSERT into attractions (name, CityID, description) VALUES ('Den lille havfrue',1, 'Stedet hvor alle japanere vil tage billeder');
INSERT into attractions (name, CityID, description) VALUES ('Aalborg tårnet',2, 'Nordens Paris');

INSERT into attractiontags VALUES(1,1);
INSERT into attractiontags VALUES(1,2);
INSERT into attractiontags VALUES(2,3);
INSERT into attractiontags VALUES(2,4);
INSERT into attractiontags VALUES(3,1);
INSERT into attractiontags VALUES(3,4);
INSERT into attractiontags VALUES(4,2);
INSERT into attractiontags VALUES(4,3);

commit;

