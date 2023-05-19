package com.example.library.ui.wantToRead

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import com.example.library.databinding.FragmentWantToReadBinding

class WantToReadFragment : Fragment() {

private var _binding: FragmentWantToReadBinding? = null
  private val binding get() = _binding!!

  override fun onCreateView(
    inflater: LayoutInflater,
    container: ViewGroup?,
    savedInstanceState: Bundle?
  ): View {
    _binding = FragmentWantToReadBinding.inflate(inflater, container, false)
    val root: View = binding.root

    val textView: TextView = binding.textNotifications
    textView.text = "ToDo"
    return root
  }

override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}