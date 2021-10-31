package com.missclickads.cleaner.ui.filemanager.items

import android.view.View
import androidx.appcompat.widget.AppCompatImageButton
import com.missclickads.cleaner.R
import com.missclickads.cleaner.databinding.ItemFileBinding
import com.missclickads.cleaner.models.FileModel
import com.xwray.groupie.viewbinding.BindableItem

class FileItem (
    private val file : FileModel,
    private val selectedCallback : (FileModel, Boolean) -> (Unit)
) : BindableItem<ItemFileBinding>(){

    private var btn : AppCompatImageButton? = null

    override fun bind(viewBinding: ItemFileBinding, position: Int) {
        btn = viewBinding.checkBtn
        file.image?.let { viewBinding.icon.setImageBitmap(it) }
        viewBinding.textNameTop.text = file.title
        viewBinding.textSizeBottom.text = file.size

        viewBinding.checkBtn.setOnClickListener {
            it.isSelected = !it.isSelected
            selectedCallback(file, it.isSelected)
        }
    }

    fun selectItem(){
        btn?.isSelected = true
    }

    override fun getLayout(): Int = R.layout.item_file

    override fun initializeViewBinding(view: View): ItemFileBinding = ItemFileBinding.bind(view)

}