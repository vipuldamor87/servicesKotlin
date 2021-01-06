package com.vipuldamor87.foregroundservice.ContentProvider

import android.Manifest
import android.content.ContentProviderOperation
import android.content.pm.PackageManager
import android.net.Uri
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
import kotlinx.android.synthetic.main.contactchild.view.*


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
        val itemTouchHelperCallBack = object : ItemTouchHelper.SimpleCallback(
            0,
            ItemTouchHelper.RIGHT or ItemTouchHelper.LEFT
        ) {
            override fun onMove(
                recyclerView: RecyclerView,
                viewHolder: RecyclerView.ViewHolder,
                target: RecyclerView.ViewHolder
            ): Boolean {
                return false
            }

            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, Direction: Int) {
             // var posi = viewHolder.adapterPosition
               var name = viewHolder.itemView.TvName.text.toString()
                Toast.makeText(applicationContext,"$name deleted",Toast.LENGTH_SHORT).show()
              deleteContact(name)

            }
        }

        val itemTouchHelper = ItemTouchHelper(itemTouchHelperCallBack)
        itemTouchHelper.attachToRecyclerView(recyclerView)


        readContact.setOnClickListener {
            readContact()
        }
        addContact.setOnClickListener {
            addnewContact(given_name = cName.text.toString(), mobile = cNum.text.toString())
        }

    }


    private fun getRawContactIdByName(givenName: String): Long {
        val contentResolver = contentResolver

        // Query raw_contacts table by display name field ( given_name family_name ) to get raw contact id.

        // Create query column array.
        val queryColumnArr = arrayOf(ContactsContract.RawContacts._ID)

        // Create where condition clause.
        val displayName = "$givenName"
        val whereClause =
            ContactsContract.RawContacts.DISPLAY_NAME_PRIMARY + " = '" + displayName + "'"

        // Query raw contact id through RawContacts uri.
        val rawContactUri = ContactsContract.RawContacts.CONTENT_URI

        // Return the query cursor.
        val cursor = contentResolver.query(rawContactUri, queryColumnArr, whereClause, null, null)
        var rawContactId: Long = -1
        if (cursor != null) {
            // Get contact count that has same display name, generally it should be one.
            val queryResultCount = cursor.count
            // This check is used to avoid cursor index out of bounds exception. android.database.CursorIndexOutOfBoundsException
            if (queryResultCount > 0) {
                // Move to the first row in the result cursor.
                cursor.moveToFirst()
                // Get raw_contact_id.
                rawContactId =
                    cursor.getLong(cursor.getColumnIndex(ContactsContract.RawContacts._ID))
            }
        }
        return rawContactId
    }





    fun deleteContact(givenName: String) {

        // First select raw contact id by given name and family name.
        // First select raw contact id by given name and family name.
        val rawContactId = getRawContactIdByName(givenName)

        val contentResolver = contentResolver

        //******************************* delete data table related data ****************************************
        // Data table content process uri.

        //******************************* delete data table related data ****************************************
        // Data table content process uri.
        val dataContentUri: Uri = ContactsContract.Data.CONTENT_URI

        // Create data table where clause.

        // Create data table where clause.
        val dataWhereClauseBuf = StringBuffer()
        dataWhereClauseBuf.append(ContactsContract.Data.RAW_CONTACT_ID)
        dataWhereClauseBuf.append(" = ")
        dataWhereClauseBuf.append(rawContactId)

        // Delete all this contact related data in data table.

        // Delete all this contact related data in data table.
        contentResolver.delete(dataContentUri, dataWhereClauseBuf.toString(), null)


        //******************************** delete raw_contacts table related data ***************************************
        // raw_contacts table content process uri.


        //******************************** delete raw_contacts table related data ***************************************
        // raw_contacts table content process uri.
        val rawContactUri: Uri = ContactsContract.RawContacts.CONTENT_URI

        // Create raw_contacts table where clause.

        // Create raw_contacts table where clause.
        val rawContactWhereClause = StringBuffer()
        rawContactWhereClause.append(ContactsContract.RawContacts._ID)
        rawContactWhereClause.append(" = ")
        rawContactWhereClause.append(rawContactId)

        // Delete raw_contacts table related data.

        // Delete raw_contacts table related data.
        contentResolver.delete(rawContactUri, rawContactWhereClause.toString(), null)

        //******************************** delete contacts table related data ***************************************
        // contacts table content process uri.

        //******************************** delete contacts table related data ***************************************
        // contacts table content process uri.
        val contactUri: Uri = ContactsContract.Contacts.CONTENT_URI

        // Create contacts table where clause.

        // Create contacts table where clause.
        val contactWhereClause = StringBuffer()
        contactWhereClause.append(ContactsContract.Contacts._ID)
        contactWhereClause.append(" = ")
        contactWhereClause.append(rawContactId)

        // Delete raw_contacts table related data.

        // Delete raw_contacts table related data.
        contentResolver.delete(contactUri, contactWhereClause.toString(), null)

        readContact()
    }



    private fun addnewContact(
        given_name: String,
        name: String = "",
        mobile: String,
        home: String = "",
        email: String = ""
    ) {
        val contact = ArrayList<ContentProviderOperation>()
        contact.add(
            ContentProviderOperation.newInsert(ContactsContract.RawContacts.CONTENT_URI)
                .withValue(ContactsContract.RawContacts.ACCOUNT_TYPE, null)
                .withValue(ContactsContract.RawContacts.ACCOUNT_NAME, null)
                .build()
        )

        // first and last names
        contact.add(
            ContentProviderOperation.newInsert(ContactsContract.Data.CONTENT_URI)
                .withValueBackReference(ContactsContract.RawContacts.Data.RAW_CONTACT_ID, 0)
                .withValue(
                    ContactsContract.RawContacts.Data.MIMETYPE,
                    ContactsContract.CommonDataKinds.StructuredName.CONTENT_ITEM_TYPE
                )
                .withValue(ContactsContract.CommonDataKinds.StructuredName.GIVEN_NAME, given_name)
                .withValue(ContactsContract.CommonDataKinds.StructuredName.FAMILY_NAME, name)
                .build()
        )

        // Contact No Mobile
        contact.add(
            ContentProviderOperation.newInsert(ContactsContract.Data.CONTENT_URI)
                .withValueBackReference(ContactsContract.RawContacts.Data.RAW_CONTACT_ID, 0)
                .withValue(
                    ContactsContract.Data.MIMETYPE,
                    ContactsContract.CommonDataKinds.Phone.CONTENT_ITEM_TYPE
                )
                .withValue(ContactsContract.CommonDataKinds.Phone.NUMBER, mobile)
                .withValue(
                    ContactsContract.CommonDataKinds.Phone.TYPE,
                    ContactsContract.CommonDataKinds.Phone.TYPE_MOBILE
                )
                .build()
        )

        // Contact Home
        contact.add(
            ContentProviderOperation.newInsert(ContactsContract.Data.CONTENT_URI)
                .withValueBackReference(ContactsContract.RawContacts.Data.RAW_CONTACT_ID, 0)
                .withValue(
                    ContactsContract.Data.MIMETYPE,
                    ContactsContract.CommonDataKinds.Phone.CONTENT_ITEM_TYPE
                )
                .withValue(ContactsContract.CommonDataKinds.Phone.NUMBER, home)
                .withValue(
                    ContactsContract.CommonDataKinds.Phone.TYPE,
                    ContactsContract.CommonDataKinds.Phone.TYPE_HOME
                )
                .build()
        )

        // Email    `
        contact.add(
            ContentProviderOperation.newInsert(ContactsContract.Data.CONTENT_URI)
                .withValueBackReference(ContactsContract.RawContacts.Data.RAW_CONTACT_ID, 0)
                .withValue(
                    ContactsContract.Data.MIMETYPE,
                    ContactsContract.CommonDataKinds.Email.CONTENT_ITEM_TYPE
                )
                .withValue(ContactsContract.CommonDataKinds.Email.DATA, email)
                .withValue(
                    ContactsContract.CommonDataKinds.Email.TYPE,
                    ContactsContract.CommonDataKinds.Email.TYPE_WORK
                )
                .build()
        )
        try {
            val results = contentResolver.applyBatch(ContactsContract.AUTHORITY, contact)
            readContact()
        } catch (e: Exception) {
            e.printStackTrace()
        }
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