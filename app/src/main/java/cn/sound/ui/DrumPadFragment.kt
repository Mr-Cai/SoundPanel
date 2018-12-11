package cn.sound.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import cn.sound.R
import cn.sound.model.Sound
import cn.sound.model.VoicePool
import com.xwray.groupie.GroupAdapter
import com.xwray.groupie.Item
import com.xwray.groupie.ViewHolder
import kotlinx.android.synthetic.main.fragment_drum_pad.*
import kotlinx.android.synthetic.main.sound_item.view.*

class DrumPadFragment : Fragment() {
    private lateinit var voicePool: VoicePool
    private var soundAdapter = GroupAdapter<ViewHolder>()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        voicePool = VoicePool(activity!!)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?) =
        inflater.inflate(R.layout.fragment_drum_pad, container, false)!!

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        drumPadRecycler.layoutManager = GridLayoutManager(activity, 3)
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
