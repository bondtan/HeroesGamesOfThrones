package com.tbondarenko.heroesgamesofthrones.domain

import com.tbondarenko.heroesgamesofthrones.data.remotedata.dto.Hero
import com.tbondarenko.heroesgamesofthrones.domain.model.HeroDomain

interface Mapper {

    fun toDomainModel ( hero: Hero): HeroDomain
}