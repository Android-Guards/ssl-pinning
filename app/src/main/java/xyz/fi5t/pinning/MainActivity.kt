package xyz.fi5t.pinning

import android.os.Bundle
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.redmadrobot.itemsadapter.itemsAdapter
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val userList = findViewById<RecyclerView>(R.id.users)

        CoroutineScope(Dispatchers.IO).launch {
            val users = try {
                (application as App).api.getUsers()
            } catch (e: Exception) {
                e.printStackTrace()
                emptyList()
            }

            withContext(Dispatchers.Main) {
                val linearLayoutManger = LinearLayoutManager(this@MainActivity)
                val divider = DividerItemDecoration(userList.context, linearLayoutManger.orientation)
                val itemsAdapter = itemsAdapter(users) { item ->
                    bind(R.layout.item_user) {
                        val userId = this.findViewById<TextView>(R.id.user_id)
                        val userLogin = this.findViewById<TextView>(R.id.user_login)
                        val userUrl = this.findViewById<TextView>(R.id.user_url)

                        userId.text = "Id: ${item.id}"
                        userLogin.text = "Login: ${item.login}"
                        userUrl.text = "Url: ${item.url}"
                    }
                }

                with(userList) {
                    layoutManager = linearLayoutManger
                    adapter = itemsAdapter
                    addItemDecoration(divider)
                }
            }
        }
    }
}
