package com.example.mvi_flow_application.advertlist

import android.os.Bundle
import android.view.View
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import com.example.mvi_flow_application.R
import com.example.mvi_flow_application.mvibase.MviView
import kotlinx.coroutines.flow.Flow

class AdvertListActivity : AppCompatActivity(), View.OnClickListener,
    MviView<AdvertListIntent, AdvertListViewState> {

    private val loadAdvertsButton: Button = findViewById(R.id.activity_button_load)
    private val clearAdvertsButton: Button = findViewById(R.id.activity_button_clear)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }

    override fun onClick(p0: View?) {

    }

    override fun intents(): Flow<AdvertListIntent> {
        TODO("Not yet implemented")
    }

    override fun render(state: AdvertListViewState) {
        TODO("Not yet implemented")
    }
}