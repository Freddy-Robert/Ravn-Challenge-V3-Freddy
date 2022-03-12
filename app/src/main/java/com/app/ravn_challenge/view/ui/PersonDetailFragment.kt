package com.app.ravn_challenge.view.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.navigation.Navigation
import androidx.navigation.fragment.navArgs
import com.app.ravn_challenge.databinding.FragmentPersonDetailBinding
import com.app.ravn_challenge.view.state.ViewState
import com.app.ravn_challenge.viewmodel.PeopleViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class DetailPersonFragment : Fragment() {

    private lateinit var binding: FragmentPersonDetailBinding
    private val args: DetailPersonFragmentArgs by navArgs()
    private val viewModel by viewModels<PeopleViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentPersonDetailBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.getPersonById(args.personId)
        observeLiveData()

    }

    private fun observeLiveData() {
        viewModel.person.observe(viewLifecycleOwner) { response ->
            when (response) {
                is ViewState.Loading -> {
                    binding.progressBar.visibility = View.VISIBLE
                    binding.errorText.visibility = View.GONE
                }
                is ViewState.Success -> {
                    if (response.value?.data?.person == null) {
                        binding.progressBar.visibility = View.GONE
                        binding.errorText.visibility = View.VISIBLE
                    } else {
                        binding.personData = response.value.data
                        binding.progressBar.visibility = View.GONE
                        binding.errorText.visibility = View.GONE
                    }
                }
                is ViewState.Error -> {
                    binding.progressBar.visibility = View.GONE
                    binding.errorText.visibility = View.VISIBLE
                }
            }
        }
    }
}