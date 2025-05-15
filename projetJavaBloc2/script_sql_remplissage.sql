/*INSERT IGNORE INTO star (star_number) VALUES (1), (2), (3), (4), (5);
INSERT IGNORE INTO country (iso, name) VALUES ("BE", "Belgique"), ("FR", "France"), ("DE", "Allemagne"), ("ES", "Espagne");
INSERT IGNORE INTO locality (city, postal_code, country) VALUES 
("Spy", "5190", "BE"), 
("Limelette", "1342", "BE"),
("Bruxelles", "1000", "BE"),
("Anvers", "2000", "BE"),
("Liège", "4000", "BE"),
("Madrid", "28001", "ES"),
("Barcelone", "08001", "ES"),
("Berlin", "10115", "DE"),
("Munich", "80331", "DE"),
("Francfort", "60311", "DE"),
("Hambourg", "20095", "DE"),
("Paris", "75001", "FR"),
("Marseille", "13001", "FR"),
("Lyon", "69001", "FR");*/

/*INSERT IGNORE INTO equipment (label) VALUES ("Piscine"), ("Spa"), ("Salle de sport"), ("Parking"), ("Voiture de service");*/

/*INSERT IGNORE INTO hotel (id, street, street_number, stars, name, city, postal_code, country) VALUES
(1, 'Rue de la Gare', 12, 4, 'Hotel du Midi', 'Bruxelles', '1000', 'BE'),
(2, 'Avenida de la Paz', 45, 5, 'El Palacio', 'Madrid', '28001', 'ES'),
(3, 'Rue Lafayette', 22, 3, 'Le Marais', 'Paris', '75001', 'FR'),
(4, 'Alexanderplatz', 10, 4, 'Berliner Hof', 'Berlin', '10115', 'DE');*/

INSERT IGNORE INTO customer (mail_adress, first_name, last_name, phone, street, street_number, birthdate, is_vegan, secondary_phone, city, postal_code, country) VALUES 
('alice@example.com', 'Alice', 'Dupont', '0485123456', 'Rue de Namur', 17, '1990-06-15', 1, NULL, 'Liège', '4000', 'BE'),
('bob@example.com', 'Bob', 'Martin', '0612345678', 'Avenue Louise', 88, '1985-11-03', 0, '069998877', 'Bruxelles', '1000', 'BE'),
('carla@example.com', 'Carla', 'Sanchez', '0622334455', 'Calle Mayor', 50, '1992-01-20', 1, NULL, 'Madrid', '28001', 'ES'),
('daniel@example.com', 'Daniel', 'Schmidt', '0151234567', 'Kurfuerstendamm', 99, '1980-09-10', 0, NULL, 'Berlin', '10115', 'DE');

/*INSERT IGNORE INTO bed (type) VALUES
('Simple'), ('Double'), ('Queen'), ('King');

INSERT IGNORE INTO amenity (label) VALUES 
('TV'), ('WiFi'), ('Mini-bar'), ('Climatisation'), ('Coffre-fort');

INSERT IGNORE INTO bedroom_type (label) VALUES 
('Standard'), ('Deluxe'), ('Suite'), ('Familiale');

INSERT IGNORE INTO bedroom (bedroom_number, nb_of_people, bedroom_size, cost_per_day, description, has_balcony, last_renovation_date, bedroom_type, hotel) VALUES 
(101, 2, 25.0, 120.00, 'Chambre standard avec vue sur la ville', 1, '2020-01-01', 'Standard', 1),
(102, 4, 40.0, 250.00, 'Suite avec balcon', 1, '2022-05-10', 'Suite', 1),
(201, 2, 30.0, 180.00, 'Deluxe avec baignoire', 0, '2019-07-20', 'Deluxe', 2),
(301, 3, 35.0, 160.00, 'Chambre familiale', 1, '2021-03-15', 'Familiale', 3),
(401, 1, 20.0, 90.00, 'Petite chambre cosy', 0, '2018-10-05', 'Standard', 4);

INSERT IGNORE INTO bedroom_owns_bed (hotel, bedroom_number, bed) VALUES 
(1, 101, 'Double'),
(1, 102, 'King'),
(2, 201, 'Queen'),
(3, 301, 'Queen'),
(3, 301, 'Simple'),
(4, 401, 'Simple');

INSERT IGNORE INTO bedroom_owns_amenity (amenity, hotel, bedroom_number) VALUES 
('WiFi', 1, 101),
('TV', 1, 101),
('Climatisation', 1, 102),
('Mini-bar', 2, 201),
('TV', 2, 201),
('WiFi', 3, 301),
('Coffre-fort', 4, 401);


INSERT IGNORE INTO hotel_owns_equipment (hotel, equipment) VALUES 
(1, 'Piscine'),
(1, 'Salle de sport'),
(2, 'Spa'),
(2, 'Piscine'),
(3, 'Parking'),
(4, 'Voiture de service');*/

INSERT IGNORE INTO review (comment, last_visit_date_hotel_country, creation_date, customer, star, is_anonymous, title, hotel) VALUES 
('Très bon séjour, personnel sympathique.', '2024-06-20', '2024-06-21', 'alice@example.com', 4, 0, 'Séjour agréable', 1),
('Trop bruyant la nuit.', '2023-12-10', '2023-12-12', 'bob@example.com', 2, 1, 'Bruit la nuit', 3),
('Parfait ! Rien à redire.', '2025-01-05', '2025-01-10', 'carla@example.com', 5, 0, 'Excellent', 2);


INSERT IGNORE INTO reservation (start_date, end_date, customer, bedroom_number, hotel) VALUES 
('2024-07-01', '2024-07-05', 'alice@example.com', 101, 1),
('2024-08-10', '2024-08-15', 'bob@example.com', 301, 3),
('2025-01-01', '2025-01-07', 'carla@example.com', 201, 2),
('2025-02-10', '2025-02-12', 'daniel@example.com', 401, 4);




select * from review;
select * from hotel;
select * from customer;
