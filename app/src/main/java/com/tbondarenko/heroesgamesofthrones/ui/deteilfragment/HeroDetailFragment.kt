package com.tbondarenko.heroesgamesofthrones.ui.deteilfragment

import android.graphics.Color
import android.os.Bundle
import android.transition.TransitionInflater
import android.transition.TransitionManager
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.navArgs
import com.google.android.material.transition.platform.MaterialArcMotion
import com.google.android.material.transition.platform.MaterialContainerTransform
import com.tbondarenko.heroesgamesofthrones.databinding.HeroDetailFragmentBinding
import com.tbondarenko.heroesgamesofthrones.glide.loadCenterInside
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HeroDetailFragment : Fragment() {

    private var _binding: HeroDetailFragmentBinding? = null
    private val binding get() = checkNotNull(_binding)

    private val viewModel by viewModels<HeroDetailViewModel>()

    private val navigationArgs: HeroDetailFragmentArgs by navArgs()
    private var fullNameHeroText: String = ""
    private var familyHeroText: String = ""
    private var titleHeroText: String = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val animation = TransitionInflater.from(requireContext())
            .inflateTransition(android.R.transition.move)
        sharedElementEnterTransition = animation
        sharedElementReturnTransition = animation
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = HeroDetailFragmentBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        fullNameHeroText = navigationArgs.fullName
        familyHeroText = navigationArgs.family
        titleHeroText = navigationArgs.title
        setData()
    }

    private fun setData() {
        with(binding) {
            fullName.text = fullNameHeroText
            family.text = familyHeroText
            title.text = titleHeroText
            imageHeroDetail.loadCenterInside(navigationArgs.imageUrl)
            floatingActionButton.setOnClickListener {
                setTransformViewToView(floatingActionButton, detailCardView, root)
                detailLinearInformation.visibility = View.GONE
                setDataCardView()
            }
            detailCardView.setOnClickListener {
                setTransformViewToView(detailCardView, floatingActionButton, root)
                detailLinearInformation.visibility = View.VISIBLE
            }
        }
    }

    private fun setTransformViewToView(start: View, end: View, root: ViewGroup) {
        val transform = MaterialContainerTransform().apply {
            startView = start
            endView = end
            addTarget(endView)
            duration = 1000
            pathMotion = MaterialArcMotion()
            scrimColor = Color.TRANSPARENT
        }
        TransitionManager.beginDelayedTransition(root, transform)
        start.visibility = View.GONE
        end.visibility = View.VISIBLE
    }

    private fun setDataCardView() {
        with(binding) {
            fullNameCv.text = fullNameHeroText
            familyCv.text = familyHeroText
            titleCv.text = titleHeroText
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}

