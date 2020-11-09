package com.example.androidx.view

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.navigation.Navigation
import androidx.recyclerview.widget.RecyclerView
import com.example.androidx.R
import com.example.androidx.databinding.ItemDogBinding

import com.example.androidx.model.DogBreed
import kotlinx.android.synthetic.main.item_dog.view.*

class DogListAdapter(val dogList : ArrayList<DogBreed>): RecyclerView.Adapter<DogListAdapter.DogViewHolder>(),DogClickListener{

    fun updateDogList(newDogList:List<DogBreed>){
        dogList.clear()
        dogList.addAll(newDogList)
        notifyDataSetChanged()
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DogViewHolder {
        var infalter = LayoutInflater.from(parent.context)
//        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_dog,parent,false)
        val view = DataBindingUtil.inflate<ItemDogBinding>(infalter,R.layout.item_dog,parent,false)
        return  DogViewHolder(view)
    }

    override fun getItemCount(): Int {
        return  dogList.size
    }




    override fun onDogClicked(view: View) {
        var action = ListFragmentDirections.actionListFragmentToDetailFragment()
        val uuid = view.dogId.text.toString().toInt()
        action.dogUid  = uuid
        Navigation.findNavController(view).navigate(action)
    }

    override fun onBindViewHolder(holder: DogViewHolder, position: Int) {
        holder.view.dog = dogList[position]
        holder.view.listener = this
    }

    class DogViewHolder(var view: ItemDogBinding):RecyclerView.ViewHolder(view.root){

    }
}