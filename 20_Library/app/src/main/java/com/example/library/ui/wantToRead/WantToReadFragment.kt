package com.example.library.ui.wantToRead

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.library.MainActivity
import com.example.library.R
import com.example.library.adapter.BookAdapter
import com.example.library.adapter.OnItemClickListener
import com.example.library.databinding.FragmentWantToReadBinding
import com.example.library.model.Status

class WantToReadFragment : Fragment(), OnItemClickListener {

    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: BookAdapter
    private lateinit var mainActivity: MainActivity
    private var _binding: FragmentWantToReadBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentWantToReadBinding.inflate(inflater, container, false)
        val root: View = binding.root
        if (context is MainActivity) {
            mainActivity = context as MainActivity
        }

        recyclerView = binding.root.findViewById(R.id.recycler_view)
        recyclerView.layoutManager = LinearLayoutManager(requireContext())
        adapter = BookAdapter(mainActivity.bookService.loadBooks(Status.WANT_TO_READ), this)
        recyclerView.adapter = adapter

        binding.fabAddBook.setOnClickListener {
            findNavController().navigate(R.id.action_add_book)
        }

        return root
    }

    override fun onItemClick(position: Int) {
        val bookId = adapter.getBookId(position)
        val bundle = Bundle()
        bundle.putInt("bookId", bookId)
        findNavController().navigate(R.id.action_add_book, bundle)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}