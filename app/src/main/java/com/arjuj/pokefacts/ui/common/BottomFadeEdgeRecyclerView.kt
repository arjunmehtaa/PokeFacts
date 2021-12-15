package com.arjuj.pokefacts.ui.common

import android.content.Context
import android.util.AttributeSet
import androidx.recyclerview.widget.RecyclerView

class BottomFadeEdgeRecyclerView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : RecyclerView(context, attrs, defStyleAttr) {

    override fun isPaddingOffsetRequired(): Boolean {
        return true
    }

    override fun getTopPaddingOffset(): Int {
        return -paddingBottom
    }

    override fun getBottomPaddingOffset(): Int {
        return paddingTop
    }

    override fun getTopFadingEdgeStrength(): Float {
        return 0f
    }
}