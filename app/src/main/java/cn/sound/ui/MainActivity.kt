package cn.sound.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import cn.sound.R
import cn.sound.model.Sound
import cn.sound.model.VoicePool
import com.xwray.groupie.GroupAdapter
import com.xwray.groupie.Item
import com.xwray.groupie.ViewHolder
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.sound_item.view.*

class MainActivity : AppCompatActivity() {
    private lateinit var voicePool: VoicePool
    private var soundAdapter = GroupAdapter<ViewHolder>()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        voicePool = VoicePool(this)
        drumPadRecycler.layoutManager = StaggeredGridLayoutManager(5, StaggeredGridLayoutManager.VERTICAL)
        drumPadRecycler.adapter = soundAdapter
        voicePool.sounds.forEach {
            soundAdapter.add(SoundItem(it))
        }
    }

    inner class SoundItem(private val sounds: Sound) : Item<ViewHolder>() {
        override fun getLayout() = R.layout.sound_item
        override fun bind(holder: ViewHolder, position: Int) {
            holder.itemView.drumPad.text = sounds.name
            holder.itemView.drumPad.setOnClickListener {
                voicePool.play(sounds)
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        voicePool.release()
    }
}
