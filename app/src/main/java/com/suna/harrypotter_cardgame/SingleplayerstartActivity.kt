package com.suna.harrypotter_cardgame

import android.content.Intent
import android.media.MediaPlayer
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import com.google.firebase.database.*
import com.suna.harrypotter_cardgame.databinding.ActivitySingleplayerstartBinding
import kotlin.random.Random


class SingleplayerstartActivity : AppCompatActivity() {
    var mMediaPlayer: MediaPlayer? = null
    var a : Int=0
    lateinit var binding: ActivitySingleplayerstartBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding=ActivitySingleplayerstartBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val ids= arrayListOf<Int>(0,1,2,3,4,5,6,7,8,9,10,11,12,13,14,15,16,17,18,19,20,21,22,
        23,24,25,26,27,28,29,30,31,32,33,34,35,36,37,38,39,40,41,42,43)

        val b: SoundData = intent.getSerializableExtra("key") as SoundData
        var choose=intent.getByteExtra("choose",-1)
        a =b.x
        if(a==1)
            playSound()
        else
            pauseSound()

        binding.startbutton.setOnClickListener {
            if(choose==1.toByte()) {
                var indexes= mutableSetOf<Int>()
                while(indexes.size<2){
                    var randomIndex=Random.nextInt(ids.size)
                    if(indexes.add(randomIndex)){
                        println("eleman eklendi")
                    }
                    else{
                        println("eleman eklenemedi")
                    }
                }
                val indexArr=indexes.toIntArray()
                val intent = Intent(this,Singleplayer2x2Activity::class.java)
                val data=SoundData(a)
                intent.putExtra("key",data)
                intent.putExtra("randindexes",indexArr)
                startActivity(intent)
                pauseSound()
                finish()
            }else if(choose==2.toByte()){
                var indexes= mutableSetOf<Int>()
                while(indexes.size<8){
                    var randomIndex=Random.nextInt(ids.size)
                    indexes.add(randomIndex)
                }
                val indexArr=indexes.toIntArray()
                val intent = Intent(this,Singleplayer4x4Activity::class.java)
                val data=SoundData(a)
                intent.putExtra("key",data)
                intent.putExtra("randindexes",indexArr)
                startActivity(intent)
                pauseSound()
                finish()
            }else if(choose==3.toByte()){
                var indexes= mutableSetOf<Int>()
                while(indexes.size<18){
                    var randomIndex=Random.nextInt(ids.size)
                    indexes.add(randomIndex)
                }
                val indexArr=indexes.toIntArray()
                val intent = Intent(this,Singleplayer6x6Activity::class.java)
                val data=SoundData(a)
                intent.putExtra("key",data)
                intent.putExtra("randindexes",indexArr)
                startActivity(intent)
                pauseSound()
                finish()
            }
            else{
                val intent = Intent(this,GamechooseActivity::class.java)
                val data=SoundData(a)
                intent.putExtra("key",data)
                startActivity(intent)
                pauseSound()
                finish()
            }
        }
        binding.getbackButton2.setOnClickListener {
            val intent = Intent(this,GamechooseActivity::class.java)
            val data=SoundData(a)
            intent.putExtra("key",data)
            startActivity(intent)
            pauseSound()
            finish()
        }

    }

    fun playSound() {
        if (mMediaPlayer == null) {
            mMediaPlayer = MediaPlayer.create(this, R.raw.prologue)
            mMediaPlayer!!.isLooping = true
            mMediaPlayer!!.start()
        } else mMediaPlayer!!.start()
    }

    // 2. Pause playback
    fun pauseSound() {
        if (mMediaPlayer?.isPlaying == true) {
            mMediaPlayer?.pause()
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        val menuInflater = menuInflater
        menuInflater.inflate(R.menu.voice,menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if(item.itemId==R.id.app_bar_switch){
            if(a==0){
                a++;
                playSound()
            }else{
                a=0;
                pauseSound()
            }
        }
        return super.onOptionsItemSelected(item)
    }
}