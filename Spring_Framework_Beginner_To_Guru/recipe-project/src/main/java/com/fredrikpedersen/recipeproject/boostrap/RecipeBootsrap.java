package com.fredrikpedersen.recipeproject.boostrap;

import com.fredrikpedersen.recipeproject.domain.*;
import com.fredrikpedersen.recipeproject.repositories.CategoryRepository;
import com.fredrikpedersen.recipeproject.repositories.RecipeRepository;
import com.fredrikpedersen.recipeproject.repositories.UnitOfMeasureRepository;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * @author Fredrik Pedersen
 * @version 1.0
 * @since 26/01/2020 at 15:46
 */

@Component
public class RecipeBootsrap implements ApplicationListener<ContextRefreshedEvent> {

    private final CategoryRepository categoryRepository;
    private final RecipeRepository recipeRepository;
    private final UnitOfMeasureRepository unitOfMeasureRepository;


    public RecipeBootsrap(CategoryRepository categoryRepository, RecipeRepository recipeRepository, UnitOfMeasureRepository unitOfMeasureRepository) {
        this.categoryRepository = categoryRepository;
        this.recipeRepository = recipeRepository;
        this.unitOfMeasureRepository = unitOfMeasureRepository;
    }

    @Override
    public void onApplicationEvent(ContextRefreshedEvent contextRefreshedEvent) {
        recipeRepository.saveAll(getRecipes());
    }

