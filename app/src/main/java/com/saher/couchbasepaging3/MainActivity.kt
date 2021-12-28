package com.saher.couchbasepaging3

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.saher.couchbasepaging3.adapter.PhotoPaging
import com.saher.couchbasepaging3.adapter.RecyclerViewDataAdapter
import com.saher.couchbasepaging3.db.CouchBase
import com.saher.couchbasepaging3.repository.PhotoFlowImpl
import com.saher.couchbasepaging3.util.ViewModelProviderFactory
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {

    private lateinit var repository: PhotoFlowImpl
    private lateinit var pagingAdapter: PhotoPaging
    private lateinit var photoRecyclerView : RecyclerView
    private lateinit var recyclerViewPhotoAdapter: RecyclerViewDataAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        //Initialize DB
        CouchBase(this)

        //add content to db (Content Added)
       //database.addContentToDB()

        //PhotoAdapter
        recyclerViewPhotoAdapter = RecyclerViewDataAdapter()

        //RecyclerView
        photoRecyclerView = findViewById(R.id.recyclerview_main)
        photoRecyclerView.layoutManager = GridLayoutManager(this, 3)

        //linking adapters
        photoRecyclerView.adapter = recyclerViewPhotoAdapter

        pagingAdapter = PhotoPaging(this)

        repository = PhotoFlowImpl(pagingAdapter)

        val myViewModel: MyViewModel by viewModels {
            ViewModelProviderFactory(repository)
        }
        observerData(myViewModel)
    }

    private fun observerData(viewModel: MyViewModel) {
        lifecycleScope.launch {
            viewModel.getContentPaging().collectLatest {
                recyclerViewPhotoAdapter.submitData(lifecycle, it)
            }
        }
    }
}