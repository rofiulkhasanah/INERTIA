package com.inertia.data.response

import com.google.gson.annotations.SerializedName

data class SubSektorResponse(
	@field:SerializedName("id")
	val id: String,

	@field:SerializedName("idSektor")
	val idSektor: String,

	@field:SerializedName("nmSubSektor")
	val nmSubSektor: String,
)
