package com.example.genesis.ui.book.bottomSheet

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.genesis.databinding.BottomSheetLayoutBinding
import com.example.genesis.ui.adapter.YouAlsoLikeAdapter
import com.example.genesis.util.State
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class CustomBottomSheetDialogFragment : BottomSheetDialogFragment() {
    private val mainViewModel: BottomSheetViewModel by viewModels()

    companion object {

        const val TAG = "CustomBottomSheetDialogFragment"

    }

    var myValue = ""

    private lateinit var binding: BottomSheetLayoutBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = BottomSheetLayoutBinding.inflate(inflater, container, false)
        myValue = this.requireArguments().getString("message").toString()
        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        lifecycleScope.launch {
            mainViewModel.screenState.collect { model ->
                when (model) {
                    is State.Content -> {
                        model.data.bookModel.forEach {
                            if (it.name == myValue) {
                                binding.readers.text = it.views
                                binding.likes.text = it.likes
                                binding.quotes.text = it.quotes
                            }
                        }
                        binding.alsoLikeRC.apply {
                            layoutManager = LinearLayoutManager(
                                requireContext(),
                                LinearLayoutManager.HORIZONTAL,
                                false
                            )

                            val filtered = model.data.bookModel.filter { it.id == model.data.slides[0].id || it.id == model.data.slides[1].id || it.id == model.data.slides[2].id }

                            adapter = YouAlsoLikeAdapter(filtered)
                        }
                    }
                    State.Empty -> {}
                    is State.Error -> {

                    }
                    State.Loading -> {}
                }
            }

        }

    }
}