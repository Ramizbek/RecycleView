package ramizbek.aliyev.recycleview

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageView
import android.widget.PopupMenu
import androidx.appcompat.app.AlertDialog
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.item_dialog.view.*
import ramizbek.aliyev.recycleview.adapters.UserAdapter
import ramizbek.aliyev.recycleview.models.User
import ramizbek.aliyev.recycleview.utils.MySharedPreference
import kotlin.random.Random

class MainActivity : AppCompatActivity() {
    lateinit var list: ArrayList<User>
    lateinit var userAdapter: UserAdapter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        MySharedPreference.init(this)
        list = ArrayList()
        list.addAll(MySharedPreference.myList)

        val listImage = arrayListOf<String>(
            "https://storage.kun.uz/source/thumbnails/_medium/8/E1EigpVtrHWycE2p10J-UkfWcYA-n7aI_medium.jpg",
            "https://storage.kun.uz/source/thumbnails/_medium/8/Zf5NUNpKEJXo7Nx_kIwHHwXY_YeE0W3K_medium.jpg",
            "https://storage.kun.uz/source/thumbnails/_medium/8/byhBRK1jNNORqbmQKx_WLIbn0yh6gnsq_medium.jpg"
        )

        btn_save.setOnClickListener {
            val index = Random.nextInt(3)
            val name = edt_name.text.toString().trim()
            val user = User(listImage[index], name)
            list.add(user)
            MySharedPreference.myList = list
            userAdapter.notifyItemInserted(list.size - 1)
        }


        rv.layoutManager = LinearLayoutManager(this)
        userAdapter = UserAdapter(this, object : UserAdapter.RvClick {
            override fun onClick(user: User) {
                val intent = Intent(this@MainActivity, MainActivity2::class.java)
                intent.putExtra("keyUser", user)
                startActivity(intent)
            }

            override fun menuClick(imageView: ImageView, user: User, position: Int) {
                val popupMenu = PopupMenu(this@MainActivity, imageView)
                popupMenu.inflate(R.menu.popup_menu)

                popupMenu.setOnMenuItemClickListener {
                    when (it.itemId) {
                        R.id.menu_delete -> {
                            list.removeAt(position)
                            MySharedPreference.myList = list
                            userAdapter.notifyItemRemoved(position)
                        }
                        R.id.menu_edit -> {
                            val alertDialog = AlertDialog.Builder(this@MainActivity).create()
                            val itemDialog =
                                layoutInflater.inflate(R.layout.item_dialog, null, false)
                            alertDialog.setView(itemDialog)

                            itemDialog.edt_dialog.setText(user.name)
                            itemDialog.dialog_save.setOnClickListener {
                                user.name = itemDialog.edt_dialog.text.toString().trim()
                                list[position] = user
                                MySharedPreference.myList = list
                                alertDialog.cancel()
                                userAdapter.notifyItemChanged(position)

                            }

                            alertDialog.show()
                        }
                    }
                    true
                }

                popupMenu.show()
            }
        }, list)
        rv.adapter = userAdapter

    }
}