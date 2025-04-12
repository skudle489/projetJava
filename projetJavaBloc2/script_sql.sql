CREATE TABLE equipment (
    label VARCHAR(100) NOT NULL,
    PRIMARY KEY(label)
);

CREATE TABLE country (
	iso VARCHAR(2) NOT NULL, 
    name VARCHAR(100) NOT NULL,
    PRIMARY KEY (iso)
    
);

CREATE TABLE locality (
	city VARCHAR(100) NOT NULL,
    postal_code VARCHAR(15) NOT NULL,
    country VARCHAR(2) NOT NULL,
    PRIMARY KEY(city, postal_code, country),
    FOREIGN KEY (country) REFERENCES country(iso)
);

CREATE TABLE customer (
	mail_adress VARCHAR (255) NOT NULL,
    first_name VARCHAR(100) NOT NULL,
    last_name VARCHAR(100) NOT NULL,
    phone VARCHAR(20),
    street VARCHAR(255) NOT NULL,
    street_number INT NOT NULL,
    birthdate DATE NOT NULL,
    is_vegan BIT NOT NULL,
    secondary_phone VARCHAR(20),
    city VARCHAR(100) NOT NULL,
    postal_code VARCHAR(15) NOT NULL,
    country VARCHAR(2) NOT NULL,
    PRIMARY KEY(mail_adress),
    FOREIGN KEY(city, postal_code, country) REFERENCES locality(city, postal_code, country)
);


CREATE TABLE star (
	star_number INT NOT NULL,
    PRIMARY KEY(star_number)
);

CREATE TABLE hotel (
	id INT NOT NULL,
    street VARCHAR(255) NOT NULL,
    street_number INT NOT NULL,
    stars INT NOT NULL,
    name VARCHAR(50) NOT NULL,
    city VARCHAR(100) NOT NULL,
    postal_code VARCHAR(15) NOT NULL,
    country VARCHAR(2) NOT NULL,
    PRIMARY KEY(id),
    FOREIGN KEY(city, postal_code, country) REFERENCES locality(city, postal_code, country)
);

CREATE TABLE hotel_owns_equipment (
	hotel INT NOT NULL,
    equipment VARCHAR(100) NOT NULL,
    PRIMARY KEY(hotel, equipment),
    FOREIGN KEY(hotel) REFERENCES hotel(id),
    FOREIGN KEY(equipment) REFERENCES equipment(label)
);

CREATE TABLE review (
	comment VARCHAR(255),
    last_visit_date_hotel_country DATE,
    creation_date DATE NOT NULL,
    customer VARCHAR(255) NOT NULL,
    star INT NOT NULL,
    is_anonymous BIT NOT NULL,
    title VARCHAR(100),
    hotel INT NOT NULL,
    PRIMARY KEY (hotel, customer, creation_date),
    FOREIGN KEY (hotel) REFERENCES hotel(id),
    FOREIGN KEY (star) REFERENCES star(star_number),
    FOREIGN KEY(customer) REFERENCES customer(mail_adress)
);

CREATE TABLE bed (
	type VARCHAR(50) NOT NULL,
    PRIMARY KEY(type)
);

CREATE TABLE amenity (
	label VARCHAR(100) NOT NULL,
    PRIMARY KEY(label)
);

CREATE TABLE bedroom_type (
	label VARCHAR(100) NOT NULL,
    PRIMARY KEY(label)
);

CREATE TABLE bedroom (
	bedroom_number INT NOT NULL,
    nb_of_people INT NOT NULL,
    bedroom_size FLOAT NOT NULL,
    cost_per_day FLOAT NOT NULL,
    description VARCHAR(255),
    has_balcony BIT NOT NULL,
    last_renovation_date DATE,
    bedroom_type VARCHAR(100) NOT NULL,
    hotel INT NOT NULL,
    PRIMARY KEY (hotel, bedroom_number),
    FOREIGN KEY (bedroom_type) REFERENCES bedroom_type(label),
    FOREIGN KEY (hotel) REFERENCES hotel(id)
);


CREATE TABLE reservation (
	start_date DATE NOT NULL,
    end_date DATE NOT NULL,
    customer VARCHAR(255) NOT NULL,
    bedroom_number INT NOT NULL,
    hotel INT NOT NULL,
    PRIMARY KEY (customer, bedroom_number, start_date),
    FOREIGN KEY (customer) REFERENCES customer(mail_adress),
    FOREIGN KEY (hotel, bedroom_number) REFERENCES bedroom(hotel, bedroom_number)
);


CREATE TABLE bedroom_owns_amenity (
	amenity VARCHAR(100) NOT NULL,
    hotel INT NOT NULL,
    bedroom_number INT NOT NULL,
    PRIMARY KEY (amenity, hotel, bedroom_number),
    FOREIGN KEY (amenity) REFERENCES amenity(label),
    FOREIGN KEY (hotel, bedroom_number) REFERENCES bedroom(hotel, bedroom_number)
);

CREATE TABLE bedroom_owns_bed (
	hotel INT NOT NULL,
    bedroom_number INT NOT NULL,
    bed VARCHAR(100) NOT NULL,
    PRIMARY KEY(hotel, bedroom_number, bed),
    FOREIGN KEY (bed) REFERENCES bed(type),
    FOREIGN KEY (hotel, bedroom_number) REFERENCES bedroom(hotel, bedroom_number)
);

