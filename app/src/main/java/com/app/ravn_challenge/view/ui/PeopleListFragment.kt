package com.app.ravn_challenge.view.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
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
    }

    private fun observeLiveData() {
        viewModel.peopleList.observe(viewLifecycleOwner) { response ->
            when (response){
                is ViewState.Loading -> {
                    binding.peopleListRecycler.visibility = View.GONE
                }
                is ViewState.Success -> {
                    if (response.value?.data?.allPeople?.people?.size == 0) {
                        peopleListAdapter.submitList(emptyList())
                        binding.progressBar.visibility = View.GONE
                        binding.peopleListRecycler.visibility = View.GONE
                        binding.noInformationText.visibility = View.VISIBLE
                    } else {
                        binding.peopleListRecycler.visibility = View.VISIBLE
                        binding.noInformationText.visibility = View.GONE
                    }
                    val results = response.value?.data?.allPeople?.people

                    peopleListAdapter.submitList(results)
                    binding.progressBar.visibility = View.GONE

                }
                is ViewState.Error -> {
                    peopleListAdapter.submitList(emptyList())
                    binding.progressBar.visibility = View.GONE
                    binding.peopleListRecycler.visibility = View.GONE
                    binding.noInformationText.visibility = View.VISIBLE
                }
            }
        }
    }
}