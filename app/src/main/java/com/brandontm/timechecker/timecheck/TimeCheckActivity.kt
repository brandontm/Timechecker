package com.brandontm.timechecker.timecheck

import androidx.appcompat.app.AppCompatActivity

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders

import com.brandontm.timechecker.R

class TimeCheckActivity : AppCompatActivity() {
    private lateinit var viewModel: TimeCheckViewModel
    private val userId: Int by lazy {
        intent.getIntExtra("userId", -1)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_timecheck)

        viewModel = ViewModelProviders.of(this).get(TimeCheckViewModel::class.java)
        viewModel.status.observe(this, Observer<Int> { status ->
            timecheck(status)
        })


    }

    fun timecheck(status: Int) {
        if (status == 1) {
            Toast.makeText(this, "Chequeo de tiempo guardado correctamente.",
                    Toast.LENGTH_SHORT).show()
        } else {
            Toast.makeText(this, "Error al guardar checado de tiempo",
                    Toast.LENGTH_SHORT).show()
        }
    }

    fun onCheckinClick(v: View) {
        viewModel.postTimecheck(userId, 1)
    }

    fun onCheckoutClick(v: View) {
        viewModel.postTimecheck(userId, 2)
    }

}
