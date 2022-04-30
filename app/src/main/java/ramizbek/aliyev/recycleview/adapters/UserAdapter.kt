package ramizbek.aliyev.recycleview.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.item_rv.view.*
import ramizbek.aliyev.recycleview.R
import ramizbek.aliyev.recycleview.models.User

class UserAdapter(var context: Context, val rvClick: RvClick, var list: List<User>) :
    RecyclerView.Adapter<UserAdapter.Vh>() {

    inner class Vh(var itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun onBind(user: User, position: Int) {

            val animation = AnimationUtils.loadAnimation(context, R.anim.rv_anim)
            itemView.startAnimation(animation)



            itemView.tv_item.text = user.name
            Picasso.get().load(user.imageLink).into(itemView.item_image)


            itemView.setOnClickListener {
                rvClick.onClick(user)
            }
            itemView.image_menu.setOnClickListener {
                rvClick.menuClick(itemView.image_menu, user,  position)
            }

        }
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Vh {
        return Vh(LayoutInflater.from(parent.context).inflate(R.layout.item_rv, parent, false))
    }

    override fun onBindViewHolder(holder: Vh, position: Int) {
        holder.onBind(list[position], position)


    }

    override fun getItemCount(): Int = list.size

    interface RvClick {
        fun onClick(user: User)
        fun menuClick(imageView: ImageView, user: User, position: Int)
    }
}