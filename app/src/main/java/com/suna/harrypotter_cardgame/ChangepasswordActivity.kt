package com.suna.harrypotter_cardgame

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.suna.harrypotter_cardgame.databinding.ActivityChangepasswordBinding

class ChangepasswordActivity : AppCompatActivity() {
    lateinit var binding: ActivityChangepasswordBinding
    private lateinit var auth: FirebaseAuth
    private var eMail:String?=null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        var binding = ActivityChangepasswordBinding.inflate(layoutInflater)
        setContentView(binding.root)
        auth = FirebaseAuth.getInstance()

        binding.resetpassword.setOnClickListener{
            eMail=binding.editTextTextEmailAddress.text.toString().trim()
            if (TextUtils.isEmpty(eMail)){
                Toast.makeText(this@ChangepasswordActivity, "Lütfen Emailinizi Doğru Girdiğinizden Emin Olunuz", Toast.LENGTH_LONG).show()
                return@setOnClickListener
            }
            else{
                auth.sendPasswordResetEmail(eMail!!).addOnCompleteListener {
                    if(it.isSuccessful){
                        Toast.makeText(this@ChangepasswordActivity, "Parola Sıfırlama Bağlantısı Başarıyla Emailinize Gönderilmiştir", Toast.LENGTH_LONG).show()
                        auth.signOut()
                        val intent = Intent(applicationContext,MainActivity::class.java)
                        startActivity(intent)
                        finish()
                    }
                    else{
                        Toast.makeText(this@ChangepasswordActivity, "Parola Sıfırlama Bağlantısı Emailinize Gönderilememiştir !!", Toast.LENGTH_LONG).show()
                    }
                }
            }
        }
    }
}