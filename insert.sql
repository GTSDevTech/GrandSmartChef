

INSERT INTO recipe (name, difficulty, servings, prep_time, description, photo_url) VALUES
('Pasta Carbonara', 2, 2, 25, 'Auténtica pasta carbonara italiana.', '/assets/images/recipes/carbonara.png'),
('Ensalada César', 1, 2, 20, 'Ensalada con pollo y aderezo clásico.', '/assets/images/recipes/cesar.png'),
('Arroz con Pollo', 2, 4, 45, 'Plato tradicional latino.', '/assets/images/recipes/arroz.png'),
('Tortilla Española', 1, 2, 30, 'Tortilla de patatas clásica.', '/assets/images/recipes/tortilla.png'),
('Smoothie de Frutas', 0, 1, 5, 'Smoothie natural sin azúcar.', '/assets/images/recipes/smoothie.png'),
('Pizza Margarita', 2, 2, 60, 'Pizza con mozzarella y tomate.', '/assets/images/recipes/pizza.png'),
('Guacamole', 0, 2, 10, 'Guacamole fresco.', '/assets/images/recipes/guacamole.png'),
('Sopa de Tomate', 1, 3, 35, 'Sopa casera de tomate.', '/assets/images/recipes/sopa.png'),
('Pollo al Curry', 2, 3, 50, 'Pollo con curry y coco.', '/assets/images/recipes/curry.png'),
('Pancakes', 0, 2, 20, 'Pancakes suaves.', '/assets/images/recipes/pancakes.png');


-- =========================
-- TAGS
-- =========================
INSERT INTO tag (name) VALUES
                           ('Vegetariano'),
                           ('Vegano'),
                           ('Rápido'),
                           ('Saludable'),
                           ('Alta Proteína'),
                           ('Desayuno'),
                           ('Comida'),
                           ('Cena'),
                           ('Económico'),
                           ('Tradicional'),
                           ('Fitness'),
                           ('Sin Gluten');

-- =========================
-- Categorías de ingredientes
-- =========================
INSERT INTO ingredient_category (name, photo_url, description) VALUES
                                                                   ('Verduras', '/assets/img/cat/verduras.png', 'Vegetales frescos'),
                                                                   ('Frutas', '/assets/img/cat/frutas.png', 'Frutas frescas'),

                                                                   ('Carnes', '/assets/img/cat/carnes.png', 'Carnes frescas'),
                                                                   ('Proteínas', '/assets/img/cat/proteinas.png', 'Huevos y otras proteínas'),

                                                                   ('Lácteos', '/assets/img/cat/lacteos.png', 'Productos lácteos'),

                                                                   ('Cereales', '/assets/img/cat/cereales.png', 'Granos y cereales'),
                                                                   ('Harinas', '/assets/img/cat/harinas.png', 'Harinas y derivados'),

                                                                   ('Salsas', '/assets/img/cat/salsas.png', 'Salsas variadas'),
                                                                   ('Condimentos', '/assets/img/cat/condimentos.png', 'Especias y condimentos'),

                                                                   ('Aceites', '/assets/img/cat/aceites.png', 'Aceites vegetales'),
                                                                   ('Grasas', '/assets/img/cat/grasas.png', 'Grasas para cocinar'),

                                                                   ('Otros', '/assets/img/cat/otros.png', 'Otros ingredientes');
-- =========================
-- Ingredientes
-- =========================
INSERT INTO ingredient (name, calories, proteins, carbs, fats, photo_url, unit, id_ingredient_category) VALUES
-- VERDURAS
('Tomate', 18, 0.9, 3.9, 0.2, '/assets/imgages/ingredientes/tomate.png', 'GRAMO', 1),
('Cebolla', 40, 1.1, 9.3, 0.1, '/assets/imgages/ingredientes/cebolla.png', 'GRAMO', 1),
('Zanahoria', 41, 0.9, 9.6, 0.2, '/assets/imgages/ingredientes/zanahoria.png', 'GRAMO', 1),
('Pimiento Verde', 20, 0.9, 4.6, 0.2, '/assets/imgages/ingredientes/pimiento_verde.png', 'GRAMO', 1),

