package ugm.bootcamp.teti.todo.ui.login

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
import ugm.bootcamp.teti.todo.databinding.FragmentLoginBinding
import ugm.bootcamp.teti.todo.util.UiEffect

class LoginFragment : Fragment() {

    private lateinit var viewModel: LoginViewModel
    private var _binding: FragmentLoginBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentLoginBinding.inflate(inflater, container, false)
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
        viewModel = LoginViewModel(
            appModule.provideAuthRepository(),
            appModule.provideEmailValidator(),
            appModule.providePasswordValidator(),
        )

        viewModel.loginState.observe(this, Observer { state ->
            binding.apply {
                tilEmail.isErrorEnabled = state.hasEmailError()
                tilEmail.error = state.emailErrorMessage
                tilPassword.isErrorEnabled = state.hasPasswordError()
                tilPassword.error = state.passwordErrorMessage
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

        binding.tieEmail.addTextChangedListener {
            viewModel.onEvent(LoginEvent.OnEmailChange(it.toString()))
        }
        binding.tiePassword.addTextChangedListener {
            viewModel.onEvent(LoginEvent.OnPasswordChange(it.toString()))
        }
        binding.btnLogin.setOnClickListener {
            viewModel.onEvent(LoginEvent.OnLoginClick)
        }
        binding.btnRegister.setOnClickListener {
            viewModel.onEvent(LoginEvent.OnGotoRegisterClick)
        }
    }

}