    private List<Recipe> getRecipes() {

        List<Recipe> recipes = new ArrayList<>(2);

        /* ----- Units of Meassure ----- */
        List<Optional<UnitOfMeasure>> uomOptionalList = new ArrayList<>();
        List<UnitOfMeasure> uomList = new ArrayList<>();

        uomOptionalList.add(unitOfMeasureRepository.findByDescription("One unit")); //0
        uomOptionalList.add(unitOfMeasureRepository.findByDescription("Milliliter")); //1
        uomOptionalList.add(unitOfMeasureRepository.findByDescription("Centiliter")); //2
        uomOptionalList.add(unitOfMeasureRepository.findByDescription("Deciliter")); //3
        uomOptionalList.add(unitOfMeasureRepository.findByDescription("Liter")); //4
        uomOptionalList.add(unitOfMeasureRepository.findByDescription("Gram")); //5
        uomOptionalList.add(unitOfMeasureRepository.findByDescription("Kilogram")); //6

        for (Optional<UnitOfMeasure> unitOfMeasureOptional : uomOptionalList) {
            if (unitOfMeasureOptional.isPresent()) {
                uomList.add(unitOfMeasureOptional.get());
            } else {
                throw new RuntimeException("Expected UOM Not Found");
            }
        }

        /* ----- Categories ----- */

        Optional<Category> americanCategoryOptional = categoryRepository.findByDescription("American");

        if (!americanCategoryOptional.isPresent()) {
            throw new RuntimeException("Expected Category Not Found");
        }

        Optional<Category> mexicanCategoryOptional = categoryRepository.findByDescription("Mexican");

        if (!mexicanCategoryOptional.isPresent()) {
            throw new RuntimeException("Expected Category Not Found");
        }

        Category americanCategory = americanCategoryOptional.get();
        Category mexicanCategory = mexicanCategoryOptional.get();

        /* ----- Guacamole Recipe ------ */

        Recipe guacRecipe = new Recipe();
        guacRecipe.setDescription("Perfect Guacamole");
        guacRecipe.setPrepTime(10);
        guacRecipe.setCookTime(0);
        guacRecipe.setDifficulty(Difficulty.EASY);
        guacRecipe.setDirections("1 Cut avocado, remove flesh: Cut the avocados in half. Remove seed. Score the inside of the avocado with a blunt knife and scoop out the flesh with a spoon" +
                "\n" +
                "2 Mash with a fork: Using a fork, roughly mash the avocado. (Don't overdo it! The guacamole should be a little chunky.)" +
                "\n" +
                "3 Add salt, lime juice, and the rest: Sprinkle with salt and lime (or lemon) juice. The acid in the lime juice will provide some balance to the richness of the avocado and will help delay the avocados from turning brown.\n" +
                "Add the chopped onion, cilantro, black pepper, and chiles. Chili peppers vary individually in their hotness. So, start with a half of one chili pepper and add to the guacamole to your desired degree of hotness.\n" +
                "Remember that much of this is done to taste because of the variability in the fresh ingredients. Start with this recipe and adjust to your taste.\n" +
                "4 Cover with plastic and chill to store: Place plastic wrap on the surface of the guacamole cover it and to prevent air reaching it. (The oxygen in the air causes oxidation which will turn the guacamole brown.) Refrigerate until ready to serve.\n" +
                "Chilling tomatoes hurts their flavor, so if you want to add chopped tomato to your guacamole, add it just before serving.\n" +
                "\n" +
                "\n" +
                "Read more: http://www.simplyrecipes.com/recipes/perfect_guacamole/#ixzz4jvpiV9Sd");

        Notes guacNotes = new Notes();
        guacNotes.setRecipeNote("For a very quick guacamole just take a 1/4 cup of salsa and mix it in with your mashed avocados.\n" +
                "Feel free to experiment! One classic Mexican guacamole has pomegranate seeds and chunks of peaches in it (a Diana Kennedy favorite). Try guacamole with added pineapple, mango, or strawberries.\n" +
                "The simplest version of guacamole is just mashed avocados with salt. Don't let the lack of availability of other ingredients stop you from making guacamole.\n" +
                "To extend a limited supply of avocados, add either sour cream or cottage cheese to your guacamole dip. Purists may be horrified, but so what? It tastes great.\n" +
                "\n" +
                "\n" +
                "Read more: http://www.simplyrecipes.com/recipes/perfect_guacamole/#ixzz4jvoun5ws");
        guacRecipe.setNotes(guacNotes);

        guacRecipe.getIngredients().add(new Ingredient("Ripe Avocados", new BigDecimal(2), uomList.get(0)));
        guacRecipe.getIngredients().add(new Ingredient("Kosher salt", new BigDecimal(2), uomList.get(5)));
        guacRecipe.getIngredients().add(new Ingredient("Fresh lime juice or lemon juice", new BigDecimal(1), uomList.get(2)));
        guacRecipe.getIngredients().add(new Ingredient("Minced red onion or thinly sliced green onion", new BigDecimal(20), uomList.get(5)));
        guacRecipe.getIngredients().add(new Ingredient("Serrano chiles, stems and seeds removed, minced", new BigDecimal(2), uomList.get(0)));
        guacRecipe.getIngredients().add(new Ingredient("Cilantro", new BigDecimal(2), uomList.get(5)));
        guacRecipe.getIngredients().add(new Ingredient("Freshly grated black pepper", new BigDecimal(2), uomList.get(5)));
        guacRecipe.getIngredients().add(new Ingredient("Ripe tomato, seeds and pulp removed, chopped", new BigDecimal(".5"), uomList.get(0)));

        guacRecipe.getCategories().add(americanCategory);
        guacRecipe.getCategories().add(mexicanCategory);

        //add to return list
        recipes.add(guacRecipe);

        /* ----- Taco Recipe ----- */
        Recipe tacosRecipe = new Recipe();
        tacosRecipe.setDescription("Spicy Grilled Chicken Taco");
        tacosRecipe.setCookTime(9);
        tacosRecipe.setPrepTime(20);
        tacosRecipe.setDifficulty(Difficulty.MODERATE);

        tacosRecipe.setDirections("1 Prepare a gas or charcoal grill for medium-high, direct heat.\n" +
                "2 Make the marinade and coat the chicken: In a large bowl, stir together the chili powder, oregano, cumin, sugar, salt, garlic and orange zest. Stir in the orange juice and olive oil to make a loose paste. Add the chicken to the bowl and toss to coat all over.\n" +
                "Set aside to marinate while the grill heats and you prepare the rest of the toppings.\n" +
                "\n" +
                "\n" +
                "3 Grill the chicken: Grill the chicken for 3 to 4 minutes per side, or until a thermometer inserted into the thickest part of the meat registers 165F. Transfer to a plate and rest for 5 minutes.\n" +
                "4 Warm the tortillas: Place each tortilla on the grill or on a hot, dry skillet over medium-high heat. As soon as you see pockets of the air start to puff up in the tortilla, turn it with tongs and heat for a few seconds on the other side.\n" +
                "Wrap warmed tortillas in a tea towel to keep them warm until serving.\n" +
                "5 Assemble the tacos: Slice the chicken into strips. On each tortilla, place a small handful of arugula. Top with chicken slices, sliced avocado, radishes, tomatoes, and onion slices. Drizzle with the thinned sour cream. Serve with lime wedges.\n" +
                "\n" +
                "\n" +
                "Read more: http://www.simplyrecipes.com/recipes/spicy_grilled_chicken_tacos/#ixzz4jvtrAnNm");

        Notes tacoNotes = new Notes();
        tacoNotes.setRecipeNote("We have a family motto and it is this: Everything goes better in a tortilla.\n" +
                "Any and every kind of leftover can go inside a warm tortilla, usually with a healthy dose of pickled jalapenos. I can always sniff out a late-night snacker when the aroma of tortillas heating in a hot pan on the stove comes wafting through the house.\n" +
                "Today’s tacos are more purposeful – a deliberate meal instead of a secretive midnight snack!\n" +
                "First, I marinate the chicken briefly in a spicy paste of ancho chile powder, oregano, cumin, and sweet orange juice while the grill is heating. You can also use this time to prepare the taco toppings.\n" +
                "Grill the chicken, then let it rest while you warm the tortillas. Now you are ready to assemble the tacos and dig in. The whole meal comes together in about 30 minutes!\n" +
                "\n" +
                "\n" +
                "Read more: http://www.simplyrecipes.com/recipes/spicy_grilled_chicken_tacos/#ixzz4jvu7Q0MJ");
        tacosRecipe.setNotes(tacoNotes);

        tacosRecipe.getIngredients().add(new Ingredient("Ancho Chili Powder", new BigDecimal(2), uomList.get(5)));
        tacosRecipe.getIngredients().add(new Ingredient("Dried Oregano", new BigDecimal(1), uomList.get(5)));
        tacosRecipe.getIngredients().add(new Ingredient("Dried Cumin", new BigDecimal(1), uomList.get(5)));
        tacosRecipe.getIngredients().add(new Ingredient("Sugar", new BigDecimal(1), uomList.get(5)));
        tacosRecipe.getIngredients().add(new Ingredient("Salt", new BigDecimal(".5"), uomList.get(5)));
        tacosRecipe.getIngredients().add(new Ingredient("Clove of Garlic, Choppedr", new BigDecimal(1), uomList.get(0)));
        tacosRecipe.getIngredients().add(new Ingredient("finely grated orange zestr", new BigDecimal(1), uomList.get(5)));
        tacosRecipe.getIngredients().add(new Ingredient("fresh-squeezed orange juice", new BigDecimal(3), uomList.get(5)));
        tacosRecipe.getIngredients().add(new Ingredient("Olive Oil", new BigDecimal(2), uomList.get(5)));
        tacosRecipe.getIngredients().add(new Ingredient("boneless chicken thighs", new BigDecimal(4), uomList.get(5)));
        tacosRecipe.getIngredients().add(new Ingredient("small corn tortillasr", new BigDecimal(8), uomList.get(0)));
        tacosRecipe.getIngredients().add(new Ingredient("packed baby arugula", new BigDecimal(3), uomList.get(4)));
        tacosRecipe.getIngredients().add(new Ingredient("medium ripe avocados, slic", new BigDecimal(2), uomList.get(0)));
        tacosRecipe.getIngredients().add(new Ingredient("radishes, thinly sliced", new BigDecimal(4), uomList.get(0)));
        tacosRecipe.getIngredients().add(new Ingredient("cherry tomatoes, halved", new BigDecimal(".5"), uomList.get(4)));
        tacosRecipe.getIngredients().add(new Ingredient("red onion, thinly sliced", new BigDecimal(".25"), uomList.get(0)));
        tacosRecipe.getIngredients().add(new Ingredient("Roughly chopped cilantro", new BigDecimal(4), uomList.get(0)));
        tacosRecipe.getIngredients().add(new Ingredient("cup sour cream thinned with 1/4 cup milk", new BigDecimal(4), uomList.get(4)));
        tacosRecipe.getIngredients().add(new Ingredient("lime, cut into wedges", new BigDecimal(4), uomList.get(0)));

        tacosRecipe.getCategories().add(americanCategory);
        tacosRecipe.getCategories().add(mexicanCategory);

        recipes.add(tacosRecipe);

        return recipes;
    }
}