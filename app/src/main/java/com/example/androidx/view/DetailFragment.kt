package com.example.androidx.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.example.androidx.R
import com.example.androidx.utils.getProgressDrawable
import com.example.androidx.utils.loadImage
import com.example.androidx.viewmodel.DetailViewModel
import kotlinx.android.synthetic.main.fragment_detail.*


class DetailFragment : Fragment() {
    private var dogUid = 0
    private lateinit var viewModel:DetailViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_detail, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        arguments?.let{
            dogUid = DetailFragmentArgs.fromBundle(it).dogUid
        }

        viewModel = ViewModelProviders.of(this).get(DetailViewModel::class.java)
        viewModel.fetch(dogUid)

        observeViewModel()

    }

    private fun observeViewModel() {
        viewModel.dogLiveData.observe(this, Observer { dog ->
            dog?.let {
                dogName.text    = dog.dogBreed
                dogPurpose.text = dog.bredFor
                dogTemperment.text = dog.temperament
                dogLifespan.text = dog.lifeSpan
                context?.let { dogImage.loadImage(dog.imageUrl, getProgressDrawable(it)) }
            }
        })
    }
}