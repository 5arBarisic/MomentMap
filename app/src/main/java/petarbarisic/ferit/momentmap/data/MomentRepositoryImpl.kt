package petarbarisic.ferit.momentmap.data

import petarbarisic.ferit.momentmap.model.Moment

class MomentRepositoryImpl (val momentDao: MomentDao) : MomentRepository{
    override fun save(moment: Moment) = momentDao.save(moment)
    override fun delete(task: Moment) = momentDao.delete(task)
    override fun getMomentById(id: Long): Moment? = momentDao.getMomentById(id)
    override fun getMomentByDate(date: String): Moment? = momentDao.getMomentByDate(date)

    override fun getAllMoments(): List<Moment> = momentDao.getAllMoments()
    override fun getAllMomentsByDate(date: String): List<Moment> =momentDao.getAllMomentsByDate(date)
    override fun deleteAllMoments() {
        momentDao.deleteAllMoments()
    }
}