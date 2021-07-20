package com.africanbongo.whipitkotlin.ui.recipeoftheday

import android.content.Context
import android.graphics.Canvas
import android.graphics.Paint
import android.util.AttributeSet
import android.view.View
import androidx.core.content.res.ResourcesCompat
import com.africanbongo.whipitkotlin.R

class CircleBackground(context: Context, attrs: AttributeSet) : View(context, attrs) {

    private val circlePaint: Paint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
        isAntiAlias = true
        style = Paint.Style.FILL
        color = ResourcesCompat
            .getColor(resources, R.color.primaryColor, null)
    }

    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)
        val halfWidth: Float = (width * 0.5).toFloat()
        val thirdWidth = (width * 0.3333).toFloat()
        canvas?.drawCircle(halfWidth, (-thirdWidth), width.toFloat(), circlePaint)
    }

}