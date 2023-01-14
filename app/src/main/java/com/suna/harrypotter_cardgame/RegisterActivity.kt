package com.suna.harrypotter_cardgame

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.suna.harrypotter_cardgame.databinding.ActivityRegisterBinding


class RegisterActivity : AppCompatActivity() {

    private var username:String?=null
    private var password:String?=null
    private var eMail:String?=null
    lateinit var binding: ActivityRegisterBinding
    private lateinit var auth:FirebaseAuth
    private var dbRef:DatabaseReference?=null
    var db:FirebaseDatabase?=null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding=ActivityRegisterBinding.inflate(layoutInflater)
        setContentView(binding.root)
        auth = FirebaseAuth.getInstance()
        db = FirebaseDatabase.getInstance()
        dbRef = db?.reference!!.child("user")



        binding.registerpagebutton.setOnClickListener{
            eMail = binding.emailAdress.text.toString().trim()
            username = binding.registerusernametext.text.toString()
            password = binding.registerpasswordtext.text.toString()

            if(TextUtils.isEmpty(eMail)|| TextUtils.isEmpty(username)|| TextUtils.isEmpty(password)){
                Toast.makeText(this@RegisterActivity, "Lütfen formu doldurduğunuzdan emin olunuz", Toast.LENGTH_LONG).show()
                return@setOnClickListener
            }
            else{
                auth.createUserWithEmailAndPassword(eMail!!, password!!).addOnCompleteListener(this){
                    if(it.isSuccessful){
                        var currentUser= auth.currentUser
                        var currentUserDB = currentUser?.let{it1 ->
                            dbRef?.child(it1.uid)
                        }
                        currentUserDB?.child("user")?.setValue(username)
                        Toast.makeText(this@RegisterActivity, "Kayıt Başarılı", Toast.LENGTH_SHORT).show()
                    }
                }
                intent = Intent(applicationContext,MainActivity::class.java)
                startActivity(intent)
                finish()
            }
        }



    }


}