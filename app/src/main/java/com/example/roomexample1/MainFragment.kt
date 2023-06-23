package com.example.roomexample1

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.example.roomexample1.adapter.AdapterClickCallbacks
import com.example.roomexample1.adapter.TodoListAdapter
import com.example.roomexample1.databinding.FragmentMainBinding
import com.example.roomexample1.room.Importance
import com.example.roomexample1.room.TodoItem
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach

class MainFragment : Fragment() {

    companion object {
        fun newInstance() = MainFragment()
    }

    private val viewModel: MainViewModel by viewModels()
    private var binding: FragmentMainBinding? = null
    private val adapter: TodoListAdapter? get() = views { todoList.adapter as TodoListAdapter }


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View = FragmentMainBinding.inflate(LayoutInflater.from(context)).also { binding = it }.root


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        views {
            floatingButton.setOnClickListener {
                val action = MainFragmentDirections.manageFragmentAction(itemId = "-1")
                findNavController().navigate(action)
            }

            todoList.adapter = TodoListAdapter(object : AdapterClickCallbacks{
                override fun onItemClick(id:String) {
                    val action = MainFragmentDirections.manageFragmentAction(itemId = id)
                    findNavController().navigate(action)
                }

                override fun onCheckClick(id:String, done:Boolean){
                    viewModel.changeItemDone(id,done)
                }

            })

        }


        viewModel.data.onEach(::updateUI).launchIn(lifecycleScope)

    }

    private fun updateUI(list: List<TodoItem>) {
        adapter?.submitList(list)
    }

    private fun addItem(todoItem: TodoItem) {
        viewModel.addItem(todoItem)
    }


    private fun <T : Any> views(block: FragmentMainBinding.() -> T): T? = binding?.block()


}