-- FRUTAS
('Manzana', 52, 0.3, 14, 0.2, '/assets/imgages/ingredientes/manzana.png', 'GRAMO', 2),
('Plátano', 89, 1.1, 23, 0.3, '/assets/imgages/ingredientes/platano.png', 'GRAMO', 2),
('Naranja', 47, 0.9, 12, 0.1, '/assets/imgages/ingredientes/naranja.png', 'GRAMO', 2),

-- CARNES
('Pechuga de Pollo', 165, 31, 0, 3.6, '/assets/imgages/ingredientes/pollo.png', 'GRAMO', 3),
('Ternera', 250, 26, 0, 20, '/assets/imgages/ingredientes/ternera.png', 'GRAMO', 3),
('Cerdo', 242, 27, 0, 14, '/assets/imgages/ingredientes/cerdo.png', 'GRAMO', 3),

-- PROTEÍNAS
('Huevo', 155, 13, 1.1, 11, '/assets/imgages/ingredientes/huevo.png', 'UNIDAD', 4),
('Tofu', 76, 8, 1.9, 4.8, '/assets/imgages/ingredientes/tofu.png', 'GRAMO', 4),
('Lentejas', 116, 9, 20, 0.4, '/assets/imgages/ingredientes/lentejas.png', 'GRAMO', 4),

-- LÁCTEOS
('Leche', 42, 3.4, 5, 1, '/assets/imgages/ingredientes/leche.png', 'MILILITRO', 5),
('Queso', 402, 25, 1.3, 33, '/assets/imgages/ingredientes/queso.png', 'GRAMO', 5),
('Yogur', 59, 10, 3.6, 0.4, '/assets/imgages/ingredientes/yogur.png', 'GRAMO', 5),

-- CEREALES
('Arroz', 130, 2.7, 28, 0.3, '/assets/imgages/ingredientes/arroz.png', 'GRAMO', 6),
('Avena', 389, 17, 66, 7, '/assets/imgages/ingredientes/avena.png', 'GRAMO', 6),

-- HARINAS
('Harina de Trigo', 364, 10, 76, 1, '/assets/imgages/ingredientes/harina_trigo.png', 'GRAMO', 7),
('Harina de Maíz', 365, 9, 74, 4.7, '/assets/imgages/ingredientes/harina_maiz.png', 'GRAMO', 7),

-- SALSAS
('Salsa de Tomate', 29, 1.4, 6, 0.2, '/assets/imgages/ingredientes/salsa_tomate.png', 'GRAMO', 8),
('Salsa de Soja', 53, 8, 4.9, 0.6, '/assets/imgages/ingredientes/salsa_soja.png', 'MILILITRO', 8),

-- CONDIMENTOS
('Sal', 0, 0, 0, 0, '/assets/imgages/ingredientes/sal.png', 'GRAMO', 9),
('Pimienta', 251, 10, 64, 3.3, '/assets/imgages/ingredientes/pimienta.png', 'GRAMO', 9),

-- ACEITES
('Aceite de Oliva', 884, 0, 0, 100, '/assets/imgages/ingredientes/aceite_oliva.png', 'MILILITRO', 10),

-- GRASAS
('Mantequilla', 717, 0.9, 0.1, 81, '/assets/imgages/ingredientes/mantequilla.png', 'GRAMO', 11),

-- OTROS
('Azúcar', 387, 0, 100, 0, '/assets/imgages/ingredientes/azucar.png', 'GRAMO', 12),
('Levadura', 325, 40, 41, 8, '/assets/imgages/ingredientes/levadura.png', 'GRAMO', 12);

-- =========================
-- Recetas
-- =========================

