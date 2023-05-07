package com.example.genesis.ui.book

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.viewpager2.widget.MarginPageTransformer
import androidx.viewpager2.widget.ViewPager2
import com.example.genesis.R
import com.example.genesis.databinding.BookFragmentBinding
import com.example.genesis.remote.BookDataModel
import com.example.genesis.ui.adapter.ViewPagerAdapter
import com.example.genesis.ui.book.bottomSheet.CustomBottomSheetDialogFragment
import com.example.genesis.util.HorizontalMarginItemDecoration
import com.example.genesis.util.State
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import java.lang.Math.abs

@AndroidEntryPoint
class BookFragment : Fragment() {

    private lateinit var binding: BookFragmentBinding

    private val mainViewModel: BookViewModel by viewModels()
    var genre = ""
    var position = 0
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = BookFragmentBinding.inflate(inflater, container, false)


        return binding.root
    }

    override fun onStart() {
        super.onStart()

        genre = this.requireArguments().getString("genre").toString()
        position = this.requireArguments().getInt("position")

        lifecycleScope.launch {
            mainViewModel.screenState.collect {
                when (it) {
                    is State.Content -> {
                        binding.viewPager.apply {
                            setupViewPager2(it.data.bookModel.filter { it.genre == genre })
                            binding.viewPager.setCurrentItem(position,true)
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


    private fun setupViewPager2(list: List<BookDataModel>) {
        // Set adapter to viewPager.
        binding.viewPager.adapter = ViewPagerAdapter(list)
        val offsetPx =
            resources.getDimension(R.dimen.dp_30).toInt()
        binding.viewPager.setPadding(offsetPx * 2, 0, offsetPx * 2, 0)

        //increase this offset to increase distance between 2 items
        val pageMarginPx =
            resources.getDimension(R.dimen.dp_5).toInt()
        val marginTransformer = MarginPageTransformer(pageMarginPx)
        binding.viewPager.setPageTransformer(marginTransformer)

        binding.viewPager.apply {
            clipToPadding = false   // allow full width shown with padding
            clipChildren = false    // allow left/right item is not clipped
            offscreenPageLimit = 3

            useDecoration()
            overScrollMode = View.OVER_SCROLL_NEVER;
            registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {
                override fun onPageSelected(position: Int) {
                    super.onPageSelected(position)
                    showSheet(list[position].name)
                }
            })

        }

    }

    private fun useDecoration() {
        val nextItemVisiblePx = resources.getDimension(R.dimen.viewpager_next_item_visible)
        val currentItemHorizontalMarginPx =
            resources.getDimension(R.dimen.viewpager_current_item_horizontal_margin)
        val pageTranslationX = nextItemVisiblePx + currentItemHorizontalMarginPx
        val pageTransformer = ViewPager2.PageTransformer { page: View, position: Float ->
            page.translationX = -pageTranslationX * position
            // Next line scales the item's height. You can remove it if you don't want this effect
            page.scaleY = 1 - (0.25f * abs(position))
            // If you want a fading effect uncomment the next line:
            // page.alpha = 0.25f + (1 - abs(position))
        }
        binding.viewPager.setPageTransformer(pageTransformer)

// The ItemDecoration gives the current (centered) item horizontal margin so that
// it doesn't occupy the whole screen width. Without it the items overlap
        val itemDecoration = HorizontalMarginItemDecoration(
            requireContext(),
            R.dimen.viewpager_current_item_horizontal_margin
        )
        binding.viewPager.addItemDecoration(itemDecoration)
    }

    fun showSheet(page: String) {
        val bundle = Bundle()
        bundle.putString("message", page)
        CustomBottomSheetDialogFragment().apply {
            arguments = bundle
            show(this@BookFragment.childFragmentManager, CustomBottomSheetDialogFragment.TAG)
        }
    }
}