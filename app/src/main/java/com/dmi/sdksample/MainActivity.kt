package com.dmi.sdksample

import android.icu.text.SimpleDateFormat
import android.os.Build
import android.os.Bundle
import android.text.TextUtils
import android.text.method.HideReturnsTransformationMethod
import android.text.method.PasswordTransformationMethod
import android.view.View
import android.widget.*
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import com.dmi.mykotlinlib.networking.UserInfo
import com.dmi.mykotlinlib.pojo.*
import com.dmi.mykotlinlib.start.ISdkInstance
import com.dmi.mykotlinlib.start.WCSDKConfig
import com.dmi.mykotlinlib.start.WexerContentSDK
import com.dmi.mykotlinlib.start.exposedcallbacks.*
import com.google.android.material.textfield.TextInputEditText
import java.util.*

class MainActivity : AppCompatActivity() {
    lateinit var wexerSdk: ISdkInstance
    var mCheckBox: CheckBox? = null
    var mUsernameEditText: TextInputEditText? = null
    var mPassowrdEditText: TextInputEditText? = null
    var mClasstag: TextInputEditText? = null
    var consentTag: TextInputEditText? = null

    var mClstag: TextInputEditText? = null
    var orderByEt: TextInputEditText? = null
    var filterBy: TextInputEditText? = null

    private fun showProgress() {
        mContentLoadingProgressBar?.visibility = View.VISIBLE
        contentLl?.visibility = View.GONE
    }

    private fun hideProgress() {
        mContentLoadingProgressBar?.visibility = View.GONE
        contentLl?.visibility = View.VISIBLE
    }

    private fun showToastMsg(msg: String) {
        Toast.makeText(this, msg, Toast.LENGTH_LONG).show()
    }

    var contentLl: LinearLayout? = null
    var mContentLoadingProgressBar: ProgressBar? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        mContentLoadingProgressBar = findViewById(R.id.pb)
        contentLl = findViewById(R.id.contentLL)

        setConfig()

