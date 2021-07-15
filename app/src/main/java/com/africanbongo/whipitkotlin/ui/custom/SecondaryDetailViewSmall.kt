package com.africanbongo.whipitkotlin.ui.custom

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.ImageView
import android.widget.RelativeLayout
import android.widget.TextView
import com.africanbongo.whipitkotlin.R

class SecondaryDetailViewSmall(context: Context, attrs: AttributeSet) :
    RelativeLayout(context, attrs) {

    private val textView: TextView
    private val iconView: ImageView

        init {
            val layout = LayoutInflater.from(context).inflate(R.layout.secondary_detail_view_small, this)
            iconView = layout.findViewById(R.id.item_smalls_icon)
            textView = layout.findViewById(R.id.item_smalls_text)

            context.theme.obtainStyledAttributes(
                attrs,
                R.styleable.SecondaryDetailViewSmall,
            0, 0).apply {

                try {
                    getString(R.styleable.SecondaryDetailViewSmall_text)?.let {
                        textView.text = it
                    }
                    iconView.setImageDrawable(getDrawable(R.styleable.SecondaryDetailViewSmall_icon))
                } finally {
                    recycle()
                }
            }
        }

    fun setText(text: String) {
        textView.text = text
    }
}