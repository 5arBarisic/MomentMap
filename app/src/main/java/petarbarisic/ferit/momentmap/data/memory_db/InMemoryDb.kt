package petarbarisic.ferit.momentmap.data.memory_db

import petarbarisic.ferit.momentmap.data.MomentDao
import petarbarisic.ferit.momentmap.model.Moment

class InMemoryDb : MomentDao {

    private val moments = mutableListOf<Moment>()
    private val sameDateMoments = mutableListOf<Moment>()

    override fun save(moment: Moment) {
        moments.add(moment)
    }

    override fun delete(moment: Moment) {
        moments.remove(moment)
    }

    override fun getMomentById(id: Long): Moment? {
        TODO("Not yet implemented")
    }


    override fun getMomentByDate(date: String): Moment? {
        return moments.firstOrNull { it.date == date}
    }

    override fun getAllMoments(): List<Moment> {
        return moments
    }

    override fun getAllMomentsByDate(date: String): List<Moment> {
        for(moment:Moment in moments){
            if(moment.date==date)
                sameDateMoments.add(moment)
        }
        return sameDateMoments
    }

    override fun deleteAllMoments() {
        sameDateMoments.clear()
    }


}