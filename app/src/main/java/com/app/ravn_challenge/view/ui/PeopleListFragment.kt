package com.app.ravn_challenge.view.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.app.ravn_challenge.R
import com.app.ravn_challenge.view.adapter.PeopleListAdapter
import com.app.ravn_challenge.databinding.FragmentPeopleListBinding
import com.app.ravn_challenge.view.state.ViewState
import com.app.ravn_challenge.viewmodel.PeopleViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class PeopleListFragment : Fragment() {

    private lateinit var binding: FragmentPeopleListBinding
    private val viewModel by viewModels<PeopleViewModel>()
    private val peopleListAdapter by lazy { PeopleListAdapter() }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentPeopleListBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.peopleListRecycler.adapter = peopleListAdapter

        viewModel.getPeopleList()
        observeLiveData()
        
        peopleListAdapter.onItemClicked = { person ->
            person.let {
                if(!person.id.isNullOrBlank()){
                    findNavController().navigate(
                        PeopleListFragmentDirections.actionListPeopleFragmentToDetailPersonFragment(personId = person.id)
                    )
                }
            }
        }

        binding.swipeRefreshLayout.setOnRefreshListener {
            viewModel.getPeopleList()
        }
    }

    private fun observeLiveData() {
        viewModel.peopleList.observe(viewLifecycleOwner) { response ->
            when (response){
                is ViewState.Loading -> {
                    showDataWhenLoading()
                }
                is ViewState.Success -> {
                    if (response.value?.data?.allPeople?.people?.size == 0) {
                        showDataWhenListIsEmpty()
                    } else {
                        binding.peopleListRecycler.visibility = View.VISIBLE
                        binding.informationText.visibility = View.GONE
                    }
                    val results = response.value?.data?.allPeople?.people

                    peopleListAdapter.submitList(results)
                    binding.swipeRefreshLayout.isRefreshing = false

                }
                is ViewState.Error -> {
                    showDataWhenError()
                }
            }
        }
    }

    private fun showDataWhenLoading() {
        binding.peopleListRecycler.visibility = View.GONE
        binding.swipeRefreshLayout.isRefreshing = true
        binding.informationText.visibility = View.VISIBLE
        binding.informationText.text = getString(R.string.text_loading_data)
        binding.informationText.setTextColor(resources.getColor(R.color.text_light))
    }

    private fun showDataWhenListIsEmpty() {
        peopleListAdapter.submitList(emptyList())
        binding.swipeRefreshLayout.isRefreshing = false
        binding.peopleListRecycler.visibility = View.GONE
        binding.informationText.visibility = View.VISIBLE
        binding.informationText.text = getString(R.string.text_not_found_data)
        binding.informationText.setTextColor(resources.getColor(R.color.text_emphasis))
    }

    private fun showDataWhenError() {
        peopleListAdapter.submitList(emptyList())
        binding.swipeRefreshLayout.isRefreshing = false
        binding.peopleListRecycler.visibility = View.GONE
        binding.informationText.visibility = View.VISIBLE
        binding.informationText.text = getString(R.string.failed_data_text)
        binding.informationText.setTextColor(resources.getColor(R.color.text_emphasis))
    }
}