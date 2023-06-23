package com.example.roomexample1

import android.app.DatePickerDialog
import android.os.Bundle
import android.text.SpannableString
import android.text.format.DateFormat
import android.text.style.ForegroundColorSpan
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.widget.PopupMenu
import android.widget.Toast
import androidx.appcompat.content.res.AppCompatResources
import androidx.core.content.ContextCompat
import androidx.core.widget.TextViewCompat
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.roomexample1.databinding.FragmentManageItemBinding
import com.example.roomexample1.room.Importance
import com.example.roomexample1.room.TodoItem
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onCompletion
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.onStart
import java.util.Calendar
import java.util.Date

class ManageItemFragment : Fragment() {
    companion object {
        fun newInstance() = ManageItemFragment()
    }


    private var binding: FragmentManageItemBinding? = null

    private val viewModel: MainViewModel by activityViewModels()

    private val args: ManageItemFragmentArgs by navArgs()

    private var item = TodoItem(
        "-1",
        "",
        Importance.MIDDLE,
        null,
        false,
        System.currentTimeMillis(),
        null
    )
    private lateinit var popupMenu : PopupMenu
    private lateinit var timePickerDialog : DatePickerDialog

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View = FragmentManageItemBinding.inflate(
        LayoutInflater.from(context),
    ).also { binding = it }.root

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        Log.d("1", args.itemId)
        createPopupMenu()
        when (args.itemId) {
            "-1" -> {
                //new item
                loadItemInfo()
                setUpViews()
            }

            else -> {
                //not new item
                viewModel.getItem(args.itemId).onEach {
                    if(item.id == "-1") {
                        item = it
                        loadItemInfo()
                        setUpViews()
                    }
                }.launchIn(lifecycleScope)
            }
        }
        //saved instance




    }

    private fun loadItemInfo() {
        views {
            when (item.importance) {
                Importance.LOW -> {
                    importanceText.text = "Низкая"
                }
                Importance.MIDDLE -> {
                    importanceText.text = "Нет"
                }
                Importance.HIGH -> {
                    importanceText.text = "!!Высокая"
                }
            }

            editTodo.setText(item.text)

            if(item.deadline != null){
                date.visibility = View.VISIBLE
                date.text = DateFormat.format("hh:mm:ss, MMM dd, yyyy", Date(item.deadline!!)).toString()
                switchCompat.isChecked = true
            }

            if(args.itemId != "-1") {
                delete.setTextColor(
                    AppCompatResources.getColorStateList(
                        requireContext(),
                        R.color.red
                    )
                )
                TextViewCompat.setCompoundDrawableTintList(
                    delete, AppCompatResources.getColorStateList(
                        requireContext(),
                        R.color.red
                    )
                )
            }

        }

    }



    private fun createPopupMenu() {
        views {
            popupMenu = PopupMenu(context, importanceText)
            popupMenu.menuInflater.inflate(R.menu.popup_importance_menu, popupMenu.menu)

            //example
            val highElement: MenuItem = popupMenu.menu.getItem(2)
            val s = SpannableString("!! Высокая")
            s.setSpan(
                ForegroundColorSpan(ContextCompat.getColor(requireContext(), R.color.red)),
                0,
                s.length,
                0
            )
            highElement.title = s

            popupMenu.setOnMenuItemClickListener {
                when (it.itemId) {
                    R.id.item_middle -> {
                        importanceText.text = "Нет"
                        importanceText.setTextColor(
                            ContextCompat.getColor(
                                requireContext(),
                                R.color.tertiary
                            )
                        )
                        item.importance = Importance.MIDDLE
                        return@setOnMenuItemClickListener true
                    }

                    R.id.item_low -> {
                        importanceText.text = "Низкая"
                        importanceText.setTextColor(
                            ContextCompat.getColor(
                                requireContext(),
                                R.color.tertiary
                            )
                        )
                        item.importance = Importance.LOW
                        return@setOnMenuItemClickListener true
                    }

                    R.id.item_high -> {
                        importanceText.text = "!! Высокая"
                        importanceText.setTextColor(
                            ContextCompat.getColor(
                                requireContext(),
                                R.color.red
                            )
                        )
                        item.importance = Importance.HIGH
                        return@setOnMenuItemClickListener true
                    }
                }
                return@setOnMenuItemClickListener false
            }
        }
    }

    private fun setUpViews() {

        val myCalendar = Calendar.getInstance()
        if(item.deadline != null){
            myCalendar.timeInMillis = item.deadline!!
        }

        views {
            timePickerDialog = DatePickerDialog(
                requireContext(),
                R.style.DatePickerStyle,
                { view, year, month, day ->
                    myCalendar.set(Calendar.YEAR, year)
                    myCalendar.set(Calendar.MONTH, month)
                    myCalendar.set(Calendar.DAY_OF_MONTH, day)
                    item.deadline = myCalendar.timeInMillis
                    date.visibility = View.VISIBLE
                    date.text = DateFormat.format("hh:mm:ss, MMM dd, yyyy", Date(item.deadline!!)).toString()
                }, myCalendar.get(Calendar.YEAR), myCalendar.get(Calendar.MONTH), myCalendar.get(
                    Calendar.DAY_OF_MONTH
                )
            )

            timePickerDialog.setOnCancelListener {
                if (date.visibility == View.INVISIBLE) {
                    switchCompat.isChecked = false
                }
            }


            switchCompat.setOnCheckedChangeListener { _, checked ->
                if (checked) {
                    openDatePicker()
                } else {
                    date.visibility = View.INVISIBLE
                    item.deadline = null
                }
            }

            chooseImportance.setOnClickListener {
                popupMenu.show()
            }

            chooseDate.setOnClickListener {
                openDatePicker()
            }


            delete.setOnClickListener {
                if (args.itemId != "-1") {
                    viewModel.deleteItem(item)

                    val action = ManageItemFragmentDirections.backAction()
                    findNavController().navigate(action)

                }
            }

            close.setOnClickListener {

                val action = ManageItemFragmentDirections.backAction()
                findNavController().navigate(action)
            }


            save.setOnClickListener {
                if (args.itemId == "-1") {
                    saveNewTask()
                } else {
                    updateTask()
                }

            }
        }
    }



    private fun saveNewTask() {
        item.id = (0..1000).random().toString()
        views {
            item.text = editTodo.text.toString()
        }
        item.dateCreation = System.currentTimeMillis()
        //
        if(item.text.isEmpty()){
            Toast.makeText(requireContext(), "Заполните что нужно сделать!", Toast.LENGTH_SHORT).show()
            return
        }


        viewModel.addItem(item)
        val action = ManageItemFragmentDirections.backAction()
        findNavController().navigate(action)

    }

    private fun updateTask() {
        views {
            item.text = editTodo.text.toString()
        }
        if(item.text.isEmpty()){
            Toast.makeText(requireContext(), "Заполните что нужно сделать!", Toast.LENGTH_SHORT).show()
            return
        }

        viewModel.changeItem(item)
        val action = ManageItemFragmentDirections.backAction()
        findNavController().navigate(action)
    }

    private fun openDatePicker(){
        views {
            switchCompat.isChecked = true
        }
        timePickerDialog.show()
    }


    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putString("todoItem", item.toString())
    }

    private fun <T : Any> views(block: FragmentManageItemBinding.() -> T): T? = binding?.block()

}