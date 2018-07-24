package com.vanethos.example.presentation.ui

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.util.Log
import android.view.KeyEvent
import android.view.View
import android.view.inputmethod.EditorInfo
import android.widget.TextView
import android.widget.Toast
import com.vanethos.example.R
import com.vanethos.example.domain.models.Repos
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.activity_main.view.*
import kotlinx.android.synthetic.main.empty_view.*
import android.app.Activity
import android.view.inputmethod.InputMethodManager


class MainActivity : AppCompatActivity() {
    private lateinit var viewModel: MainViewModel
    private lateinit var adapter : MainAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        viewModel = ViewModelProviders.of(this).get(MainViewModel::class.java)

        setupViews()
        registerObservables()
    }



    private fun setupViews() {
        adapter = MainAdapter(
                object : MainAdapter.ItemClickListener {
                    override fun onItemClicked(repos: Repos) {
                        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
                    }
                }
        )
        main_recyclerView.layoutManager = LinearLayoutManager(this)
        main_recyclerView.adapter = adapter


        // handle enter key in edittext to have a new search happening
        main_editText.setOnEditorActionListener(
                object :TextView.OnEditorActionListener {
                    override fun onEditorAction(v: TextView?, actionId: Int, event: KeyEvent?): Boolean {
                        if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                            viewModel.newSearch(main_editText.text.toString())
                            hideKeyboard()
                            return true
                        }
                        return false
                    }
                }
        )
    }

    private fun registerObservables() {
        submitItems()

        viewModel.errorToastEvent.observe(this,
                Observer { Toast.makeText(this, getString(R.string.err_search), Toast.LENGTH_LONG) }
        )

        viewModel.clearDataEvents.observe(this,
                Observer {
                    viewModel.clearDataSource()
                    submitItems()
                    adapter.notifyDataSetChanged()
                }
        )

        viewModel.emptyVisibilityEvents.observe(this,
                Observer { show ->
                    if(show != null) {
                        var visibility = if (show.peek()) View.VISIBLE else View.GONE
                        this.empty_view_imageView.visibility = visibility
                    }
                }
        )

        viewModel.recyclerViewLoadingEvents.observe(this,
                Observer { show ->
                    if(show != null) {
                        adapter.loading = show.peek()
                    }
                })
    }

    fun submitItems() {
        viewModel.getItems()!!
                .subscribe(
                        { items -> adapter.submitList(items) },
                        {
                            //Error handling
                        }
                )
    }

    fun hideKeyboard() {
        val imm = this.getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager
        //Find the currently focused view, so we can grab the correct window token from it.
        var view = this.getCurrentFocus()
        //If no view currently has focus, create a new one, just so we can grab a window token from it
        if (view == null) {
            view = View(this)
        }
        imm.hideSoftInputFromWindow(view!!.getWindowToken(), 0)
    }
}
