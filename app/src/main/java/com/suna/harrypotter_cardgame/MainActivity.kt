package com.suna.harrypotter_cardgame


import android.content.Intent
import android.media.MediaPlayer
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.suna.harrypotter_cardgame.databinding.ActivityMainBinding


class MainActivity : AppCompatActivity() {
    private lateinit var auth: FirebaseAuth
    lateinit var binding: ActivityMainBinding
    var mMediaPlayer: MediaPlayer? = null
    var a : Int=1
    private var eMail:String?=null
    private var password:String?=null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        playSound()
        auth=FirebaseAuth.getInstance()
        var currentUser=auth.currentUser
        if(currentUser!=null){
            intent=Intent(applicationContext,GamechooseActivity::class.java)
            val data=SoundData(a)
            intent.putExtra("key",data)
            startActivity(intent)
            pauseSound()
            finish()
        }

        binding.registerbutton.setOnClickListener {
            val intent = Intent(this,RegisterActivity::class.java)
            startActivity(intent)
            finish()
        }

        binding.changepasswordbutton.setOnClickListener {
            val intent = Intent(this,ChangepasswordActivity::class.java)
            startActivity(intent)
            finish()
        }

        binding.loginbutton.setOnClickListener {
            eMail = binding.usernametext.text.toString()
            password = binding.passwordtext.text.toString()
            if(TextUtils.isEmpty(eMail) || TextUtils.isEmpty(password)){
                Toast.makeText(this@MainActivity, "Lütfen Formu Doldurduğunuzdan Emin Olunuz", Toast.LENGTH_LONG).show()
                return@setOnClickListener
            }
            else{
                auth.signInWithEmailAndPassword(eMail!!,password!!).addOnCompleteListener(this){
                    if(it.isSuccessful){
                        intent=Intent(applicationContext,GamechooseActivity::class.java)
                        val data=SoundData(a)
                        intent.putExtra("key",data)
                        startActivity(intent)
                        pauseSound()
                        finish()
                    }
                    else{
                        Toast.makeText(this@MainActivity, "Hatalı Giriş Lütfen Tekrar Deneyin", Toast.LENGTH_LONG).show()
                        return@addOnCompleteListener
                    }
                }
            }
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