package com.example.harrisonwjy.charitree.onboarding

import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.harrisonwjy.charitree.R
import com.example.harrisonwjy.charitree.viewmodel.AuthViewModel
import kotlinx.android.synthetic.main.fragment_getstarted.*
import org.koin.android.viewmodel.ext.android.viewModel


class GetStartedFragment : Fragment() {
    // TODO: Rename and change types of parameters
    val myViewModel: AuthViewModel by viewModel()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_getstarted, container, false)
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        //myViewModel.sayHello()


        //loginButton.text = myViewModel.authenticate(email.text.toString(),password.text.toString())
//            myViewModel.getProjectDetail().observe(this, android.arch.lifecycle.Observer {
//                registerButton.text = it
//            })

        getStartedButton.setOnClickListener {
            val fragmentManager = fragmentManager
            val fragmentTransaction = fragmentManager!!.beginTransaction()
            fragmentTransaction.replace(R.id.fragment_frame, LoginFragment.newInstance())
                    .addToBackStack("asd")
            fragmentTransaction.commit()
        }
    }



    companion object {
        fun newInstance(): GetStartedFragment {
            return GetStartedFragment()
        }

    }
}
