package com.example.roomexample1

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.example.roomexample1.databinding.FragmentMainBinding
import com.example.roomexample1.room.Importance
import com.example.roomexample1.room.TodoItem
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach

class MainFragment : Fragment() {

    companion object {
        fun newInstance() = MainFragment()
    }

    private val viewModel:MainViewModel by viewModels()
    private lateinit var binding:FragmentMainBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View = FragmentMainBinding.inflate(LayoutInflater.from(context)).also { binding = it }.root


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.data.onEach(::updateUI).launchIn(lifecycleScope)

        views{
            floatingButton.setOnClickListener {
                viewModel.addItem(
                    TodoItem(
                        (0..1000).random().toString(),
                        "some text",
                        Importance.MIDDLE
                    )
                )
            }
        }

    }

    private fun updateUI(list:List<TodoItem>){

    }



    private fun <T : Any> views(block: FragmentMainBinding.() -> T): T = binding.block()


}