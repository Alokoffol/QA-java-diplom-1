package praktikum.tests;

import praktikum.model.IngredientType;

public class BurgerTestData {

    public static Object[][] getReceiptTestData() {
        return new Object[][] {
                {"black bun", IngredientType.SAUCE, "hot sauce", "black bun", "hot sauce"},
                {"white bun", IngredientType.FILLING, "cutlet", "white bun", "cutlet"},
                {"red bun", IngredientType.SAUCE, "chili sauce", "red bun", "chili sauce"}
        };
    }

    public static Object[][] getPriceTestData() {
        return new Object[][] {
                {100f, 50f, 250f}, // bunPrice, ingredientPrice, expectedTotal
                {200f, 100f, 500f},
                {300f, 150f, 750f}
        };
    }
}