@file:OptIn(ExperimentalTime::class)

package io.altenems.companion.android.data

import android.content.Context
import io.altenems.companion.android.common.util.WearDataMessages.DnsLookup.CAPABILITY_DNS_VIA_MOBILE
import io.altenems.companion.android.common.util.WearDataMessages.DnsLookup.encodeDNSResult
import io.altenems.companion.android.fakes.FakeCapabilityClient
import io.altenems.companion.android.fakes.FakeClock
import io.altenems.companion.android.fakes.FakeDns
import io.altenems.companion.android.fakes.FakeMessageClient
import io.mockk.every
import io.mockk.mockk
import java.net.InetAddress
import java.net.UnknownHostException
import kotlin.time.Duration.Companion.seconds
import kotlin.time.ExperimentalTime
import kotlin.time.Instant
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.fail
import org.junit.jupiter.api.Test

class WearDnsTest {
    private val context = mockk<Context> {
        every { applicationContext } returns this
        every { packageManager } returns mockk()
    }
    val messageClient = FakeMessageClient(context)
    val capabilityClient = FakeCapabilityClient(context)
    val clock = FakeClock()
    val dns = FakeDns()
    private val wearDns = WearDns(messageClient, capabilityClient, clock, dns)

    val altenemsLocal: InetAddress = InetAddress.getByAddress("altenems.local", byteArrayOf(192.toByte(), 168.toByte(), 0, 23))
    val altenemsLocal2: InetAddress = InetAddress.getByAddress("altenems.local", byteArrayOf(192.toByte(), 168.toByte(), 0, 24))

    @Test
    fun `Given a hostname when making DNS lookup then returns DNS entry`() {
        // given
        dns.results["altenems.local"] = Result.success(listOf(altenemsLocal))

        // when
        val results = wearDns.lookup("altenems.local")

        // then
        assertEquals(altenemsLocal, results.single())
    }

    @Test
    fun `Given a hostname when making DNS lookup then falls back to mobile dns when present`() {
        // given
        dns.results["altenems.local"] = Result.failure(UnknownHostException())

        capabilityClient.capabilities[CAPABILITY_DNS_VIA_MOBILE] = setOf("1234")
        messageClient.onRequest = { listOf(altenemsLocal).encodeDNSResult() }

        // when
        val results = wearDns.lookup("altenems.local")

        // then
        assertEquals(altenemsLocal, results.single())
    }

    @Test
    fun `Given a hostname when making DNS lookup then fails when mobile not present`() {
        // given
        dns.results["altenems.local"] = Result.failure(UnknownHostException())

        capabilityClient.capabilities[CAPABILITY_DNS_VIA_MOBILE] = setOf()

        try {
            // when
            wearDns.lookup("altenems.local")
            fail { "Lookup should throw" }
        } catch (exception: UnknownHostException) {
            // then
            assertEquals("No Mobile DNS helper registered. Unable to resolve altenems.local", exception.message)
        }
    }

    @Test
    fun `Given a hostname when making DNS lookup then fails when mobile fails`() {
        // given
        dns.results["altenems.local"] = Result.failure(UnknownHostException())

        capabilityClient.capabilities[CAPABILITY_DNS_VIA_MOBILE] = setOf("1234")
        messageClient.onRequest = { byteArrayOf() }

        try {
            // when
            wearDns.lookup("altenems.local")
            fail { "Lookup should throw" }
        } catch (exception: UnknownHostException) {
            // then
            assertEquals("Mobile helper unable to resolve altenems.local", exception.message)
        }
    }

    @Test
    fun `Given a cached hostname when making DNS lookup then returns cached results`() {
        // given
        clock.time = Instant.fromEpochSeconds(1757959158)
        dns.results["altenems.local"] = Result.failure(UnknownHostException())
        capabilityClient.capabilities[CAPABILITY_DNS_VIA_MOBILE] = setOf("1234")
        messageClient.onRequest = { listOf(altenemsLocal).encodeDNSResult() }

        wearDns.lookup("altenems.local")

        messageClient.onRequest = { listOf<InetAddress>().encodeDNSResult() }

        // when
        val results = wearDns.lookup("altenems.local")

        // then
        assertEquals(altenemsLocal, results.single())
    }

    @Test
    fun `Given a stale cached hostname when making DNS lookup then fetches fresh results`() {
        // given
        clock.time = Instant.fromEpochSeconds(1757959158)
        dns.results["altenems.local"] = Result.failure(UnknownHostException())
        capabilityClient.capabilities[CAPABILITY_DNS_VIA_MOBILE] = setOf("1234")
        messageClient.onRequest = { listOf(altenemsLocal).encodeDNSResult() }

        wearDns.lookup("altenems.local")

        messageClient.onRequest = { listOf(altenemsLocal2).encodeDNSResult() }
        clock.time = clock.time + wearDns.cacheLifetime + 1.seconds

        // when
        val results = wearDns.lookup("altenems.local")

        // then
        assertEquals(altenemsLocal2, results.single())
    }

    @Test
    fun `Given an UnknownHostException when making DNS lookup then returns cached failure`() {
        // given
        clock.time = Instant.fromEpochSeconds(1757959158)
        dns.results["altenems.local"] = Result.failure(UnknownHostException())
        capabilityClient.capabilities[CAPABILITY_DNS_VIA_MOBILE] = setOf("1234")
        messageClient.onRequest = { listOf<InetAddress>().encodeDNSResult() }

        try {
            wearDns.lookup("altenems.local")
            fail()
        } catch (e: UnknownHostException) {
            // expected
        }

        messageClient.onRequest = { listOf(altenemsLocal).encodeDNSResult() }

        try {
            // when
            wearDns.lookup("altenems.local")
            fail { "Lookup should throw" }
        } catch (exception: UnknownHostException) {
            // then
            assertEquals("Mobile helper unable to resolve altenems.local", exception.message)
        }
    }
}
