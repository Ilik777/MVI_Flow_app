package com.example.mvi_flow_application.advertlist.presentation

import android.content.Intent
import android.os.Bundle
import android.os.PersistableBundle
import android.view.View
import android.widget.Button
import android.widget.ProgressBar
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.lifecycle.*
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.mvi_flow_application.R
import com.example.mvi_flow_application.advertdetails.AdvertDetailsActivity
import com.example.mvi_flow_application.advertlist.data.DefaultAdvertsRepository
import com.example.mvi_flow_application.advertlist.domain.DeleteAllAdvertsUseCase
import com.example.mvi_flow_application.advertlist.domain.LoadAdvertUseCase
import com.example.mvi_flow_application.advertlist.presentation.mapper.AdvertDataMapper
import com.example.mvi_flow_application.advertlist.presentation.mapper.AdvertViewDataMapper
import com.example.mvi_flow_application.advertlist.presentation.model.AdvertListIntent
import com.example.mvi_flow_application.advertlist.presentation.model.AdvertListSideEffect
import com.example.mvi_flow_application.advertlist.presentation.model.AdvertListViewState
import com.example.mvi_flow_application.advertlist.presentation.model.AdvertViewData
import com.example.mvi_flow_application.advertlist.presentation.navigation.AdvertListNavigator
import com.example.mvi_flow_application.mvibase.MviView
import com.google.android.material.snackbar.Snackbar
import kotlinx.coroutines.launch

class MyViewModelFactory(
    private val loadUseCase: LoadAdvertUseCase,
    private val deleteUseCase: DeleteAllAdvertsUseCase,
    private val advertDataMapper: AdvertDataMapper,
    private val reducer: AdvertListReducer,
) : ViewModelProvider.NewInstanceFactory() {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {

        if (modelClass.isAssignableFrom(AdvertListViewModel::class.java)) {

            return AdvertListViewModel(
                loadAdvertUseCase = loadUseCase,
                deleteAdvertUseCase = deleteUseCase,
                advertDataMapper = advertDataMapper,
                reducer = reducer
            ) as T
        }

        throw IllegalArgumentException("Unknown ViewModel class")
    }
}

class AdvertListActivity : AppCompatActivity(), View.OnClickListener,
    MviView<AdvertListViewState, AdvertListSideEffect>, AdvertClickedCallBack {

    private lateinit var mainContainer: ConstraintLayout
    private lateinit var advertList: RecyclerView
    private lateinit var loader: ProgressBar
    private lateinit var loadAdvertsButton: Button
    private lateinit var clearAdvertsButton: Button

    private val navigator: AdvertListNavigator = AdvertListNavigator()
    private val advertListAdapter: AdvertsAdapter = AdvertsAdapter(this@AdvertListActivity)

    private val viewModel: AdvertListViewModel by lazy {
        val factory = MyViewModelFactory(
            loadUseCase = LoadAdvertUseCase(
                advertsRepository = DefaultAdvertsRepository()
            ),
            deleteUseCase = DeleteAllAdvertsUseCase(
                advertsRepository = DefaultAdvertsRepository()
            ),
            advertDataMapper = AdvertDataMapper(),
            reducer = AdvertListReducer(AdvertViewDataMapper())
        )

        ViewModelProvider(this, factory)[AdvertListViewModel::class.java]
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        viewModel.setNavigationCallBack(navigator::openAdvertDetails)

        initViews()
    }

    override fun onResume() {
        super.onResume()
        navigator.activity = this
    }

    override fun onDestroy() {
        super.onDestroy()
        viewModel.setNavigationCallBack(null)
        navigator.activity = null
    }

    override fun onSaveInstanceState(outState: Bundle, outPersistentState: PersistableBundle) {
        super.onSaveInstanceState(outState, outPersistentState)
        outState.putParcelable("KEY", viewModel.states.value)
    }

    override fun onRestoreInstanceState(savedInstanceState: Bundle) {
        super.onRestoreInstanceState(savedInstanceState)
        viewModel.createIdleState(
            savedInstanceState.getParcelable(
                "Key",
                AdvertListViewState::class.java
            )
        )
    }

    override fun render(state: AdvertListViewState) {
        loader.visibility = if (state.isLoading) {
            View.VISIBLE
        } else {
            View.GONE
        }

        if (state.advertList.isEmpty()) {
            loadAdvertsButton.visibility = View.VISIBLE
            advertListAdapter.clear()
        } else {
            loadAdvertsButton.visibility = View.GONE
            advertListAdapter.updateAdvertList(state.advertList)
        }
    }

    override fun handleSideEffect(sideEffect: AdvertListSideEffect) {
        when (sideEffect) {
            is AdvertListSideEffect.OpenAdvertDetails -> {
                startActivity(Intent(this, AdvertDetailsActivity::class.java).apply {
                    putExtra("advertData", sideEffect.advertViewData)
                })
            }
            is AdvertListSideEffect.ShowError -> {
                Snackbar.make(mainContainer, sideEffect.errorText, Snackbar.LENGTH_SHORT).show()
            }
            is AdvertListSideEffect.ShowToast -> {
                Toast.makeText(this, sideEffect.text, Toast.LENGTH_SHORT).show()
            }
        }
    }

    override fun onClick(view: View?) {
        when (view?.id) {
            loadAdvertsButton.id -> {
                viewModel.onIntentCalled(AdvertListIntent.LoadAdvertList)
            }
            clearAdvertsButton.id -> {
                viewModel.onIntentCalled(AdvertListIntent.ClearAdvertList)
            }
        }
    }

    override fun onAdvertItemClicked(advertViewData: AdvertViewData) {
        viewModel.onIntentCalled(
            AdvertListIntent.OpenAdvertDetails(
                advertViewData
            )
        )
    }

    override fun onFireButtonClicked(advertViewData: AdvertViewData) {
        viewModel.onIntentCalled(
            AdvertListIntent.DeleteAdvertDetails(
                advertViewData
            )
        )
    }

    private fun initViews() {
        mainContainer = findViewById(R.id.layout_container)
        advertList = findViewById(R.id.activity_advert_list)
        loader = findViewById(R.id.activity_advert_list_loader)
        loadAdvertsButton = findViewById(R.id.activity_button_load)
        clearAdvertsButton = findViewById(R.id.activity_button_clear)
        loadAdvertsButton.setOnClickListener(this)
        clearAdvertsButton.setOnClickListener(this)
        advertList.layoutManager = LinearLayoutManager(this)
        advertList.adapter = advertListAdapter

//        viewModel.states.observe(this) {
//            render(it)
//        }

        lifecycleScope.launch {
            viewModel.stateFlow.flowWithLifecycle(lifecycle, Lifecycle.State.STARTED).collect {
                render(it)
            }
        }
        lifecycleScope.launchWhenCreated {
            viewModel.sideEffect.collect {
                handleSideEffect(it)
            }
        }
    }
}