package com.bimbo.android.presentation.login;


import static com.bimbo.android.common.Constants.EMPTY;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.fragment.NavHostFragment;

import com.bimbo.android.R;
import com.bimbo.android.databinding.FragmentLoginBinding;
import com.bimbo.android.common.utiljava.BooleanExtension;

import dagger.hilt.android.AndroidEntryPoint;

/**
 * Fragmento encargado de manejar la pantalla de login de la aplicación.
 * <p>
 * Utiliza Hilt para inyección de dependencias (@AndroidEntryPoint).
 * Gestiona la interfaz de usuario para que el usuario ingrese sus credenciales,
 * valida los datos ingresados y navega a la lista de Pokémon si el login es exitoso.
 * </p>
 */
@AndroidEntryPoint
public class LoginFragment extends Fragment {

    /**
     * Binding para acceder a las vistas del layout fragment_login.xml
     */
    private FragmentLoginBinding binding;

    /**
     * ViewModel asociado a este fragmento para manejar la lógica de negocio
     */
    private LoginViewModel viewModel;

    /**
     * Infla el layout del fragmento usando ViewBinding.
     *
     * @param inflater           Inflater para inflar el layout
     * @param container          Contenedor padre del fragmento
     * @param savedInstanceState Estado previo guardado
     * @return La vista raíz del fragmento
     */
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        binding = FragmentLoginBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    /**
     * Se llama después de que la vista ha sido creada.
     * Inicializa el ViewModel y configura la interfaz de usuario.
     *
     * @param view               Vista creada
     * @param savedInstanceState Estado previo guardado
     */
    @Override
    public void onViewCreated(@NonNull View view,
                              @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        viewModel = new ViewModelProvider(this).get(LoginViewModel.class);

        setupView();
    }

    /**
     * Configura la vista: muestra el nombre de usuario si ya está logueado,
     * observa cambios en el nombre de usuario y configura el botón de login.
     */
    private void setupView() {
        if (viewModel.getIsLogin()) {
            NavHostFragment.findNavController(this)
                    .navigate(R.id.action_loginFragment_to_pokemonListFragment);
        }

        binding.tvAdmin.setText(getString(R.string.admin));

        // Observa el LiveData del nombre de usuario para actualizar el campo de texto
        viewModel.getUsernameLiveData().observe(getViewLifecycleOwner(),
                name -> binding.etUsername.setText(name != null ? name : EMPTY));

        // Configura el listener del botón login
        binding.btnLogin.setOnClickListener(v -> {
            String username = binding.etUsername.getText().toString().trim();
            String password = binding.etPassword.getText().toString();
            validateUser(username, password);
        });
    }

    /**
     * Valida las credenciales ingresadas por el usuario.
     * Muestra mensajes de error en caso de datos inválidos,
     * y navega a la siguiente pantalla si el login es exitoso.
     *
     * @param username Nombre de usuario ingresado
     * @param password Contraseña ingresada
     */
    private void validateUser(String username, String password) {
        binding.tvError.setVisibility(View.GONE);

        if (!BooleanExtension.isValidEmail(username)) {
            binding.tvError.setText(getString(R.string.error_invalid_email));
            binding.tvError.setVisibility(View.VISIBLE);
        } else if (!BooleanExtension.isValidPassword(password)) {
            binding.tvError.setText(getString(R.string.error_invalid_password));
            binding.tvError.setVisibility(View.VISIBLE);
        } else if (BooleanExtension.isValidCredentials(username, password)) {
            viewModel.saveLogin(true);
            viewModel.saveUsername(username);
            NavHostFragment.findNavController(this)
                    .navigate(R.id.action_loginFragment_to_pokemonListFragment);
        } else {
            binding.tvError.setText(getString(R.string.error_invalid_credentials));
            binding.tvError.setVisibility(View.VISIBLE);
        }
    }

    /**
     * Limpia el binding para evitar fugas de memoria cuando la vista es destruida.
     */
    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}
