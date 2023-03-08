package com.example.coalba2.ui.view

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import android.widget.RadioGroup
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.example.coalba2.R
import com.example.coalba2.api.retrofit.RetrofitManager
import com.example.coalba2.data.response.WorkspaceListLookResponseData
import com.example.coalba2.databinding.ActivityStoreRegisterBinding
import com.example.coalba2.ui.fragment.WorkspaceFragment
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody
import org.json.JSONObject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.io.File

class StoreRegisterActivity : AppCompatActivity() {
    // 전역 변수로 바인딩 객체 선언
    private var mBinding: ActivityStoreRegisterBinding? = null
    // 매번 null 체크를 할 필요없이 편의성을 위해 바인딩 변수 재선언
    private val binding get() = mBinding!!
    var imageFile : File? = null
    var imageWideUri: Uri? = null
    var storeRegister: String = ""
    var storeRegisterPayment: String = ""

    companion object {
        // 갤러리 권한 요청
        const val REQ_GALLERY = 1
    }
    // 이미지를 결과값으로 받는 변수
    private val imageResult = registerForActivityResult(
        ActivityResultContracts.StartActivityForResult()){
            result ->
        if (result.resultCode == RESULT_OK){
            // 이미지를 받으면 ImageView에 적용
            val imageUri = result.data?.data
            imageWideUri = imageUri
            imageUri?.let{
                // 서버 업로드를 위해 파일 형태로 변환
                // imageFile = File(getRealPathFromURI(it))

                // 이미지를 불러온다
                Glide.with(this)
                    .load(imageUri)
                    .fitCenter()
                    .apply(RequestOptions().override(500,500))
                    .into(binding.ivStoreRegister)
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // 바인딩
        mBinding = ActivityStoreRegisterBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.ivStoreRegisterCamera.setOnClickListener {
            selectGallery()
        }
        binding.ivStoreRegisterBack.setOnClickListener {
            finish()
        }
        binding.rgStoreRegister.setOnCheckedChangeListener { radioGroup, i ->
            when(i){
                R.id.rb_fix -> storeRegister = "FIXED_WORK"
                R.id.rb_weekly -> storeRegister = "WEEKLY_WORK"
                R.id.rb_monthly -> storeRegister = "MONTHLY_WORK"
            }
        }
        binding.rgStoreRegisterPaymentmethod.setOnCheckedChangeListener { radioGroup, i ->
            when(i){
                R.id.rb_weeklywage -> storeRegisterPayment = "WEEKLY_PAY"
                R.id.rb_monthlywage -> storeRegisterPayment = "MONTHLY_PAY"
            }
        }
        // 워크스페이스 추가 서버 연동
        binding.btnStoreRegisterFinish.setOnClickListener {
            Log.d("storeRegister", "시작")
            Log.d("datavalue", "multipart값=> " + imageWideUri)
            imageFile = File(getRealPathFromURI(imageWideUri!!))
            // 서버로 보내기 위해 RequestBody객체로 변환
            val requestFile = RequestBody.create("image/*".toMediaTypeOrNull(), imageFile!!)
            val body =
                MultipartBody.Part.createFormData("imageFile", imageFile!!.name, requestFile)
            // String 값에 "" 없애기
            val jsonObj = JSONObject()
            jsonObj.put("name", binding.etStoreRegisterName.text)
            jsonObj.put("phoneNumber", binding.etStoreRegisterPhonenumber.text)
            jsonObj.put("address", binding.etStoreRegisterAddress.text)
            jsonObj.put("businessNumber", binding.etStoreRegisterNumber.text)
            jsonObj.put("workType", storeRegister)
            jsonObj.put("payType", storeRegisterPayment)
            val body2 = RequestBody.create("application/json".toMediaTypeOrNull(), jsonObj.toString())
            Log.d("Network_WorkspaceAdd_data", body2.toString())
            // 현재 사용자의 정보를 받아올 것을 명시
            // 서버 통신은 I/O 작업이므로 비동기적으로 받아올 Callback 내부 코드는 나중에 데이터를 받아오고 실행
            RetrofitManager.workspaceService?.workspaceAdd(body2,body)?.enqueue(object :
                Callback<WorkspaceListLookResponseData> {
                override fun onResponse(call: Call<WorkspaceListLookResponseData>, response: Response<WorkspaceListLookResponseData>) {
                    // 네트워크 통신에 성공한 경우
                    if (response.isSuccessful) {
                        Log.d("WorkspaceAdd", "success")
                        val data = response.body()
                        Log.d("responsevalue", "response값=> " + data)
                        val intent = Intent(this@StoreRegisterActivity, WorkspaceFragment::class.java)
                            .putExtra("responseData", response.body()) //워크스페이스 등록 api 응답 데이터 이전 화면인 WorkspaceFragment에게 전달
                        setResult(RESULT_OK, intent)
                        finish()
                    }else { // 이곳은 에러 발생할 경우 실행됨
                        Log.d("WorkspaceAdd", "fail")
                    }
                }
                override fun onFailure(call: Call<WorkspaceListLookResponseData>, t: Throwable) {
                    Log.d("WorkspaceAdd", "error!")
                }
            })
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