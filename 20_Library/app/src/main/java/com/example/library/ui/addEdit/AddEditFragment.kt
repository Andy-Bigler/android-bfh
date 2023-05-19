package com.example.library.ui.addEdit

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.NavHostFragment
import com.example.library.MainActivity
import com.example.library.databinding.FragmentAddEditBinding
import com.example.library.model.Book


class AddEditFragment : Fragment() {

    private lateinit var mainActivity: MainActivity
    private var _binding: FragmentAddEditBinding? = null
    private val binding get() = _binding!!
    private var bookId: Int? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentAddEditBinding.inflate(inflater, container, false)

        if (context is MainActivity) {
            mainActivity = context as MainActivity
        }

        val bundle = arguments
        bookId = bundle?.getInt("bookId")

        if (bookId == null) {
            mainActivity.supportActionBar?.title = "Add new book"
            binding.fabDeleteBook.visibility = View.GONE
        }

        if (bookId != null) {
            val book = mainActivity.bookService.getBook(bookId!!)
            if (book != null) {
                mainActivity.supportActionBar?.title = book.title
                binding.editTextTitle.setText(book.title)
                binding.editTextAuthor.setText(book.author)
            }
        }

        binding.saveButton.setOnClickListener {
            val title = binding.editTextTitle.text.toString()
            val author = binding.editTextAuthor.text.toString()
            if (title.isNotEmpty() && author.isNotEmpty()) {
                if (bookId == null) {
                    mainActivity.bookService.saveBook(Book(title, author))
                    Toast.makeText(
                        mainActivity,
                        "Book saved",
                        Toast.LENGTH_SHORT
                    ).show()
                } else {
                    mainActivity.bookService.updateBook(Book(title, author, id = bookId!!))
                    Toast.makeText(
                        mainActivity,
                        "Book updated",
                        Toast.LENGTH_SHORT
                    ).show()
                }
                NavHostFragment.findNavController(this).navigateUp();
            } else {
                Toast.makeText(
                    mainActivity,
                    "Please enter a title and author",
                    Toast.LENGTH_SHORT
                ).show()
            }
        }

        binding.fabDeleteBook.setOnClickListener {
            mainActivity.bookService.deleteBook(bookId!!)
            Toast.makeText(
                mainActivity,
                "Book deleted",
                Toast.LENGTH_SHORT
            ).show()
            NavHostFragment.findNavController(this).navigateUp();
        }

        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}