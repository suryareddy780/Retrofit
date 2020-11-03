package edmt.dev.myapplicationretrofit

import android.os.Bundle
import android.os.CountDownTimer
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import edmt.dev.myapplicationretrofit.adapters.RecyclerAdapter
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

const val BASE_URL = "https://newsapi.org"

class MainActivity : AppCompatActivity() {

    lateinit var countDownTimer : CountDownTimer

    private var titlesList = mutableListOf<String>()
    private var descList = mutableListOf<String>()
    private var imagesList = mutableListOf<String>()
    private var linksList = mutableListOf<String>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        makeAPIRequest()
    }

    private fun setUpRecyclerView()
    {
        var rv  : RecyclerView = findViewById(R.id.rv_recyclerView)
        rv.layoutManager = LinearLayoutManager(applicationContext)
        rv.adapter = RecyclerAdapter(titlesList,descList,imagesList,linksList)

    }

    private fun addToList(title : String, description : String, image : String, link : String)
    {
titlesList.add(title)
        descList.add(description)
        imagesList.add(image)
        linksList.add(link)
    }

    private fun makeAPIRequest() {
        val api = Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(APIRequest::class.java)

        GlobalScope.launch(Dispatchers.IO) {
            try {
             val response = api.getNews()

                for (article in response.articles)
                {
                    Log.i("MainActivity","Result = $article")
                    addToList(article.title,article.description,article.urlToImage,article.url)
                }
                withContext(Dispatchers.Main){
                    setUpRecyclerView()
                }
            } catch (e : Exception)
            {
                Log.e("MainActivity",e.toString())
            }
        }
    }
}