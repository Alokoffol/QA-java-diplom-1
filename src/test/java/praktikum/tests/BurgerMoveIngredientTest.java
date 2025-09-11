package praktikum.tests;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import praktikum.model.Burger;
import praktikum.model.Ingredient;

import static org.junit.Assert.*;

public class BurgerMoveIngredientTest {

    @Mock
    private Ingredient firstIngredient;

    @Mock
    private Ingredient secondIngredient;

    @Mock
    private Ingredient thirdIngredient;

    private Burger burger;

    @Before
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        burger = new Burger();
    }

    @Test
    public void shouldMoveFirstIngredientToLastPosition() {
        burger.addIngredient(firstIngredient);
        burger.addIngredient(secondIngredient);
        burger.addIngredient(thirdIngredient);

        burger.moveIngredient(0, 2); // Перемещаем первый элемент (индекс 0) на позицию 2

        assertSame("Первый ингредиент должен стать последним", firstIngredient, burger.ingredients.get(2));
        assertSame("Второй ингредиент должен остаться на месте", secondIngredient, burger.ingredients.get(0));
        assertSame("Третий ингредиент должен стать вторым", thirdIngredient, burger.ingredients.get(1));
    }

    @Test
    public void shouldMoveLastIngredientToFirstPosition() {
        burger.addIngredient(firstIngredient);
        burger.addIngredient(secondIngredient);
        burger.addIngredient(thirdIngredient);

        burger.moveIngredient(2, 0); // Перемещаем последний элемент (индекс 2) на позицию 0

        assertSame("Последний ингредиент должен стать первым", thirdIngredient, burger.ingredients.get(0));
        assertSame("Первый ингредиент должен стать вторым", firstIngredient, burger.ingredients.get(1));
        assertSame("Второй ингредиент должен стать третьим", secondIngredient, burger.ingredients.get(2));
    }
}