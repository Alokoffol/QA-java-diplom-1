package praktikum.tests;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import praktikum.model.Bun;
import praktikum.model.Burger;
import praktikum.model.Ingredient;
import praktikum.model.IngredientType;

import java.util.Arrays;
import java.util.Collection;

import static org.junit.Assert.assertEquals;

@RunWith(Parameterized.class)
public class BurgerPriceParameterizedTest {

    private final float bunPrice;
    private final float ingredientPrice;
    private final float expectedTotalPrice;

    public BurgerPriceParameterizedTest(float bunPrice, float ingredientPrice, float expectedTotalPrice) {
        this.bunPrice = bunPrice;
        this.ingredientPrice = ingredientPrice;
        this.expectedTotalPrice = expectedTotalPrice;
    }

    @Parameterized.Parameters(name = "Булочка: {0}, Ингредиент: {1}, Ожидаемая цена: {2}")
    public static Collection<Object[]> testData() {
        return Arrays.asList(BurgerTestData.getPriceTestData());
    }

    @Test
    public void parameterizedGetPriceTest() {
        Bun testBun = new Bun("test bun", bunPrice);
        Ingredient testIngredient = new Ingredient(IngredientType.SAUCE, "test sauce", ingredientPrice);

        Burger testBurger = new Burger();
        testBurger.setBuns(testBun);
        testBurger.addIngredient(testIngredient);

        float actualPrice = testBurger.getPrice();

        assertEquals("Цена бургера рассчитана неверно", expectedTotalPrice, actualPrice, 0.01f);
    }
}