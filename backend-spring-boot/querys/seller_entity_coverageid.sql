INSERT INTO seller_entity_coverageid (seller_entity_userid,coverageid) 
VALUES
(3, (SELECT coverageID FROM civilo_roller_db.coverages WHERE commune = 'Angol')),
(3, (SELECT coverageID FROM civilo_roller_db.coverages WHERE commune = 'Isla de Maipo')),
(3, (SELECT coverageID FROM civilo_roller_db.coverages WHERE commune = 'Olmué'));