INSERT INTO recipe (name, difficulty, servings, prep_time, description, photo_url) VALUES
                                                                                       ('Ensalada de Tomate y Cebolla', 1, 2, 10, 'Ensalada fresca y ligera', '/assets/img/recipes/ensalada_tomate.png'),
                                                                                       ('Arroz Blanco Básico', 1, 2, 20, 'Arroz cocido tradicional', '/assets/img/recipes/arroz_blanco.png'),
                                                                                       ('Pechuga de Pollo a la Plancha', 1, 1, 15, 'Pollo simple y saludable', '/assets/img/recipes/pollo_plancha.png'),
                                                                                       ('Tortilla Francesa', 1, 1, 10, 'Tortilla rápida de huevo', '/assets/img/recipes/tortilla.png'),
                                                                                       ('Zanahorias Salteadas', 1, 2, 15, 'Zanahorias salteadas con aceite de oliva', '/assets/img/recipes/zanahoria.png'),

                                                                                       ('Avena con Leche', 1, 1, 10, 'Desayuno energético', '/assets/img/recipes/avena_leche.png'),
                                                                                       ('Plátano con Yogur', 1, 1, 5, 'Postre rápido y sano', '/assets/img/recipes/platano_yogur.png'),
                                                                                       ('Manzana con Queso', 1, 1, 5, 'Snack dulce-salado', '/assets/img/recipes/manzana_queso.png'),
                                                                                       ('Arroz con Verduras', 2, 2, 25, 'Arroz salteado con verduras', '/assets/img/recipes/arroz_verduras.png'),
                                                                                       ('Ensalada de Frutas', 1, 2, 10, 'Frutas frescas variadas', '/assets/img/recipes/ensalada_frutas.png'),

                                                                                       ('Pollo con Arroz', 2, 2, 30, 'Plato completo clásico', '/assets/img/recipes/pollo_arroz.png'),
                                                                                       ('Lentejas Cocidas', 1, 2, 35, 'Lentejas sencillas', '/assets/img/recipes/lentejas.png'),
                                                                                       ('Tofu Salteado', 2, 2, 20, 'Tofu dorado con verduras', '/assets/img/recipes/tofu.png'),
                                                                                       ('Huevos Revueltos', 1, 1, 10, 'Huevos suaves', '/assets/img/recipes/huevos_revueltos.png'),
                                                                                       ('Arroz con Huevo', 1, 1, 15, 'Arroz con huevo salteado', '/assets/img/recipes/arroz_huevo.png'),

                                                                                       ('Yogur con Avena', 1, 1, 5, 'Desayuno fitness', '/assets/img/recipes/yogur_avena.png'),
                                                                                       ('Plátano con Avena', 1, 1, 5, 'Snack energético', '/assets/img/recipes/platano_avena.png'),
                                                                                       ('Zanahoria Rallada', 1, 2, 5, 'Guarnición fresca', '/assets/img/recipes/zanahoria_rallada.png'),
                                                                                       ('Ensalada Mixta', 1, 2, 10, 'Verduras frescas combinadas', '/assets/img/recipes/ensalada_mixta.png'),
                                                                                       ('Pollo con Verduras', 2, 2, 25, 'Salteado de pollo', '/assets/img/recipes/pollo_verduras.png'),

                                                                                       ('Arroz con Lentejas', 2, 2, 30, 'Proteína vegetal completa', '/assets/img/recipes/arroz_lentejas.png'),
                                                                                       ('Leche con Azúcar', 1, 1, 5, 'Bebida sencilla', '/assets/img/recipes/leche_azucar.png'),
                                                                                       ('Queso a la Plancha', 1, 1, 10, 'Queso dorado', '/assets/img/recipes/queso_plancha.png'),
                                                                                       ('Yogur con Fruta', 1, 1, 5, 'Postre saludable', '/assets/img/recipes/yogur_fruta.png'),
                                                                                       ('Manzana Asada', 1, 2, 20, 'Manzana caliente con azúcar', '/assets/img/recipes/manzana_asada.png'),

                                                                                       ('Arroz con Leche', 2, 3, 40, 'Postre tradicional', '/assets/img/recipes/arroz_leche.png'),
                                                                                       ('Avena Dulce', 1, 1, 10, 'Avena con azúcar', '/assets/img/recipes/avena_dulce.png'),
                                                                                       ('Tortilla con Queso', 2, 1, 15, 'Tortilla rellena', '/assets/img/recipes/tortilla_queso.png'),
                                                                                       ('Pollo con Salsa de Tomate', 2, 2, 30, 'Pollo guisado', '/assets/img/recipes/pollo_tomate.png'),
                                                                                       ('Arroz Salteado', 2, 2, 20, 'Arroz con aceite', '/assets/img/recipes/arroz_salteado.png'),

                                                                                       ('Verduras al Vapor', 1, 2, 15, 'Verduras suaves', '/assets/img/recipes/verduras_vapor.png'),
                                                                                       ('Fruta Picada', 1, 2, 5, 'Fruta variada', '/assets/img/recipes/fruta_picada.png'),
                                                                                       ('Lentejas con Verduras', 2, 3, 40, 'Plato completo vegetal', '/assets/img/recipes/lentejas_verduras.png'),
                                                                                       ('Tofu con Arroz', 2, 2, 30, 'Plato vegano', '/assets/img/recipes/tofu_arroz.png'),
                                                                                       ('Huevos Cocidos', 1, 2, 10, 'Huevos hervidos', '/assets/img/recipes/huevos_cocidos.png'),

                                                                                       ('Arroz con Aceite', 1, 1, 10, 'Acompañamiento básico', '/assets/img/recipes/arroz_aceite.png'),
                                                                                       ('Yogur Natural', 1, 1, 1, 'Yogur simple', '/assets/img/recipes/yogur.png'),
                                                                                       ('Manzana Fresca', 1, 1, 1, 'Fruta natural', '/assets/img/recipes/manzana.png'),
                                                                                       ('Plátano Fresco', 1, 1, 1, 'Fruta rápida', '/assets/img/recipes/platano.png'),
                                                                                       ('Ensalada de Zanahoria', 1, 2, 10, 'Zanahoria rallada aliñada', '/assets/img/recipes/ensalada_zanahoria.png');



