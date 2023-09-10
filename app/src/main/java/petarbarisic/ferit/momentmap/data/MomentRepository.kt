package petarbarisic.ferit.momentmap.data

import petarbarisic.ferit.momentmap.model.Moment
import java.time.LocalDate

interface MomentRepository {

    fun save(moment: Moment)
    fun delete(moment: Moment)
    fun getMomentById(id: Long): Moment?
    fun getMomentByDate(date: String): Moment?
    fun getAllMoments(): List<Moment>
    fun getAllMomentsByDate(date: String): List<Moment>
    fun deleteAllMoments()
}