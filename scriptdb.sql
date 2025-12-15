
CREATE TABLE "user" (
id BIGSERIAL PRIMARY KEY,
username VARCHAR(50) NOT NULL UNIQUE,
password VARCHAR(100) NOT NULL,
email VARCHAR(100) NOT NULL UNIQUE,
role INT NOT NULL,
is_active BOOLEAN DEFAULT TRUE,
created_at TIMESTAMP DEFAULT NOW(),
updated_at TIMESTAMP DEFAULT NOW()
);

-- ClientService inherits from user
CREATE TABLE client (
id BIGINT PRIMARY KEY REFERENCES "user"(id) ON DELETE CASCADE,
full_name VARCHAR(100),
birth_date DATE,
country VARCHAR(100),
photo_url VARCHAR(255)
);

-- Demo inherits from user
CREATE TABLE demo (
id BIGINT PRIMARY KEY REFERENCES "user"(id) ON DELETE CASCADE
);

--RESET PASSWORD
CREATE TABLE password_reset_token(
 id BIGSERIAL PRIMARY KEY,
 token VARCHAR(100) NOT NULL,
 expiry_date TIMESTAMP,
 user_id BIGINT REFERENCES "user"(id) ON DELETE CASCADE

);

-- Companies
CREATE TABLE company (
id BIGSERIAL PRIMARY KEY,
name VARCHAR(150) NOT NULL,
tax_id VARCHAR(15) NOT NULL,
address VARCHAR(255),
postal_code VARCHAR(10),
google_maps_url TEXT
);


CREATE TABLE ingredient_category (
id BIGSERIAL PRIMARY KEY,
name VARCHAR(100) NOT NULL UNIQUE,
photo_url VARCHAR(255) NOT NULL,
description VARCHAR(200)
);

CREATE TABLE ingredient (
id BIGSERIAL PRIMARY KEY,
name VARCHAR(100) NOT NULL UNIQUE,
calories NUMERIC(38,2),
proteins NUMERIC(38,2),
carbs NUMERIC(38,2),
fats NUMERIC(38,2),
photo_url VARCHAR(255),
unit VARCHAR(50),
id_ingredient_category BIGINT REFERENCES ingredient_category(id),
is_active BOOLEAN DEFAULT TRUE
);

-- Recipes
CREATE TABLE recipe (
id BIGSERIAL PRIMARY KEY,
name VARCHAR(100) NOT NULL,
difficulty INT,
servings INTEGER,
prep_time DOUBLE PRECISION,
description TEXT,
creation_date TIMESTAMP DEFAULT NOW(),
updated_at TIMESTAMP DEFAULT NOW(),
photo_url VARCHAR(255),
is_active BOOLEAN DEFAULT TRUE
);


CREATE TABLE recipe_step (
 id BIGSERIAL PRIMARY KEY,
 id_recipe BIGINT NOT NULL,
 step_number INT NOT NULL,
 instruction VARCHAR(2000) NOT NULL,
 CONSTRAINT fk_recipe FOREIGN KEY (id_recipe)
     REFERENCES recipe(id)
     ON DELETE CASCADE
);

CREATE TABLE tag (
    id BIGSERIAL PRIMARY KEY,
    name VARCHAR(100) NOT NULL UNIQUE
);

CREATE TABLE recipe_tag (
    id_recipe BIGINT REFERENCES recipe(id) ON DELETE CASCADE,
    id_tag BIGINT REFERENCES tag(id) ON DELETE CASCADE,
    PRIMARY KEY (id_recipe, id_tag)
);


CREATE TABLE client_tag (
    id_client BIGINT REFERENCES client(id) ON DELETE CASCADE,
    id_tag BIGINT REFERENCES tag(id) ON DELETE CASCADE,
    PRIMARY KEY (id_client, id_tag)
);

