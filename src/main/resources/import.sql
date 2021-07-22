insert into ingredient values(1, 'Beef');
insert into ingredient values(2, 'Potatoes');
insert into ingredient values(3, 'Carrots');
insert into ingredient values(4, 'Cereal');
insert into ingredient values(5, 'Milk');
insert into ingredient values(6, 'Eggs');
insert into ingredient values(7, 'Milk');
insert into ingredient values(8, 'Oranges');
insert into ingredient values(9, 'Salt');
insert into ingredient values(10, 'Black pepper');
insert into ingredient values(11, 'Butter');


--recipe ingredient for omelet
--omelet -> eggs: 4, milk: 1, Salt: 0.1g, Black pepper: 1g, Butter:10g
insert into recipe(ID, RECIPE_NAME,COOKING_INSTRUCTIONS,CUISINE,SUITABLE_FOR,DISH_TYPE) values(1,'omelet','1. BEAT eggs, milk, salt and pepper in small bowl until blended.\r\n 2. HEAT butter in 7 to 10-inch nonstick omelet pan or skillet over medium-high heat until hot. TILT pan to coat bottom. POUR IN egg mixture. Mixture should set immediately at edges. \r\n 3. GENTLY PUSH cooked portions from edges toward the center with inverted turner so that uncooked eggs can reach the hot pan surface. CONTINUE cooking, tilting pan and gently moving cooked portions as needed.\r\n 4.Enjoy', 'American','2 persons',0);

-- insert into recipe_ingredient(ID, INGREDIENT_ID, QUANTITY, RECIPE_ID) values(1,6,4.0,1);
-- insert into recipe_ingredient(ID, INGREDIENT_ID, QUANTITY, RECIPE_ID) values(2,7,1,1);
-- insert into recipe_ingredient(ID, INGREDIENT_ID, QUANTITY, RECIPE_ID) values(3,9,0.1,1);
-- insert into recipe_ingredient(ID, INGREDIENT_ID, QUANTITY, RECIPE_ID) values(4,10,1,1);
-- insert into recipe_ingredient(ID, INGREDIENT_ID, QUANTITY, RECIPE_ID) values(5,11,10,1);

