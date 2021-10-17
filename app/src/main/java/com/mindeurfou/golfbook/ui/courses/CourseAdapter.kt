package com.mindeurfou.golfbook.ui.courses

import android.view.View
import android.widget.TextView
import com.mindeurfou.golfbook.R
import com.mindeurfou.golfbook.data.course.local.Course
import com.mindeurfou.golfbook.ui.common.BaseAdapter
import com.mindeurfou.golfbook.ui.common.BaseViewHolder

class CourseViewHolder(itemView: View) : BaseViewHolder<Course>(itemView) {

    private val courseName: TextView = itemView.findViewById(R.id.courseName)
    private val courseNumberHole: TextView = itemView.findViewById(R.id.courseHoleNumber)

    override fun bind(item: Course, position: Int, onClick: (Course) -> Unit) {
        courseName.text = item.name
        courseNumberHole.text = itemView.resources.getString(R.string.numberOfHoles, item.numberOfHOles)
        itemView.setOnClickListener { onClick(item) }
    }
}
class CourseAdapter(
    courses: List<Course>,
    onClick: (course: Course) -> Unit
) : BaseAdapter<Course>(
    list = courses,
    layoutItems = listOf(R.layout.item_course_main, R.layout.item_course_first, R.layout.item_course_second, R.layout.item_course_third, R.layout.item_course_fourth),
    onClick
) {
    override fun additionalLayout(view: View) {}

    override fun getViewHolder(itemView: View): BaseViewHolder<Course> =
        CourseViewHolder(itemView)

}