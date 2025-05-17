-- Insertion des pays
INSERT INTO country (iso, name) VALUES
                                    ('FR', 'France'),
                                    ('BE', 'Belgique'),
                                    ('DE', 'Allemagne');

-- Insertion des localités
INSERT INTO locality (city, postal_code, country) VALUES
                                                      ('Paris', '75001', 'FR'),
                                                      ('Bruxelles', '1000', 'BE'),
                                                      ('Berlin', '10115', 'DE');

-- Insertion des équipements d'hôtel
INSERT INTO equipment (label) VALUES
                                  ('Piscine'),
                                  ('Salle de sport'),
                                  ('WiFi gratuit');

-- Insertion des clients
INSERT INTO customer (mail_adress, first_name, last_name, phone, street, street_number, birthdate, is_vegan, secondary_phone, city, postal_code, country) VALUES
                                                                                                                                                              ('jean.dupont@email.fr', 'Jean', 'Dupont', '0612345678', 'Rue de la Paix', 10, '1985-04-15', 0, NULL, 'Paris', '75001', 'FR'),
                                                                                                                                                              ('marie.claire@email.be', 'Marie', 'Claire', '0478123456', 'Avenue Louise', 45, '1990-09-21', 1, '0487123456', 'Bruxelles', '1000', 'BE'),
                                                                                                                                                              ('luc.martin@email.fr', 'Luc', 'Martin', '0699887766', 'Rue Lafayette', 12, '1992-07-03', 0, NULL, 'Paris', '75001', 'FR');

-- Insertion des étoiles pour hôtels
INSERT INTO star (star_number) VALUES (1), (2), (3), (4), (5);

-- Insertion des hôtels
INSERT INTO hotel (id, street, street_number, stars, name, city, postal_code, country) VALUES
                                                                                           (1, 'Boulevard Haussmann', 50, 4, 'Hôtel de Paris', 'Paris', '75001', 'FR'),
                                                                                           (2, 'Rue des Fripiers', 20, 3, 'Hôtel Bruxelles Central', 'Bruxelles', '1000', 'BE');

-- Insertion des équipements possédés par les hôtels
INSERT INTO hotel_owns_equipment (hotel, equipment) VALUES
                                                        (1, 'Piscine'),
                                                        (1, 'WiFi gratuit'),
                                                        (2, 'WiFi gratuit');

-- Insertion des types de lits
INSERT INTO bed (type) VALUES
                           ('Lit simple'),
                           ('Lit double'),
                           ('Lit queen size');

-- Insertion des équipements de chambre
INSERT INTO amenity (label) VALUES
                                ('Climatisation'),
                                ('Télévision'),
                                ('Mini-bar');

-- Insertion des types de chambre
INSERT INTO bedroom_type (label) VALUES
                                     ('Simple'),
                                     ('Double'),
                                     ('Suite');

-- Insertion des chambres
INSERT INTO bedroom (bedroom_number, nb_of_people, bedroom_size, cost_per_day, description, has_balcony, last_renovation_date, bedroom_type, hotel) VALUES
                                                                                                                                                        (101, 1, 15.5, 70.0, 'Chambre simple avec vue sur la cour', 0, '2020-01-10', 'Simple', 1),
                                                                                                                                                        (102, 2, 25.0, 120.0, 'Chambre double avec balcon', 1, '2019-06-05', 'Double', 1),
                                                                                                                                                        (201, 3, 40.0, 200.0, 'Suite spacieuse avec salon', 1, '2021-11-15', 'Suite', 2);

-- Association des chambres avec des équipements
INSERT INTO bedroom_owns_amenity (amenity, hotel, bedroom_number) VALUES
                                                                      ('Climatisation', 1, 101),
                                                                      ('Télévision', 1, 101),
                                                                      ('Mini-bar', 2, 201);

-- Association des lits aux chambres
INSERT INTO bedroom_owns_bed (hotel, bedroom_number, bed) VALUES
                                                              (1, 101, 'Lit simple'),
                                                              (1, 102, 'Lit double'),
                                                              (2, 201, 'Lit queen size');

-- Insertion des réservations
INSERT INTO reservation (start_date, end_date, customer, bedroom_number, hotel) VALUES
                                                                                    ('2025-06-01', '2025-06-05', 'jean.dupont@email.fr', 101, 1),
                                                                                    ('2025-07-10', '2025-07-15', 'marie.claire@email.be', 201, 2);

-- Insertion des avis
INSERT INTO review (comment, last_visit_date_hotel_country, creation_date, customer, star, is_anonymous, title, hotel) VALUES
                                                                                                                           ('Très bon séjour, personnel accueillant.', '2025-04-20', '2025-05-22', 'jean.dupont@email.fr', 4, 0, 'Bon hôtel', 1),
                                                                                                                           ('Chambre propre mais un peu bruyante.', '2025-02-16', '2025-07-17', 'marie.claire@email.be', 3, 1, 'Satisfaisant', 2),
                                                                                                                           ('Pas très propre', null, '2024-02-16', 'luc.martin@email.fr', 1, 1, 'Sale !', 2);