-- =========================
-- Pasos
-- =========================
-- RECETA 1
INSERT INTO recipe_step VALUES
                            (DEFAULT,1,1,'Lavar los tomates y la cebolla.'),
                            (DEFAULT,1,2,'Cortar en rodajas y aliñar con aceite y sal.');

-- RECETA 2
INSERT INTO recipe_step VALUES
                            (DEFAULT,2,1,'Lavar el arroz.'),
                            (DEFAULT,2,2,'Cocer en agua hirviendo hasta que esté tierno.');

-- RECETA 3
INSERT INTO recipe_step VALUES
                            (DEFAULT,3,1,'Calentar una plancha con aceite.'),
                            (DEFAULT,3,2,'Cocinar la pechuga por ambos lados.');

-- RECETA 4
INSERT INTO recipe_step VALUES
                            (DEFAULT,4,1,'Batir los huevos.'),
                            (DEFAULT,4,2,'Cuajar en sartén caliente.');

-- RECETA 5
INSERT INTO recipe_step VALUES
                            (DEFAULT,5,1,'Pelar y cortar zanahorias.'),
                            (DEFAULT,5,2,'Saltear con aceite hasta dorar.');

-- RECETA 6
INSERT INTO recipe_step VALUES
                            (DEFAULT,6,1,'Calentar la leche.'),
                            (DEFAULT,6,2,'Añadir la avena y remover.');

-- RECETA 7
INSERT INTO recipe_step VALUES
                            (DEFAULT,7,1,'Cortar el plátano.'),
                            (DEFAULT,7,2,'Mezclar con el yogur.');

-- RECETA 8
INSERT INTO recipe_step VALUES
                            (DEFAULT,8,1,'Cortar la manzana.'),
                            (DEFAULT,8,2,'Acompañar con queso.');

