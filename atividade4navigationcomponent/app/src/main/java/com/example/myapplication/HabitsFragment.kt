package com.example.myapplication

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.myapplication.databinding.FragmentHabitsBinding

class HabitsFragment : Fragment(R.layout.fragment_habits) {

    private var _binding: FragmentHabitsBinding? = null
    private val binding get() = _binding!!

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        _binding = FragmentHabitsBinding.bind(view)

        binding.buttonViewHabit.setOnClickListener {

            val nomeDoHabito = "Ler"

            val action = HabitsFragmentDirections.actionHabitsFragmentToHabitDetailFragment(nomeDoHabito)

            findNavController().navigate(action)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}