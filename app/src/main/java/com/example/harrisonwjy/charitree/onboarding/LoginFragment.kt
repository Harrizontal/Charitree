package com.example.harrisonwjy.charitree.onboarding

import android.os.Bundle
import android.support.v4.app.Fragment
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.harrisonwjy.charitree.R
import com.example.harrisonwjy.charitree.UserViewModel
import kotlinx.android.synthetic.main.fragment_login.*
import org.koin.android.viewmodel.ext.android.viewModel
import android.content.Context.MODE_PRIVATE
import com.example.harrisonwjy.charitree.MainActivity
import android.content.Intent
import android.support.design.widget.TextInputLayout
import android.view.WindowManager
import android.widget.EditText
import com.example.harrisonwjy.charitree.helper.InputValidateShowError
import com.example.harrisonwjy.charitree.helper.Validation


// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Activities that contain this fragment must implement the
 * [LoginFragment.OnFragmentInteractionListener] interface
 * to handle interaction events.
 * Use the [LoginFragment.newInstance] factory method to
 * create an instance of this fragment.
 *
 */
class LoginFragment : Fragment(),Validation {
    // TODO: Rename and change types of parameters
    val myViewModel: UserViewModel by viewModel()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_login, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        myViewModel.sayHello()

        input_email.addTextChangedListener(InputValidateShowError(input_email,layout_email,getString(R.string.error_message_email)))
        input_password.addTextChangedListener(InputValidateShowError(input_password,layout_password,getString(R.string.error_message_password)))

        loginButton.setOnClickListener{
            val getEmailAddress = input_email.text.toString()
            val getPassword = input_password.text.toString()

            isValidEmail(input_email,layout_email)
            isValidInput(input_password,layout_password,R.string.error_message_password)


            myViewModel.authenticate(getEmailAddress,getPassword).observe(this,android.arch.lifecycle.Observer {
                Log.e("LoginFragment","asdasdasdasdasasd "+ it?.user_token)
                val editor = activity?.getSharedPreferences("PREFERENCE", MODE_PRIVATE)?.edit()
                editor?.putString("token", it?.user_token)
                editor?.apply()

                // bad code
                if(it?.user_token != null){
                    val intent = Intent(activity, MainActivity::class.java)
                    activity?.startActivity(intent)
                    activity?.finish()
                }
            })
            //loginButton.text = myViewModel.authenticate(email.text.toString(),password.text.toString())
//            myViewModel.getProjectDetail().observe(this, android.arch.lifecycle.Observer {
//                registerButton.text = it
//            })
        }

        registerButton.setOnClickListener {
            val fragmentManager = fragmentManager
            val fragmentTransaction = fragmentManager!!.beginTransaction()
            fragmentTransaction.replace(R.id.fragment_frame, RegisterFragment.newInstance())
                    .addToBackStack("asd")
            fragmentTransaction.commit()
        }
    }

    private fun isValidEmail(textField: EditText, inputLayout: TextInputLayout) : Boolean{

        if (android.util.Patterns.EMAIL_ADDRESS.matcher(textField.text.toString()).matches()){
            Log.e("LoginFragment","Email is valid")
            inputLayout.isErrorEnabled = false
        }else{
            Log.e("LoginFragment","Email is not valid")
            inputLayout.error = getString(R.string.error_message_email)
            requestFocus(textField)
            return false
        }
        return true
    }

    // just check valid is empty
    private fun isValidInput(textField: EditText, inputLayout: TextInputLayout,message: Int): Boolean{
        if(textField.text.toString().isNotEmpty()){
            Log.e("LoginFragment","Password is not empty")
            inputLayout.isErrorEnabled = false
        }else{
            Log.e("LoginFragment","Password is empty")
            inputLayout.error = getString(message)
            requestFocus(textField)
            return false
        }
        return true
    }


    private fun requestFocus(view: View) {
        if (view.requestFocus()) {
            activity!!.window.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_VISIBLE)
        }
    }


    companion object {
        fun newInstance(): LoginFragment {
            return LoginFragment()
        }


    }

}

