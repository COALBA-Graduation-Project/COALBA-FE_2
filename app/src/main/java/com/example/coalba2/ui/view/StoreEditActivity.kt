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
import android.view.View
import android.widget.Adapter
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.example.coalba2.R
import com.example.coalba2.api.retrofit.RetrofitManager
import com.example.coalba2.data.response.*
import com.example.coalba2.databinding.ActivityProfileEditBinding
import com.example.coalba2.databinding.ActivityStoreEditBinding
import com.example.coalba2.ui.adapter.PartTimeJobManageAdapter
import com.example.coalba2.ui.adapter.StoreListAdapter
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody
import org.json.JSONObject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.io.File

class StoreEditActivity : AppCompatActivity() {
    // 전역 변수로 바인딩 객체 선언
    private var mBinding: ActivityStoreEditBinding? = null
    // 매번 null 체크를 할 필요없이 편의성을 위해 바인딩 변수 재선언
    private val binding get() = mBinding!!
    var imageFile : File? = null
    var imageWideUri: Uri? = null
    var storeRegister: String = ""
    var storeRegisterPayment: String = ""
    var storeList : Array<String> = emptyArray()

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

        // 나의 워크스페이스 리스트 조회 서버 연동
        RetrofitManager.workspaceService?.workspaceListLook()?.enqueue(object:
            Callback<WorkspaceListLookResponseData> {
            override fun onResponse(
                call: Call<WorkspaceListLookResponseData>,
                response: Response<WorkspaceListLookResponseData>) {
                if(response.isSuccessful){
                    Log.d("WorkspaceListLook_edit", "success")
                    val data = response.body()
                    val num = data!!.workspaceList.count()
                    Glide.with(this@StoreEditActivity).load(data.workspaceList.get(0).imageUrl).into(binding.ivStoreEdit)

                    for(i in 0..num-1){
                        val itemdata = response.body()?.workspaceList?.get(i)
                        storeList = storeList.plus(itemdata!!.name)
                    }
                    val adapter = ArrayAdapter(this@StoreEditActivity, android.R.layout.simple_list_item_1, storeList)
                    adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
                    binding.spinnerStore.adapter = adapter
                    binding.spinnerStore.onItemSelectedListener = object: AdapterView.OnItemSelectedListener{
                        override fun onItemSelected(p0: AdapterView<*>?, p1: View?, position: Int, p3: Long) {
                            if(position!=0){
                                Toast.makeText(this@StoreEditActivity, storeList[position],Toast.LENGTH_SHORT).show()
                            }
                        }
                        override fun onNothingSelected(p0: AdapterView<*>?) {

                        }
                    }
                }else{
                    // 이곳은 에러 발생할 경우 실행됨
                    Log.d("WorkspaceListLook_edit", "fail")
                }
            }
            override fun onFailure(call: Call<WorkspaceListLookResponseData>, t: Throwable) {
                Log.d("WorkspaceListLook_edit", "error")
            }
        })
        // 해당 워크스페이스 정보 상세 조회 서버 연동
        RetrofitManager.workspaceService?.workspaceDetail(3)?.enqueue(object:
            Callback<WorkspaceDetailResponseData> {
            override fun onResponse(
                call: Call<WorkspaceDetailResponseData>,
                response: Response<WorkspaceDetailResponseData>
            ) {
                if(response.isSuccessful){
                    Log.d("Network_WorkspaceDetailLook", "success")
                    val data = response.body()
                    Glide.with(this@StoreEditActivity).load(data!!.imageUrl).into(binding.ivStoreEdit)
                    binding.etStoreEditPhonenumber.setText(data!!.phoneNumber)
                    binding.etStoreEditAddress.setText(data!!.address)
                    binding.etStoreEditNumber.setText(data!!.businessNumber)
                    binding.rbMonthly.isChecked = true
                    binding.rbMonthlywage.isChecked = true
                }else{
                    // 이곳은 에러 발생할 경우 실행됨
                    Log.d("Network_WorkspaceDetail", "fail")
                }
            }
            override fun onFailure(call: Call<WorkspaceDetailResponseData>, t: Throwable) {
                Log.d("Network_WorkspaceDetail", "error")
            }
        })

        binding.ivStoreEditCamera.setOnClickListener {
            selectGallery()
        }
        binding.ivStoreEditBack.setOnClickListener {
            finish()
        }

        // 워크스페이스 수정 서버 연동
        binding.btnStoreEditFinish.setOnClickListener {
            Log.d("storeEdit", "시작")
            Log.d("datavalue", "multipart값=> " + imageWideUri)
            imageFile = File(getRealPathFromURI(imageWideUri!!))
            // 서버로 보내기 위해 RequestBody객체로 변환
            val requestFile = RequestBody.create("image/*".toMediaTypeOrNull(), imageFile!!)
            val body =
                MultipartBody.Part.createFormData("imageFile", imageFile!!.name, requestFile)
            // String 값에 "" 없애기
            val sendData = intent.getIntExtra("prevImageUrl", 0)
            val jsonObj = JSONObject()
            jsonObj.put("name", binding.spinnerStore.getSelectedItem().toString())
            jsonObj.put("phoneNumber", binding.etStoreEditPhonenumber.text)
            jsonObj.put("address", binding.etStoreEditAddress.text)
            jsonObj.put("prevImageUrl", sendData)
            val body2 = RequestBody.create("application/json".toMediaTypeOrNull(), jsonObj.toString())
            // 현재 사용자의 정보를 받아올 것을 명시
            // 서버 통신은 I/O 작업이므로 비동기적으로 받아올 Callback 내부 코드는 나중에 데이터를 받아오고 실행
            RetrofitManager.workspaceService?.workspaceEdit(3,body2,body)?.enqueue(object :
                Callback<Void> {
                override fun onResponse(call: Call<Void>, response: Response<Void>) {
                    // 네트워크 통신에 성공한 경우
                    if (response.isSuccessful) {
                        Log.d("Network_StoreEdit", "success")
                        finish()

                    }else { // 이곳은 에러 발생할 경우 실행됨
                        val data1 = response.code()
                        Log.d("status code", data1.toString())
                        val data2 = response.headers()
                        Log.d("header", data2.toString())
                        Log.d("server err", response.errorBody()?.string().toString())
                        Log.d("Network_StoreEdit", "fail")
                    }
                }
                override fun onFailure(call: Call<Void>, t: Throwable) {
                    Log.d("Network_StoreEdit", "error!")
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