INSERT INTO users (dtype, userid, name, surname, email, password, rut, phone_number, commune, birth_date, age, company_name, role, disponibility) 
VALUES
('UserEntity', 1, 'Marcelo', 'Civilo', 'mcivilo@gmail.com', 'admin', '11.111.111-1', '0 1234 5678', 
    (SELECT commune FROM civilo_roller_db.coverages WHERE commune = 'Santiago'), '1990-07-25', 40, null,
        (SELECT roleID FROM civilo_roller_db.roles WHERE account_type = 'Administrador'),null),
('UserEntity', 2, 'Ejecutivo', '1', 'ejecutivo@gmail.com', 'ejecutivo', '12.121.212-1', '0 1234 5678',
    (SELECT commune FROM civilo_roller_db.coverages WHERE commune = 'Vallenar'), '1999-12-10', 24, null,
        (SELECT roleID FROM civilo_roller_db.roles WHERE account_type = 'Ejecutivo'),null),
('SellerEntity', 3, 'Vendedor', '1', 'vendedor@gmail.com', 'vendedor', '11.222.333-4', '0 1234 5678',
    (SELECT commune FROM civilo_roller_db.coverages WHERE commune = 'Petorca'), '2000-04-10', 23, "Compañia 1",
        (SELECT roleID FROM civilo_roller_db.roles WHERE account_type = 'Vendedor'),1),
('UserEntity', 4, 'Cliente', '1', 'cliente@gmail.com', 'cliente',  '12.345.678-9','0 1234 5678',
    (SELECT commune FROM civilo_roller_db.coverages WHERE commune = 'Hualpén'), '2002-01-26', 21, null,
        (SELECT roleID FROM civilo_roller_db.roles WHERE account_type = 'Cliente'),null);