package com.example.dealerapp

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.dealerapp.application.DealerApplication
import com.example.dealerapp.databinding.FragmentSecondBinding
import com.example.dealerapp.model.Dealer
import com.example.dealerapp.ui.DealerViewModelFactory
import com.example.dealerapp.ui.DealerappViewModel

/**
 * A simple [Fragment] subclass as the second destination in the navigation.
 */
class SecondFragment : Fragment() {

    private var _binding: FragmentSecondBinding? = null
    private val binding get() = _binding!!
    private lateinit var applicationContext: Context
    private val dealerViewModel: DealerappViewModel by viewModels {
        DealerViewModelFactory((applicationContext as DealerApplication).repository)
    }
    private val args : SecondFragmentArgs by navArgs()
    private var dealer: Dealer? = null

    override fun onAttach(context: Context) {
        super.onAttach(context)
        applicationContext = requireContext().applicationContext
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        _binding = FragmentSecondBinding.inflate(inflater, container, false)
        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        dealer = args.dealer
        if (dealer!= null){
            binding.deleteButton.visibility = View.VISIBLE
            binding.saveButton.text = "Update"
            binding.NamaDealerEditText.setText(dealer?.Nama)
            binding.AlamatEditText.setText(dealer?.Alamat)
            binding.TelpEditText.setText(dealer?.Telp)
        }
        val Nama= binding.NamaDealerEditText.text
        val Alamat= binding.AlamatEditText.text
        val Telp = binding.TelpEditText.text
        binding.saveButton.setOnClickListener {
            if (Nama.isEmpty()) {
                Toast.makeText(context, "Nama tidak boleh kosong", Toast.LENGTH_SHORT).show()
            } else if (Alamat.isEmpty()) {
                Toast.makeText(context, "Alamat tidak boleh kosong", Toast.LENGTH_SHORT).show()
            } else if (Telp.isEmpty()) {
                Toast.makeText(context, "Telp tidak boleh kosong", Toast.LENGTH_SHORT).show()
            } else {
                if (dealer == null) {
                    val dealer =
                        Dealer(0, Nama.toString(), Alamat.toString(), Telp.toString())
                   dealerViewModel.insert(dealer)
                } else {
                    val dealer = Dealer(
                        dealer?.id!!,
                        Nama .toString(),
                        Alamat.toString(),
                        Telp.toString()
                    )
                    dealerViewModel.update(dealer)
                }
                findNavController().popBackStack() // untuk dismis halaman ini
            }
        }

        binding.deleteButton.setOnClickListener {
            dealer?.let { dealerViewModel.delete(it) }
            findNavController().popBackStack()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}