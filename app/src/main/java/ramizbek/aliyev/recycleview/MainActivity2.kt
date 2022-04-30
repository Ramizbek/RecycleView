package ramizbek.aliyev.recycleview

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.activity_main2.*
import ramizbek.aliyev.recycleview.models.User

class MainActivity2 : AppCompatActivity() {
    lateinit var user: User
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main2)

        user = intent.getSerializableExtra("keyUser") as User
        Picasso.get().load(user.imageLink).into(image_info)
        tv_info.text = user.name

    }
}