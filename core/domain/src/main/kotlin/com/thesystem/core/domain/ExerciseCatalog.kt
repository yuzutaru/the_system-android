package com.thesystem.core.domain

object ExerciseCatalog {
    val samples: List<Exercise> = listOf(
        Exercise("barbell-squat", "Barbell Squat", ExerciseKind.STRENGTH, Attribute.STRENGTH),
        Exercise("bench-press", "Bench Press", ExerciseKind.STRENGTH, Attribute.STRENGTH),
        Exercise(
            "deadlift", "Deadlift", ExerciseKind.STRENGTH,
            Attribute.STRENGTH, Attribute.ENDURANCE,
        ),
        Exercise("overhead-press", "Overhead Press", ExerciseKind.STRENGTH, Attribute.STRENGTH),
        Exercise(
            "pull-up", "Pull-up", ExerciseKind.STRENGTH,
            Attribute.STRENGTH, Attribute.AGILITY,
        ),
        Exercise("run", "Run", ExerciseKind.CARDIO, Attribute.ENDURANCE),
        Exercise(
            "cycling", "Cycling", ExerciseKind.CARDIO,
            Attribute.ENDURANCE, Attribute.STRENGTH,
        ),
        Exercise(
            "jump-rope", "Jump Rope", ExerciseKind.CARDIO,
            Attribute.ENDURANCE, Attribute.AGILITY,
        ),
        Exercise(
            "yoga", "Yoga Flow", ExerciseKind.MOBILITY,
            Attribute.AGILITY, Attribute.VITALITY,
        ),
        Exercise("mobility-drill", "Mobility Drill", ExerciseKind.MOBILITY, Attribute.AGILITY),
    )

    fun byId(id: String): Exercise? = samples.firstOrNull { it.id == id }
}
