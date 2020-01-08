package com.example.imgur.view.links

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.imgur.R
import kotlinx.android.synthetic.main.links_fragment.*

class LinksFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.links_fragment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initAdapter()
    }

    private fun initAdapter() {
        rvLinsk.setHasFixedSize(true)
        val linearLayoutManager = LinearLayoutManager(context)
        rvLinsk.layoutManager = linearLayoutManager

        val onClick: (String) -> Unit = { link ->
            Toast.makeText(context, link, Toast.LENGTH_SHORT).show()
            val intent = Intent(Intent.ACTION_VIEW)
            intent.data = Uri.parse(link)
            context?.startActivity(intent)
        }

        getLinkList()?.let {
            val adapter = LinksAdapter(it, onClick)
            rvLinsk.adapter = adapter
        }
    }

    private fun getLinkList(): ArrayList<String>? {
        val sharedPref = activity?.getPreferences(Context.MODE_PRIVATE)
        return sharedPref?.getStringArrayList(getString(R.string.key), arrayListOf())
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
}
