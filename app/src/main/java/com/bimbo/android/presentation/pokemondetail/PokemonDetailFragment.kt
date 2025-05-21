package com.bimbo.android.presentation.pokemondetail

import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.bimbo.android.R
import com.bimbo.android.common.AppUiState
import com.bimbo.android.databinding.FragmentPokemonDetailBinding
import com.bimbo.android.utils.enum.PokemonType
import com.bimbo.android.utils.loader.LoadingDialog
import com.bumptech.glide.Glide
import dagger.hilt.android.AndroidEntryPoint

/**
 * Fragmento que muestra los detalles de un Pokémon específico.
 *
 * Utiliza Hilt para inyección de dependencias (@AndroidEntryPoint).
 * Observa los datos del ViewModel para actualizar la interfaz de usuario con la información del Pokémon.
 */
@AndroidEntryPoint
class PokemonDetailFragment : Fragment() {

    /**
     * Binding para acceder a las vistas del layout fragment_pokemon_detail.xml.
     * Se maneja con un backing property para evitar fugas de memoria.
     */
    private val binding by lazy { FragmentPokemonDetailBinding.inflate(layoutInflater) }

    /** ViewModel asociado que provee los detalles del Pokémon */
    private val viewModel: PokemonDetailViewModel by viewModels()

    /**
     * Diálogo personalizado para mostrar una pantalla de carga.
     */
    private lateinit var loader: LoadingDialog
    /**
     * Infla el layout del fragmento usando ViewBinding.
     *
     * @param inflater Inflater para inflar el layout
     * @param container Contenedor padre del fragmento
     * @param savedInstanceState Estado previo guardado
     * @return La vista raíz del fragmento
     */
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        return binding.root
    }

    /**
     * Se llama después de que la vista ha sido creada.
     * Obtiene el nombre del Pokémon desde los argumentos y observa los datos para actualizar la UI.
     *
     * @param view Vista creada
     * @param savedInstanceState Estado previo guardado
     */
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        loader = LoadingDialog(binding.root.context)
        init()
    }

    private fun init() {
        val pokemonName = arguments?.getString("pokemonName") ?: return
        observer()
        viewModel.loadPokemonDetail(pokemonName)
    }

    private fun observer() = with(binding) {
        viewModel.apply {
            pokemonDetail.observe(viewLifecycleOwner) { state ->
                when (state) {
                    is AppUiState.Loading -> {
                        loader.show()
                    }

                    is AppUiState.Error -> {
                        loader.dismiss()
                    }

                    is AppUiState.Success -> {
                        binding.tvName.text = state.data.name.replaceFirstChar { it.uppercase() }
                        Glide.with(requireActivity()).load(state.data.imageUrl)
                            .into(binding.ivPokemon)
                        binding.tvHeight.text =
                            getString(R.string.height_label, state.data.height.toString())
                        binding.tvWeight.text =
                            getString(R.string.weight_label, state.data.weight.toString())
                        binding.tvTypes.text =
                            getString(R.string.type_label, state.data.types.joinToString(", "))
                        setupView(state.data.types)
                        loader.hide()
                        loader.dismiss()
                    }

                    else -> {}
                }
            }
        }
    }

    /**
     * Configura dinámicamente la vista de tipos de Pokémon.
     * Crea TextViews con estilos específicos para cada tipo y los añade al contenedor.
     *
     * @param types Lista de tipos del Pokémon
     */
    private fun setupView(types: List<String> = emptyList()) {
        types.forEach { type ->
            val typeView = TextView(binding.root.context).apply {
                text = type.replaceFirstChar { it.uppercase() }
                setTextColor(Color.WHITE)
                setPadding(12, 6, 12, 6)
                textSize = 14f
                background = ContextCompat.getDrawable(context, getTypeBackground(type))
                val params = LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.MATCH_PARENT,
                    LinearLayout.LayoutParams.WRAP_CONTENT
                )
                params.setMargins(20, 5, 20, 5)
                layoutParams = params
            }
            binding.container.addView(typeView)
        }
    }

    /**
     * Obtiene el recurso drawable correspondiente al fondo según el tipo de Pokémon.
     *
     * @param type Tipo de Pokémon
     * @return ID del recurso drawable para el fondo
     */
    private fun getTypeBackground(type: String): Int {
        val pokemonType = PokemonType.from(type)
        return pokemonType.drawableRes
    }
}
