package com.example.coalba2.ui.view

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.MediaStore
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.example.coalba2.R
import com.example.coalba2.databinding.ActivityProfileEditBinding
import com.example.coalba2.databinding.ActivityStoreEditBinding
import java.io.File

class StoreEditActivity : AppCompatActivity() {
    // 전역 변수로 바인딩 객체 선언
    private var mBinding: ActivityStoreEditBinding? = null
    // 매번 null 체크를 할 필요없이 편의성을 위해 바인딩 변수 재선언
    private val binding get() = mBinding!!
    var imageFile : File? = null

    companion object {
        const val REVIEW_MIN_LENGTH = 10
        // 갤러리 권한 요청
        const val REQ_GALLERY = 1
        // API 호출시 Parameter Key 값
        const val PARAM_KEY_IMAGE = "image"
        const val PARAM_KEY_PRODUCT_ID = "product_id"
        const val PARAM_KEY_REVIEW = "review_content"
        const val PARAM_KEY_RATING = "rating"
    }
    // 이미지를 결과값으로 받는 변수
    private val imageResult = registerForActivityResult(
        ActivityResultContracts.StartActivityForResult()){
            result ->
        if (result.resultCode == RESULT_OK){
            // 이미지를 받으면 ImageView에 적용
            val imageUri = result.data?.data
            imageUri?.let{
                // 서버 업로드를 위해 파일 형태로 변환
                imageFile = File(getRealPathFromURI(it))

                // 이미지를 불러온다
                Glide.with(this)
                    .load(imageUri)
                    .fitCenter()
                    .apply(RequestOptions().override(500,500))
                    .into(binding.ivStoreEdit)
            }
        }
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // 바인딩
        mBinding = ActivityStoreEditBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.ivStoreEditCamera.setOnClickListener {
            selectGallery()
        }
        binding.ivStoreEditBack.setOnClickListener {
            finish()
        }
    }
    // 이미지 실제 경로 반환
    fun getRealPathFromURI(uri: Uri): String {
        val buildName = Build.MANUFACTURER
        if(buildName.equals("Xiaomi")){
            return uri.path!!
        }
        var columnIndex = 0
        val proj = arrayOf(MediaStore.Images.Media.DATA)
        val cursor = contentResolver.query(uri, proj, null,null,null)
        if(cursor!!.moveToFirst()){
            columnIndex = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA)
        }
        val result = cursor.getString(columnIndex)
        cursor.close()
        return result
    }
    // 갤러리를 부르는 메서드
    private fun selectGallery(){
        val writePermission = ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE)
        val readPermission = ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE)

        // 권한 확인
        if(writePermission == PackageManager.PERMISSION_DENIED || readPermission == PackageManager.PERMISSION_DENIED){
            // 권한 요청
            ActivityCompat.requestPermissions(this, arrayOf(Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.READ_EXTERNAL_STORAGE), REQ_GALLERY)
        }else{
            // 권한이 있는 경우 갤러리 실행
            val intent = Intent(Intent.ACTION_PICK)
            // intent의 data와 type을 동시에 설정하는 메서드
            intent.setDataAndType(
                MediaStore.Images.Media.EXTERNAL_CONTENT_URI,"image/*"
            )
            imageResult.launch(intent)
        }
    }
}