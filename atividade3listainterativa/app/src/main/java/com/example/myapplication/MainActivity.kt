package com.example.myapplication

import android.os.Bundle
import android.text.InputType
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.myapplication.databinding.ActivityMainBinding
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private var taskList: MutableList<Task> = mutableListOf()
    private lateinit var adapter: TaskAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        adapter = TaskAdapter(
            onTaskClicked = { task ->
                val idx = taskList.indexOfFirst { it.id == task.id }
                if (idx != -1) {
                    val current = taskList[idx]
                    taskList[idx] = current.copy(isCompleted = !current.isCompleted)
                    refreshList()
                }
            },
            onDeleteClicked = { task ->
                val removed = taskList.removeAll { it.id == task.id }
                if (removed) refreshList() else
                    Toast.makeText(this, "Item não encontrado", Toast.LENGTH_SHORT).show()
            },
            onEditClicked = { task ->
                showEditDialog(task)
            }
        )

        binding.rvTasks.layoutManager = LinearLayoutManager(this)
        binding.rvTasks.adapter = adapter
        binding.rvTasks.addItemDecoration(
            DividerItemDecoration(this, RecyclerView.VERTICAL)
        )

        binding.btnAdd.setOnClickListener {
            val title = binding.etTitle.text.toString().trim()
            if (title.isEmpty()) {
                Toast.makeText(this, "Digite um título", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }
            taskList.add(0, Task(title = title))
            refreshList()
            binding.etTitle.text?.clear()
        }
    }

    private fun refreshList() {
        adapter.submitList(taskList.toList())
    }

    private fun showEditDialog(task: Task) {
        val container = TextInputLayout(this).apply {
            layoutParams = ViewGroup.MarginLayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT
            ).apply { setMargins(32, 16, 32, 0) }
            hint = getString(R.string.edit_task_title)
        }

        val editText = TextInputEditText(this).apply {
            setText(task.title)
            inputType = InputType.TYPE_CLASS_TEXT or InputType.TYPE_TEXT_FLAG_CAP_SENTENCES
        }
        container.addView(editText)

        MaterialAlertDialogBuilder(this)
            .setTitle(getString(R.string.edit_task))
            .setView(container)
            .setPositiveButton(getString(R.string.save)) { dialog, _ ->
                val newTitle = editText.text?.toString()?.trim().orEmpty()
                if (newTitle.isEmpty()) {
                    Toast.makeText(this, R.string.empty_title_msg, Toast.LENGTH_SHORT).show()
                } else {
                    val idx = taskList.indexOfFirst { it.id == task.id }
                    if (idx != -1) {
                        taskList[idx] = taskList[idx].copy(title = newTitle)
                        refreshList()
                    }
                }
                dialog.dismiss()
            }
            .setNegativeButton(android.R.string.cancel, null)
            .show()
    }
}
