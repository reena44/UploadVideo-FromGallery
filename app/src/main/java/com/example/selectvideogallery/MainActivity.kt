package com.example.selectvideogallery

import android.app.Activity
import android.app.PendingIntent.getActivity
import android.content.Intent
import android.database.Cursor
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.provider.MediaStore
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_main.*


class MainActivity : AppCompatActivity() {

    private val RESULT_CANCELED: Any? = null

    private val VIDEO_DIRECTORY = "/demonuts"
    private val GALLERY = 1
    private  var CAMERA:Int = 2

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        btn.setOnClickListener {
            showVideoChooserDialog()
            }
        }

    private fun showVideoChooserDialog() {
        if (Build.VERSION.SDK_INT <= 19) {
            val i = Intent()
            i.type = "video/*"
            i.action = Intent.ACTION_GET_CONTENT
            i.addCategory(Intent.CATEGORY_OPENABLE)
            startActivityForResult(i, 20)
        } else if (Build.VERSION.SDK_INT > 19) {
            val intent = Intent(
                Intent.ACTION_PICK,
                MediaStore.Video.Media.EXTERNAL_CONTENT_URI
            )
            startActivityForResult(intent, 20)
        }

    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == Activity.RESULT_OK) {

            if (requestCode == 10) {
                var  selectedImageUri = data!!.data;
                val selectedImagePath = getRealPathFromURI(selectedImageUri)
            } else if (requestCode == 20) {
                val selectedVideoUri = data!!.getData();
                val  selectedVideoPath = getRealPathFromURI(selectedVideoUri);
            }
        }
    }

    private fun getRealPathFromURI(uri: Uri?): Any? {
        if (uri == null) {
            return null
        }
        val projection =
            arrayOf(MediaStore.Images.Media.DATA)
        val cursor: Cursor = getActivity(applicationContext).getContentResolver().query(uri, projection, null, null, null)
        val column_index = cursor
            .getColumnIndexOrThrow(MediaStore.Images.Media.DATA)
        cursor.moveToFirst()
        return cursor.getString(column_index)
        return uri.getPath()


    }


}
