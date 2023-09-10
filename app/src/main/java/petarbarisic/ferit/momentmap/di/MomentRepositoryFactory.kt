package petarbarisic.ferit.momentmap.di

import petarbarisic.ferit.momentmap.data.MomentDao
import petarbarisic.ferit.momentmap.data.MomentRepository
import petarbarisic.ferit.momentmap.data.MomentRepositoryImpl
import petarbarisic.ferit.momentmap.data.memory_db.InMemoryDb

object MomentRepositoryFactory {
    private val momentDao: MomentDao = InMemoryDb()
    val momentRepository: MomentRepository = MomentRepositoryImpl(momentDao)
}