-- RECETA 9
INSERT INTO recipe_step VALUES
                            (DEFAULT,9,1,'Cocer el arroz.'),
                            (DEFAULT,9,2,'Saltear verduras y mezclar.');

-- RECETA 10
INSERT INTO recipe_step VALUES
                            (DEFAULT,10,1,'Pelar y cortar las frutas.'),
                            (DEFAULT,10,2,'Mezclar y servir.');

-- RECETA 11
INSERT INTO recipe_step VALUES
                            (DEFAULT,11,1,'Cocer el arroz.'),
                            (DEFAULT,11,2,'Cocinar el pollo y mezclar.');

-- RECETA 12
INSERT INTO recipe_step VALUES
                            (DEFAULT,12,1,'Lavar las lentejas.'),
                            (DEFAULT,12,2,'Cocer hasta que estén tiernas.');

-- RECETA 13
INSERT INTO recipe_step VALUES
                            (DEFAULT,13,1,'Cortar el tofu.'),
                            (DEFAULT,13,2,'Saltear hasta dorar.');

-- RECETA 14
INSERT INTO recipe_step VALUES
                            (DEFAULT,14,1,'Batir huevos.'),
                            (DEFAULT,14,2,'Cocinar removiendo.');

-- RECETA 15
INSERT INTO recipe_step VALUES
                            (DEFAULT,15,1,'Cocer el arroz.'),
                            (DEFAULT,15,2,'Añadir huevo y saltear.');

-- RECETA 16
INSERT INTO recipe_step VALUES
                            (DEFAULT,16,1,'Servir yogur.'),
                            (DEFAULT,16,2,'Añadir avena.');

-- RECETA 17
INSERT INTO recipe_step VALUES
                            (DEFAULT,17,1,'Cortar plátano.'),
                            (DEFAULT,17,2,'Mezclar con avena.');

-- RECETA 18
INSERT INTO recipe_step VALUES
                            (DEFAULT,18,1,'Pelar zanahorias.'),
                            (DEFAULT,18,2,'Rallar y servir.');

-- RECETA 19
INSERT INTO recipe_step VALUES
                            (DEFAULT,19,1,'Lavar verduras.'),
                            (DEFAULT,19,2,'Cortar y mezclar.');

-- RECETA 20
INSERT INTO recipe_step VALUES
                            (DEFAULT,20,1,'Cortar verduras.'),
                            (DEFAULT,20,2,'Saltear con pollo.');

-- RECETA 21
INSERT INTO recipe_step VALUES
                            (DEFAULT,21,1,'Cocer arroz.'),
                            (DEFAULT,21,2,'Mezclar con lentejas.');

-- RECETA 22
INSERT INTO recipe_step VALUES
                            (DEFAULT,22,1,'Calentar leche.'),
                            (DEFAULT,22,2,'Añadir azúcar y remover.');

-- RECETA 23
INSERT INTO recipe_step VALUES
                            (DEFAULT,23,1,'Calentar sartén.'),
                            (DEFAULT,23,2,'Dorar el queso.');

-- RECETA 24
INSERT INTO recipe_step VALUES
                            (DEFAULT,24,1,'Cortar fruta.'),
                            (DEFAULT,24,2,'Mezclar con yogur.');

-- RECETA 25
INSERT INTO recipe_step VALUES
                            (DEFAULT,25,1,'Cortar manzanas.'),
                            (DEFAULT,25,2,'Hornear con azúcar.');

-- RECETA 26
INSERT INTO recipe_step VALUES
                            (DEFAULT,26,1,'Cocer arroz en leche.'),
                            (DEFAULT,26,2,'Añadir azúcar.');

-- RECETA 27
INSERT INTO recipe_step VALUES
                            (DEFAULT,27,1,'Cocer avena.'),
                            (DEFAULT,27,2,'Añadir azúcar.');

-- RECETA 28
INSERT INTO recipe_step VALUES
                            (DEFAULT,28,1,'Batir huevos.'),
                            (DEFAULT,28,2,'Añadir queso y cuajar.');

