package com.example.harrisonwjy.charitree.views.onboarding

import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.harrisonwjy.charitree.R
import com.example.harrisonwjy.charitree.viewmodel.UserViewModel
import kotlinx.android.synthetic.main.fragment_success_register.*
import org.koin.android.viewmodel.ext.android.viewModel


class SuccessRegisterFragment : Fragment() {
    // TODO: Rename and change types of parameters
    val myViewModel: UserViewModel by viewModel()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_success_register, container, false)
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        //myViewModel.sayHello()


        //loginButton.text = myViewModel.authenticate(email.text.toString(),password.text.toString())
//            myViewModel.getProjectDetail().observe(this, android.arch.lifecycle.Observer {
//                registerButton.text = it
//            })

        goBackToLoginButton.setOnClickListener {
//            val fm = fragmentManager
//            if (fm!!.backStackEntryCount > 0) {
//                Log.i("RegisterFragment", "popping backstack")
//                fm.popBackStack()
//            } else {
//                Log.i("RegisterFragment", "nothing on backstack, calling super")
//                val fragmentTransaction = fragmentManager!!.beginTransaction()
//                fragmentTransaction.replace(R.id.fragment_frame, LoginFragment.newInstance())
//                        .addToBackStack("OnboardingStack")
//                fragmentTransaction.commit()
//            }

            fragmentManager?.popBackStack("RegisterFragment", FragmentManager.POP_BACK_STACK_INCLUSIVE)

//            val fragment = fragmentManager?.findFragmentByTag("RegisterFragment")
//            if (fragment != null)
//                fragmentManager?.beginTransaction()?.remove(fragment)?.commit()
        }
    }


    fun onBackPressed() {
        fragmentManager?.popBackStack("RegisterFragment", FragmentManager.POP_BACK_STACK_INCLUSIVE);
    }

    companion object {
        fun newInstance(): SuccessRegisterFragment {
            return SuccessRegisterFragment()
        }

    }
}
