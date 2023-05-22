package app.reminders.presentation.features.todo

import android.view.ViewGroup
import app.common.base.BindingVH
import app.common.base.ImmutableBindingAdapter
import app.common.extension.parseServerDate
import app.common.extension.parseServerDateToDateTime
import app.common.extension.setOnSingleClickListener
import app.reminders.domain.mapper.ReminderDomain
import com.aashutosh.reminders.databinding.ItemToDoBinding
import kotlin.properties.Delegates

class TodoListAdapter(private val onClick: (item: ReminderDomain) -> Unit) :
    ImmutableBindingAdapter<ReminderDomain, ItemToDoBinding>() {
    override var items: List<ReminderDomain> by Delegates.observable(emptyList()) { _, old, new ->
        autoNotify(old, new) { o, n -> o == n }
    }

    override fun getVB(parent: ViewGroup): ItemToDoBinding {
        return ItemToDoBinding.inflate(parent.inflater(), parent, false)
    }

    override fun onBindViewHolder(holder: BindingVH<ItemToDoBinding>, position: Int) {
        val item = items[position]
        with(holder.binding) {
            tvTitle.text = item.title
            tvDueDate.text = parseServerDate(item.dueDate)
            root.setOnSingleClickListener {
                onClick.invoke(item)
            }
        }
    }
}