package ru.wearemad.cleanarcexm.presentation.changehandlers

import android.view.View
import android.animation.ObjectAnimator
import android.animation.AnimatorSet
import android.os.Build
import android.view.ViewGroup
import android.animation.Animator


class CircularRevealChangeHandlerCompat
    : CircularRevealChangeHandler {

    constructor() : super()
    constructor(fromView: View, containerView: View) : super(fromView, containerView)

    override fun getAnimator(container: ViewGroup, from: View?,
                             to: View?, isPush: Boolean,
                             toAddedToContainer: Boolean): Animator {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            return super.getAnimator(container, from, to, isPush, toAddedToContainer)
        } else {
            val animator = AnimatorSet()
            if (to != null) {
                val start: Float = if (toAddedToContainer) 0F else to.alpha
                animator.play(ObjectAnimator.ofFloat<View>(to, View.ALPHA, start, 1F))
            }

            if (from != null) {
                animator.play(ObjectAnimator.ofFloat<View>(from, View.ALPHA, 0F))
            }

            return animator
        }
    }
}