        initUi()
    }

    private fun initUi() {
        mCheckBox = findViewById(R.id.showPassCheckbox)
        mUsernameEditText = findViewById(R.id.usernameEt)
        mPassowrdEditText = findViewById(R.id.passwordEt)
        mClasstag = findViewById(R.id.classtagEt)
        consentTag = findViewById(R.id.consentTag)

        mClstag = findViewById(R.id.clstagEt)
        filterBy = findViewById(R.id.filterByEt)
        orderByEt = findViewById(R.id.orderByEt)

        mCheckBox?.setOnCheckedChangeListener(object : CompoundButton.OnCheckedChangeListener {
            override fun onCheckedChanged(buttonView: CompoundButton?, isChecked: Boolean) {
                if (isChecked) {
                    mPassowrdEditText?.setTransformationMethod(HideReturnsTransformationMethod.getInstance())
                } else {
                    mPassowrdEditText?.setTransformationMethod(PasswordTransformationMethod.getInstance())
                }
            }
        })
    }

    private fun setConfig() {
        // init config
        val config = WCSDKConfig(
            applicationContext,
            //"http://77ff37ea.ngrok.io",
            "http://cfltest.cloudapp.net/",
            "1234123412341234",
            "573572796441127398913495789868468206481",
            "wexer"
            //, "f02ae5da88300bb10b07891-5ea60c60-e4c8-11e9-e12c-007c928ca240"
        )

        // now init sdk
        wexerSdk = WexerContentSDK.init(config)

        // enable debug log
        wexerSdk.enableDebugLog(true)

        // set api header language
        wexerSdk.setLanguageForRestApi("en-UK")

        if (wexerSdk != null)
            showToastMsg("SDK init successfully")
    }

    fun setConfig(view: View) {
        setConfig()
    }


    fun getSignupConsent(view: View) {
        showToastMsg("It is under development")
    }


    fun signIn(view: View) {

        /*val subscriptions = arrayListOf<UserSubscription>()
        subscriptions.add(
            UserSubscription(
                "monthly", "4687ew34"
            )
        )
        subscriptions.add(
            UserSubscription(
                "kala kabootar", "4687ew34"
            )
        )

        val user = BodyUser(
            "same6",
            "sam6@123",
            "",
            subscriptions,
            true
        )*/

        var userName: String = mUsernameEditText?.getText().toString().trim()
        var password: String = mPassowrdEditText?.getText().toString().trim()
        if (TextUtils.isEmpty(userName)) {
            showToastMsg("Please enter Username")
            return
        }
        if (TextUtils.isEmpty(password)) {
            showToastMsg("Please enter password")
            return
        }

        val user = UserInfo(
            userName,
            password,
            true
        )

        showProgress()

        wexerSdk.createUser(user, object : UserLoginListener {
            override fun onError(error: SdkError) {
                hideProgress()
                showToastMsg(error.message)
            }

            override fun onSuccess(result: String, user: User?) {
                hideProgress()
                showToastMsg(result)
            }
        })
    }

    fun getClasses(view: View) {
        showProgress()

        var clsTagText: String = mClstag?.getText().toString().trim()
        var filterBy: String = filterBy?.getText().toString().trim()
        var orderBy: String = orderByEt?.getText().toString().trim()

        // tag 50122
        wexerSdk.fetchClassData(clsTagText, filterBy, orderBy, object : ClassDataFetchListener {
            override fun onError(error: SdkError) {
                hideProgress()
                showToastMsg(error.message)
            }

            override fun onSuccess(response: String, tenantCollection: List<ClassSingle>?) {
                hideProgress()
                showToastMsg(response)
            }

        })
    }

    fun searchOndemand(view: View) {
        val url =
            "https://cflondemandcontentprod.blob.core.windows.net/asset-cacc6cb6-52a3-47d4-bec8-7bb6d846b420/WE_01_Yoga_Spirits_OV_wexer_prof_1280x720_3400.mp4?sv=2015-07-08&sr=c&si=93c9f30f-866d-4815-8e32-713fef4ee0b3&sig=jA4Nicrc%2FnvME%2FvjMzcyK%2FQarNOUN%2BEeZ8SoUX7KXto%3D&se=2028-10-19T05%3A45%3A10Z"

        val urlStream =
            "https://0fcb8e4c4b5b4e069c231a71b0ff5f12.azureedge.net/b3db8bcd-224d-4a36-963e-70405b236724/WE_01_Yoga_Spirits_OV_wexer_prof.ism/manifest(format=m3u8-aapl)"
        val urlStream1 =
            "https://0fcb8e4c4b5b4e069c231a71b0ff5f12.azureedge.net/59d2a981-f469-4ff5-9651-3e6cffc12c34/PF_26_Aerobics_OV_1_wexer_profil.ism/manifest(format=m3u8-aapl)"
        val urlStream2 =
            "https://0fcb8e4c4b5b4e069c231a71b0ff5f12.azureedge.net/ddb69f0a-679a-495b-9bb8-0eb83fee9a87/MV_YOG30_GB019_YOGA_BASICS_CULTI.ism/manifest(format=m3u8-aapl)"

        showProgress()

        var mOnDemandSearch = OnDemandSearch()
        mOnDemandSearch.take = 200
        mOnDemandSearch.skip = 0
        //mOnDemandSearch.type = "Cycling,Weight Loss"
        //mOnDemandSearch.provider = "L1FT"
        //mOnDemandSearch.classLanguage = "L1FT"
        //mOnDemandSearch.duration = "1,109"
        //mOnDemandSearch.intensity = "1,10"
        //mOnDemandSearch.keywords = "hi"
        mOnDemandSearch.level = ExerciseLevel.Advanced // beginner intermediate advanced

        wexerSdk.fetchOnDemandSearch(mOnDemandSearch, object : OnDemandMetadataSearchListener {
            override fun onError(error: SdkError) {
                hideProgress()
                showToastMsg(error.message)
            }

            override fun onSuccess(response: String, tenantCollection: OnDemandSearchResult?) {
                hideProgress()
                showToastMsg(response)
            }

        })
    }

    fun cancelSubscription(view: View) {
        //showToastMsg("Coming soon...")
        //convertDate()
        showProgress()
        wexerSdk.cancelSubscription(object : SubscriptionCanceledListener {
            override fun onError(result: SdkError) {
                hideProgress()
                showToastMsg(result.message)
            }

            override fun onSuccess(response: String) {
                hideProgress()
                showToastMsg(response)
            }
        })
    }

    fun activateSubscription(view: View) {
        //showToastMsg("Coming soon...")
        showProgress()

        val userSubscription = UserSubscription("2019-01-02T06:20:49.000", "monthly")

        wexerSdk.activateSubscription(userSubscription, object : SubscriptionActivatedListener {
            override fun onError(error: SdkError) {
                hideProgress()
                showToastMsg(error.message)
            }

            override fun onSuccess(response: String) {
                hideProgress()
                showToastMsg(response)
            }
        })
    }

    @RequiresApi(Build.VERSION_CODES.N)
    fun convertDate() {

        val strDate = "02-1-2018 06:07:59"

        var simpleDate = SimpleDateFormat("dd-M-yyyy hh:mm:ss")

        var date = Date()

        date = simpleDate.parse(strDate)

        var newSimpleDate = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS")

        var ab = newSimpleDate.format(date)

        println("============ converted date : $ab")
    }

    fun ondemandPerform(view: View) {
        var classTag: String = mClasstag?.getText().toString().trim()
        if (TextUtils.isEmpty(classTag)) {
            showToastMsg("Please enter class id")
            return
        }
        showProgress()
        wexerSdk.fetchPerform(classTag, object : OnDemandPerformListener {
            override fun onError(error: SdkError) {
                showToastMsg("${error.message}")
                hideProgress()
            }

            override fun onSuccess(response: String, tenantCollection: List<ClassSingle>?) {
                //showToastMsg("${response}")
                hideProgress()
            }

            override fun playerExit(duration: Long?) {
                println("playerExit duration : $duration")
            }

            override fun playerStatus(status: Int) {
                println("playerStatus status : $status")
            }

        })
    }

    fun getTenantCollection(view: View) {
        showProgress()
        wexerSdk.fetchTenantCollection(object : TenantCollectionListener {
            override fun onError(error: SdkError) {
                hideProgress()
                showToastMsg(error.message)
            }

            override fun onSuccess(response: String, tenantCollection: List<TenantCollection>?) {
                hideProgress()
                showToastMsg(response)
            }
        })
    }

    fun getMetadata(view: View) {
        showProgress()
        wexerSdk.fetchOnDemandMetadata(object : OnDemandMetadataListener {
            override fun onError(error: SdkError) {
                hideProgress()
                showToastMsg(error.message)
            }

            override fun onSuccess(
                response: String,
                tenantCollection: ServerOnDemandMetadataResponse?
            ) {
                hideProgress()
                showToastMsg(response)
            }
        })
    }

    fun acceptConsent(view: View) {
        var consent: String = consentTag?.getText().toString().trim()
        if (TextUtils.isEmpty(consent)) {
            showToastMsg("Please enter Consent Tag")
            return
        }

        showProgress()
        //val consentTag = "13b9710a-b2a6-4620-87a8-8c9946e1b50a"
        wexerSdk.acceptConsent(consent, object : AcceptConsentListener {
            override fun onError(error: SdkError) {
                hideProgress()
                showToastMsg(error.message)
            }

            override fun onSuccess(response: String) {
                hideProgress()
                showToastMsg(response)
            }
        })
    }
}
