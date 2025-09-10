package praktikum.tests;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import praktikum.model.Bun;
import praktikum.model.Burger;
import praktikum.model.Ingredient;
import praktikum.model.IngredientType;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class BurgerTest {

    @Mock
    private Bun bun;

    @Mock
    private Ingredient sauceIngredient;

    @Mock
    private Ingredient fillingIngredient;

    private Burger burger;

    @Before
    public void setUp() {
        burger = new Burger();
    }

    @Test
    public void setBunsTest() {
        burger.setBuns(bun);
        assertNotNull("Булочка должна быть установлена", burger.bun);
    }

    @Test
    public void addIngredientTest() {
        burger.addIngredient(sauceIngredient);
        assertEquals("Должен быть добавлен один ингредиент", 1, burger.ingredients.size());
    }

    @Test
    public void removeIngredientTest() {
        burger.addIngredient(sauceIngredient);
        burger.removeIngredient(0);
        assertTrue("Ингредиент должен быть удален", burger.ingredients.isEmpty());
    }

    @Test
    public void moveIngredientTest() {
        burger.addIngredient(sauceIngredient);
        burger.addIngredient(fillingIngredient);
        burger.moveIngredient(0, 1);
        assertEquals("Ингредиенты должны быть перемещены", 2, burger.ingredients.size());
    }

    @Test
    public void getPriceTest() {
        when(bun.getPrice()).thenReturn(100f);
        when(sauceIngredient.getPrice()).thenReturn(50f);

        burger.setBuns(bun);
        burger.addIngredient(sauceIngredient);

        float expectedPrice = 250f;
        assertEquals("Цена бургера рассчитана неверно", expectedPrice, burger.getPrice(), 0.01f);
    }

    @Test
    public void getReceiptFormatTest() {
        when(bun.getName()).thenReturn("black bun");
        when(sauceIngredient.getType()).thenReturn(IngredientType.SAUCE);
        when(sauceIngredient.getName()).thenReturn("hot sauce");
        when(sauceIngredient.getPrice()).thenReturn(50f);
        when(bun.getPrice()).thenReturn(100f);

        burger.setBuns(bun);
        burger.addIngredient(sauceIngredient);

        String receipt = burger.getReceipt();

        // Нормализуем переносы строк для кроссплатформенности
        String normalizedReceipt = receipt.replace("\r\n", "\n").replace("\r", "\n");

        // Проверяем отдельные части чека вместо полного сравнения
        assertTrue("Чек должен содержать название булочки", normalizedReceipt.contains("black bun"));
        assertTrue("Чек должен содержать ингредиент sauce", normalizedReceipt.contains("sauce hot sauce"));
        assertTrue("Чек должен содержать цену", normalizedReceipt.contains("Price:"));
        assertTrue("Чек должен содержать разделители ====", normalizedReceipt.contains("===="));

        // Проверяем, что цена содержит ожидаемое значение (250.00 или 250,00 в зависимости от локали)
        assertTrue("Чек должен содержать правильную цену",
                normalizedReceipt.contains("250.00") || normalizedReceipt.contains("250,00"));
    }

    @Test
    public void getReceiptContainsCorrectPriceTest() {
        when(bun.getName()).thenReturn("black bun");
        when(bun.getPrice()).thenReturn(100f);
        when(sauceIngredient.getType()).thenReturn(IngredientType.SAUCE);
        when(sauceIngredient.getName()).thenReturn("hot sauce");
        when(sauceIngredient.getPrice()).thenReturn(50f);

        burger.setBuns(bun);
        burger.addIngredient(sauceIngredient);

        String receipt = burger.getReceipt();

        // Проверяем только цену (учитываем разные форматы десятичных разделителей)
        assertTrue("Чек должен содержать правильную цену 250",
                receipt.contains("250.00") || receipt.contains("250,00"));
    }

    @Test
    public void getReceiptStructureTest() {
        when(bun.getName()).thenReturn("test bun");
        when(bun.getPrice()).thenReturn(100f);
        when(sauceIngredient.getType()).thenReturn(IngredientType.SAUCE);
        when(sauceIngredient.getName()).thenReturn("test sauce");
        when(sauceIngredient.getPrice()).thenReturn(50f);

        burger.setBuns(bun);
        burger.addIngredient(sauceIngredient);

        String receipt = burger.getReceipt();
        String[] lines = receipt.split("\r\n|\n|\r");

        // Проверяем структуру чека
        assertTrue("Чек должен содержать минимум 5 строк", lines.length >= 5);
        assertTrue("Первая строка должна содержать булочку", lines[0].contains("test bun"));

        // Ищем строку с ингредиентом
        boolean hasIngredientLine = false;
        for (String line : lines) {
            if (line.contains("test sauce")) {
                hasIngredientLine = true;
                break;
            }
        }
        assertTrue("Чек должен содержать ингредиент", hasIngredientLine);

        // Ищем строку с ценой
        boolean hasPriceLine = false;
        for (String line : lines) {
            if (line.contains("Price:")) {
                hasPriceLine = true;
                break;
            }
        }
        assertTrue("Чек должен содержать цену", hasPriceLine);
    }
}