CREATE TABLE recipe_ingredient (
       id BIGSERIAL PRIMARY KEY,
       id_recipe BIGINT REFERENCES recipe(id) ON DELETE CASCADE,
       id_ingredient BIGINT REFERENCES ingredient(id) ON DELETE CASCADE,
       quantity NUMERIC(38,2) NOT NULL,
       unit VARCHAR(50) -- ENUM: 1=grams, 2=ml...
);

-- Favorites
CREATE TABLE favorite_collection (
         id BIGSERIAL PRIMARY KEY,
         title VARCHAR(100) NOT NULL,
         color VARCHAR(9),
         is_active BOOLEAN NOT NULL DEFAULT TRUE,
         id_user BIGINT NOT NULL REFERENCES "user"(id) ON DELETE CASCADE,
         created_at TIMESTAMP DEFAULT NOW()
);

CREATE TABLE favorite_collection_recipe (
                id_collection BIGINT REFERENCES favorite_collection(id) ON DELETE CASCADE,
                id_recipe BIGINT REFERENCES recipe(id) ON DELETE CASCADE,
                PRIMARY KEY (id_collection, id_recipe)
);

-- Shopping Lists
CREATE TABLE shopping_list (
   id BIGSERIAL PRIMARY KEY,
   id_user BIGINT NOT NULL REFERENCES "user"(id) ON DELETE CASCADE,
   creation_date TIMESTAMP DEFAULT NOW(),
   status BOOLEAN DEFAULT TRUE
);

CREATE TABLE shopping_list_ingredient (
id BIGSERIAL PRIMARY KEY,
id_shopping_list BIGINT NOT NULL REFERENCES shopping_list(id) ON DELETE CASCADE,
id_recipe BIGINT NOT NULL REFERENCES recipe(id) ON DELETE CASCADE,
id_ingredient BIGINT NOT NULL REFERENCES ingredient(id) ON DELETE CASCADE,
quantity NUMERIC (38,2) NOT NULL,
unit VARCHAR(50), -- ENUM: 1=grams, 2=ml...)
bought BOOLEAN NOT NULL

);

-- History
CREATE TABLE history (
id BIGSERIAL PRIMARY KEY,
id_user BIGINT NOT NULL REFERENCES "user"(id) ON DELETE CASCADE,
id_recipe BIGINT NOT NULL REFERENCES recipe(id) ON DELETE CASCADE,
date date
);

-- Ratings
CREATE TABLE recipe_rating (
   id BIGSERIAL PRIMARY KEY,
   id_user BIGINT NOT NULL REFERENCES "user"(id) ON DELETE CASCADE,
   id_recipe BIGINT NOT NULL REFERENCES recipe(id) ON DELETE CASCADE,
   stars INTEGER NOT NULL CHECK (stars BETWEEN 1 AND 5),
   rating_date TIMESTAMP DEFAULT NOW(),
   review TEXT
);


DROP TABLE IF EXISTS shopping_list_ingredient CASCADE;
DROP TABLE IF EXISTS shopping_list CASCADE;

DROP TABLE IF EXISTS favorite_collection_recipe CASCADE;
DROP TABLE IF EXISTS favorite_collection CASCADE;

DROP TABLE IF EXISTS recipe_ingredient CASCADE;
DROP TABLE IF EXISTS recipe_tag CASCADE;
DROP TABLE IF EXISTS client_tag CASCADE;
DROP TABLE IF EXISTS tag CASCADE;

DROP TABLE IF EXISTS recipe_rating CASCADE;
DROP TABLE IF EXISTS recipe_step CASCADE;
DROP TABLE IF EXISTS history CASCADE;

DROP TABLE IF EXISTS ingredient CASCADE;
DROP TABLE IF EXISTS ingredient_category CASCADE;

DROP TABLE IF EXISTS recipe CASCADE;

DROP TABLE IF EXISTS company CASCADE;
DROP TABLE IF EXISTS password_reset_token CASCADE;
DROP TABLE IF EXISTS demo CASCADE;
DROP TABLE IF EXISTS client CASCADE;
DROP TABLE IF EXISTS "user" CASCADE;