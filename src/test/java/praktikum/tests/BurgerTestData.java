package praktikum.tests;

import praktikum.model.IngredientType;

public class BurgerTestData {

    // Для тестирования формата чека
    public static Object[][] getReceiptFormatData() {
        return new Object[][] {
                {"Black Bun with Hot Sauce", "black bun", IngredientType.SAUCE, "hot sauce"},
                {"White Bun with Cutlet", "white bun", IngredientType.FILLING, "cutlet"},
                {"Red Bun with Chili Sauce", "red bun", IngredientType.SAUCE, "chili sauce"}
        };
    }

    // Для тестирования расчета цены
    public static Object[][] getPriceCalculationData() {
        return new Object[][] {
                {100.0f, 50.0f, 250.0f},   // стандартные цены
                {200.0f, 100.0f, 500.0f},  // высокие цены
                {50.0f, 25.0f, 125.0f}     // низкие цены
        };
    }

    // Для тестирования разных типов ингредиентов
    public static Object[][] getIngredientTypesData() {
        return new Object[][] {
                {IngredientType.SAUCE, "sauce"},
                {IngredientType.FILLING, "filling"}
        };
    }
}