-- RECETA 29
INSERT INTO recipe_step VALUES
                            (DEFAULT,29,1,'Cocinar pollo.'),
                            (DEFAULT,29,2,'Añadir salsa de tomate.');

-- RECETA 30
INSERT INTO recipe_step VALUES
                            (DEFAULT,30,1,'Cocer arroz.'),
                            (DEFAULT,30,2,'Saltear con aceite.');

-- RECETA 31
INSERT INTO recipe_step VALUES
                            (DEFAULT,31,1,'Cortar verduras.'),
                            (DEFAULT,31,2,'Cocer al vapor.');

-- RECETA 32
INSERT INTO recipe_step VALUES
                            (DEFAULT,32,1,'Pelar frutas.'),
                            (DEFAULT,32,2,'Cortar y servir.');

-- RECETA 33
INSERT INTO recipe_step VALUES
                            (DEFAULT,33,1,'Cocer lentejas.'),
                            (DEFAULT,33,2,'Añadir verduras.');

-- RECETA 34
INSERT INTO recipe_step VALUES
                            (DEFAULT,34,1,'Cocer arroz.'),
                            (DEFAULT,34,2,'Añadir tofu.');

-- RECETA 35
INSERT INTO recipe_step VALUES
                            (DEFAULT,35,1,'Hervir huevos.'),
                            (DEFAULT,35,2,'Pelar y servir.');

-- RECETA 36
INSERT INTO recipe_step VALUES
                            (DEFAULT,36,1,'Cocer arroz.'),
                            (DEFAULT,36,2,'Añadir aceite.');

-- RECETA 37
INSERT INTO recipe_step VALUES
                            (DEFAULT,37,1,'Servir yogur.'),
                            (DEFAULT,37,2,'Consumir frío.');

-- RECETA 38
INSERT INTO recipe_step VALUES
                            (DEFAULT,38,1,'Lavar manzana.'),
                            (DEFAULT,38,2,'Consumir.');

-- RECETA 39
INSERT INTO recipe_step VALUES
                            (DEFAULT,39,1,'Pelar plátano.'),
                            (DEFAULT,39,2,'Consumir.');

-- RECETA 40
INSERT INTO recipe_step VALUES
                            (DEFAULT,40,1,'Rallar zanahoria.'),
                            (DEFAULT,40,2,'Aliñar y servir.');


-- =========================
-- Etiquetas de recetas
-- =========================
INSERT INTO recipe_tag VALUES
                           (1,2),(1,4),(1,7),
                           (2,3),(2,12),(2,7),
                           (3,5),(3,11),(3,8),
                           (4,3),(4,6),
                           (5,2),(5,4),
                           (6,6),(6,4),
                           (7,6),(7,4),
                           (8,9),(8,6),
                           (9,2),(9,7),
                           (10,2),(10,6),
                           (11,5),(11,7),
                           (12,1),(12,7),
                           (13,2),(13,8),
                           (14,3),(14,6),
                           (15,9),(15,7),
                           (16,11),(16,6),
                           (17,11),(17,6),
                           (18,2),(18,4),
                           (19,2),(19,4),
                           (20,5),(20,7),
                           (21,1),(21,7),
                           (22,9),(22,6),
                           (23,1),(23,8),
                           (24,4),(24,6),
                           (25,10),(25,8),
                           (26,10),(26,6),
                           (27,6),(27,9),
                           (28,5),(28,6),
                           (29,10),(29,7),
                           (30,9),(30,7),
                           (31,2),(31,4),
                           (32,2),(32,6),
                           (33,1),(33,7),
                           (34,2),(34,7),
                           (35,5),(35,6),
                           (36,9),(36,7),
                           (37,4),(37,6),
                           (38,2),(38,6),
                           (39,2),(39,6),
                           (40,2),(40,4);

INSERT INTO tag (name)
SELECT v.name
FROM (VALUES
          ('Vegetariano'),
          ('Sin Gluten'),
          ('Rápida'),
          ('Económica')
     ) AS v(name)
