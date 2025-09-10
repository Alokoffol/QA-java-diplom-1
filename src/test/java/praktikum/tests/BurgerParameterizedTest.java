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

import static org.junit.Assert.assertTrue;

@RunWith(Parameterized.class)
public class BurgerParameterizedTest {

    private final String bunName;
    private final IngredientType ingredientType;
    private final String ingredientName;
    private final String expectedBunInReceipt;
    private final String expectedIngredientInReceipt;

    public BurgerParameterizedTest(String bunName, IngredientType ingredientType, String ingredientName,
                                   String expectedBunInReceipt, String expectedIngredientInReceipt) {
        this.bunName = bunName;
        this.ingredientType = ingredientType;
        this.ingredientName = ingredientName;
        this.expectedBunInReceipt = expectedBunInReceipt;
        this.expectedIngredientInReceipt = expectedIngredientInReceipt;
    }

    @Parameterized.Parameters(name = "Булочка: {0}, Ингредиент: {2}")
    public static Collection<Object[]> testData() {
        return Arrays.asList(BurgerTestData.getReceiptTestData());
    }

    @Test
    public void parameterizedGetReceiptTest() {
        Bun testBun = new Bun(bunName, 100f);
        Ingredient testIngredient = new Ingredient(ingredientType, ingredientName, 50f);

        Burger testBurger = new Burger();
        testBurger.setBuns(testBun);
        testBurger.addIngredient(testIngredient);

        String receipt = testBurger.getReceipt();

        assertTrue("Чек должен содержать название булочки: " + expectedBunInReceipt,
                receipt.contains(expectedBunInReceipt));
        assertTrue("Чек должен содержать ингредиент: " + expectedIngredientInReceipt,
                receipt.contains(expectedIngredientInReceipt));
    }
}