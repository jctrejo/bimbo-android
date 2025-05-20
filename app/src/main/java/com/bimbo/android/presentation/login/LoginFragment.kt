package com.bimbo.android.presentation.login

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import com.bimbo.android.R
import com.bimbo.android.databinding.FragmentLoginBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class LoginFragment : Fragment() {

    private val viewModel: LoginViewModel by viewModels()
    private val binding by lazy { FragmentLoginBinding.inflate(layoutInflater) }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupView()
    }

    private fun setupView() {
        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.username.collect { name ->
                    binding.etUserName.setText(name ?: "")
                }
            }
        }

        binding.btnLogin.setOnClickListener {
            val name = binding.etUserName.text.toString().trim()
            if (name.isNotEmpty()) {
                viewModel.saveUsername(name)
                findNavController().navigate(R.id.action_loginFragment_to_pokemonListFragment)
            } else {
                Toast.makeText(requireContext(), "Ingresa tu nombre", Toast.LENGTH_SHORT).show()
            }
        }
    }
}