WHERE NOT EXISTS (
    SELECT 1
    FROM tag t
    WHERE LOWER(t.name) = LOWER(v.name)
);

INSERT INTO recipe_ingredient (id_recipe, id_ingredient, quantity, unit) VALUES

-- 1 Ensalada de Tomate y Cebolla
(1,1,150,'GRAMO'),(1,2,80,'GRAMO'),(1,25,10,'MILILITRO'),(1,23,2,'GRAMO'),

-- 2 Arroz Blanco Básico
(2,17,200,'GRAMO'),(2,23,3,'GRAMO'),

-- 3 Pechuga de Pollo a la Plancha
(3,8,220,'GRAMO'),(3,25,10,'MILILITRO'),(3,23,2,'GRAMO'),

-- 4 Tortilla Francesa
(4,11,2,'UNIDAD'),(4,25,5,'MILILITRO'),(4,23,1,'GRAMO'),

-- 5 Zanahorias Salteadas
(5,3,200,'GRAMO'),(5,25,10,'MILILITRO'),(5,23,2,'GRAMO'),

-- 6 Avena con Leche
(6,18,60,'GRAMO'),(6,14,250,'MILILITRO'),(6,27,10,'GRAMO'),

-- 7 Plátano con Yogur
(7,6,120,'GRAMO'),(7,16,125,'GRAMO'),

-- 8 Manzana con Queso
(8,5,150,'GRAMO'),(8,15,40,'GRAMO'),

-- 9 Arroz con Verduras
(9,17,180,'GRAMO'),(9,3,80,'GRAMO'),(9,2,60,'GRAMO'),(9,25,10,'MILILITRO'),

-- 10 Ensalada de Frutas
(10,5,100,'GRAMO'),(10,6,100,'GRAMO'),(10,7,100,'GRAMO'),

-- 11 Pollo con Arroz
(11,8,220,'GRAMO'),(11,17,180,'GRAMO'),(11,25,15,'MILILITRO'),

-- 12 Lentejas Cocidas
(12,13,200,'GRAMO'),(12,23,3,'GRAMO'),

-- 13 Tofu Salteado
(13,12,180,'GRAMO'),(13,25,10,'MILILITRO'),

-- 14 Huevos Revueltos
(14,11,2,'UNIDAD'),(14,25,5,'MILILITRO'),

-- 15 Arroz con Huevo
(15,17,180,'GRAMO'),(15,11,1,'UNIDAD'),

-- 16 Yogur con Avena
(16,16,125,'GRAMO'),(16,18,40,'GRAMO'),

-- 17 Plátano con Avena
(17,6,120,'GRAMO'),(17,18,40,'GRAMO'),

-- 18 Zanahoria Rallada
(18,3,200,'GRAMO'),(18,25,5,'MILILITRO'),

-- 19 Ensalada Mixta
(19,1,100,'GRAMO'),(19,2,60,'GRAMO'),(19,3,60,'GRAMO'),

-- 20 Pollo con Verduras
(20,8,220,'GRAMO'),(20,3,80,'GRAMO'),(20,2,60,'GRAMO'),

-- 21 Arroz con Lentejas
(21,17,150,'GRAMO'),(21,13,150,'GRAMO'),

-- 22 Leche con Azúcar
(22,14,250,'MILILITRO'),(22,27,10,'GRAMO'),

-- 23 Queso a la Plancha
(23,15,120,'GRAMO'),(23,25,5,'MILILITRO'),

-- 24 Yogur con Fruta
(24,16,125,'GRAMO'),(24,5,100,'GRAMO'),

-- 25 Manzana Asada
(25,5,200,'GRAMO'),(25,27,15,'GRAMO'),

-- 26 Arroz con Leche
(26,17,120,'GRAMO'),(26,14,500,'MILILITRO'),(26,27,20,'GRAMO'),

-- 27 Avena Dulce
(27,18,60,'GRAMO'),(27,14,250,'MILILITRO'),(27,27,15,'GRAMO'),

