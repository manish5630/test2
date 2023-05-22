package com.example.common

import android.content.Context
import android.view.View
import it.sephiroth.android.library.xtooltip.ClosePolicy
import it.sephiroth.android.library.xtooltip.Tooltip

fun showToolTip(context: Context, anchor: View, message: String, onToolTipHide: () -> Unit) {
    val tooltip = Tooltip.Builder(context)
        .anchor(anchor)
        .text(message)
        .closePolicy(ClosePolicy.TOUCH_ANYWHERE_CONSUME)
        .overlay(false)
        .arrow(true)
        .floatingAnimation(Tooltip.Animation.DEFAULT)
        .styleId(R.style.ToolTipAltStyle)
        .create()

    tooltip.doOnHidden { onToolTipHide.invoke() }
    tooltip.show(anchor, Tooltip.Gravity.TOP)
}