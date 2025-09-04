package praktikum.tests;

import io.qameta.allure.junit4.DisplayName;
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
@DisplayName("Тесты класса Burger")
public class BurgerTest {

    @Mock
    private Bun bun;

    @Mock
    private Ingredient ingredient1;

    @Mock
    private Ingredient ingredient2;

    private Burger burger;

    @Before
    @DisplayName("Инициализация бургера перед каждым тестом")
    public void setUp() {
        burger = new Burger();
    }

    @Test
    @DisplayName("Тест установки булочки")
    public void setBunsTest() {
        burger.setBuns(bun);
        assertNotNull("Булочка должна быть установлена", burger.bun);
    }

    @Test
    @DisplayName("Тест добавления ингредиента")
    public void addIngredientTest() {
        burger.addIngredient(ingredient1);
        assertEquals("Должен быть добавлен один ингредиент", 1, burger.ingredients.size());
    }

    @Test
    @DisplayName("Тест удаления ингредиента")
    public void removeIngredientTest() {
        burger.addIngredient(ingredient1);
        burger.removeIngredient(0);
        assertTrue("Ингредиент должен быть удален", burger.ingredients.isEmpty());
    }

    @Test
    @DisplayName("Тест перемещения ингредиента")
    public void moveIngredientTest() {
        burger.addIngredient(ingredient1);
        burger.addIngredient(ingredient2);
        burger.moveIngredient(0, 1);
        assertEquals("Ингредиенты должны быть перемещены", 2, burger.ingredients.size());
    }

    @Test
    @DisplayName("Тест расчета цены бургера")
    public void getPriceTest() {
        when(bun.getPrice()).thenReturn(100f);
        when(ingredient1.getPrice()).thenReturn(50f);

        burger.setBuns(bun);
        burger.addIngredient(ingredient1);

        float expectedPrice = 250f; // 100*2 + 50
        assertEquals("Цена бургера рассчитана неверно", expectedPrice, burger.getPrice(), 0.01f);
    }

    @Test
    @DisplayName("Тест генерации чека")
    public void getReceiptTest() {
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