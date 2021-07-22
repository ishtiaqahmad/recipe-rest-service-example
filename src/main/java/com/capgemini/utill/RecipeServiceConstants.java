package com.capgemini.utill;

import java.security.PublicKey;

public final class RecipeServiceConstants {
    public static final String ARG_NULL_ERROR_MESSAGE = "The \"%s\" argument may not be null";
    public static final String NOT_INSTANTIABLE_MESSAGE = "This class should not be instantiated";

    public enum FoodDishType {
        NON_VEGETARIN(0),  // Non Vegetarian with value 0
        VEGETARIN(1); // Vegetarian with value 1
        private final int dishTypeCode;

        FoodDishType(int typeCode) {
            this.dishTypeCode = typeCode;
        }

        public int getLevelCode() {
            return this.dishTypeCode;
        }

    }

    //private constructor to prevent instantiation
    private RecipeServiceConstants() {
        throw new UnsupportedOperationException(NOT_INSTANTIABLE_MESSAGE);
    }
}
