package petarbarisic.ferit.momentmap.ui.moment_list

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import org.threeten.bp.format.DateTimeFormatter
import petarbarisic.ferit.momentmap.R
import petarbarisic.ferit.momentmap.databinding.MomentItemBinding
import petarbarisic.ferit.momentmap.model.Moment

class MomentAdapter(private val  moments3: ArrayList<Moment>) : RecyclerView.Adapter<MomentViewHolder>() {

    val moments = mutableSetOf<Moment>()
    var onMomentSelectedListener: OnMomentSelectedListener? = null
    val formatterWithoutTime = DateTimeFormatter.ofPattern("dd/MM/uuuu")

    fun setMoments(moments2: List<Moment>) {
        moments.clear()
        for(moment:Moment in moments2){
            moments.add(moment)
        }
        notifyDataSetChanged()
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MomentViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.moment_item, parent, false)
        return MomentViewHolder(view)
    }

    override fun onBindViewHolder(holder: MomentViewHolder, position: Int) {
        val moment = moments3.elementAt(position)
        holder.setIsRecyclable(false)
        holder.bind(moment)
        onMomentSelectedListener?.let { listener ->
            holder.itemView.setOnClickListener { listener.onMomentSelected(moment.date ?:"") }
        }
    }

    override fun getItemCount(): Int = moments3.count()
}

class MomentViewHolder(momentView: View) : RecyclerView.ViewHolder(momentView) {

    fun bind(moment: Moment) {
        val binding = MomentItemBinding.bind(itemView)
        binding.itemMomentTitle.text = moment.title
        binding.itemMomentContent.text = moment.content
        binding.itemMomentDate.text= moment.date

    }
}