package com.example.library.ui.read

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import com.example.library.databinding.FragmentReadBinding

class ReadFragment : Fragment() {

private var _binding: FragmentReadBinding? = null
  private val binding get() = _binding!!

  override fun onCreateView(
    inflater: LayoutInflater,
    container: ViewGroup?,
    savedInstanceState: Bundle?
  ): View {
    _binding = FragmentReadBinding.inflate(inflater, container, false)
    val root: View = binding.root

    val textView: TextView = binding.textDashboard
    textView.text = "ToDo"
    return root
  }

override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}