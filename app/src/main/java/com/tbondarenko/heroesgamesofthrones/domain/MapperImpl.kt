package com.tbondarenko.heroesgamesofthrones.domain

import com.tbondarenko.heroesgamesofthrones.data.remotedata.dto.Hero
import com.tbondarenko.heroesgamesofthrones.domain.model.HeroDomain

object MapperImpl: Mapper {

    override fun toDomainModel(hero: Hero): HeroDomain {
        return HeroDomain(
            id = hero.id,
            firstName = hero.firstName,
            lastName = hero.lastName,
            fullName = hero.fullName,
            title = hero.title,
            family = hero.family,
            imageUrl = hero.imageUrl
        )
    }
}