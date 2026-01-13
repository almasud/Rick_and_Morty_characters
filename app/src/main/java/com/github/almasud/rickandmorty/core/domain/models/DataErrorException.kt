package com.github.almasud.rickandmorty.core.domain.models

class DataErrorException(val dataError: DataError) : Exception(dataError.toString())
