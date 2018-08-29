package ru.wearemad.cleanarcexm.presentation.changehandlers

import android.animation.Animator
import android.annotation.TargetApi
import android.os.Build
import android.view.View
import com.bluelinelabs.conductor.changehandler.AnimatorChangeHandler
import android.view.ViewAnimationUtils
import android.view.ViewGroup
import android.os.Bundle

@TargetApi(Build.VERSION_CODES.LOLLIPOP)
open class CircularRevealChangeHandler : AnimatorChangeHandler {

    private val KEY_CX = "CircularRevealChangeHandler.cx"
    private val KEY_CY = "CircularRevealChangeHandler.cy"

    private var cx: Int = 0
    private var cy: Int = 0

    constructor() : super()

    constructor(fromView: View, containerView: View)
            : this(fromView, containerView, DEFAULT_ANIMATION_DURATION)

    constructor(fromView: View, containerView: View, duration: Long)
            : this(fromView, containerView, duration, true)

    constructor(fromView: View, containerView: View, removesFromViewOnPush: Boolean)
            : this(fromView, containerView, DEFAULT_ANIMATION_DURATION, removesFromViewOnPush)

    constructor(fromView: View, containerView: View, duration: Long, removesFromViewOnPush: Boolean)
            : super(duration, removesFromViewOnPush) {
        val fromLocation = IntArray(2)
        fromView.getLocationInWindow(fromLocation)

        val containerLocation = IntArray(2)
        containerView.getLocationInWindow(containerLocation)

        val relativeLeft = fromLocation[0] - containerLocation[0]
        val relativeTop = fromLocation[1] - containerLocation[1]

        cx = fromView.width / 2 + relativeLeft
        cy = fromView.height / 2 + relativeTop
    }

    override fun getAnimator(container: ViewGroup, from: View?,
                             to: View?, isPush: Boolean,
                             toAddedToContainer: Boolean): Animator {
        val radius = Math.hypot(cx.toDouble(), cy.toDouble()).toFloat()
        val animator: Animator? = if (isPush && to != null) {
            ViewAnimationUtils.createCircularReveal(to, cx, cy, 0F, radius)
        } else if (!isPush && from != null) {
            ViewAnimationUtils.createCircularReveal(from, cx, cy, radius, 0F)
        } else {
            null
        }
        return animator!!
    }

    override fun resetFromView(from: View) {}

    override fun saveToBundle(bundle: Bundle) {
        super.saveToBundle(bundle)
        bundle.putInt(KEY_CX, cx)
        bundle.putInt(KEY_CY, cy)
    }

    override fun restoreFromBundle(bundle: Bundle) {
        super.restoreFromBundle(bundle)
        cx = bundle.getInt(KEY_CX)
        cy = bundle.getInt(KEY_CY)
    }

}