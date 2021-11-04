package com.missclickads.cleaner.ui.filemanager.items

import android.view.View
import android.widget.TextView
import com.missclickads.cleaner.R
import com.missclickads.cleaner.databinding.ItemDropDownParentBinding
import com.xwray.groupie.ExpandableGroup
import com.xwray.groupie.ExpandableItem
import com.xwray.groupie.viewbinding.BindableItem

class ParentDropDown(

) : BindableItem<ItemDropDownParentBinding>(), ExpandableItem{

    private lateinit var expandableGroup: ExpandableGroup

    private var text : TextView? = null

    override fun bind(viewBinding: ItemDropDownParentBinding, position: Int) {
        viewBinding.root.setOnClickListener {
            expandableGroup.onToggleExpanded()
            text = viewBinding.textSort
        }
    }

    override fun getLayout(): Int = R.layout.item_drop_down_parent

    override fun initializeViewBinding(view: View): ItemDropDownParentBinding = ItemDropDownParentBinding.bind(view)

    override fun setExpandableGroup(onToggleListener: ExpandableGroup) {
        expandableGroup = onToggleListener
    }

    fun expand(type : String){
        expandableGroup.onToggleExpanded()
        text?.text = type
    }

}