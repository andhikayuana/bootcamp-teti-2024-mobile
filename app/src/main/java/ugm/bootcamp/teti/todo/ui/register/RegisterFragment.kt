package ugm.bootcamp.teti.todo.ui.register

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import ugm.bootcamp.teti.todo.TodoApp
import ugm.bootcamp.teti.todo.databinding.FragmentRegisterBinding
import ugm.bootcamp.teti.todo.util.UiEffect

class RegisterFragment : Fragment() {


    private lateinit var viewModel: RegisterViewModel
    private var _binding: FragmentRegisterBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentRegisterBinding.inflate(inflater, container, false)
        val view = binding.root
        return view
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val appModule = (requireActivity().application as TodoApp).appModule
        viewModel = RegisterViewModel(
            appModule.provideAuthRepository(),
            appModule.provideEmailValidator(),
            appModule.providePasswordValidator(),
            appModule.providePasswordConfirmValidator()
        )

        viewModel.registerState.observe(this, Observer { state ->
            binding.apply {
                tilEmail.isErrorEnabled = state.hasEmailError()
                tilEmail.error = state.emailErrorMessage
                tilPassword.isErrorEnabled = state.hasPasswordError()
                tilPassword.error = state.passwordErrorMessage
                tilPasswordConfirm.isErrorEnabled = state.hasPasswordConfirmError()
                tilPasswordConfirm.error = state.passwordConfirmErrorMessage
            }
        })
        viewModel.uiEffect.observe(this, Observer { effect ->
            when (effect) {
                is UiEffect.Navigate -> findNavController().navigate(effect.directions)
                UiEffect.PopBackStack -> findNavController().popBackStack()
                is UiEffect.ShowToast -> Toast.makeText(
                    requireContext(),
                    effect.message,
                    Toast.LENGTH_SHORT
                ).show()

                else -> {}
            }
        })
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.apply {
            tieEmail.addTextChangedListener {
                viewModel.onEvent(RegisterEvent.OnEmailChange(it.toString()))
            }
            tiePassword.addTextChangedListener {
                viewModel.onEvent(RegisterEvent.OnPasswordChange(it.toString()))
            }
            tiePasswordConfirm.addTextChangedListener {
                viewModel.onEvent(RegisterEvent.OnPasswordConfirmChange(it.toString()))
            }
            btnRegister.setOnClickListener {
                viewModel.onEvent(RegisterEvent.OnRegisterClick)
            }
        }

    }

}