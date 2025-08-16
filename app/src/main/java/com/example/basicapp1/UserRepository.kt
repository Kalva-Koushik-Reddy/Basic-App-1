package com.example.basicapp1

import android.content.Context
import android.content.SharedPreferences
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

class UserRepository(private val context: Context) {
    private val sharedPreferences: SharedPreferences = context.getSharedPreferences("user_prefs", Context.MODE_PRIVATE)
    private val gson = Gson()
    private val userListType = object : TypeToken<MutableList<User>>() {}.type
    
    fun registerUser(user: User): RegistrationResult {
        // Validate input
        if (user.firstName.isBlank() || user.lastName.isBlank() || user.mobileNumber.isBlank()) {
            return RegistrationResult.Error("Please fill all fields")
        }
        
        // Validate mobile number format (basic validation)
        if (!isValidMobileNumber(user.mobileNumber)) {
            return RegistrationResult.Error("Please enter a valid mobile number")
        }
        
        // Check if mobile number already exists
        val existingUsers = getUsers()
        if (existingUsers.any { it.mobileNumber == user.mobileNumber }) {
            return RegistrationResult.OldUser
        }
        
        // Add new user
        existingUsers.add(user)
        saveUsers(existingUsers)
        
        return RegistrationResult.Success
    }
    
    private fun getUsers(): MutableList<User> {
        val usersJson = sharedPreferences.getString("users", "[]")
        return try {
            gson.fromJson(usersJson, userListType) ?: mutableListOf()
        } catch (e: Exception) {
            mutableListOf()
        }
    }
    
    private fun saveUsers(users: List<User>) {
        val usersJson = gson.toJson(users)
        sharedPreferences.edit().putString("users", usersJson).apply()
    }
    
    private fun isValidMobileNumber(mobileNumber: String): Boolean {
        // Basic validation: should be 10 digits
        return mobileNumber.matches(Regex("^[0-9]{10}$"))
    }
}

sealed class RegistrationResult {
    object Success : RegistrationResult()
    object OldUser : RegistrationResult()
    data class Error(val message: String) : RegistrationResult()
}
