package com.example.imgur.view.images

import android.Manifest
import android.app.Activity
import android.content.Context
import android.content.ContextWrapper
import android.content.SharedPreferences
import android.content.pm.PackageManager
import android.content.res.Configuration
import android.graphics.Bitmap
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.navigation.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import com.arellomobile.mvp.presenter.InjectPresenter
import com.arellomobile.mvp.presenter.ProvidePresenter
import com.example.imgur.R
import com.example.imgur.data.helpers.EncodeImageToBase64
import com.example.imgur.data.helpers.ImagesPath
import com.example.imgur.model.Image
import com.example.imgur.view.base.BaseFragment
import dagger.Lazy
import kotlinx.android.synthetic.main.images_fragment.*
import javax.inject.Inject


class ImagesFragment : BaseFragment(), IImagesView {

    @Inject
    lateinit var daggerPresenter: Lazy<ImagesPresenter>

    @InjectPresenter
    lateinit var presenter: ImagesPresenter

    @ProvidePresenter
    fun providePresenter(): ImagesPresenter = daggerPresenter.get()

    val MY_PERMISSIONS_REQUEST_READ_EXTERNAL_STORAGE=111

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.images_fragment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initAdapter()
    }

    private fun initAdapter() {
        rvViewImages.setHasFixedSize(true)
        val spanCount =
            if (resources.configuration.orientation == Configuration.ORIENTATION_LANDSCAPE) 5 else 3

        val gridLayoutManager = GridLayoutManager(requireContext(), spanCount)
        rvViewImages.layoutManager = gridLayoutManager



        context?.let {
            val pathImages = ImagesPath.getAllImagesPath(it?.contentResolver);

            val onClick: (View, Int, Bitmap) -> Unit =
                { view: View, position: Int, bitmap: Bitmap ->
                    Toast.makeText(context, pathImages.get(position), Toast.LENGTH_SHORT).show()
                    presenter.uploadImage(EncodeImageToBase64.encode(bitmap))
                }

            val imageAdapter = ImageAdapter(pathImages, onClick)
            rvViewImages.adapter = imageAdapter
        }
        btn_upload.setOnClickListener {
            view?.findNavController()
                ?.navigate(R.id.action_imageFragment_to_linksFragment)
        }
    }

    override fun handleUploadImage(image: Image) {
        Toast.makeText(context, image.success.toString(), Toast.LENGTH_LONG).show()
        image.data?.link?.let {
            storeLinks(it)
        }
    }

    private fun storeLinks(link: String) {
        val sharedPref = activity?.getPreferences(Context.MODE_PRIVATE) ?: return
        val linkList = sharedPref.getStringArrayList(getString(R.string.key), arrayListOf())
        linkList?.add(link)
        with(sharedPref.edit()) {
            putStringArrayList(getString(R.string.key), linkList)
            apply()
        }
    }

    private fun SharedPreferences.Editor.putStringArrayList(
        key: String,
        list: ArrayList<String>?
    ): SharedPreferences.Editor {
        putString(key, list?.joinToString(",") ?: "")
        return this
    }

    private fun SharedPreferences.getStringArrayList(
        key: String,
        defValue: ArrayList<String>?
    ): ArrayList<String>? {
        val value = getString(key, null)
        if (value.isNullOrBlank())
            return defValue
        return ArrayList(value.split(",").map { it })
    }

    fun requestPermission(){
        context?.let {
            // Here, thisActivity is the current activity
            if (ContextCompat.checkSelfPermission(it,
                    Manifest.permission.READ_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED) {


               it.activity()?.let {
                   // Permission is not granted
                   // Should we show an explanation?
                   if (ActivityCompat.shouldShowRequestPermissionRationale(
                           it,
                           Manifest.permission.READ_CONTACTS
                       )
                   ) {
                       // Show an explanation to the user *asynchronously* -- don't block
                       // this thread waiting for the user's response! After the user
                       // sees the explanation, try again to request the permission.
                   } else {
                       // No explanation needed, we can request the permission.
                       ActivityCompat.requestPermissions(
                           it,
                           arrayOf(Manifest.permission.READ_EXTERNAL_STORAGE),
                           MY_PERMISSIONS_REQUEST_READ_EXTERNAL_STORAGE
                       )

                       // MY_PERMISSIONS_REQUEST_READ_CONTACTS is an
                       // app-defined int constant. The callback method gets the
                       // result of the request.
                   }
               }
            }
                else {
                    // Permission has already been granted
                }
               }

        }


    }
    tailrec fun Context.activity(): Activity? = when {
        this is Activity -> this
        else -> (this as? ContextWrapper)?.baseContext?.activity()
    }
