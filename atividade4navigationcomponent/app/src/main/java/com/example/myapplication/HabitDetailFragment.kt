package com.example.myapplication

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.navArgs
import com.example.myapplication.databinding.FragmentHabitDetailBinding

class HabitDetailFragment : Fragment(R.layout.fragment_habit_detail) {

    private var _binding: FragmentHabitDetailBinding? = null
    private val binding get() = _binding!!

    private val args: HabitDetailFragmentArgs by navArgs()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        _binding = FragmentHabitDetailBinding.bind(view)

        val nomeDoHabito = args.habitName

        binding.tvHabitName.text = "Hábito: $nomeDoHabito"
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}