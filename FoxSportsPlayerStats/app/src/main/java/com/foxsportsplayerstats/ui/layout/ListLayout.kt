package com.foxsportsplayerstats.ui.layout

import android.content.Context
import android.database.DataSetObserver
import android.util.AttributeSet
import android.view.View
import android.widget.Adapter
import android.widget.LinearLayout


class ListLayout
@JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : LinearLayout(context, attrs, defStyleAttr) {

    private var observer = Observer()

    private var adapter: Adapter? = null

    fun setAdapter(adapter: Adapter) {
        this.adapter?.unregisterDataSetObserver(observer)

        this.adapter = adapter.apply {
            registerDataSetObserver(observer)
        }

        observer.onChanged()
    }

    inner class Observer : DataSetObserver() {

        override fun onChanged() {
            val oldViews: MutableList<View> = ArrayList(childCount)
            for (i in 0 until childCount)
                oldViews.add(getChildAt(i))

            val iterator: Iterator<View> = oldViews.iterator()

            removeAllViews()

            adapter?.let { adapter ->
                for (i in 0 until adapter.count) {
                    val convertView: View? = if (iterator.hasNext()) iterator.next() else null
                    val newView: View = adapter.getView(i, convertView, this@ListLayout)
                    addView(newView)
                }
            }

            super.onChanged()
        }

        override fun onInvalidated() {
            removeAllViews()
            super.onInvalidated()
        }
    }
}