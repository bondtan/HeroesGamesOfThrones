package com.tbondarenko.heroesgamesofthrones.ui.listfragment

import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import androidx.core.view.MenuHost
import androidx.core.view.MenuProvider
import androidx.core.view.doOnPreDraw
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.navigation.findNavController
import androidx.navigation.fragment.FragmentNavigatorExtras
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.tbondarenko.heroesgamesofthrones.R
import com.tbondarenko.heroesgamesofthrones.databinding.HeroesListFragmentBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HeroesListFragment : Fragment() {

    private var _binding: HeroesListFragmentBinding? = null
    private val binding get() = checkNotNull(_binding)

    private val viewModel by viewModels<HeroesListViewModel>()

    private lateinit var recyclerView: RecyclerView
    private lateinit var heroesAdapter: HeroesAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = HeroesListFragmentBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        postponeEnterTransition()
        setMenu()
        setProgressBar()
        setRecyclerView(view)
        setError()
    }

    private fun setRecyclerView(view: View) {
        recyclerView = binding.heroRecyclerView
        recyclerView.layoutManager = LinearLayoutManager(requireContext())
        heroesAdapter = HeroesAdapter { heroDomain, imageView ->
            val action = HeroesListFragmentDirections
                .actionHeroesListFragmentToHeroDetailFragment(
                    fullName = heroDomain.fullName,
                    family = heroDomain.family,
                    title = heroDomain.title,
                    imageUrl = heroDomain.imageUrl
                )
            val extras = FragmentNavigatorExtras(imageView to "shared_image")
            view.findNavController().navigate(action, extras)
        }
        recyclerView.adapter = heroesAdapter
        viewModel.heroesList.observe(this.viewLifecycleOwner) {
            heroesAdapter.add(it)
            (view.parent as? ViewGroup)?.doOnPreDraw {
                startPostponedEnterTransition()
            }
        }
    }

    private fun setProgressBar() {
        viewModel.loading.observe(this.viewLifecycleOwner) {
            binding.progress.visibility = if (it) View.VISIBLE else View.GONE
        }
    }

    private fun setError() {
        viewModel.errorMassage.observe(this.viewLifecycleOwner) {
            MaterialAlertDialogBuilder(requireContext())
                .setTitle(getString(R.string.title_dialog_error))
                .setMessage(it)
                .show()
        }
    }

    private fun setMenu() {
        val menuHost: MenuHost = requireActivity()
        menuHost.addMenuProvider(object : MenuProvider {
            override fun onCreateMenu(menu: Menu, menuInflater: MenuInflater) {
                menuInflater.inflate(R.menu.menu_list_herous, menu)
            }

            override fun onMenuItemSelected(menuItem: MenuItem): Boolean {
                return when (menuItem.itemId) {
                    R.id.menu_update -> {
                        viewModel.refreshHeroes()
                        true
                    }
                    else -> false
                }
            }
        }, viewLifecycleOwner, Lifecycle.State.RESUMED)
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}