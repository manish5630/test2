package com.example.profilecreation.ui.confirmation

import android.content.Context
import android.net.Uri
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.bumptech.glide.request.RequestOptions
import com.example.common.UIModel
import com.example.common.extenssion.addOnBackPressedCallback
import com.example.common.extenssion.collectWhileStarted
import com.example.common.extenssion.hideKeyboard
import com.example.common.extenssion.viewBinding
import com.example.domain.Portfolio
import com.example.profilecreation.App
import com.example.profilecreation.R
import com.example.profilecreation.databinding.FragmentConfirmationBinding
import com.example.profilecreation.ui.MainActivity

class ConfirmationFragment : Fragment(R.layout.fragment_confirmation) {

    companion object {
        fun newInstance() = ConfirmationFragment()
    }

    private val viewModel: ConfirmationViewModel by viewModels {
        ConfirmationViewModel.ConfirmationViewModelFactory(App.instance.userService)
    }

    private val binding by viewBinding(FragmentConfirmationBinding::bind)

    private lateinit var mainActivity: MainActivity

    override fun onAttach(context: Context) {
        super.onAttach(context)
        mainActivity = context as MainActivity
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        hideKeyboard()
        addOnBackPressedCallback(viewLifecycleOwner) { mainActivity.openSignUpFragment() }
        initView()
        observeViewModel()
    }

    private fun observeViewModel() {
        viewModel.uiModel.collectWhileStarted(viewLifecycleOwner) {
            showProgress(it is UIModel.Loading)
            when (it) {
                is UIModel.Data -> setUiData(it.data)
                else -> Unit
            }
        }
    }

    private fun initView() {
        binding.signInButton.setOnClickListener {
            Toast.makeText(
                requireContext(),
                "Sign in pressed",
                Toast.LENGTH_SHORT
            ).show()
        }
    }

    private fun setUiData(data: Portfolio) {
        with(binding) {
            titleTextView.isVisible = true
            descriptionTextView.isVisible = true
            signInButton.isVisible = true

            showAvatar(data.avatarUri)
            titleTextView.text = getString(R.string.confirmation_title, data.firstName)
            webSiteTextView.text = data.webSite
            firstNameTextView.text = data.firstName
            emailTextView.text = data.emailAddress
        }
    }

    private fun showAvatar(stringUri: String) {
        if (stringUri.isEmpty()) {
            binding.avatarImageView.isVisible = false
            return
        }
        Glide.with(requireActivity()).load(Uri.parse(stringUri)).fitCenter().apply(
            RequestOptions.bitmapTransform(
                RoundedCorners(
                    resources.getDimension(R.dimen.sign_up_avatar_placeholder_radius).toInt()
                )
            )
        ).into(binding.avatarImageView)
    }

    private fun showProgress(isVisible: Boolean) {
        binding.progressBar.isVisible = isVisible
        binding.disableView.isVisible = isVisible
    }
}