INSERT INTO permissions (permissionID,permission,roles) 
VALUES
(1,'crearSolicitud',(SELECT roleID FROM civilo_roller_db.roles WHERE account_type = 'Cliente')),
(2,'verMisSolicitudes',(SELECT roleID FROM civilo_roller_db.roles WHERE account_type = 'Cliente')),
(3,'verSolicitudesAsignadas',(SELECT roleID FROM civilo_roller_db.roles WHERE account_type = 'Vendedor'));