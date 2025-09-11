package praktikum.tests;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import praktikum.model.Bun;
import praktikum.model.Burger;
import praktikum.model.Ingredient;
import praktikum.model.IngredientType;

import java.util.Arrays;
import java.util.Collection;
import java.util.Locale;

import static org.junit.Assert.*;
import static org.mockito.Mockito.when;
import static praktikum.tests.TestConstants.*;

@RunWith(Parameterized.class)
public class BurgerTest {

    @Mock
    private Bun bun;

    @Mock
    private Ingredient ingredient;

    private Burger burger;

    private final String testName;
    private final String testBunName;
    private final String testIngredientName;
    private final IngredientType testIngredientType;

    public BurgerTest(String testName, String bunName, IngredientType ingredientType, String ingredientName) {
        this.testName = testName;
        this.testBunName = bunName;
        this.testIngredientType = ingredientType;
        this.testIngredientName = ingredientName;
    }

    @Parameterized.Parameters(name = "{0}")
    public static Collection<Object[]> testData() {
        return Arrays.asList(BurgerTestData.getReceiptFormatData());
    }

    @Before
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        burger = new Burger();
    }

    @Test
    public void shouldHandleBunAndIngredientOperations() {
        // Проверяем установку булочки
        burger.setBuns(bun);
        assertSame("Булочка должна быть установлена", bun, burger.bun);

        // Проверяем добавление ингредиента
        burger.addIngredient(ingredient);
        assertEquals("Должен быть добавлен один ингредиент", 1, burger.ingredients.size());
        assertSame("Ингредиент должен быть добавлен в список", ingredient, burger.ingredients.get(0));

        // Проверяем удаление ингредиента
        burger.removeIngredient(0);
        assertTrue("Ингредиент должен быть удален", burger.ingredients.isEmpty());
    }

    @Test
    public void shouldMoveIngredientCorrectly() {
        Ingredient firstIngredient = ingredient;
        Ingredient secondIngredient = new Ingredient(IngredientType.FILLING, "second", 100);

        burger.addIngredient(firstIngredient);
        burger.addIngredient(secondIngredient);

        burger.moveIngredient(0, 1);

        assertSame("Ингредиенты должны быть перемещены", secondIngredient, burger.ingredients.get(0));
        assertSame("Ингредиенты должны быть перемещены", firstIngredient, burger.ingredients.get(1));
    }

    @Test
    public void shouldCalculatePricesCorrectly() {
        // Проверяем расчет цены только с булочкой
        when(bun.getPrice()).thenReturn(BUN_PRICE_100);
        burger.setBuns(bun);
        float bunOnlyPrice = BUN_PRICE_100 * 2;
        assertEquals("Цена только с булочкой должна быть рассчитана правильно",
                bunOnlyPrice, burger.getPrice(), PRICE_DELTA);

        // Проверяем расчет цены с булочкой и ингредиентом
        when(ingredient.getPrice()).thenReturn(INGREDIENT_PRICE_50);
        burger.addIngredient(ingredient);
        float totalPrice = bunOnlyPrice + INGREDIENT_PRICE_50;
        assertEquals("Цена с булочкой и ингредиентом должна быть рассчитана правильно",
                totalPrice, burger.getPrice(), PRICE_DELTA);
    }

    @Test
    public void shouldGenerateCorrectReceiptFormat() {
        when(bun.getName()).thenReturn(testBunName);
        when(bun.getPrice()).thenReturn(BUN_PRICE_100);
        when(ingredient.getType()).thenReturn(testIngredientType);
        when(ingredient.getName()).thenReturn(testIngredientName);
        when(ingredient.getPrice()).thenReturn(INGREDIENT_PRICE_50);

        burger.setBuns(bun);
        burger.addIngredient(ingredient);

        String receipt = burger.getReceipt();
        String[] lines = receipt.split("\n");

        assertTrue("Чек должен начинаться с верхней булочки", lines[0].contains(testBunName));
        assertTrue("Чек должен содержать ингредиент", lines[1].contains(testIngredientName.toLowerCase()));
        assertTrue("Чек должен заканчиваться нижней булочкой", lines[2].contains(testBunName));
        assertTrue("Предпоследняя строка должна быть пустой", lines[3].trim().isEmpty());
        assertTrue("Чек должен содержать секцию цены", lines[4].contains("Price:"));
    }

    @Test
    public void shouldContainCorrectIngredientTypeInReceipt() {
        when(bun.getName()).thenReturn(testBunName);
        when(ingredient.getType()).thenReturn(testIngredientType);
        when(ingredient.getName()).thenReturn(testIngredientName);

        burger.setBuns(bun);
        burger.addIngredient(ingredient);

        String receipt = burger.getReceipt();
        String expectedType = testIngredientType.toString().toLowerCase();

        assertTrue("Чек должен содержать тип ингредиента: " + expectedType,
                receipt.contains(expectedType));
    }

    @Test
    public void shouldContainCorrectPriceInReceipt() {
        when(bun.getPrice()).thenReturn(BUN_PRICE_100);
        when(ingredient.getPrice()).thenReturn(INGREDIENT_PRICE_50);
        when(bun.getName()).thenReturn(BLACK_BUN_NAME);
        when(ingredient.getType()).thenReturn(IngredientType.SAUCE);
        when(ingredient.getName()).thenReturn(HOT_SAUCE_NAME);

        burger.setBuns(bun);
        burger.addIngredient(ingredient);

        String receipt = burger.getReceipt();
        float expectedPrice = BUN_PRICE_100 * 2 + INGREDIENT_PRICE_50;

        assertTrue("Чек должен содержать правильную цену: " + expectedPrice,
                receipt.contains(String.format(Locale.US, "%.2f", expectedPrice)));
    }

    @Test
    public void shouldWorkWithDifferentIngredientTypes() {
        // Этот тест покрывает параметризованные данные для разных типов ингредиентов
        when(bun.getName()).thenReturn(testBunName);
        when(ingredient.getType()).thenReturn(testIngredientType);
        when(ingredient.getName()).thenReturn(testIngredientName);

        burger.setBuns(bun);
        burger.addIngredient(ingredient);

        String receipt = burger.getReceipt();
        String expectedType = testIngredientType.toString().toLowerCase();

        // Проверяем, что чек генерируется и содержит тип ингредиента
        assertNotNull("Чек должен быть сгенерирован", receipt);
        assertTrue("Чек должен содержать тип ингредиента", receipt.contains(expectedType));
    }
}