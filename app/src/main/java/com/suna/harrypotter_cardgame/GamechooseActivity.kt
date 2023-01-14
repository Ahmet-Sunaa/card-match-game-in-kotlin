package com.suna.harrypotter_cardgame

import android.content.Intent
import android.media.MediaPlayer
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.CheckBox
import com.google.firebase.auth.FirebaseAuth
import com.suna.harrypotter_cardgame.databinding.ActivityGamechooseBinding

class GamechooseActivity : AppCompatActivity() {
    private lateinit var binding: ActivityGamechooseBinding
    private lateinit var cb2x2: CheckBox
    private lateinit var cb4x4: CheckBox
    private lateinit var cb6x6: CheckBox
    private var choose:Byte=0
    var a : Int=0
    var mMediaPlayer: MediaPlayer? = null
    private lateinit var auth: FirebaseAuth
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivityGamechooseBinding.inflate(layoutInflater)
        setContentView(binding.root)
        auth=FirebaseAuth.getInstance()
        val b: SoundData = intent.getSerializableExtra("key") as SoundData
        a =b.x
        if(a==1)
            playSound()
        else
            pauseSound()
        binding.singleplayerbutton.setOnClickListener {
            cb2x2=findViewById(binding.gamemode2x2.id);
            cb4x4=findViewById(binding.gamemode4x4.id);
            cb6x6=findViewById(binding.gamemode6x6.id);

            if(cb2x2.isChecked && !cb4x4.isChecked && !cb6x6.isChecked){
                choose=1
                val intent = Intent(this,SingleplayerstartActivity::class.java)
                intent.putExtra("choose",choose)
                val data = SoundData(a)
                intent.putExtra("key",data)
                pauseSound()
                startActivity(intent)
                finish()
            }
            else if(!cb2x2.isChecked && cb4x4.isChecked && !cb6x6.isChecked){
                choose=2
                val intent = Intent(this,SingleplayerstartActivity::class.java)
                intent.putExtra("choose",choose)
                val data = SoundData(a)
                intent.putExtra("key",data)
                startActivity(intent)
                pauseSound()
                finish()
            }
            else if(!cb2x2.isChecked && !cb4x4.isChecked && cb6x6.isChecked){
                choose=3
                val intent = Intent(this,SingleplayerstartActivity::class.java)
                intent.putExtra("choose",choose)
                val data = SoundData(a)
                intent.putExtra("key",data)
                startActivity(intent)
                pauseSound()
                finish()
            }
        }
        binding.multiplayerbutton.setOnClickListener {
            cb2x2=findViewById(binding.gamemode2x2.id);
            cb4x4=findViewById(binding.gamemode4x4.id);
            cb6x6=findViewById(binding.gamemode6x6.id);

            if(cb2x2.isChecked && !cb4x4.isChecked && !cb6x6.isChecked){
                choose=1
                val intent = Intent(this,MultiplayerstartActivity::class.java)
                intent.putExtra("choose",choose)
                val data = SoundData(a)
                intent.putExtra("key",data)
                pauseSound()
                startActivity(intent)
                finish()
            }
            else if(!cb2x2.isChecked && cb4x4.isChecked && !cb6x6.isChecked){
                choose=2
                val intent = Intent(this,MultiplayerstartActivity::class.java)
                intent.putExtra("choose",choose)
                val data = SoundData(a)
                intent.putExtra("key",data)
                pauseSound()
                startActivity(intent)
                finish()
            }
            else if(!cb2x2.isChecked && !cb4x4.isChecked && cb6x6.isChecked){
                choose=3
                val intent = Intent(this,MultiplayerstartActivity::class.java)
                intent.putExtra("choose",choose)
                val data = SoundData(a)
                intent.putExtra("key",data)
                pauseSound()
                startActivity(intent)
                finish()
            }
        }
        binding.getbackChangePass.setOnClickListener {
            val intent = Intent(this,ChangepasswordActivity::class.java)
            val data = SoundData(a)
            intent.putExtra("key",data)
            startActivity(intent)
            pauseSound()
            finish()
        }
        binding.logoutButton.setOnClickListener {
            auth.signOut()
            val intent = Intent(this,MainActivity::class.java)
            val data = SoundData(a)
            intent.putExtra("key",data)
            pauseSound()
            startActivity(intent)
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