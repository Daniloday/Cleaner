package com.missclickads.cleaner.ui.filemanager.items

import android.view.View
import androidx.appcompat.widget.AppCompatImageButton
import androidx.core.graphics.drawable.toDrawable
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
        file.let {
            println(it.type)
            when(it.type){
                "audio" -> viewBinding.icon.setImageResource(R.drawable.ic_audio_icon)
                "doc" -> viewBinding.icon.setImageResource(R.drawable.ic_documents_icon)
                else -> viewBinding.icon.setImageBitmap(it.image)
            }

        }
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