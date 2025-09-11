package praktikum.tests;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import praktikum.model.Bun;
import praktikum.model.Burger;
import praktikum.model.Ingredient;
import praktikum.model.IngredientType;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.when;
import static praktikum.tests.TestConstants.*;

public class BurgerMultipleIngredientsTest {

    @Mock
    private Bun bun;

    @Mock
    private Ingredient sauce;

    @Mock
    private Ingredient filling;

    private Burger burger;

    @Before
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        burger = new Burger();
    }

    @Test
    public void shouldCalculatePriceWithMultipleIngredients() {
        // Используем данные из тестовых данных
        float bunPrice = 100.0f;
        float saucePrice = 50.0f;
        float fillingPrice = 75.0f;
        float expectedPrice = bunPrice * 2 + saucePrice + fillingPrice; // 325.0f

        when(bun.getPrice()).thenReturn(bunPrice);
        when(sauce.getPrice()).thenReturn(saucePrice);
        when(filling.getPrice()).thenReturn(fillingPrice);

        burger.setBuns(bun);
        burger.addIngredient(sauce);
        burger.addIngredient(filling);

        assertEquals("Цена с несколькими ингредиентами должна быть рассчитана правильно",
                expectedPrice, burger.getPrice(), PRICE_DELTA);
    }

    @Test
    public void shouldGenerateReceiptWithMultipleIngredients() {
        when(bun.getName()).thenReturn(BLACK_BUN_NAME);
        when(sauce.getType()).thenReturn(IngredientType.SAUCE);
        when(sauce.getName()).thenReturn(HOT_SAUCE_NAME);
        when(filling.getType()).thenReturn(IngredientType.FILLING);
        when(filling.getName()).thenReturn(CUTLET_NAME);

        burger.setBuns(bun);
        burger.addIngredient(sauce);
        burger.addIngredient(filling);

        String receipt = burger.getReceipt();
        String[] lines = receipt.split("\n");

        // Чек должен содержать: верхняя булочка, соус, начинка, нижняя булочка, пустая строка, цена
        assertEquals("Чек должен содержать 6 строк", 6, lines.length);
        assertTrue("Первая строка должна содержать булочку", lines[0].contains(BLACK_BUN_NAME));
        assertTrue("Вторая строка должна содержать соус", lines[1].contains(HOT_SAUCE_NAME));
        assertTrue("Третья строка должна содержать начинку", lines[2].contains(CUTLET_NAME));
        assertTrue("Четвертая строка должна содержать булочку", lines[3].contains(BLACK_BUN_NAME));
        assertTrue("Пятая строка должна быть пустой", lines[4].trim().isEmpty());
        assertTrue("Последняя строка должна содержать цену", lines[5].contains("Price:"));
    }
}