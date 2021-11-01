package com.missclickads.cleaner.ui.filemanager.items

import android.view.View
import androidx.appcompat.widget.AppCompatImageButton
import com.missclickads.cleaner.R
import com.missclickads.cleaner.databinding.ItemFileBinding
import com.missclickads.cleaner.databinding.ItemSpinnerBinding
import com.missclickads.cleaner.models.FileModel
import com.xwray.groupie.viewbinding.BindableItem

class ChildrenDropDown (
    private val textSort : String
) : BindableItem<ItemSpinnerBinding>(){


    override fun bind(viewBinding: ItemSpinnerBinding, position: Int) {
        viewBinding.sort.text = textSort
    }



    override fun getLayout(): Int = R.layout.item_spinner

    override fun initializeViewBinding(view: View): ItemSpinnerBinding = ItemSpinnerBinding.bind(view)

}