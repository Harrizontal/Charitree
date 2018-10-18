package com.example.harrisonwjy.charitree

import android.os.Bundle
import android.support.v4.app.Fragment
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import com.example.harrisonwjy.charitree.onboarding.LoginFragment
import kotlinx.android.synthetic.main.fragment_register.*

// TODO: Rename parameter arguments, choose names that match

/**
 * A simple [Fragment] subclass.
 * Activities that contain this fragment must implement the
 * [TreeFragment.OnFragmentInteractionListener] interface
 * to handle interaction events.
 * Use the [TreeFragment.newInstance] factory method to
 * create an instance of this fragment.
 *
 */
class TreeFragment : Fragment() {


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_tree, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
    }

    companion object {
        fun newInstance(): TreeFragment {
            return TreeFragment()
        }

    }
}
