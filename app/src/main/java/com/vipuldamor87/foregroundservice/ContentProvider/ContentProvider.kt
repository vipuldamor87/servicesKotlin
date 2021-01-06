package com.vipuldamor87.foregroundservice.ContentProvider

import android.Manifest
import android.content.ContentProviderOperation
import android.content.pm.PackageManager
import android.os.Bundle
import android.provider.ContactsContract
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.vipuldamor87.foregroundservice.R
import kotlinx.android.synthetic.main.activity_content_provider.*


class ContentProvider : AppCompatActivity() {

    private  lateinit var viewAdapter: RecyclerView.Adapter<*>
    private lateinit var  viewManager: RecyclerView.LayoutManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_content_provider)
        recyclerView.layoutManager = LinearLayoutManager(this)
        if(ActivityCompat.checkSelfPermission(this, Manifest.permission.READ_CONTACTS)!= PackageManager.PERMISSION_GRANTED){
            ActivityCompat.requestPermissions(
                this, arrayOf(
                    android.Manifest.permission.READ_CONTACTS,
                    android.Manifest.permission.WRITE_CONTACTS
                ), 112
            )
        }
        val itemTouchHelperCallBack = object : ItemTouchHelper.SimpleCallback(0,ItemTouchHelper.RIGHT or ItemTouchHelper.LEFT) {
            override fun onMove(
                recyclerView: RecyclerView,
                viewHolder: RecyclerView.ViewHolder,
                target: RecyclerView.ViewHolder
            ): Boolean {
                return false
            }

            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, Direction: Int) {
              var posi =  viewHolder.adapterPosition
              
            }
        }

        val itemTouchHelper = ItemTouchHelper(itemTouchHelperCallBack)
        itemTouchHelper.attachToRecyclerView(recyclerView)


        readContact.setOnClickListener {
            readContact()
        }
        addContact.setOnClickListener {
            addnewContact()
        }



    }

    private fun addnewContact(){
        val ops = ArrayList<ContentProviderOperation>()
        val rawContactInsertIndex = ops.size
        var cname = cName.text.toString()
        var cnum = cNum.text.toString()

        ops.add(
            ContentProviderOperation.newInsert(ContactsContract.Data.CONTENT_URI)
                .withValueBackReference(ContactsContract.Data.RAW_CONTACT_ID, rawContactInsertIndex)
                .withValue(
                    ContactsContract.Data.MIMETYPE,
                    ContactsContract.CommonDataKinds.StructuredName.CONTENT_ITEM_TYPE
                )
                .withValue(
                    ContactsContract.CommonDataKinds.StructuredName.DISPLAY_NAME,
                    cname
                ) // Name of the person
                .build()
        )

        ops.add(
            ContentProviderOperation.newInsert(ContactsContract.Data.CONTENT_URI)
                .withValueBackReference(ContactsContract.Data.RAW_CONTACT_ID, rawContactInsertIndex)
                .withValue(
                    ContactsContract.Data.MIMETYPE,
                    ContactsContract.CommonDataKinds.Phone.CONTENT_ITEM_TYPE
                )
                .withValue(
                    ContactsContract.CommonDataKinds.Phone.NUMBER,
                    cnum
                ) //Number of the person
                .build()
        )
        Toast.makeText(applicationContext,"contact added $cname $cnum",Toast.LENGTH_LONG).show()
        getContentResolver().applyBatch(ContactsContract.AUTHORITY, ops);
    }


    private fun readContact() {
       /* var rs = contentResolver.query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI,
                                        null,null,cols,
                                        ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME)

        if(rs?.moveToNext()!!)
        {
            Toast.makeText(applicationContext,rs.getString(3),Toast.LENGTH_LONG).show()
        }
                                        */
        val contactList : MutableList<ContactDTO> = ArrayList()

        val rs = contentResolver.query(
            ContactsContract.CommonDataKinds.Phone.CONTENT_URI,
            null, null, null, ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME
        )
        while(rs?.moveToNext()!!){
            val name = rs.getString(rs.getColumnIndex(ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME))
            val number = rs.getString(rs.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER))
            val obj = ContactDTO()
            obj.name = name
            obj.number = number
            contactList.add(obj)
        }
        recyclerView.adapter = ContactAdapter(contactList, this)


        rs.close()


    }
}