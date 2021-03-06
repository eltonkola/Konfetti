package nl.dionsegijn.konfetti.models

import androidx.compose.ui.unit.Dp

/**
 * Created by dionsegijn on 3/26/17.
 * [sizeInDp] the size of the confetti in dip
 * [mass] each size can have its own mass for slightly different behavior. For example, the closer
 * the mass is to zero the easier it will accelerate.
 */
data class SizeZ(val sizeInDp: Dp, val mass: Float = 5f) {

    internal val sizeInPx: Float
        get() = sizeInDp.value

    init {
        require(mass != 0F) { "mass=$mass must be != 0" }
    }

}
