package com.example.library.ui.addEdit

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.NavHostFragment
import com.example.library.MainActivity
import com.example.library.R
import com.example.library.databinding.FragmentAddEditBinding
import com.example.library.model.Book
import com.example.library.model.Status


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
            mainActivity.supportActionBar?.title = getString(R.string.title_add_new_book)
            binding.fabDeleteBook.visibility = View.GONE
        }

        val adapter: ArrayAdapter<Status> =
            ArrayAdapter<Status>(requireContext(), android.R.layout.simple_spinner_dropdown_item, Status.values())
        binding.statusSpinner.adapter = adapter
        binding.statusSpinner.setSelection(Status.WANT_TO_READ.ordinal)

        if (bookId != null) {
            val book = mainActivity.bookService.getBook(bookId!!)
            if (book != null) {
                mainActivity.supportActionBar?.title = book.title
                binding.editTextTitle.setText(book.title)
                binding.editTextAuthor.setText(book.author)
                binding.statusSpinner.setSelection(book.status.ordinal)
                binding.checkBoxOwned.isChecked = book.owned
                binding.checkBoxAudioBook.isChecked = book.audioBook
                binding.editTextNotes.setText(book.notes)
            }
        }

        binding.saveButton.setOnClickListener {
            val title = binding.editTextTitle.text.toString()
            val author = binding.editTextAuthor.text.toString()
            val status = binding.statusSpinner.selectedItem as Status
            val owned = binding.checkBoxOwned.isChecked
            val audioBook = binding.checkBoxAudioBook.isChecked
            val notes = binding.editTextNotes.text.toString()

            if (title.isNotEmpty() && author.isNotEmpty()) {
                if (bookId == null) {
                    mainActivity.bookService.saveBook(Book(title, author, status, notes, owned, audioBook))
                    Toast.makeText(
                        mainActivity,
                        getString(R.string.toast_book_saved),
                        Toast.LENGTH_SHORT
                    ).show()
                } else {
                    mainActivity.bookService.updateBook(Book(title, author, status, notes, owned, audioBook, id = bookId!!))
                    Toast.makeText(
                        mainActivity,
                        getString(R.string.toast_book_updated),
                        Toast.LENGTH_SHORT
                    ).show()
                }
                NavHostFragment.findNavController(this).navigateUp();
            } else {
                Toast.makeText(
                    mainActivity,
                    getString(R.string.toast_please_enter_a_title_and_author),
                    Toast.LENGTH_SHORT
                ).show()
            }
        }

        binding.fabDeleteBook.setOnClickListener {
            mainActivity.bookService.deleteBook(bookId!!)
            Toast.makeText(
                mainActivity,
                getString(R.string.toast_book_deleted),
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