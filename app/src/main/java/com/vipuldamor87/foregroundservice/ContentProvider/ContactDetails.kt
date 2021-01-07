package com.vipuldamor87.foregroundservice.ContentProvider

import android.R.attr
import android.content.ContentProviderOperation
import android.content.OperationApplicationException
import android.database.Cursor
import android.os.Bundle
import android.os.RemoteException
import android.provider.ContactsContract
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.vipuldamor87.foregroundservice.R
import kotlinx.android.synthetic.main.activity_contact_details.*


class ContactDetails : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_contact_details)
        val name = intent.getStringExtra("name")
        val number = intent.getStringExtra("number")
        coDetails.setText("For $name And $number")
        val Contactid : Long = getRawContactIdByName(name)
        //coNum.setText("$number")

        val newNum = coNum.text

        updateContact.setOnClickListener {
            UpdateContactNameNum(name = "$name", phone = "$newNum", ContactId = Contactid.toString())
        }


       // Toast.makeText(applicationContext,"$name $number",Toast.LENGTH_SHORT).show()
    }

    private fun UpdateContactNameNum(
            name: String = "",
            phone: String = "",
            ContactId: String = "",
            email: String = ""
    ) {
        val cr = contentResolver

        val where = ContactsContract.Data.DISPLAY_NAME + " = ? AND " +
                ContactsContract.Data.MIMETYPE + " = ? AND " + ContactsContract.CommonDataKinds.Phone.TYPE + " = ? "
        val params = arrayOf<String>(name,
                ContactsContract.CommonDataKinds.Phone.CONTENT_ITEM_TYPE, ContactsContract.CommonDataKinds.Phone.TYPE_MOBILE.toString())

        val phoneCur: Cursor? = managedQuery(ContactsContract.Data.CONTENT_URI, null, where, params, null)

        val ops = ArrayList<ContentProviderOperation>()

        if (null == phoneCur) {
         // createContact(name, phone)
        } else {
            ops.add(ContentProviderOperation.newUpdate(ContactsContract.Data.CONTENT_URI)
                    .withSelection(where, params)
                    .withValue(ContactsContract.CommonDataKinds.Phone.DATA, phone)
                    .build())
        }

        phoneCur!!.close()

        try {
            cr.applyBatch(ContactsContract.AUTHORITY, ops)
        } catch (e: RemoteException) {
            // TODO Auto-generated catch block
            e.printStackTrace()
        } catch (e: OperationApplicationException) {
            // TODO Auto-generated catch block
            e.printStackTrace()
        }

        Toast.makeText(applicationContext, "Updated the phone number of $name to: $phone", Toast.LENGTH_SHORT).show()


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
}