-- 28 Tortilla con Queso
(28,11,2,'UNIDAD'),(28,15,40,'GRAMO'),

-- 29 Pollo con Salsa de Tomate
(29,8,220,'GRAMO'),(29,21,100,'GRAMO'),

-- 30 Arroz Salteado
(30,17,180,'GRAMO'),(30,25,10,'MILILITRO'),

-- 31 Verduras al Vapor
(31,3,100,'GRAMO'),(31,2,80,'GRAMO'),(31,4,60,'GRAMO'),

-- 32 Fruta Picada
(32,5,100,'GRAMO'),(32,6,100,'GRAMO'),

-- 33 Lentejas con Verduras
(33,13,200,'GRAMO'),(33,3,80,'GRAMO'),(33,2,60,'GRAMO'),

-- 34 Tofu con Arroz
(34,12,150,'GRAMO'),(34,17,180,'GRAMO'),

-- 35 Huevos Cocidos
(35,11,2,'UNIDAD'),

-- 36 Arroz con Aceite
(36,17,180,'GRAMO'),(36,25,10,'MILILITRO'),

-- 37 Yogur Natural
(37,16,125,'GRAMO'),

-- 38 Manzana Fresca
(38,5,150,'GRAMO'),

-- 39 Plátano Fresco
(39,6,120,'GRAMO'),

-- 40 Ensalada de Zanahoria
(40,3,200,'GRAMO'),(40,25,5,'MILILITRO');









select fcr.id_recipe, c.id from favorite_collection_recipe fcr
                                    join favorite_collection fc on fcr.id_collection = fc.id
                                    join client c on fc.id_user = c.id

select r.id, count(r.id) as "TotalFav"
from recipe r
         join favorite_collection_recipe fcr on r.id= fcr.id_recipe
where r.id = fcr.id_recipe
group by r.id


select c.id from client c
                     join favorite_collection fc on c.id = fc.id_user
                     join favorite_collection_recipe fcr on fc.id = fcr.id_collection
                     join recipe r on fcr.id_recipe = r.id
where r.id = 3


SELECT
    DISTINCT (c.*) AS client_id,
             fcr.id_recipe as receta
FROM client c
         JOIN favorite_collection fc
              ON c.id = fc.id_user
         JOIN favorite_collection_recipe fcr
              ON fc.id = fcr.id_collection
WHERE fcr.id_recipe = (
    SELECT id_recipe
    FROM favorite_collection_recipe
    GROUP BY id_recipe
    ORDER BY COUNT(*) DESC
    LIMIT 1


)


select * from favorite_collection_recipe f
order by f.id_recipe

select r.id, c.id from client c
                           join favorite_collection fc on c.id = fc.id_user
                           join favorite_collection_recipe fcr on fc.id = fcr.id_collection
                           join recipe r on fcr.id_recipe = r.id
where r.id = fcr.id_recipe
group by r.id, c.id
order by c.id asc



select r.* from recipe r
                    left join recipe_tag rt on r.id = rt.id_recipe
                    join client_tag ct on ct.id_tag = rt.id_tag
                    left join client c on ct.id_client = c.id
where rt.id_tag = (ct.id_tag) and ct.id_client = 2


select r.* from recipe r
                    left join recipe_ingredient ri on r.id = ri.id_recipe
                    left join ingredient i on ri.id_ingredient = i.



SELECT DISTINCT r.*
FROM recipe r
    LEFT JOIN recipe_tag rt ON r.id = rt.id_recipe
    JOIN client_tag ct ON ct.id_tag = rt.id_tag
    LEFT JOIN client c ON ct.id_client = c.id
WHERE ct.id_client = 2
  AND ct.id_tag = rt.id_tag
  AND r.is_active = TRUE


ALTER TABLE shopping_list
    ALTER COLUMN status SET DEFAULT TRUE;

SELECT * FROM recipe_rating
SELECT *
FROM recipe_ingredient ri
         JOIN ingredient i ON ri.id_ingredient = i.id
ORDER BY ri.id_recipe ASC;

