package com.example.profilecreation.ui.signUp

import android.content.ContentValues
import android.content.Context
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.view.View
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.view.isVisible
import androidx.core.widget.doAfterTextChanged
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.bumptech.glide.request.RequestOptions
import com.example.common.UIModel
import com.example.common.extenssion.addOnBackPressedCallback
import com.example.common.extenssion.collectWhileStarted
import com.example.common.extenssion.viewBinding
import com.example.common.showToolTip
import com.example.domain.Portfolio
import com.example.profilecreation.App
import com.example.profilecreation.R
import com.example.profilecreation.databinding.FragmentSignUpBinding
import com.example.profilecreation.ui.MainActivity

class SignUpFragment : Fragment(R.layout.fragment_sign_up) {

    companion object {
        fun newInstance() = SignUpFragment()
    }

    private val viewModel: SignUpViewModel by viewModels { SignUpViewModelFactory(App.instance.userService) }

    private val binding by viewBinding(FragmentSignUpBinding::bind)

    private lateinit var mainActivity: MainActivity

    private lateinit var avatarUri: Uri

    private val takePicture =
        registerForActivityResult(ActivityResultContracts.TakePicture()) { success: Boolean ->
            if (success) {
                viewModel.updateAvatar(avatarUri = avatarUri.toString())
            } else {
                Toast.makeText(
                    requireContext(),
                    R.string.camera_was_close,
                    Toast.LENGTH_SHORT
                ).show()
            }
        }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        mainActivity = context as MainActivity
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        addOnBackPressedCallback(viewLifecycleOwner) { mainActivity.finish() }
        prepareAvatarUri()
        initView()
        observeViewModel()
    }

    private fun prepareAvatarUri() {
        avatarUri = mainActivity.contentResolver.insert(
            MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
            ContentValues()
        ) ?: error("Uri is null")
    }

    private fun initView() {
        with(binding) {
            with(viewModel) {
                firstNameEditText.doAfterTextChanged { updateFirstName(it.toString()) }
                emailAddressEditText.doAfterTextChanged { updateEmailAddress(it.toString()) }
                passwordEditText.doAfterTextChanged { updatePassword(it.toString()) }
                websiteEditText.doAfterTextChanged { updateWebSite(it.toString()) }
            }

            avatarPlaceHolderTextView.setOnClickListener { takePicture.launch(avatarUri) }
            submitButton.setOnClickListener { viewModel.submit() }
        }
    }

    private fun observeViewModel() {
        with(viewModel) {
            uiModel.collectWhileStarted(viewLifecycleOwner) {
                showProgress(it is UIModel.Loading)
                when (it) {
                    is UIModel.Data -> updateUi(it.data)
                    else -> Unit
                }
            }
            validationError.collectWhileStarted(viewLifecycleOwner) { showError(it) }
            command.collectWhileStarted(viewLifecycleOwner) {
                when (it) {
                    is Command.OpenConfirmationPage -> {
                        mainActivity.openConfirmationFragment()
                    }
                }
            }
        }
    }

    private fun updateUi(data: Portfolio) {
        with(binding) {
            showAvatar(data.avatarUri)
            if (firstNameEditText.text.toString() != data.firstName) {
                firstNameEditText.setText(data.firstName)
            }
            if (emailAddressEditText.text.toString() != data.emailAddress) {
                emailAddressEditText.setText(data.emailAddress)
            }
            if (passwordEditText.text.toString() != data.password) {
                passwordEditText.setText(data.password)
            }
            if (websiteEditText.text.toString() != data.webSite) {
                websiteEditText.setText(data.webSite)
            }
        }
    }

    private fun showAvatar(stringUri: String) {
        if (stringUri.isEmpty()) return

        with(binding) {
            avatarImageView.isVisible = true
            avatarPlaceHolderTextView.isVisible = false

            Glide.with(requireActivity()).load(Uri.parse(stringUri)).fitCenter().apply(
                RequestOptions.bitmapTransform(
                    RoundedCorners(
                        resources.getDimension(R.dimen.sign_up_avatar_placeholder_radius)
                            .toInt()
                    )
                )
            ).into(avatarImageView)
        }
    }

    private fun showError(validationError: ValidationError) {
        with(binding) {
            val messageResId: Int
            val view: View

            when (validationError) {
                ValidationError.InvalidEmailAddress -> {
                    messageResId = R.string.error_email_address
                    view = emailAddressEditText
                }
                ValidationError.InvalidInvalidWebSite -> {
                    messageResId = R.string.error_website
                    view = websiteEditText
                }
                ValidationError.RequiredEmailAddress -> {
                    messageResId = R.string.error_required
                    view = emailAddressEditText
                }
                ValidationError.RequiredPassword -> {
                    messageResId = R.string.error_required
                    view = passwordEditText
                }
                ValidationError.None -> return
            }

            view.setBackgroundResource(R.drawable.shape_edit_text_background_error)
            showToolTip(requireContext(), view, getString(messageResId)) {
                view.setBackgroundResource(R.drawable.shape_edit_text_background)
            }
        }
    }

    private fun showProgress(isVisible: Boolean) {
        binding.progressBar.isVisible = isVisible
        binding.disableView.isVisible = isVisible
    }
}