package com.example.tendermanagementsystem

object TenantRepository {

    // Shared list of tenants held in memory.
    val tenants = mutableListOf<Tenant>()

    // Add a tenant to the shared list.
    fun addTenant(tenant: Tenant) {
        tenants.add(tenant)
    }

    // Calculate the sum of all tenant rents.
    fun totalRent(): Double {
        return tenants.sumOf {
            it.rent.toDoubleOrNull() ?: 0.0
        }
    }
}