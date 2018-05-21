package com.miho.springmongodbrecipeapp.bootstrap

import com.miho.springmongodbrecipeapp.domain.*
import com.miho.springmongodbrecipeapp.repositories.CategoryRepository
import com.miho.springmongodbrecipeapp.repositories.RecipeRepository
import com.miho.springmongodbrecipeapp.repositories.UnitOfMeasureRepository
import org.springframework.beans.factory.InitializingBean
import org.springframework.stereotype.Component
import java.math.BigDecimal

@Component
class RecipeDataBootstrap(private val categoryRepository: CategoryRepository,
                          private val unitRepository: UnitOfMeasureRepository,
                          private val recipeRepository: RecipeRepository) : InitializingBean {

    override fun afterPropertiesSet() {
        loadCategories()

        loadUnits()

        val units = getUnits()

        val guacaRecipe = createGuacaRecipe(units)

        val spicyChickenTacoRecipe = createTacoRecipe(units)

        recipeRepository.saveAll(listOf(guacaRecipe, spicyChickenTacoRecipe))

    }

    private fun loadUnits() {
        arrayOf(UnitOfMeasure("Teaspoon"), UnitOfMeasure("Tablespoon"), UnitOfMeasure("Cup"),
                UnitOfMeasure("Pinch"), UnitOfMeasure("Ounce"), UnitOfMeasure("Clove"),
                UnitOfMeasure("Pint"), UnitOfMeasure("Dash"), UnitOfMeasure("Each"))
                .forEach { unitRepository.save(it) }
    }

    private fun loadCategories() {
        arrayOf(Category("American"), Category("Italian"),
                Category("Mexican"), Category("Austrian"),
                Category("Fast Food")).forEach { categoryRepository.save(it) }
    }

    private fun getUnits() = mapOf("Each" to unitRepository.findByUnit("Each"),
            "Teaspoon" to unitRepository.findByUnit("Teaspoon"),
            "Tablespoon" to unitRepository.findByUnit("Tablespoon"),
            "Dash" to unitRepository.findByUnit("Dash"))

    private fun createGuacaRecipe(units: Map<String, UnitOfMeasure?>): Recipe {

        val guacaRecipe = Recipe("How to Make Perfect Guacamole Recipe")

        val ingredients = createGuacaIngredients(units)

        guacaRecipe.prepTime = 10

        guacaRecipe.servings = 4

        guacaRecipe.url = "http://www.simplyrecipes.com/recipes/perfect_guacamole/"

        guacaRecipe.addIngredients(ingredients)

        guacaRecipe.addCategories(getCategories())

        guacaRecipe.difficulty = Difficulty.EASY

        guacaRecipe.directions = "1 Cut avocado, remove flesh: Cut the avocados in half. Remove seed. Score the inside of the avocado with a blunt knife and scoop out the flesh with a spoon. " +
                "Place in a bowl. \n" +

                "2 Mash with a fork: Using a fork, roughly mash the avocado. (Don't overdo it! The guacamole should be a little chunky.)\n" +

                "3 Add salt, lime juice, and the rest: Sprinkle with salt and lime (or lemon) juice. The acid in the lime juice will provide some balance " +
                "to the richness of the avocado and will help delay the avocados from turning brown.\n" +

                "Add the chopped onion, cilantro, black pepper, and chiles. Chili peppers vary individually in their hotness. " +
                "So, start with a half of one chili pepper and add to the guacamole to your desired degree of hotness.\n" +

                "Remember that much of this is done to taste because of the variability in the fresh ingredients. Start with this recipe and adjust to your taste.\n" +

                "4 Cover with plastic and chill to store: Place plastic wrap on the surface of the guacamole cover it and to prevent air reaching it. " +
                "(The oxygen in the air causes oxidation which will turn the guacamole brown.) Refrigerate until ready to serve.\n" +

                "Chilling tomatoes hurts their flavor, so if you want to add chopped tomato to your guacamole, add it just before serving."

        val notes = Notes(/*guacaRecipe,*/ "For a very quick guacamole just take a 1/4 cup of salsa and mix it in with your mashed avocados.\n" +

                "Feel free to experiment! One classic Mexican guacamole has pomegranate seeds and chunks of peaches in it (a Diana Kennedy favorite). " +
                "Try guacamole with added pineapple, mango, or strawberries.\n" +

                "The simplest version of guacamole is just mashed avocados with salt. Don't let the lack of availability of other ingredients stop you from making guacamole. \n" +

                "To extend a limited supply of avocados, add either sour cream or cottage cheese to your guacamole dip. Purists may be horrified, but so what? It tastes great.")

        guacaRecipe.notes = notes

        guacaRecipe.source = "Simply Recipes"

        return guacaRecipe

    }

    private fun createGuacaIngredients(units: Map<String, UnitOfMeasure?>): Set<Ingredient> {

        val each = units["Each"]

        val teaspoon = units["Teaspoon"]

        val tablespoon = units["Tablespoon"]

        val dash = units["Dash"]

        val result = mutableSetOf(Ingredient("ripe avocados", BigDecimal(2), each))

        result.add(Ingredient("Kosher salt", BigDecimal(0.5), teaspoon))

        result.add(Ingredient("fresh lime juice or lemon juice", BigDecimal.ONE, tablespoon))

        result.add(Ingredient("minced red onion or thinly sliced green onion", BigDecimal(2), tablespoon))

        result.add(Ingredient("serrano chiles, stems and seeds removed, minced", BigDecimal(2), each))

        result.add(Ingredient("cilantro (leaves and tender stems), finely chopped", BigDecimal(2), tablespoon))

        result.add(Ingredient("freshly grated black pepper", BigDecimal.ONE, dash))

        result.add(Ingredient("ripe Tomato, seeds and pulp removed, chopped", BigDecimal(0.5), each))

        return result

    }


    private fun getCategories(): Set<Category> {

        val mexican = categoryRepository.findByDescription("Mexican")

        return if (mexican != null)
            setOf(mexican)
        else
            emptySet()

    }

    private fun createTacoRecipe(units: Map<String, UnitOfMeasure?>): Recipe {

        val tacoRecipe = Recipe("Spicy Grilled Chicken Tacos")

        val ingredients = createTacoIngredients(units)

        tacoRecipe.prepTime = 20

        tacoRecipe.cookTime = 15

        tacoRecipe.servings = 6

        tacoRecipe.url = "http://www.simplyrecipes.com/recipes/spicy_grilled_chicken_tacos/"

        tacoRecipe.addIngredients(ingredients)

        tacoRecipe.addCategories(getCategories())

        tacoRecipe.difficulty = Difficulty.EASY

        tacoRecipe.directions = "1 Prepare a gas or charcoal grill for medium-high, direct heat.\n" +

                "2 Make the marinade and coat the chicken: In a large bowl, stir together the chili powder, oregano, cumin, " +
                "sugar, salt, garlic and orange zest. Stir in the orange juice and olive oil to make a loose paste. Add the chicken to the bowl and toss to coat all over.\n" +

                "Set aside to marinate while the grill heats and you prepare the rest of the toppings.\n" +

                "3 Grill the chicken: Grill the chicken for 3 to 4 minutes per side, or until a thermometer inserted into the thickest part of the meat registers 165F. " +
                "Transfer to a plate and rest for 5 minutes.\n" +

                "4 Warm the tortillas: Place each tortilla on the grill or on a hot, dry skillet over medium-high heat. " +
                "As soon as you see pockets of the air start to puff up in the tortilla, turn it with tongs and heat for a few seconds on the other side.\n" +

                "Wrap warmed tortillas in a tea towel to keep them warm until serving.\n" +

                "5 Assemble the tacos: Slice the chicken into strips. On each tortilla, place a small handful of arugula. " +
                "Top with chicken slices, sliced avocado, radishes, tomatoes, and onion slices. Drizzle with the thinned sour cream. Serve with lime wedges."


        val notes = Notes(/* tacoRecipe, */ "We have a family motto and it is this: Everything goes better in a tortilla.\n" +

                "Any and every kind of leftover can go inside a warm tortilla, usually with a healthy dose of pickled jalapenos. " +
                "I can always sniff out a late-night snacker when the aroma of tortillas heating in a hot pan on the stove comes wafting through the house.\n" +

                "Today’s tacos are more purposeful – a deliberate meal instead of a secretive midnight snack!\n" +

                "First, I marinate the chicken briefly in a spicy paste of ancho chile powder, oregano, cumin, " +
                "and sweet orange juice while the grill is heating. You can also use this time to prepare the taco toppings.\n" +

                "Grill the chicken, then let it rest while you warm the tortillas. Now you are ready to assemble the tacos and dig in. " +
                "The whole meal comes together in about 30 minutes!\n" +

                "The ancho chiles I use in the marinade are named for their wide shape. They are large, have a deep reddish brown color when dried, " +
                "and are mild in flavor with just a hint of heat. You can find ancho chile powder at any markets that sell Mexican ingredients, or online. \n" +

                "I like to put all the toppings in little bowls on a big platter at the center of the table: avocados, radishes, tomatoes, " +
                "red onions, wedges of lime, and a sour cream sauce. I add arugula, as well – this green isn’t traditional for tacos, " +
                "but we always seem to have some in the fridge and I think it adds a nice green crunch to the tacos.\n " +

                "Everyone can grab a warm tortilla from the pile and make their own tacos just they way they like them.\n" +

                "You could also easily double or even triple this recipe for a larger party. A taco and a cold beer on a warm day? Now that’s living!")

        tacoRecipe.notes = notes


        tacoRecipe.source = "Simply Recipes"

        return tacoRecipe


    }

    private fun createTacoIngredients(units: Map<String, UnitOfMeasure?>): Set<Ingredient> {

        val each = units["Each"]

        val teaspoon = units["Teaspoon"]

        val tablespoon = units["Tablespoon"]

        val cup = unitRepository.findByUnit("Cup")

        val clove = unitRepository.findByUnit("Clove")

        val pint = unitRepository.findByUnit("Pint")

        val result = mutableSetOf(Ingredient("ancho chili powder", BigDecimal(2), tablespoon))

        result.add(Ingredient("died oregano", BigDecimal.ONE, teaspoon))

        result.add(Ingredient("dried cumin", BigDecimal.ONE, teaspoon))

        result.add(Ingredient("sugar", BigDecimal.ONE, teaspoon))

        result.add(Ingredient("salt", BigDecimal(0.5), teaspoon))

        result.add(Ingredient("garlic, finely chopped", BigDecimal.ONE, clove))

        result.add(Ingredient("finely grated orange zest", BigDecimal.ONE, tablespoon))

        result.add(Ingredient("fresh-squeezed orange juice", BigDecimal(3), tablespoon))

        result.add(Ingredient("olive oil", BigDecimal(2), tablespoon))

        result.add(Ingredient("skinless, boneless chicken thighs", BigDecimal(6), each))

        result.add(Ingredient("small corn tortillas", BigDecimal(8), each))

        result.add(Ingredient("packed baby arugula", BigDecimal(3), cup))

        result.add(Ingredient("medium ripe avocados, sliced", BigDecimal(2), each))

        result.add(Ingredient("radishes, thinly sliced", BigDecimal(4), each))

        result.add(Ingredient("cherry tomatoes, halved", BigDecimal(0.5), pint))

        result.add(Ingredient("red onion, thinly sliced", BigDecimal(0.25), each))

        result.add(Ingredient("roughly chopped cilantro", BigDecimal.ONE, each))

        result.add(Ingredient("sour cream thinned with 1/4 cup of milk", BigDecimal(0.5), cup))

        result.add(Ingredient("lime, cut into wedges", BigDecimal.ONE, each))

        return result
    }

}