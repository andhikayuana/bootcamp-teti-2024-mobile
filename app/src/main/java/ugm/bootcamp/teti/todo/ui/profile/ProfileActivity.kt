package ugm.bootcamp.teti.todo.ui.profile

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import coil.load
import coil.transform.CircleCropTransformation
import ugm.bootcamp.teti.todo.TodoApp
import ugm.bootcamp.teti.todo.databinding.ActivityProfileBinding
import ugm.bootcamp.teti.todo.util.UiEffect

class ProfileActivity : AppCompatActivity() {

    private lateinit var binding: ActivityProfileBinding
    private lateinit var viewModel: ProfileViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityProfileBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        viewModel = ProfileViewModel(
            (application as TodoApp).appModule.provideAuthRepository()
        )
        viewModel.profileState.observe(this, Observer { state ->
            binding.apply {
                sivProfile.load(state.getImageUrl()) {
                    crossfade(true)
                    transformations(CircleCropTransformation())
                }
                tvName.text = state.getName()
                tvEmail.text = state.email
            }
        })
        viewModel.uiEffect.observe(this, Observer { effect ->
            when (effect) {
                is UiEffect.ShowToast -> Toast.makeText(
                    this,
                    effect.message,
                    Toast.LENGTH_SHORT
                ).show()

                else -> {}
            }
        })

        viewModel.onEvent(ProfileEvent.OnLoadProfile)

    }
}