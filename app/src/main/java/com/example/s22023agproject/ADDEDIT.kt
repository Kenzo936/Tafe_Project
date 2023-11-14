package com.example.s22023agproject

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Toast
import com.example.s22023agproject.databinding.AddeditBinding

class ADDEDIT:Activity(), View.OnClickListener {
    private lateinit var binding: AddeditBinding
    //create a DBHandler object
    val dbh = DBHandler(this,null)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = AddeditBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.btnSave.setOnClickListener(this)
        binding.btnCancel.setOnClickListener(this)
        val extras = intent.extras
        if(extras!=null){
            //read id and from intent
            val id:Int = extras.getInt("ID")
            //get person object from database from this ID
            val person = dbh.getPerson(id)
            // populate the edit texts
            binding.etID.setText(person.id.toString())
            binding.etName.setText(person.name)
            binding.etImageFile.setText(person.imageFile)
            binding.etAddress.setText(person.address)
            binding.etMobile.setText(person.mobile)
            binding.etEmail.setText(person.email)
            binding.ivImageFile.setImageResource(
                this.resources.getIdentifier(person.imageFile,"drawable", "com.example.s22023agproject"))
        }
    }
    override fun onClick(btn: View) {
        // code to handle btnsave and btncancel click
        when(btn.id){
            R.id.btnSave->{
                //read the id and assign 0 if no id
                val cid:Int = binding.etID.text.toString().toIntOrNull()?:0
                if(cid==0){
                    addPerson()
                }else{
                    editPerson(cid)
                }
            }
            R.id.btnCancel->{
                 goBack()
            }
        }
    }
    private fun goBack() {
        // discard changes and go back to main activity
        val mainAct = Intent(this,MainActivity::class.java)
        // start Main Activity
        startActivity(mainAct)
    }
    private fun editPerson(cid: Int) {
        //create a person object and fill with new values
        val person = dbh.getPerson(cid)
        //read and assign name, mobile, address, email, imagefile
        person.name = binding.etName.text.toString()
        person.mobile = binding.etMobile.text.toString()
        person.address = binding.etAddress.text.toString()
        person.email = binding.etEmail.text.toString()
        person.imageFile = binding.etImageFile.text.toString()
        //call DBhandler update function
        dbh.updatePerson(person)
        //display confirmation and go to main activity
        Toast.makeText(this,"Person has been updated",Toast.LENGTH_LONG).show()
        //go back to main activity
        goBack()
    }
    private fun addPerson() {
        //read all the input and assign to person object
        val person = Person()
        person.name = binding.etName.text.toString()
        person.mobile = binding.etMobile.text.toString()
        person.address = binding.etAddress.text.toString()
        person.email = binding.etEmail.text.toString()
        person.imageFile = binding.etImageFile.text.toString()
        //cal dbhandler add function
        dbh.addPerson(person)
        //confirm and display message
        Toast.makeText(this,"New Person has been added",Toast.LENGTH_LONG).show()
        //go back to main activity
        goBack()
    }
}
