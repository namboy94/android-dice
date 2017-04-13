package net.namibsun.dice

/**
 * Class that defines the attributes for a theme.
 * For example, the permutations for a normal 6-sided die
 * @param style: The style of the theme. Used to define which images to use for the die
 * @param vibrate: Can be set to true to enable vibrating
 * @param wiggleAnimation: Can be set to true to enable a wiggle animation
 * @param changeAnimation: Can be set to true to enable an animation that
 *                         changes the eyes of the die while it is rolling
 */
class Theme(style: ThemeStyles, val vibrate: Boolean,
            val wiggleAnimation: Boolean, val changeAnimation: Boolean) {

    /**
     * The permutations for a 6-sided die
     */
    val permutations = this.findPermutations(style)

    /**
     * Defines the permutations for a 6-sided die based on the provided style value
     */
    fun findPermutations(style: ThemeStyles) : List<Int> {

        // Add new styles here!
        return when(style) {
            ThemeStyles.CLASSIC -> listOf(
                    R.drawable.dice1, R.drawable.dice2, R.drawable.dice3,
                    R.drawable.dice4, R.drawable.dice5, R.drawable.dice6
            )
        }
    }
}

/**
 * An enum to help define different Theme style types
 */
enum class ThemeStyles {
    CLASSIC
}