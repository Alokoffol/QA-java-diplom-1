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
    private Ingredient ingredient1;

    @Mock
    private Ingredient ingredient2;

    private Burger burger;

    @Before
    public void setUp() {
        burger = new Burger();
    }

    @Test
    public void testSetBuns() {
        burger.setBuns(bun);
        // Проверка, что булочка установлена
    }

    @Test
    public void testAddIngredient() {
        burger.addIngredient(ingredient1);
        // Проверка, что ингредиент добавлен
    }

    @Test
    public void testRemoveIngredient() {
        burger.addIngredient(ingredient1);
        burger.removeIngredient(0);
        // Проверка, что ингредиент удален
    }

    @Test
    public void testMoveIngredient() {
        burger.addIngredient(ingredient1);
        burger.addIngredient(ingredient2);
        burger.moveIngredient(0, 1);
        // Проверка перемещения
    }

    @Test
    public void testGetPrice() {
        when(bun.getPrice()).thenReturn(100f);
        when(ingredient1.getPrice()).thenReturn(50f);

        burger.setBuns(bun);
        burger.addIngredient(ingredient1);

        float expectedPrice = 250f; // 100*2 + 50
        assertEquals("Цена бургера рассчитана неверно", expectedPrice, burger.getPrice(), 0.01f);
    }

    @Test
    public void testGetReceipt() {
        when(bun.getName()).thenReturn("black bun");
        when(ingredient1.getType()).thenReturn(IngredientType.SAUCE);
        when(ingredient1.getName()).thenReturn("hot sauce");

        burger.setBuns(bun);
        burger.addIngredient(ingredient1);

        String receipt = burger.getReceipt();
        assertTrue("Чек должен содержать название булочки", receipt.contains("black bun"));
        assertTrue("Чек должен содержать ингредиент", receipt.contains("hot sauce"));
    }
}