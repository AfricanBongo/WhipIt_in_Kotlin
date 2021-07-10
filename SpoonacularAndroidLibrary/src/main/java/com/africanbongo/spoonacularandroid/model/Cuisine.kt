package com.africanbongo.spoonacularandroid.model

/**
 * An enum class illustrating the cuisine that a recipe would belong to,
 * e.g. A Texas BBQ Medley is considered to be part of American cuisine.
 */
enum class Cuisine(val id: Int, val type: String) {
    NONE(1, "none"),
    AFRICAN(2,"african"),
    AMERICAN(3, "american"),
    BRITISH(4, "british"),
    CAJUN(5,"cajun"),
    CARIBBEAN(6, "caribbean"),
    CHINESE(7, "chinese"),
    EASTERN_EUROPEAN(8, "eastern_european"),
    EUROPEAN(9, "european"),
    FRENCH(10, "french"),
    GERMAN(11, "german"),
    GREEK(12, "greek"),
    INDIAN(13, "indian"),
    IRISH(14, "irish"),
    ITALIAN(15, "italian"),
    JAPANESE(16, "japanese"),
    JEWISH(17, "jewish"),
    KOREAN(18, "korean"),
    LATIN_AMERICAN(19, "latin_american"),
    MEDITERRANEAN(20, "mediterranean"),
    MEXICAN(21, "mexican"),
    MIDDLE_EASTERN(22, "middle_eastern"),
    NORDIC(23, "nordic"),
    SOUTHERN(24, "southern"),
    THAI(25, "thai"),
    VIETNAMESE(26,"vietnamese");
}