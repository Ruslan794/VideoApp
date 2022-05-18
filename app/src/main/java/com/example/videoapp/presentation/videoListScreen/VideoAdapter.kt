package com.example.videoapp.presentation.videoListScreen

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.example.domain.models.Video
import com.example.videoapp.R

class VideoAdapter(
    context: Context,
    var videoList: List<Video>,
    val clickListener: VideoClickListener
) : RecyclerView.Adapter<VideoAdapter.ViewHolder>() {

    private val inflater: LayoutInflater = LayoutInflater.from(context)

    override fun getItemCount(): Int = videoList.size

    private fun getItem(position: Int): Video = videoList[position]

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder =
        ViewHolder(inflater.inflate(R.layout.video_item_view_holder, parent, false))


    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.itemView.setOnClickListener { clickListener.onClick(getItem(position)) }
        holder.bind(getItem(position))
    }

    class ViewHolder(view: View) :
        RecyclerView.ViewHolder(view) {

        private val imageUrl: String =
            "https://commondatastorage.googleapis.com/gtv-videos-bucket/sample/"

        private val thumb: ImageView = itemView.findViewById(R.id.thumb)
        private val title: TextView = itemView.findViewById(R.id.title)

        fun bind(videoItem: Video) {
            thumb.load(imageUrl + videoItem.thumbSource)
            title.text = videoItem.name
        }
    }
}