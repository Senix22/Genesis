package com.example.genesis.ui.library

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.viewpager.widget.ViewPager.OnPageChangeListener
import androidx.viewpager.widget.ViewPager.SCROLL_STATE_IDLE
import com.example.genesis.R
import com.example.genesis.databinding.LibraryFragmentBinding
import com.example.genesis.ui.adapter.GenreAdapter
import com.example.genesis.ui.adapter.ImageSlideAdapter
import com.example.genesis.ui.book.BookFragment
import com.example.genesis.util.State
import com.example.genesis.util.prepareForTwoWayPaging
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch


@AndroidEntryPoint
class LibraryFragment : Fragment() {

    private val mainViewModel: LibraryViewModel by viewModels()
    private lateinit var binding: LibraryFragmentBinding


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = LibraryFragmentBinding.inflate(inflater, container, false)
        return binding.root
    }


    override fun onStart() {
        super.onStart()

        lifecycleScope.launch {
            mainViewModel.screenState.collect {
                when (it) {
                    is State.Content -> {
                        binding.rvSubject.apply {
                            layoutManager = LinearLayoutManager(requireContext())
                            it.data.bookModel.forEach {
                                mainViewModel.list.add(it.genre)
                            }

                            binding.viewpager.apply {
                                adapter = ImageSlideAdapter(requireContext(), it.data.slides.prepareForTwoWayPaging())
                                addOnPageChangeListener(object : OnPageChangeListener {

                                    override fun onPageScrolled(
                                        position: Int,
                                        positionOffset: Float,
                                        positionOffsetPixels: Int
                                    ) {
                                        if (positionOffsetPixels != 0) {
                                            return
                                        }
                                        when (position) {
                                            0 -> setCurrentItem((adapter as ImageSlideAdapter).count - 2, false)
                                            (adapter as ImageSlideAdapter).count - 1 -> setCurrentItem(1, false)
                                        }

                                    }

                                    override fun onPageSelected(position: Int) {

                                    }

                                    override fun onPageScrollStateChanged(state: Int) {
                                        if (state == SCROLL_STATE_IDLE) {
                                            val curr = currentItem;
                                            val lastReal = (adapter as ImageSlideAdapter).count - 2;
                                            if (curr == 0) {
                                                setCurrentItem(lastReal, false);
                                            } else if (curr > lastReal) {
                                                setCurrentItem(0, false);
                                            }
                                        }
                                    }

                                })
                            }

                            adapter =
                                GenreAdapter(mainViewModel.list, it.data.bookModel) { genre ,position ->
                                    openBook( genre,position)
                                }
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


    private fun openBook(genre: String, position : Int){
        val bundle = Bundle()
        bundle.putString("genre", genre)
        bundle.putInt("position", position)

        val transaction = this.parentFragmentManager.beginTransaction()
        val frag2 = BookFragment()
        frag2.arguments = bundle

        transaction.replace(R.id.fragment_container_view, frag2)
        transaction.addToBackStack(null)
        transaction.commit()
    }
}

