package com.mindeurfou.golfbook.ui.courses

import android.view.View
import android.widget.ImageView
import android.widget.TextView
import com.mindeurfou.golfbook.R
import com.mindeurfou.golfbook.data.course.local.Course
import com.mindeurfou.golfbook.ui.common.BaseAdapter
import com.mindeurfou.golfbook.ui.common.BaseViewHolder

class CourseViewHolder(itemView: View) : BaseViewHolder<Course>(itemView) {

    private val courseName: TextView = itemView.findViewById(R.id.courseName)
    private val courseNumberHole: TextView = itemView.findViewById(R.id.courseHoleNumber)
    private val starBall1: ImageView = itemView.findViewById(R.id.starBall1)
    private val starBall2: ImageView = itemView.findViewById(R.id.starBall2)
    private val starBall3: ImageView = itemView.findViewById(R.id.starBall3)
    private val starBall4: ImageView = itemView.findViewById(R.id.starBall4)
    private val starBall5: ImageView = itemView.findViewById(R.id.starBall5)

    override fun bind(item: Course, position: Int, onClick: (Course) -> Unit) {
        courseName.text = item.name
        courseNumberHole.text = itemView.resources.getString(R.string.numberOfHoles, item.numberOfHOles)
        bindStars(item.stars)
        itemView.setOnClickListener { onClick(item) }
    }

    private fun bindStars(stars: Int) {
        for (i in 1..stars) {
            when (i) {
                1 -> starBall1.setImageResource(R.drawable.ic_ball_full)
                2 -> starBall2.setImageResource(R.drawable.ic_ball_full)
                3 -> starBall3.setImageResource(R.drawable.ic_ball_full)
                4 -> starBall4.setImageResource(R.drawable.ic_ball_full)
                5 -> starBall5.setImageResource(R.drawable.ic_ball_full)
            }
        }
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