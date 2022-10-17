package com.hojibobo.hojibobopost

import android.Manifest
import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import com.hojibobo.hojibobopost.adapter.OrderListAdapter
import com.hojibobo.hojibobopost.base.BaseActivity
import com.hojibobo.hojibobopost.databinding.MainActivityBinding
import com.hojibobo.hojibobopost.model.Order
import com.karumi.dexter.Dexter
import com.karumi.dexter.MultiplePermissionsReport
import com.karumi.dexter.PermissionToken
import com.karumi.dexter.listener.PermissionDeniedResponse
import com.karumi.dexter.listener.PermissionGrantedResponse
import com.karumi.dexter.listener.PermissionRequest
import com.karumi.dexter.listener.multi.MultiplePermissionsListener
import com.karumi.dexter.listener.single.PermissionListener

class MainActivity : BaseActivity<MainActivityBinding, MainViewModel>(
    MainViewModel::class.java, R.layout.main_activity
), MainNavigator, PermissionListener, MultiplePermissionsListener {

    private lateinit var orderListAdapter: OrderListAdapter
    private var selectedOrder: Order? = null
    private var orderArrayList = ArrayList<Order>()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel.setNavigator(this)
        orderListAdapter =
            OrderListAdapter(orderArrayList, object : OrderListAdapter.SentClickListener {
                override fun sentClick(position: Int) {
                    selectedOrder = orderArrayList[position]
                    var intent = Intent(this@MainActivity, SimpleScannerActivity::class.java)
                    startActivityForResult(intent, 1000)

                }

            })
        binding.orderRecyclerList.layoutManager = LinearLayoutManager(this)
        binding.orderRecyclerList.adapter = orderListAdapter

        viewModel.createUser()

        val permissions = listOf(
            Manifest.permission.CAMERA
        )

        Dexter.withActivity(this)
            .withPermissions(permissions)
            .withListener(this)
            .check()


        viewModel.orderArrayListLiveData.observe(this, Observer {
            orderArrayList.clear()
            orderArrayList.addAll(it)
            orderListAdapter.notifyDataSetChanged()
        })
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == Activity.RESULT_OK) {
            if (resultCode == 1000) {
                var post_code = data!!.getStringExtra("post_code")
                if (selectedOrder != null)
                    viewModel.makeOrderAsSent(post_code, selectedOrder!!)
            }
        }

    }

    override fun onPermissionGranted(response: PermissionGrantedResponse?) {

    }

    override fun onPermissionRationaleShouldBeShown(
        permission: PermissionRequest?,
        token: PermissionToken?
    ) {

    }

    override fun onPermissionDenied(response: PermissionDeniedResponse?) {

    }

    override fun onPermissionsChecked(report: MultiplePermissionsReport?) {

    }

    override fun onPermissionRationaleShouldBeShown(
        permissions: MutableList<PermissionRequest>?,
        token: PermissionToken?
    ) {

    }
}