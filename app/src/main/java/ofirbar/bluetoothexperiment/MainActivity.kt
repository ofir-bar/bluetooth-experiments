package ofirbar.bluetoothexperiment

import android.app.Activity
import android.bluetooth.BluetoothAdapter
import android.bluetooth.BluetoothDevice
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ArrayAdapter
import kotlinx.android.synthetic.main.activity_main.*
import org.jetbrains.anko.AnkoLogger
import org.jetbrains.anko.toast
import org.jetbrains.anko.warn

private const val REQUEST_ENABLE_BT = 1

class MainActivity : AppCompatActivity(), AnkoLogger {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Tests if Bluetooth is supported
        val bluetoothAdapter: BluetoothAdapter? = BluetoothAdapter.getDefaultAdapter()
        if (bluetoothAdapter == null)
        {
            warn("Bluetooth is not supported on this device")
            finish()
        }


        // Test if Bluetooth is disabled
        if (bluetoothAdapter?.isEnabled == false) {
            warn { "Bluetooth is disabled" }
            val enableBtIntent = Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE)
            startActivityForResult(enableBtIntent, REQUEST_ENABLE_BT)
        }

        val pairedDevices: Set<BluetoothDevice>? = bluetoothAdapter?.bondedDevices

        // Get currently paired devices
        val deviceNameAndAddress : ArrayList<String> = ArrayList()
        pairedDevices?.forEach { device ->
            deviceNameAndAddress.add(device.name + device.address)
        }

        paired_devices_list.adapter = ArrayAdapter(this, android.R.layout.simple_list_item_1, deviceNameAndAddress)



    }


    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)


        // Bluetooth Connection Requests
        if (requestCode == REQUEST_ENABLE_BT){
            when (resultCode) {
                Activity.RESULT_OK -> toast("BLUETOOTH CONNECTED")
                Activity.RESULT_CANCELED -> {
                    toast("BLUETOOTH PERMISSION IS MANDATORY")
                    finish()
                }
                else -> { toast("UNEXPECTED ERROR OCCURED")
                    finish() }
            }
        }




    }

}
